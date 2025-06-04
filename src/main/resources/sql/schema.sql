CREATE TABLE IF NOT EXISTS product (
   id bigint PRIMARY KEY AUTO_INCREMENT,
   name varchar(50) NOT NULL,
   unit_price decimal(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS sale (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    quantity int NOT NULL,
    total_value decimal(10, 2) NOT NULL,
    product_id bigint NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id)
);

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
    user_id VARCHAR(36) NOT NULL,
    role_id bigint NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

INSERT IGNORE INTO role(id, name, description) VALUES (1, 'admin', 'has all system accesses');
INSERT IGNORE INTO role(id, name, description) VALUES (2, 'basic', 'has limited system accesses');