CREATE DATABASE spa_management;

USE spa_management;

-- CREATE TABLE customer_type (
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     name VARCHAR(45) NOT NULL
-- );

-- CREATE TABLE customers (
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     name VARCHAR(255) NOT NULL,
--     date_of_birth DATE,
--     gender BIT(1),
--     phone_number VARCHAR(20) NOT NULL,
--     email VARCHAR(255),
--     address VARCHAR(255),
--     customer_type_id int,
--     FOREIGN KEY (customer_type_id) REFERENCES customer_type(id)
-- );

-- CREATE TABLE services (
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     name VARCHAR(255) NOT NULL,
--     price DECIMAL(10,2) NOT NULL
-- );

-- CREATE TABLE positions (
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     name VARCHAR(255) NOT NULL
-- );

CREATE TABLE account (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE role (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE account_role (
    user_id INT,
    role_id INT,
    FOREIGN KEY (user_id) REFERENCES account(id),
    FOREIGN KEY (role_id) REFERENCES role(id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE person (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date_of_birth DATE,
    gender BIT(1),
    id_card VARCHAR(20) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    email VARCHAR(255),
    -- salary DOUBLE,
    -- positions_id INT,
    user_id INT,
    -- FOREIGN KEY (positions_id) REFERENCES positions(id),
    FOREIGN KEY (user_id) REFERENCES account(id)
);

-- CREATE TABLE appointments (
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     customer_id INT,
--     service_id INT,
--     employee_id INT,
--     start_time DATETIME NOT NULL,
--     end_time DATETIME NOT NULL,
--     FOREIGN KEY (customer_id) REFERENCES customers(id),
--     FOREIGN KEY (service_id) REFERENCES services(id),
--     FOREIGN KEY (employee_id) REFERENCES employees(id)
-- );

CREATE TABLE image (
    id INT PRIMARY KEY AUTO_INCREMENT,
    url text NOT NULL
);

CREATE TABLE category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE products (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  price DECIMAL(10,2) NOT NULL,
  stock INT NOT NULL,
  category_id INT,
  image_id INT,
  FOREIGN KEY (image_id) REFERENCES image(id),
  FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE orders (
  id INT PRIMARY KEY AUTO_INCREMENT,
  person_id INT NOT NULL,
  order_date DATE NOT NULL,
  total_amount DECIMAL(10,2) NOT NULL,
  shipping_address VARCHAR(255),
  FOREIGN KEY (person_id) REFERENCES person(id)
);

CREATE TABLE order_details (
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity INT NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (order_id, product_id),
  FOREIGN KEY (order_id) REFERENCES orders(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
);