package com.data.dataexchangeplatform.secure;


import org.apache.activemq.jaas.GroupPrincipal;

// 创建一个类实现org.apache.activemq.jaas.GroupPrincipal接口，用于表示用户组信息
public class CustomGroupPrincipal extends GroupPrincipal {
    private String groupName;

    public CustomGroupPrincipal(String groupName) {
        super(groupName);
        this.groupName = groupName;
    }

    @Override
    public String getName() {
        return groupName;
    }
}