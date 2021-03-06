package com.pastebin.backend.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.pastebin.backend.model.User;
import com.pastebin.backend.service.UserService;

/**
 * Este componente (Bean) para obter o usuário logado.
 * 
 *
 */
@Component
@RequestScope
public class CurrentUser {
	
	@Autowired
	private UserService userService;
	
	/**
	 * Caso a requisição venha de um usuário logado retorna este usuário,
	 * caso contrário retorna vazio.
	 *
	 */
	public Optional<User> get() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return userService.findByEmail(authentication.getName());
	}
}
