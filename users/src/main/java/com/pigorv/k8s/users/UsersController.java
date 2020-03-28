package com.pigorv.k8s.users;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final Collection<User> registeredUsers = new HashSet<>();
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public User createUser() {
        var user = new User();
        user.setName(RandomStringUtils.randomAlphabetic(13));

        registeredUsers.add(user);

        return user;
    }

    @GetMapping("/{userName}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable String userName) {
        return registeredUsers.stream()
                .filter(storedUser -> storedUser.getName().equals(userName))
                .findFirst()
                .orElseThrow();
    }

    @GetMapping("/{userName}/products")
    public String[] getProductsByUser(@PathVariable String userName) {
        return restTemplate.getForObject("http://localhost:8383/orders/users/" + userName, String[].class);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

