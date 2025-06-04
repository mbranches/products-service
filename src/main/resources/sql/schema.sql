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