package com.hand.hap.core.util;

import com.hand.hap.core.util.mess.Address;
import com.hand.hap.core.util.mess.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author shira 2019/03/25 16:12
 */
public class BeanUtilTest {

	private User user ;

	@Before
	public void init(){
		List<Address> addressList = new ArrayList<>();
		addressList.add(Address.builder().addressId(1L).Country("China").province("HuBei").city("WuHan").build());
		addressList.add(Address.builder().addressId(1L).Country("China").province("HuNan").city("ChangSha").build());
		addressList.add(Address.builder().addressId(1L).Country("America").city("New York").build());

		user = User.builder()
				.userId(1L)
				.userName("tom")
				.age(20L)
				.birthDay(new Date())
				.adress(addressList).build();

	}

	@Test
	public void convert2Bean() {
		Map<String, Object> map = BeanUtil.convert2Map(user);
		System.out.println(map.toString());

		User theUser = (User) BeanUtil.convert2Bean(User.class, map);
		System.out.println(theUser.toString());
	}

	@Test
	public void convert2Map() {
		Map<String, Object> map = BeanUtil.convert2Map(user);

		System.out.println(map.toString());

		map.forEach( (key, value) ->{
			System.out.println("property : " + key + " |  value : " + value);

		});


	}

}