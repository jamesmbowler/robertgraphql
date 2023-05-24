insert into organization (id, name) values (1, 'Test1');
insert into organization (id, name) values (2, 'Test2');

insert into department (id, name) values (1, 'Test1');
insert into department (id, name) values (2, 'Test2');
insert into department (id, name) values (3, 'Test3');
insert into department (id, name) values (4, 'Test4');
insert into department (id, name) values (5, 'Test5');
insert into department (id, name) values (6, 'Test6');
insert into department (id, name) values (7, 'Test7');
insert into department (id, name) values (8, 'Test8');
insert into department (id, name) values (9, 'Test9');

insert into employee (id, first_name, last_name, position, salary, age) values (1, 'John', 'Smith', 'Developer', 10000, 30);
insert into employee (id, first_name, last_name, position, salary, age) values (2, 'Adam', 'Hamilton', 'Developer', 12000, 35);

insert into department_employees (departments_id, employee_id) values (1, 1);

insert into address (id, street, is_active) values (1, 'Test7', 1);

insert into employee_addresses (employees_id, addresses_id) values (1, 1);

insert into clubs (id, name, description) values (1, 'Gerrish', 'Swimming');
insert into venues (id, name, description) values (1, 'The Grill', "near the pickleball");
insert into clubs_venues (clubs_id, venues_id) values (1, 1);

insert into menus (id, name) values (1, "Grill menu");
insert into venues_menus (venue_id, menus_id) values (1, 1);

insert into menu_items (id, name, description, price) values (1, "Burger", "Beef burger", 10.00);
insert into menu_items (id, name, description, price) values (2, "Fries", "French fries", 5.00);
insert into menus_menu_items (menu_items_id, menus_id) values (1, 1);
insert into menus_menu_items (menu_items_id, menus_id) values (2, 1);