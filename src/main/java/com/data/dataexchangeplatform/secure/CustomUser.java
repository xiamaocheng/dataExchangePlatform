package com.data.dataexchangeplatform.secure;

import org.springframework.boot.autoconfigure.security.SecurityProperties;


import java.security.Principal;
import java.util.Set;

// 创建一个类实现org.apache.activemq.jaas.User类，用于验证用户的身份和权限
public class CustomUser extends SecurityProperties.User {
    private Set<Principal> groups;

    public CustomUser(String username, String password, Set<Principal> groups) {
        this.groups = groups;
    }

//    @Override
//    public Set<Principal> getGroups() {
//        return groups;
//    }
}