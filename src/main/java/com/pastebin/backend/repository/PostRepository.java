package com.pastebin.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pastebin.backend.model.Post;
import com.pastebin.backend.model.User;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	@Query(value="SELECT p FROM Post p WHERE p.publicAccess = TRUE")
	List<Post> findAllPublics();
	
	@Query(value="SELECT p FROM Post p WHERE p.publicAccess = TRUE OR p.belongsTo = ?1")
	List<Post> findAllByUser(User user);
	
	@Query(value="SELECT p FROM Post p WHERE p.id = ?1 AND p.publicAccess = TRUE")
	Optional<Post> findByIdPublics(Long id);
	
	@Query(value="SELECT p FROM Post p WHERE p.id = ?1 AND (p.publicAccess = TRUE OR p.belongsTo = ?2)")
	Optional<Post> findAllByIdAndUser(Long id, User user);

}
