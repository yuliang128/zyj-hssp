/*
 * Copyright Hand China Co.,Ltd.
 */

package com.hand.hap.demo.ws;

import javax.jws.WebService;

import com.hand.hap.account.dto.User;

@WebService(endpointInterface = "com.hand.hap.demo.ws.HelloWorld",serviceName="HelloGT")
public class HelloWorldImpl implements HelloWorld{  
  
    @Override  
    public User sayHello(String name, User user) {
        System.out.println(user.getUserName());
      
        user.setEmail("test@hand.com");
        return user;
    }  
  
}  