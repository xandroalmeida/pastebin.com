package com.pastebin.backend.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.pastebin.backend.model.Post;
import com.pastebin.backend.model.User;
import com.pastebin.backend.repository.PostRepository;
import com.pastebin.backend.service.PostService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

	@Value("${application.filestore.dir}")
	private String filestoreDir;

	@Autowired
	private PostRepository postRepository;

	@Override
	public Optional<Post> create(String originalFilename, InputStream inStream, Boolean publicAccess,
			User currentUser) {
		var post = new Post();
		post.setOriginalFileName(originalFilename);
		post.setPublicAccess(publicAccess);
		post.setSentIn(new Date());
		post.setStoreFileName(UUID.randomUUID().toString());
		post.setBelongsTo(currentUser);
		
		try {
			Files.createDirectories(Paths.get(filestoreDir));
		} catch (IOException e1) {
			log.error(e1.getMessage(), e1);
			return Optional.empty();
		}

		try (var outStream = new FileOutputStream(new File(filestoreDir, post.getStoreFileName()))) {
			FileCopyUtils.copy(inStream, outStream);
			post = postRepository.save(post);
			var user = new User();
			user.setId(post.getBelongsTo().getId());
			post.setBelongsTo(user);
			return Optional.of(post);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return Optional.empty();
		}
	}
	
	@Override
	public Optional<Post> read(Long id) {
		return postRepository.findById(id);
	}

	@Override
	public Resource getResource(Post post) {
		try {
			var path = Paths.get(filestoreDir, post.getStoreFileName());
			return new ByteArrayResource(Files.readAllBytes(path));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public List<Post> findAllPublics() {
		return postRepository.findAllPublics();
	}

	@Override
	public List<Post> findAllByUser(User user) {
		return postRepository.findAllByUser(user);
	}

	@Override
	public Optional<Post> findByIdPublics(Long id) {
		return postRepository.findByIdPublics(id);
	}

	@Override
	public Optional<Post> findAllByIdAndUser(Long id, User user) {
		return postRepository.findAllByIdAndUser(id, user);
	}
}
