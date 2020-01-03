package com.hand.hap.core.util.mess;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description
 *
 * @author shira 2019/03/25 14:11
 */


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

	private Long addressId;

	private String Country;

	private String province;

	private String city;
}
