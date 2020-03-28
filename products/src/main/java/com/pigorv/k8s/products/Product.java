package com.pigorv.k8s.products;

import lombok.Data;

@Data
public class Product {
    private String name;
    private Long quantity;
}
