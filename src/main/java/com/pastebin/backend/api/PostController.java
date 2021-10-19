package com.pastebin.backend.api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pastebin.backend.model.Post;
import com.pastebin.backend.security.CurrentUser;
import com.pastebin.backend.service.PostService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping({"/posts"})
@Slf4j
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CurrentUser currentUser;
	
	@PostMapping
	public ResponseEntity<Post> uploadPost(
			@RequestParam("postFile") MultipartFile postFile, 
			@RequestParam("publicAccess") Boolean publicAccess) {
		
		String originalFilename = postFile.getOriginalFilename();
		try (var inStream = postFile.getInputStream()) {
			return ResponseEntity.of(postService.create(originalFilename, inStream, publicAccess, currentUser.get().get()));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Post>> list() {
		//Se o usuário esta logado lista todos os posts publicos e os pertecentes ao usuário logado
		return ResponseEntity.ok(currentUser.get()
			.map(user -> postService.findAllByUser(user))
			.orElse(postService.findAllPublics())); //Se não, pela apenas os posts publicos		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Resource> read(@PathVariable("id") Long id) {
		return currentUser.get()
			.map(user -> postService.findAllByIdAndUser(id, user))
			.orElse(postService.findByIdPublics(id))
			.map(post -> postService.getResource(post))
			.map(resource -> ResponseEntity.ok(resource))
			.orElse(ResponseEntity.notFound().build());
	}
}
