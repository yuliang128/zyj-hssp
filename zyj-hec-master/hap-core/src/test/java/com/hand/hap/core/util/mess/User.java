package com.hand.hap.core.util.mess;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * description
 *
 * @author shira 2019/03/25 14:08
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private Long userId;
	private String userName;
	private Long age;
	private Date birthDay;
	private List<Address> adress;
}
