
    create table address (
        is_active bit,
        id bigint not null auto_increment,
        street varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department_employees (
        departments_id bigint not null,
        employees_id bigint not null,
        primary key (departments_id, employees_id)
    ) engine=InnoDB;

    create table employee (
        age integer,
        salary integer,
        id bigint not null auto_increment,
        first_name varchar(255),
        last_name varchar(255),
        position varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table employee_addresses (
        addresses_id bigint not null,
        employees_id bigint not null,
        primary key (addresses_id, employees_id)
    ) engine=InnoDB;

    create table organization (
        is_active bit,
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table organization_departments (
        departments_id bigint not null,
        organizations_id bigint not null,
        primary key (departments_id, organizations_id)
    ) engine=InnoDB;

    alter table department_employees 
       add constraint FKnoa5axqogt38rt5mr2q5sabme 
       foreign key (employees_id) 
       references employee (id);

    alter table department_employees 
       add constraint FK87bhcbmhks6l5u3ehmqqg6ywa 
       foreign key (departments_id) 
       references department (id);

    alter table employee_addresses 
       add constraint FKh2yku498btgqo0m3cci8l36f4 
       foreign key (addresses_id) 
       references address (id);

    alter table employee_addresses 
       add constraint FKssykducvk7tqpulktobfuqvwe 
       foreign key (employees_id) 
       references employee (id);

    alter table organization_departments 
       add constraint FKtfsi1dy1xvjlg1l8g0j2l4vyn 
       foreign key (departments_id) 
       references department (id);

    alter table organization_departments 
       add constraint FKoi87imyfq089rifktyddyfucw 
       foreign key (organizations_id) 
       references organization (id);

    create table address (
        is_active bit,
        id bigint not null auto_increment,
        street varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department_employees (
        departments_id bigint not null,
        employees_id bigint not null,
        primary key (departments_id, employees_id)
    ) engine=InnoDB;

    create table employee (
        age integer,
        salary integer,
        id bigint not null auto_increment,
        first_name varchar(255),
        last_name varchar(255),
        position varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table employee_addresses (
        addresses_id bigint not null,
        employees_id bigint not null,
        primary key (addresses_id, employees_id)
    ) engine=InnoDB;

    create table organization (
        is_active bit,
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table organization_departments (
        departments_id bigint not null,
        organizations_id bigint not null,
        primary key (departments_id, organizations_id)
    ) engine=InnoDB;

    alter table department_employees 
       add constraint FKnoa5axqogt38rt5mr2q5sabme 
       foreign key (employees_id) 
       references employee (id);

    alter table department_employees 
       add constraint FK87bhcbmhks6l5u3ehmqqg6ywa 
       foreign key (departments_id) 
       references department (id);

    alter table employee_addresses 
       add constraint FKh2yku498btgqo0m3cci8l36f4 
       foreign key (addresses_id) 
       references address (id);

    alter table employee_addresses 
       add constraint FKssykducvk7tqpulktobfuqvwe 
       foreign key (employees_id) 
       references employee (id);

    alter table organization_departments 
       add constraint FKtfsi1dy1xvjlg1l8g0j2l4vyn 
       foreign key (departments_id) 
       references department (id);

    alter table organization_departments 
       add constraint FKoi87imyfq089rifktyddyfucw 
       foreign key (organizations_id) 
       references organization (id);

    create table address (
        is_active bit,
        id bigint not null auto_increment,
        street varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department_employees (
        departments_id bigint not null,
        employees_id bigint not null,
        primary key (departments_id, employees_id)
    ) engine=InnoDB;

    create table employee (
        age integer,
        salary integer,
        id bigint not null auto_increment,
        first_name varchar(255),
        last_name varchar(255),
        position varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table employee_addresses (
        addresses_id bigint not null,
        employees_id bigint not null,
        primary key (addresses_id, employees_id)
    ) engine=InnoDB;

    create table organization (
        is_active bit,
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table organization_departments (
        departments_id bigint not null,
        organizations_id bigint not null,
        primary key (departments_id, organizations_id)
    ) engine=InnoDB;

    alter table department_employees 
       add constraint FKnoa5axqogt38rt5mr2q5sabme 
       foreign key (employees_id) 
       references employee (id);

    alter table department_employees 
       add constraint FK87bhcbmhks6l5u3ehmqqg6ywa 
       foreign key (departments_id) 
       references department (id);

    alter table employee_addresses 
       add constraint FKh2yku498btgqo0m3cci8l36f4 
       foreign key (addresses_id) 
       references address (id);

    alter table employee_addresses 
       add constraint FKssykducvk7tqpulktobfuqvwe 
       foreign key (employees_id) 
       references employee (id);

    alter table organization_departments 
       add constraint FKtfsi1dy1xvjlg1l8g0j2l4vyn 
       foreign key (departments_id) 
       references department (id);

    alter table organization_departments 
       add constraint FKoi87imyfq089rifktyddyfucw 
       foreign key (organizations_id) 
       references organization (id);

    create table address (
        is_active bit,
        id bigint not null auto_increment,
        street varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department_employees (
        departments_id bigint not null,
        employees_id bigint not null,
        primary key (departments_id, employees_id)
    ) engine=InnoDB;

    create table employee (
        age integer,
        salary integer,
        id bigint not null auto_increment,
        first_name varchar(255),
        last_name varchar(255),
        position varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table employee_addresses (
        addresses_id bigint not null,
        employees_id bigint not null,
        primary key (addresses_id, employees_id)
    ) engine=InnoDB;

    create table organization (
        is_active bit,
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table organization_departments (
        departments_id bigint not null,
        organizations_id bigint not null,
        primary key (departments_id, organizations_id)
    ) engine=InnoDB;

    alter table department_employees 
       add constraint FKnoa5axqogt38rt5mr2q5sabme 
       foreign key (employees_id) 
       references employee (id);

    alter table department_employees 
       add constraint FK87bhcbmhks6l5u3ehmqqg6ywa 
       foreign key (departments_id) 
       references department (id);

    alter table employee_addresses 
       add constraint FKh2yku498btgqo0m3cci8l36f4 
       foreign key (addresses_id) 
       references address (id);

    alter table employee_addresses 
       add constraint FKssykducvk7tqpulktobfuqvwe 
       foreign key (employees_id) 
       references employee (id);

    alter table organization_departments 
       add constraint FKtfsi1dy1xvjlg1l8g0j2l4vyn 
       foreign key (departments_id) 
       references department (id);

    alter table organization_departments 
       add constraint FKoi87imyfq089rifktyddyfucw 
       foreign key (organizations_id) 
       references organization (id);

    create table address (
        is_active bit,
        id bigint not null auto_increment,
        street varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department_employees (
        departments_id bigint not null,
        employees_id bigint not null,
        primary key (departments_id, employees_id)
    ) engine=InnoDB;

    create table employee (
        age integer,
        salary integer,
        id bigint not null auto_increment,
        first_name varchar(255),
        last_name varchar(255),
        position varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table employee_addresses (
        addresses_id bigint not null,
        employees_id bigint not null,
        primary key (addresses_id, employees_id)
    ) engine=InnoDB;

    create table organization (
        is_active bit,
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table organization_departments (
        departments_id bigint not null,
        organizations_id bigint not null,
        primary key (departments_id, organizations_id)
    ) engine=InnoDB;

    alter table department_employees 
       add constraint FKnoa5axqogt38rt5mr2q5sabme 
       foreign key (employees_id) 
       references employee (id);

    alter table department_employees 
       add constraint FK87bhcbmhks6l5u3ehmqqg6ywa 
       foreign key (departments_id) 
       references department (id);

    alter table employee_addresses 
       add constraint FKh2yku498btgqo0m3cci8l36f4 
       foreign key (addresses_id) 
       references address (id);

    alter table employee_addresses 
       add constraint FKssykducvk7tqpulktobfuqvwe 
       foreign key (employees_id) 
       references employee (id);

    alter table organization_departments 
       add constraint FKtfsi1dy1xvjlg1l8g0j2l4vyn 
       foreign key (departments_id) 
       references department (id);

    alter table organization_departments 
       add constraint FKoi87imyfq089rifktyddyfucw 
       foreign key (organizations_id) 
       references organization (id);

    create table address (
        is_active bit,
        id bigint not null auto_increment,
        street varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department_employees (
        departments_id bigint not null,
        employees_id bigint not null,
        primary key (departments_id, employees_id)
    ) engine=InnoDB;

    create table employee (
        age integer,
        salary integer,
        id bigint not null auto_increment,
        first_name varchar(255),
        last_name varchar(255),
        position varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table employee_addresses (
        addresses_id bigint not null,
        employees_id bigint not null,
        primary key (addresses_id, employees_id)
    ) engine=InnoDB;

    create table organization (
        is_active bit,
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table organization_departments (
        departments_id bigint not null,
        organizations_id bigint not null,
        primary key (departments_id, organizations_id)
    ) engine=InnoDB;

    alter table department_employees 
       add constraint FKnoa5axqogt38rt5mr2q5sabme 
       foreign key (employees_id) 
       references employee (id);

    alter table department_employees 
       add constraint FK87bhcbmhks6l5u3ehmqqg6ywa 
       foreign key (departments_id) 
       references department (id);

    alter table employee_addresses 
       add constraint FKh2yku498btgqo0m3cci8l36f4 
       foreign key (addresses_id) 
       references address (id);

    alter table employee_addresses 
       add constraint FKssykducvk7tqpulktobfuqvwe 
       foreign key (employees_id) 
       references employee (id);

    alter table organization_departments 
       add constraint FKtfsi1dy1xvjlg1l8g0j2l4vyn 
       foreign key (departments_id) 
       references department (id);

    alter table organization_departments 
       add constraint FKoi87imyfq089rifktyddyfucw 
       foreign key (organizations_id) 
       references organization (id);

    create table address (
        is_active bit,
        id bigint not null auto_increment,
        street varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department_employees (
        departments_id bigint not null,
        employees_id bigint not null,
        primary key (departments_id, employees_id)
    ) engine=InnoDB;

    create table employee (
        age integer,
        salary integer,
        id bigint not null auto_increment,
        first_name varchar(255),
        last_name varchar(255),
        position varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table employee_addresses (
        addresses_id bigint not null,
        employees_id bigint not null,
        primary key (addresses_id, employees_id)
    ) engine=InnoDB;

    create table organization (
        is_active bit,
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table organization_departments (
        departments_id bigint not null,
        organizations_id bigint not null,
        primary key (departments_id, organizations_id)
    ) engine=InnoDB;

    alter table department_employees 
       add constraint FKnoa5axqogt38rt5mr2q5sabme 
       foreign key (employees_id) 
       references employee (id);

    alter table department_employees 
       add constraint FK87bhcbmhks6l5u3ehmqqg6ywa 
       foreign key (departments_id) 
       references department (id);

    alter table employee_addresses 
       add constraint FKh2yku498btgqo0m3cci8l36f4 
       foreign key (addresses_id) 
       references address (id);

    alter table employee_addresses 
       add constraint FKssykducvk7tqpulktobfuqvwe 
       foreign key (employees_id) 
       references employee (id);

    alter table organization_departments 
       add constraint FKtfsi1dy1xvjlg1l8g0j2l4vyn 
       foreign key (departments_id) 
       references department (id);

    alter table organization_departments 
       add constraint FKoi87imyfq089rifktyddyfucw 
       foreign key (organizations_id) 
       references organization (id);

    create table address (
        is_active bit,
        id bigint not null auto_increment,
        street varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department_employees (
        departments_id bigint not null,
        employees_id bigint not null,
        primary key (departments_id, employees_id)
    ) engine=InnoDB;

    create table employee (
        age integer,
        salary integer,
        id bigint not null auto_increment,
        first_name varchar(255),
        last_name varchar(255),
        position varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table employee_addresses (
        addresses_id bigint not null,
        employees_id bigint not null,
        primary key (addresses_id, employees_id)
    ) engine=InnoDB;

    create table organization (
        is_active bit,
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table organization_departments (
        departments_id bigint not null,
        organizations_id bigint not null,
        primary key (departments_id, organizations_id)
    ) engine=InnoDB;

    alter table department_employees 
       add constraint FKnoa5axqogt38rt5mr2q5sabme 
       foreign key (employees_id) 
       references employee (id);

    alter table department_employees 
       add constraint FK87bhcbmhks6l5u3ehmqqg6ywa 
       foreign key (departments_id) 
       references department (id);

    alter table employee_addresses 
       add constraint FKh2yku498btgqo0m3cci8l36f4 
       foreign key (addresses_id) 
       references address (id);

    alter table employee_addresses 
       add constraint FKssykducvk7tqpulktobfuqvwe 
       foreign key (employees_id) 
       references employee (id);

    alter table organization_departments 
       add constraint FKtfsi1dy1xvjlg1l8g0j2l4vyn 
       foreign key (departments_id) 
       references department (id);

    alter table organization_departments 
       add constraint FKoi87imyfq089rifktyddyfucw 
       foreign key (organizations_id) 
       references organization (id);

    create table address (
        is_active bit,
        id bigint not null auto_increment,
        street varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table department_employees (
        departments_id bigint not null,
        employees_id bigint not null,
        primary key (departments_id, employees_id)
    ) engine=InnoDB;

    create table employee (
        age integer,
        salary integer,
        id bigint not null auto_increment,
        first_name varchar(255),
        last_name varchar(255),
        position varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table employee_addresses (
        addresses_id bigint not null,
        employees_id bigint not null,
        primary key (addresses_id, employees_id)
    ) engine=InnoDB;

    create table organization (
        is_active bit,
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table organization_departments (
        departments_id bigint not null,
        organizations_id bigint not null,
        primary key (departments_id, organizations_id)
    ) engine=InnoDB;

    alter table department_employees 
       add constraint FKnoa5axqogt38rt5mr2q5sabme 
       foreign key (employees_id) 
       references employee (id);

    alter table department_employees 
       add constraint FK87bhcbmhks6l5u3ehmqqg6ywa 
       foreign key (departments_id) 
       references department (id);

    alter table employee_addresses 
       add constraint FKh2yku498btgqo0m3cci8l36f4 
       foreign key (addresses_id) 
       references address (id);

    alter table employee_addresses 
       add constraint FKssykducvk7tqpulktobfuqvwe 
       foreign key (employees_id) 
       references employee (id);

    alter table organization_departments 
       add constraint FKtfsi1dy1xvjlg1l8g0j2l4vyn 
       foreign key (departments_id) 
       references department (id);

    alter table organization_departments 
       add constraint FKoi87imyfq089rifktyddyfucw 
       foreign key (organizations_id) 
       references organization (id);
