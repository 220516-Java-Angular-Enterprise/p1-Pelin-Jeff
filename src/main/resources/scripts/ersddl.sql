-- DDL: CREATE, DROP, ALTER, COMMENT, RENAME

drop table if exists reimbursements_status;
drop table if exists reimbusements_types;
drop table if exists reimbursements;
drop table if exists users_roles;
drop table if exists users;

create table user_roles (
	role_id varchar not null,
	roles varchar not null,

	constraint pk_user_roles_id
		primary key (role_id)
);

create table reimbursements_statuses (
	status_id varchar not null,
	status varchar not null,

	constraint pk_reimb_status_id
		primary key (status_id)
);

create table reimbursements_types (
	type_id varchar not null,
	type varchar not null,

	constraint pk_riemb_types_id
		primary key (type_id)
);

create table users (
	user_id varchar not null,
	username varchar not null,
	email varchar not null,
	passwords varchar not null,
	given_name varchar not null,
	surname varchar not null,
	is_active boolean,
	role_id varchar not null,

	constraint pk_users_id
		primary key (user_id)
);

create table reimbursements (
	reimb_id varchar not null,
	amount numeric(6, 2) not null,
	submitted timestamp not null,
	resolved timestamp,
	description varchar not null,
	receipt bytea,
	payment_id varchar not null,
	author_id varchar not null,
	resolver_id varchar not null,
	status_id varchar not null,
	type_id varchar not null,

	constraint pk_reimb_id
		primary key (reimb_id)
);

alter table users
	add constraint fk_roles
		foreign key (role_id) references user_roles (role_id);

alter table reimbursements
	add constraint fk_author
		foreign key (author_id) references users (user_id),
	add constraint fk_resolver
		foreign key (resolver_id) references users,
	add constraint fk_status
		foreign key (status_id) references reimbursements_statuses (status_id),
	add constraint fk_type
		foreign key (type_id) references reimbursements_types (type_id);