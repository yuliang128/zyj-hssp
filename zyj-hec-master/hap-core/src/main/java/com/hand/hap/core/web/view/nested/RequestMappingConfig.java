package com.hand.hap.core.web.view.nested;

import com.hand.hap.core.components.ApplicationContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * 暂时无用，已使用其他方案解决此问题
 *
 * @author shira 2019/04/01 11:20
 */
public class RequestMappingConfig {

	RequestMappingHandlerMapping requestMappingHandlerMapping;

	@Autowired
	public RequestMappingConfig( RequestMappingHandlerMapping requestMappingHandlerMapping){
		this.requestMappingHandlerMapping = requestMappingHandlerMapping;

		requestMappingHandlerMapping( );
	}


	public   RequestMappingHandlerMapping requestMappingHandlerMapping(){
		ApplicationContext applicationContext = ApplicationContextHelper.getApplicationContext();
		ApplicationContext applicationContext2 = requestMappingHandlerMapping.getApplicationContext();

		String[] beans = applicationContext.getBeanDefinitionNames();
		//RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);

		for(String beanName : beans) {
			Class<?> type = applicationContext.getType(beanName);
			if (type != null && !type.isInterface()) {
				Controller annotation = type.getAnnotation(Controller.class);

				Object bean = applicationContext.getBean(beanName);

				//如果此bean是Controller，则注册到RequestMappingHandlerMapping里面
				if (annotation != null) {

					Method getMappingForMethod = ReflectionUtils
							.findMethod(RequestMappingHandlerMapping.class, "getMappingForMethod", Method.class, Class.class);
					getMappingForMethod.setAccessible(true);

					try {
						Method[] methodArray = type.getMethods();
						for (Method method : methodArray) {
							if (method.getAnnotation(RequestMapping.class) != null) {
								//创建RequestMappingInfo
								RequestMappingInfo requestMappingInfo = (RequestMappingInfo) getMappingForMethod
										.invoke(requestMappingHandlerMapping, method, type);
								//注册
								requestMappingHandlerMapping.registerMapping(requestMappingInfo, bean, method);
							}

						}

					} catch (Exception e) {
						e.printStackTrace();
					}


				}
			}
		}

		return requestMappingHandlerMapping;

	}

}
