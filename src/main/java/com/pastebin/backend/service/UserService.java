package com.pastebin.backend.service;

import java.util.List;
import java.util.Optional;

import com.pastebin.backend.model.User;

/**
 * Interface para a camada de lógica de negócio para o modelo User
 * 
 */
public interface UserService {
	User create(User user);
	Optional<User> read(Long id);
	User update(User user);
	void delete(Long id);
	List<User> findAll();
}
