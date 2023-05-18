SELECT * FROM kamispa.product;

INSERT INTO `kamispa`.`product` (`description`, `name`, `price`, `product_quantity`, `release_date`, `brands_id`, `category_id`) 
VALUES 
('giúp da sáng mịn', ' Sữa rửa mặt tinh chất hoa cúc', '200000', '2', '2023-01-01', '2', '3'),
('giúp da sáng mịn', ' Sữa dưởng da', '200000', '22', '2023-01-01', '3', '2'),
('giúp da sáng mịn', ' Tinh Dầu', '200000', '12', '2023-01-01', '4', '4'),
('giúp da sáng mịn', ' Sữa rửa mặt', '200000', '23', '2023-01-01', '3', '2'),
('giúp da sáng mịn', ' tinh chất hoa cúc', '200000', '32', '2023-01-01', '4', '3');

SELECT p.* FROM product p JOIN category c ON p.category_id = c.id JOIN brands b ON p.brands_id = b.id WHERE c.name like '%%' AND b.name like '%%' AND p.name like '%d%' ORDER BY release_date desc;

SELECT p.id, p.description, p.name, p.price, p.product_quantity, p.release_date, p.brands_id, p.category_id FROM product p JOIN category c ON p.category_id = c.id JOIN brands b ON p.brands_id = b.id WHERE c.name like '%%' AND b.name like '%%'  AND p.name like '%%' AND p.price >= 5 ORDER BY price desc;
 
SELECT p.* FROM product p JOIN category c ON p.category_id = c.id JOIN brands b ON p.brands_id = b.id WHERE c.name like '%%' AND b.name like '%%'  AND p.name like '%%' AND p.price <= 5 ORDER BY price asc;
 
SELECT p.* FROM product p JOIN category c ON p.category_id = c.id JOIN brands b ON p.brands_id = b.id WHERE c.name like '%%' AND b.name like '%%'  AND p.name like '%%' AND p.price BETWEEN 5 AND 7 ORDER BY price desc;
