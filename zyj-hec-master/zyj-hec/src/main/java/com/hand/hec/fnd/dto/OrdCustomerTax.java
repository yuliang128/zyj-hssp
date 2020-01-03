package com.hand.hec.fnd.dto;

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
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 客户税务信息表
 * </p>
 * 
 * @author guiyuting 2019/02/22 16:15
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "ord_customer_tax")
public class OrdCustomerTax extends BaseDTO {

    public static final String FIELD_TAX_ID = "taxId";
    public static final String FIELD_CUSTOMER_ID = "customerId";
    public static final String FIELD_TAXPAYER_TYPE = "taxpayerType";
    public static final String FIELD_TAXPAYER_NUMBER = "taxpayerNumber";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_TAXPAYER_BANK = "taxpayerBank";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long taxId;

    /**
     * 客户ID
     */
    @Where
    @NotNull
    private Long customerId;

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

    @Transient
    private Long accEntityId;

}
