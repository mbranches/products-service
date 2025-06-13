insert into user (id, first_name, last_name, login, password) values (UUID(), 'Customer', 'User', 'customer.user', '$2a$10$uJSAqUT5K06qvbKLFZEEGelpObdbTIpRq.s66LIxwKEh2V6oBAxSa');

insert into user_role (user_id, role_id) values((select u.id from user u where login = 'customer.user'), 2);