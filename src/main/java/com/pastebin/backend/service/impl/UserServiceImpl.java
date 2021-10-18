package com.pastebin.backend.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pastebin.backend.model.User;
import com.pastebin.backend.repository.UserRepository;
import com.pastebin.backend.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	@Autowired
	private UserRepository repository;

	@Override
	public User create(User user) {
		return repository.save(user);	
	}

	@Override
	public Optional<User> read(Long id) {
		return repository.findById(id);
	}

	@Override
	public User update(User user) {
		return repository.save(user);
		
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);	
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(""));
	}

}
