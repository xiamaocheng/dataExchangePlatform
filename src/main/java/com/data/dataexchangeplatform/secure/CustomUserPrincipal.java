package com.data.dataexchangeplatform.secure;


// 创建一个类实现org.apache.activemq.jaas.UserPrincipal接口，用于表示用户身份信息


import org.apache.activemq.jaas.UserPrincipal;

public class CustomUserPrincipal extends UserPrincipal {
    private String username;

    public CustomUserPrincipal(String username) {
        super(username);
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }
}