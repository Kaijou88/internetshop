package com.project.internetshop.service.impl;

import com.project.internetshop.dao.ProductDao;
import com.project.internetshop.lib.Inject;
import com.project.internetshop.lib.Service;
import com.project.internetshop.model.Product;
import com.project.internetshop.service.ProductService;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Inject
    private ProductDao productDao;

    @Override
    public Product create(Product product) {
        return productDao.create(product);
    }

    @Override
    public Product get(Long id) {
        return productDao.get(id).get();
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public Product update(Product product) {
        return productDao.update(product);
    }

    @Override
    public boolean delete(Long id) {
        return productDao.delete(id);
    }

}
