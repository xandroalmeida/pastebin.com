package com.pastebin.backend.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@Entity
@Table(name = "post")
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties({"storeFileName"})

public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "belongs_to")
	@NotNull
	@JsonIgnoreProperties({"name", "email", "username", "password"})
	private User belongsTo;

	@Column(name = "sent_in")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date sentIn;

	@Column(name = "original_file_name")
	@NotEmpty
	@Size(min = 1, max = 255)
	private String originalFileName;

	@Column(name = "store_file_name")
	@NotNull
	private String storeFileName;

	@Column(name = "public_access")
	private boolean publicAccess;

}
