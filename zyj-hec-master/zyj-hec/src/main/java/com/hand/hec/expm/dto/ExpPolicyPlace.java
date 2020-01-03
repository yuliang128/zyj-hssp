package com.hand.hec.expm.dto;

import javax.persistence.*;

import com.hand.hap.mybatis.common.query.Where;
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
@Table(name = "exp_policy_place")
public class ExpPolicyPlace extends BaseDTO {

     public static final String FIELD_PLACE_ID = "placeId";
     public static final String FIELD_PLACE_CODE = "placeCode";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_DISTRICT_ID = "districtId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";
     public static final String FIELD_COUNTRY_CODE = "countryCode";
     public static final String FIELD_COUNTRY_NAME = "countryName";
     public static final String FIELD_DISTRICT_NAME = "districtName";

     @Id
     @GeneratedValue
     private Long placeId;

    /**
     * 地点编码
     */
     @NotEmpty
     @Length(max = 30)
     private String placeCode;

    /**
     * 描述
     */
     @NotEmpty
     @Length(max = 500)
     private String description;

    /**
     * 描述id
     */
     private Long districtId;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

    /**
     * 国家代码
     */
     @Length(max = 100)
     private String countryCode;

    /**
     * 区域名称
     */
    @Transient
     private String districtName;

    /**
     * 国家名称
     */
     @Transient
     private String countryName;

    /**
     * 区域从
     */
    @Transient
    private String districtNameFrom;

    /**
     * 区域到
     */
    @Transient
    private String districtNameTo;




}
