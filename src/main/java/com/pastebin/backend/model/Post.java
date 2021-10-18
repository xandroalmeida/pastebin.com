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

import lombok.Data;

@Data
@Entity
@Table(name = "post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "belongs_to")
	@NotNull
	private User belongsTo;

	@Column(name = "sent_in")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date sentIn;

	@Column(name = "file_name")
	@NotEmpty
	@Size(min = 1, max = 255)
	private String fileName;

	@Column(name = "file_content")
	@NotNull
	private String fileContent;

	@Column(name = "public_access")
	private boolean publicAccess;

}
