CREATE DATABASE IF NOT EXISTS ticket;

USE ticket;

DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS ticket_line_items;

CREATE TABLE tickets
(
    ticket_id bigint NOT NULL,
    state varchar(16) NOT NULL,
    restaurant_id bigint NOT NULL,
    ready_by timestamp ,
    accept_time timestamp ,
    PRIMARY KEY (ticket_id)
);

CREATE TABLE ticket_line_items
(
    ticket_ticket_id bigint NOT NULL,
    quantity int NOT NULL,
    menu_item_id varchar(64) NOT NULL,
    name varchar(64) NOT NULL
);