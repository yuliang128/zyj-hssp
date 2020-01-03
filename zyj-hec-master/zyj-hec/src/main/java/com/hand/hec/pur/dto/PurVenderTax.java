package com.hand.hec.pur.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 供应商税务信息Dto
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/25 17:34
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "pur_vender_tax")
public class PurVenderTax extends BaseDTO {

    public static final String FIELD_TAXPAYER_NUMBER = "taxpayerNumber";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_TAXPAYER_BANK = "taxpayerBank";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_TAX_ID = "taxId";
    public static final String FIELD_VENDER_ID = "venderId";
    public static final String FIELD_TAXPAYER_TYPE = "taxpayerType";


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

    @Id
    @GeneratedValue
    private Long taxId;

    /**
     * 供应商ID
     */
    @NotNull
    private Long venderId;

    /**
     * 纳税人类型（SYSCODE：TYPE_OF_TAXPAYER）
     */
    @NotEmpty
    @Length(max = 200)
    private String taxpayerType;

    /**
     * 供应商ID
     */
    @Transient
    private Long accEntityId;

}
