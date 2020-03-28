# k8s-spring-cloud-pet-project
It is pet-project for getting hands on experience to write Spring Cloud apps running on Kubernetes

# How to use

## Product service
`cd products` then run `mvn spring-boot:run`

### Use Product service:
``` bash
curl -X POST http://localhost:8282/products
```
``` bash
curl -X GET http://localhost:8282/products{сreatedProductName}
```
Get all products:
``` bash
curl -X GET http://localhost:8282/products
```
Decrease quantity of product by 1:
``` bash
curl -X PUT http://localhost:8282/products/{сreatedProductName}
```

## User service
`cd users` then run `mvn spring-boot:run`

### Use User service:
``` bash
curl -X POST http://localhost:8181/users
```
``` bash
curl -X GET http://localhost:8181/users/{сreatedUserName}
```
Get user's products:
``` bash
curl -X GET GET http://localhost:8181/users/{сreatedUserName}/products
```

## Order service
`cd orders` then run `mvn spring-boot:run`

### Use User service:
``` bash
curl --url http://localhost:8383/orders \
     -H "Content-Type: application/json" \
     -d '{"userName": "{сreatedUserName}", "product": "{сreatedProductName}"}'
```
Get user's products:
``` bash
curl -X GET http://localhost:8383/orders/users/{сreatedUserName}
```