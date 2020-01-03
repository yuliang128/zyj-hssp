package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
 * 付款条款dto
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/19 11:13
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_payment_term")
@MultiLanguage
public class CshPaymentTerm extends BaseDTO {

    public static final String FIELD_PAYMENT_TERM_ID = "paymentTermId";
    public static final String FIELD_PAYMENT_TERM_CODE = "paymentTermCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_DUE_DATES = "dueDates";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long paymentTermId;

    /**
     * 条款代码
     */
    @NotEmpty
    @Length(max = 30)
    private String paymentTermCode;

    /**
     * 描述
     */
    @NotEmpty
    @Length(max = 500)
    @MultiLanguageField
    private String description;

    /**
     * 付款期限(天)
     */
    @NotNull
    private Long dueDates;

    /**
     * 启用标志(Y启用，N不启用)
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
