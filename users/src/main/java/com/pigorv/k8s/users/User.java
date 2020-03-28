package com.pigorv.k8s.users;

import lombok.Data;

@Data
public class User {
    private String name;
    private boolean isActive = true;
}
