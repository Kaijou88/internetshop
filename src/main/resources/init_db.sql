CREATE SCHEMA `internetshop` DEFAULT CHARACTER SET utf8;
CREATE TABLE `internetshop`.`products` (
  `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `product_name` VARCHAR(225) NOT NULL,
  `product_price` BIGINT(11) NOT NULL,
  PRIMARY KEY (`product_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
