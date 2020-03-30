package com.pigorv.k8s.orders;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private Map<Long, Order> orderList = new HashMap<>();
    private long nextId = 1;

    @Value("${k8sPet.services.users.url}")
    private String userServiceUrl;
    @Value("${k8sPet.services.products.url}")
    private String productServiceUrl;

    @Autowired
    RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<Order> createNewOrder(@RequestBody Order order) {
        UserDto user = restTemplate.getForObject(userServiceUrl + "/" + order.getUserName(), UserDto.class);
        if (user == null || !user.isActive()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            restTemplate.put(productServiceUrl + "/" + order.getProduct(), null);
        } catch (RestClientException e) {
            return ResponseEntity.badRequest().build();
        }

        orderList.put(nextId++, order);

        return ResponseEntity.created(URI.create("/orders/" + (nextId - 1))).body(order);
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable Long orderId) {
        return orderList.get(orderId);
    }

    @GetMapping("/users/{userName}")
    public String[] getProductsForUser(@PathVariable String userName) {
        return orderList.values().stream()
                .filter(order -> userName.equals(order.getUserName()))
                .map(Order::getProduct)
                .toArray(String[]::new);
    }

    @Bean
    @LoadBalanced
    private RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}

