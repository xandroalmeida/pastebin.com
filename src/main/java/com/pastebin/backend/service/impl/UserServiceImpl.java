package com.pastebin.backend.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pastebin.backend.model.User;
import com.pastebin.backend.repository.UserRepository;
import com.pastebin.backend.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;

	@Override
	public User create(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return securityCleanup(repository.save(user));	
	}

	@Override
	public Optional<User> read(Long id) {
		return repository.findById(id).map(u -> securityCleanup(u));
	}

	@Override
	public User update(User user) {
		//Se não vem com uma nova senha, então tem que pegar a anterior no banco		
		if (!StringUtils.hasText(user.getPassword())) {
			repository.findById(user.getId()).map(udb -> {
				user.setPassword(udb.getPassword());
				return user;
			});
		} else { // Se tem uma senha nova, faz o hash da nova senha
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		
		return securityCleanup(repository.save(user));
		
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);	
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		var user = repository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(""));
		return user;
	}
	
	/**
	 * Nem o hash vamos mandar para a API, seguro morreu de velho.
	 * 
	 * @param user
	 * @return
	 */
	private User securityCleanup(User user) {
		user.setPassword("####");
		return user;
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return repository.findByEmail(email);
	}
}
