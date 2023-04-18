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
