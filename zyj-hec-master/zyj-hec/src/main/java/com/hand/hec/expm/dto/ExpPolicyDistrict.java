package com.hand.hec.expm.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_policy_district")
public class ExpPolicyDistrict extends BaseDTO {

     public static final String FIELD_DISTRICT_ID = "districtId";
     public static final String FIELD_DISTRICT_CODE = "districtCode";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long districtId;

    /**
     * 描述编码
     */
     @NotEmpty
     @Length(max = 30)
     private String districtCode;

    /**
     * 描述
     */
     @NotEmpty
     @Length(max = 500)
     private String description;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
