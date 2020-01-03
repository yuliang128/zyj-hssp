package com.hand.hec.gld.dto;

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
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "gld_acc_entity_tax")
public class GldAccEntityTax extends BaseDTO {

     public static final String FIELD_TAX_ID = "taxId";
     public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
     public static final String FIELD_TAXPAYER_TYPE = "taxpayerType";
     public static final String FIELD_TAXPAYER_NUMBER = "taxpayerNumber";
     public static final String FIELD_ADDRESS = "address";
     public static final String FIELD_TAXPAYER_BANK = "taxpayerBank";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long taxId;

    /**
     * 核算主体ID
     */
     @NotNull
     private Long accEntityId;

    /**
     * 纳税人类型（SYSCODE：TYPE_OF_TAXPAYER）
     */
     @NotEmpty
     @Length(max = 200)
     private String taxpayerType;

    /**
     * 纳税人识别号
     */
     @Length(max = 200)
     private String taxpayerNumber;

    /**
     * 地址电话
     */
     @Length(max = 2000)
     private String address;

    /**
     * 开户行及帐号
     */
     @Length(max = 200)
     private String taxpayerBank;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
