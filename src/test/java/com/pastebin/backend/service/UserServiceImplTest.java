package com.pastebin.backend.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.pastebin.backend.model.User;
import com.pastebin.backend.repository.UserRepository;
import com.pastebin.backend.service.impl.UserServiceImpl;

/**
 * Testes Unitários para a implementação do serviço de usuários
 * 
 *
 */
class UserServiceImplTest {

	private UserRepository buildUserRepository(User dbUser) {
		var repository = Mockito.mock(UserRepository.class);
		Mockito.when(repository.save(Mockito.any())).then(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				return (User) invocation.getArguments()[0];
			}
		});
		Mockito.when(repository.findById(1l)).thenReturn(Optional.ofNullable(dbUser));
		return repository;
	}
	
	private PasswordEncoder buildPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	private UserServiceImpl buildUserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
		UserServiceImpl service = new UserServiceImpl();
		ReflectionTestUtils.setField(service, UserServiceImpl.class, "repository", repository, UserRepository.class);
		ReflectionTestUtils.setField(service, UserServiceImpl.class, "passwordEncoder", passwordEncoder,
				PasswordEncoder.class);
		return service;
	}

	/**
	 * Testa se o serviço esta cryptografando a senha antes de salvar no banco de dados
	 * 
	 */
	@Test
	public void testSave_CryptPassword() {
		var repository = buildUserRepository(null);
		var passwordEncoder = buildPasswordEncoder();
		var service = buildUserServiceImpl(repository, passwordEncoder);

		var password = "123456";
		User user = new User();
		user.setPassword(password);
		var createdUser = service.create(user);

		// Verifica se a senha foi criptografada
		assertTrue(passwordEncoder.matches(password, createdUser.getPassword()));
		
		//Verifica se foi persistido
		Mockito.verify(repository, Mockito.times(1)).save(user);
	}
	
	/**
	 * No caso de atualização, caso senha informada uma senha a senha informada deve ser criptografada
	 * e salva no banco
	 * 
	 */
	@Test
	public void testUpdate_updatePassword() {
		var password = "123456";
		var passwordEncoder = buildPasswordEncoder();

		var oldUser = new User();
		oldUser.setPassword(passwordEncoder.encode(password));
		var repository = buildUserRepository(oldUser);
		var service = buildUserServiceImpl(repository, passwordEncoder);

		User user = new User();
		user.setPassword("654321");
		var updatedUser = service.update(user);
		
		//Verifica se foi persistido
		Mockito.verify(repository, Mockito.times(1)).save(user);
		
		// Verifica se a senha foi atualizada
		assertTrue(passwordEncoder.matches("654321", updatedUser.getPassword()));	
	}
	
	
	/**
	 * No caso de uma atualização, caso não seja informada uma nova senha, a senha atual do 
	 * usuário deve ser mantida.
	 * 
	 */
	@Test
	public void testUpdate_noUpdatePassword() {
		var password = "123456";
		var passwordEncoder = buildPasswordEncoder();

		var oldUser = new User();
		oldUser.setPassword(passwordEncoder.encode(password));
		var repository = buildUserRepository(oldUser);
		var service = buildUserServiceImpl(repository, passwordEncoder);

		assertTrue(passwordEncoder.matches(password, oldUser.getPassword()));
		
		User user = new User();
		user.setId(1l);
		var updatedUser = service.update(user);
		
		//Verifica se foi persistido
		Mockito.verify(repository, Mockito.times(1)).save(user);
		
		// Verifica se a senha foi atualizada
		assertTrue(passwordEncoder.matches(password, updatedUser.getPassword()));	
	}
}
