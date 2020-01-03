package com.hand.hap.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.*;

/**
 * 关于bean的工具类
 * 参考：https://my.oschina.net/MinghanSui/blog/911051
 * 用法请参见： BeanUtilsTest
 * @author shira 2019/03/25 14:03
 */
public class BeanUtil {
	private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	/**
	 * 基本类型的容器
	 */
	private static final Set<Class> BASIC_CLASS_TYPE_LIST = new HashSet<Class>();

	static {
		BASIC_CLASS_TYPE_LIST.add(Boolean.class);
		BASIC_CLASS_TYPE_LIST.add(Byte.class);
		BASIC_CLASS_TYPE_LIST.add(String.class);
		BASIC_CLASS_TYPE_LIST.add(Short.class);
		BASIC_CLASS_TYPE_LIST.add(Integer.class);
		BASIC_CLASS_TYPE_LIST.add(Long.class);
		BASIC_CLASS_TYPE_LIST.add(Float.class);
		BASIC_CLASS_TYPE_LIST.add(Double.class);
		BASIC_CLASS_TYPE_LIST.add(BigDecimal.class);
		BASIC_CLASS_TYPE_LIST.add(Date.class);
		BASIC_CLASS_TYPE_LIST.add(java.sql.Date.class);
		BASIC_CLASS_TYPE_LIST.add(Time.class);
	}


	private BeanUtil() {
	}


	/**
	 * Map转实体类共通方法
	 *
	 * @param type 实体类class
	 * @param map map
	 * @return Object
	 *
	 */
	public static Object convert2Bean(Class type, Map map) {
		Object obj = null;
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			obj = type.newInstance();
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : propertyDescriptors) {
				String propertyName = descriptor.getName();
				if (map.containsKey(propertyName)) {
					Object value = map.get(propertyName);
					descriptor.getWriteMethod().invoke(obj, value);
				}
			}


		} catch (IntrospectionException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		}

		return obj;
	}


	/**
	 * 实体类转Map共通方法
	 *
	 * @param bean 实体类
	 * @return Map
	 *
	 */
	public static Map convert2Map(Object bean) {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type);

			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : propertyDescriptors) {
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean);

					if (result != null) {
						// process basic type
						if (BASIC_CLASS_TYPE_LIST.contains(result.getClass())) {
							returnMap.put(propertyName, result);
						}

						// process collection
						if (descriptor.getPropertyType().equals(List.class)  && !((List)result).isEmpty() ) {

							List childList = new ArrayList();

							((List) result).forEach(element -> {
								childList.add(convert2Map(element));
							});

							returnMap.put(propertyName, childList);

						}else if(descriptor.getPropertyType().equals(Map.class) && !((Map)result).isEmpty()  ){

								Map childMap = new HashMap();

								((Map) result).forEach((key, value) -> {
									childMap.put(key, convert2Map(value));
								});

								returnMap.put(propertyName, childMap);

						}
					}

				}
			}

		} catch (IllegalAccessException | IntrospectionException | InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		}


		return returnMap;
	}



	/**
	 * List转Map共通方法
	 *
	 * @param list list
	 * @return Map
	 *
	 */
	public static Map convert2Map(List list) {
		Map resultMap = new HashMap();

		int[] idx = { 0 };

		list.forEach(item -> {
			resultMap.put(idx[0]++ ,convert2Map(item));
		});

		return resultMap;
	}

}
