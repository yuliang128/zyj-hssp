package com.hand.hec.csh.dto;

import javax.persistence.*;

import com.hand.hap.mybatis.common.query.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 付款方式dto
 * </p>
 *
 * @author yang.cai@hand-china.com 2019/02/19 11:15
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_payment_method")
@MultiLanguage
public class CshPaymentMethod extends BaseDTO {

    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_PAYMENT_METHOD_CODE = "paymentMethodCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PAY_METHOD_CODE = "payMethodCode";
    public static final String FIELD_PAY_CARRIER_CODE = "payCarrierCode";
    public static final String FIELD_POSTING_FLAG = "postingFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    /**
     * 支付方式
     * 
     * @PAY_CODE_METHOD_10 网银
     * @PAY_CODE_METHOD_20 报盘
     * @PAY_CODE_METHOD_30 系统外
     */
    public static final String PAY_CODE_METHOD_10 = "10";
    public static final String PAY_CODE_METHOD_20 = "20";
    public static final String PAY_CODE_METHOD_30 = "30";

    /**
     * 支付载体
     * 
     * @PAY_CARRIER_10 现金
     * @PAY_CARRIER_20 票据
     */
    public static final String PAY_CARRIER_10 = "10";
    public static final String PAY_CARRIER_20 = "20";

    @Id
    @GeneratedValue
    private Long paymentMethodId;

    /**
     * 付款方式代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String paymentMethodCode;

    /**
     * 付款方式名称
     */
    @Length(max = 500)
    @MultiLanguageField
    @Column(name = "description")
    @Where
    private String description;

    /**
     * 支付方式（SYSCODE：CSH_PAY_METHOD）
     */
    @NotEmpty
    @Length(max = 30)
    private String payMethodCode;

    /**
     * 支付载体（SYSCODE：CSH_PAY_CARRIER）
     */
    @NotEmpty
    @Length(max = 30)
    private String payCarrierCode;

    /**
     * 直接过账标志
     */
    @NotEmpty
    @Length(max = 1)
    private String postingFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
