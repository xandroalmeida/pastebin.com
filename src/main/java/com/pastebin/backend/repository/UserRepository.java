package com.pastebin.backend.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.pastebin.backend.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByEmail(String email);

}
