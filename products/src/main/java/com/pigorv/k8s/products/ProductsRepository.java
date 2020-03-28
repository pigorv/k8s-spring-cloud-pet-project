package com.pigorv.k8s.products;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ProductsRepository {
    private Set<Product> productCatalog = new HashSet<>();

    public void add(Product product) {
        productCatalog.add(product);
    }

    public Optional<Product> getByName(String name) {
        return productCatalog.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst();
    }

    public Optional<Long> getQuantityByName(String name) {
        return productCatalog.stream()
                .filter(product -> product.getName().equals(name))
                .map(Product::getQuantity)
                .findFirst();
    }

    public void update(Product product) {
        if (product.getQuantity() <= 0) {
            productCatalog.removeIf(storedProduct ->
                    storedProduct.getName().equals(product.getName())
            );
        } else {
            add(product);
        }
    }

    public List<Product> getAll() {
        return new ArrayList<>(productCatalog);
    }
}