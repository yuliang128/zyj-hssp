/*
 * Copyright Hand China Co.,Ltd.
 */

package com.hand.hap.demo.ws;

import javax.jws.WebService;

import com.hand.hap.account.dto.User;
  

@WebService  
public interface HelloWorld {  
      
     User sayHello(String name, User user);
      
} 