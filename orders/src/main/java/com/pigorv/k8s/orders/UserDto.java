package com.pigorv.k8s.orders;

import lombok.Data;

@Data
public class UserDto {
    private String name;
    private boolean isActive;
}
