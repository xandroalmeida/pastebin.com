package com.pastebin.backend.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastebin.backend.model.User;
import com.pastebin.backend.service.UserService;

@RestController
@RequestMapping({"/users"})
public class UserController {
	
	@Autowired
	private UserService service;

	@PostMapping
	public ResponseEntity<User> create(@Valid  @RequestBody User user) {
		return ResponseEntity.ok(service.create(user));
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> read(@PathVariable Long id) {
		return ResponseEntity.of(service.read(id));
		
	}
}
