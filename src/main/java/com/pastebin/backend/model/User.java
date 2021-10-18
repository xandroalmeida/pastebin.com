package com.pastebin.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "auth_user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name")
	@NotBlank
	@Size(min = 2, max = 100)
	private String name;
	
	@Column(name = "pwd_hash")
	@NotNull
	private String pwdHash;
	
	@Column(name = "email")
	@NotBlank
	@Email
	private String email;
}
