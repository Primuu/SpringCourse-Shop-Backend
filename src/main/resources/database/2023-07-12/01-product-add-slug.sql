--liquibase formatted sql
--changeset primuu:3
ALTER TABLE product ADD slug VARCHAR(255) AFTER image;
ALTER TABLE product ADD CONSTRAINT ui_product_slug UNIQUE KEY(slug);