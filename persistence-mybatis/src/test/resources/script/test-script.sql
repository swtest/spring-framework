DROP TABLE customer IF EXISTS;
CREATE TABLE customer (
	cust_id 	NUMERIC 		PRIMARY KEY,
	cust_nm 	VARCHAR(200) 	NOT NULL,
	cust_email 	VARCHAR(200)
);