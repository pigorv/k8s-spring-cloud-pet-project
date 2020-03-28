package com.pigorv.k8s.products;

import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductsController {
    private final ProductsRepository repository;

    public ProductsController(ProductsRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Product createProduct() {
        var product = new Product();
        product.setName(RandomStringUtils.randomAlphabetic(16));
        product.setQuantity(RandomUtils.nextLong(1, 10));
        repository.add(product);

        return product;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return repository.getAll();
    }

    @GetMapping("/{productName}")
    public Product getProduct(@PathVariable String productName) {
        return repository.getByName(productName).orElseThrow(NotFoundProductException::new);
    }

    @PutMapping("/{productName}")
    public Product removeOneProduct(@PathVariable String productName) {
        Product product = repository.getByName(productName).orElseThrow(NotFoundProductException::new);

        product.setQuantity(product.getQuantity() - 1);
        repository.update(product);

        return product;
    }
}
