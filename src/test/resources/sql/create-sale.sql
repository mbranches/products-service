
insert into sale(user_id, total_value) VALUES ((select u.id from user u where login = 'customer.user'), 3200);

insert into sale_product(sale_id, product_id, quantity, total_value) VALUES ((select s.id from sale s where s.total_value = 3200), (select p.id from product p where p.name = 'Mouse Logitech'), 10, 3200);