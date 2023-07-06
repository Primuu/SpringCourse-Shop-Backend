--liquibase formatted sql
--changeset primuu:2
ALTER TABLE product ADD image VARCHAR(128) AFTER currency;