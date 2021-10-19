package com.pastebin.backend.service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;

import com.pastebin.backend.model.Post;
import com.pastebin.backend.model.User;

/**
 * Interface da classe de serviço responsável pela lógica de negócio envolvendo os Posts
 * @author alexandro
 *
 */
public interface PostService {

	Optional<Post> create(String originalFilename, InputStream inStream, Boolean publicAccess, User currentUser);
	Optional<Post> read(Long id);
	Resource getResource(Post post);
	
	List<Post> findAllPublics();
	List<Post> findAllByUser(User user);
	Optional<Post> findByIdPublics(Long id);
	Optional<Post> findAllByIdAndUser(Long id, User user);

}
