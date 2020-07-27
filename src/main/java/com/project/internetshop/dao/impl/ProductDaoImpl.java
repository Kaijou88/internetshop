package com.project.internetshop.dao.impl;

import com.project.internetshop.dao.ProductDao;
import com.project.internetshop.dao.Storage;
import com.project.internetshop.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class ProductDaoImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        Storage.addProduct(product);
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Storage.products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAll() {
        return Storage.products;
    }

    @Override
    public Product update(Product product) {
        IntStream.range(0, Storage.products.size())
                .filter(x -> product.getId().equals(Storage.products.get(x).getId()))
                .forEach(p -> Storage.products.set(p, product));
        return product;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.products.removeIf(p -> p.getId().equals(id));
    }
}
