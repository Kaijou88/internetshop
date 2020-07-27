CREATE SCHEMA `internetshop` DEFAULT CHARACTER SET utf8;

CREATE TABLE `internetshop`.`products` (
    `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `product_name` VARCHAR(256) NOT NULL,
    `product_price` BIGINT(11) NOT NULL,
    PRIMARY KEY (`product_id`))
    ENGINE = InnoDB
    AUTO_INCREMENT=1 CHARACTER SET = utf8;

CREATE TABLE `internetshop`.`roles` (
    `role_id` bigint NOT NULL AUTO_INCREMENT,
    `role_name` varchar(256) NOT NULL,
     PRIMARY KEY (`role_id`),
     UNIQUE KEY `role_name_UNIQUE` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8;

CREATE TABLE `internetshop`.`users` (
     `user_id` bigint NOT NULL AUTO_INCREMENT,
     `name` varchar(256) NOT NULL,
     `login` varchar(256) NOT NULL,
     `password` varchar(256) NOT NULL,
     `salt` varbinary(500) DEFAULT NULL,
     PRIMARY KEY (`user_id`),
     UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `internetshop`.`orders` (
      `order_id` bigint NOT NULL AUTO_INCREMENT,
      `user_id` bigint NOT NULL,
      PRIMARY KEY (`order_id`),
      KEY `orders_users_fk_idx` (`user_id`),
      CONSTRAINT `orders_users_fk` FOREIGN KEY (`user_id`) REFERENCES `internetshop`.`users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `internetshop`.`orders_products` (
     `order_id` bigint NOT NULL,
     `product_id` bigint NOT NULL,
     KEY `orders_products_orders_idx` (`order_id`),
     KEY `orders_products_products_idx` (`product_id`),
     CONSTRAINT `orders_products_orders` FOREIGN KEY (`order_id`) REFERENCES `internetshop`.`orders` (`order_id`),
     CONSTRAINT `orders_products_products` FOREIGN KEY (`product_id`) REFERENCES `internetshop`.`products` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `internetshop`.`shopping_carts` (
     `cart_id` bigint NOT NULL AUTO_INCREMENT,
     `user_id` bigint NOT NULL,
     PRIMARY KEY (`cart_id`),
     KEY `shopping_carts_users_fk_idx` (`user_id`),
     CONSTRAINT `shopping_carts_users_fk` FOREIGN KEY (`user_id`) REFERENCES `internetshop`.`users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `internetshop`.`shopping_carts_products` (
     `cart_id` bigint NOT NULL,
     `product_id` bigint NOT NULL,
     KEY `shopping_carts_products_shopping_carts_idx` (`cart_id`),
     KEY `shopping_carts_products_products_idx` (`product_id`),
     CONSTRAINT `shopping_carts_products_products` FOREIGN KEY (`product_id`) REFERENCES `internetshop`.`products` (`product_id`),
     CONSTRAINT `shopping_carts_products_shopping_carts` FOREIGN KEY (`cart_id`) REFERENCES `internetshop`.`shopping_carts` (`cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `internetshop`.`users_roles` (
     `user_id` bigint NOT NULL,
     `role_id` bigint NOT NULL,
     KEY `users_roles_users_idx` (`user_id`),
     KEY `users_roles_roles_idx` (`role_id`),
     CONSTRAINT `users_roles_roles` FOREIGN KEY (`role_id`) REFERENCES `internetshop`.`roles` (`role_id`),
     CONSTRAINT `users_roles_users` FOREIGN KEY (`user_id`) REFERENCES `internetshop`.`users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
