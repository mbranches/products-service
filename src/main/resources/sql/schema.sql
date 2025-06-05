CREATE TABLE IF NOT EXISTS role (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    description varchar(50)
);

CREATE TABLE IF NOT EXISTS user (
    id varchar(36) PRIMARY KEY,
    login varchar(50) NOT NULL UNIQUE,
    password varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_role (
     id bigint PRIMARY KEY AUTO_INCREMENT,
     user_id varchar(36) NOT NULL,
     role_id bigint NOT NULL,
     FOREIGN KEY (user_id) REFERENCES user(id),
     FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE IF NOT EXISTS product (
   id bigint PRIMARY KEY AUTO_INCREMENT,
   name varchar(50) NOT NULL,
   unit_price decimal(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS sale (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    user_id varchar(36) NOT NULL,
    total_value decimal(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS sale_product (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    sale_id bigint NOT NULL,
    product_id bigint NOT NULL,
    quantity int NOT NULL,
    total_value decimal(10, 2),
    FOREIGN KEY (sale_id) REFERENCES sale(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

INSERT IGNORE INTO role(id, name, description) VALUES (1, 'ADMIN', 'has all system accesses');
INSERT IGNORE INTO role(id, name, description) VALUES (2, 'BASIC', 'has limited system accesses');
INSERT IGNORE INTO role(id, name, description) VALUES (3, 'MANAGER', 'has limited access to administrative and management functionalities');