package com.data.dataexchangeplatform.secure;

import org.apache.activemq.Message;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.security.MessageAuthorizationPolicy;
import org.apache.activemq.security.SecurityContext;

// 创建一个类实现org.apache.activemq.security.MessageAuthorizationPolicy接口，用于消息的权限控制
public class CustomMessageAuthorizationPolicy implements MessageAuthorizationPolicy {
//    @Override
//    public boolean isAllowedToConsume(ConnectionContext context, Message message) {
//        // 在这里实现消息的权限控制逻辑，例如检查用户是否有权限消费该消息
//        return true; // 返回true表示允许消费消息，返回false表示不允许消费消息
//    }
//
//    @Override
//    public boolean isAllowedToConsume(Message message, SecurityContext securityContext) {
//        // 在这里实现消息的权限控制逻辑，例如检查用户是否有权限消费该消息
//        return true; // 返回true表示允许消费消息，返回false表示不允许消费消息
//    }

//    @Override
//    public boolean isAllowedToConsume(Message message, org.apache.activemq.command.MessageEvaluationContext messageEvaluationContext) {
//        // 在这里实现消息的权限控制逻辑，例如检查用户是否有权限消费该消息
//        return true; // 返回true表示允许消费消息，返回false表示不允许消费消息
//    }

    @Override
    public boolean isAllowedToConsume(ConnectionContext connectionContext, org.apache.activemq.command.Message message) {
        return false;
    }
}
