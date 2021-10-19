CREATE TABLE auth_user
(
	id 			IDENTITY 		NOT NULL,
	name		VARCHAR(100) 	NOT NULL,
	pwd_hash 	VARCHAR(100)	NOT NULL,
	email		VARCHAR(100) 	NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE auth_user 
	ADD CONSTRAINT email_unique 
	UNIQUE(email);


CREATE TABLE post
(
	id 					IDENTITY 		NOT NULL,
	belongs_to			LONG 			NOT NULL,
	sent_in				TIMESTAMP 		NOT NULL,
	original_file_name	VARCHAR(255) 	NOT NULL,
	store_file_name		VARCHAR(255) 	NOT NULL,
	public_access		BOOLEAN			NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE post
	ADD FOREIGN KEY (belongs_to) 
    REFERENCES auth_user(id);
    
ALTER TABLE post 
	ADD CONSTRAINT store_file_name_unique 
	UNIQUE(store_file_name);