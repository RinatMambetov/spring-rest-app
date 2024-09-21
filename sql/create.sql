create table person(
    id serial primary key,
    "name" varchar(255) not null,
    age int not null,
    email varchar not null
)

insert into person(name, age, email) values
('Jane', 30, 'jane@gmail.com'),
('John', 20, 'john@gmail.com'),
('Mary', 25,'mary@gmail.com')

