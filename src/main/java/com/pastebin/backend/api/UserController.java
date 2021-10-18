package com.pastebin.backend.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastebin.backend.model.User;
import com.pastebin.backend.security.CurrentUser;
import com.pastebin.backend.service.UserService;

@RestController
@RequestMapping({"/users"})
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private CurrentUser currentUser;

	@PostMapping
	public ResponseEntity<User> create(@Valid  @RequestBody User user) {
		return ResponseEntity.ok(service.create(user));
		
	}
	
	@GetMapping("/me")
	public ResponseEntity<User> read() {
		var me = currentUser.get().map(user -> 
			service.findByEmail(user.getEmail()).orElse(null)
		);
		return ResponseEntity.of(me);
	}
	
	@PutMapping("/me")
	public ResponseEntity<User> update(@Valid  @RequestBody User user) {
		var me = currentUser.get().map(u -> {
			user.setId(u.getId());
			return service.update(user);
		});
		return ResponseEntity.of(me);
	}
}
