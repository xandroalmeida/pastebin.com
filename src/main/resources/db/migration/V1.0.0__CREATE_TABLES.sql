CREATE TABLE auth_user
(
	id 			IDENTITY 		NOT NULL,
	name		VARCHAR(100) 	NOT NULL,
	pwd_hash 	VARCHAR(100)	NOT NULL,
	email		VARCHAR(100) 	NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE post
(
	id 				IDENTITY 		NOT NULL,
	belongs_to		LONG 			NOT NULL,
	sent_in			TIMESTAMP 		NOT NULL,
	file_name		VARCHAR(255) 	NOT NULL,
	file_content	TEXT 			NOT NULL,
	public_access	BOOLEAN			NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE post
	ADD FOREIGN KEY (belongs_to) 
    REFERENCES auth_user(id);