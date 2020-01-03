package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_payment_batch_rule_dtl")
public class CshPaymentBatchRuleDtl extends BaseDTO {

    public static final String FIELD_FILTRATE_METHOD = "filtrateMethod";
    public static final String FIELD_UPPER_LIMIT = "upperLimit";
    public static final String FIELD_LOWER_LIMIT = "lowerLimit";
    public static final String FIELD_INVALID_DATE = "invalidDate";
    public static final String FIELD_RULE_DTL_ID = "ruleDtlId";
    public static final String FIELD_RULE_LNS_ID = "ruleLnsId";
    public static final String FIELD_RULE_PARAMETER = "ruleParameter";

    /**
     * 增加付款批则规则参数
     *
     * @PARAMETER_DOCUMENT_CATEGORY 单据类别
     * @PARAMETER_DOCUMENT_TYPE 单据类型
     * @PARAMETER_PAYEE 收款方
     * @PARAMETER_PAYEE_CATEGORY 收款方类型
     * @PARAMETER_PAYMENT_ACCOUNT 收款方账户
     * @PARAMETER_PAYMENT_CURRENCY 付款币种
     * @PARAMETER_PAYMENT_METHOD 付款方式
     * @PARAMETER_PAYMENT_USEDE 付款用途
     */
    public static final String PARAMETER_DOCUMENT_CATEGORY = "DOCUMENT_CATEGORY";
    public static final String PARAMETER_DOCUMENT_TYPE = "DOCUMENT_TYPE";
    public static final String PARAMETER_PAYEE = "PAYEE";
    public static final String PARAMETER_PAYEE_CATEGORY = "PAYEE_CATEGORY";
    public static final String PARAMETER_PAYMENT_ACCOUNT = "PAYMENT_ACCOUNT";
    public static final String PARAMETER_PAYMENT_CURRENCY = "PAYMENT_CURRENCY";
    public static final String PARAMETER_PAYMENT_METHOD = "PAYMENT_METHOD";
    public static final String PARAMETER_PAYMENT_USEDE = "PAYMENT_USEDE";

    /**
     * 取值方式（SYSCODE：WFL_WORKFLOW_FILTRATE_METHOD）
     */
    @NotEmpty
    @Length(max = 30)
    private String filtrateMethod;

    /**
     * 上限
     */
    @Length(max = 30)
    private String upperLimit;

    /**
     * 下限
     */
    @Length(max = 30)
    private String lowerLimit;

    /**
     * 失效日期
     */
    private Date invalidDate;

    @Id
    @GeneratedValue
    private Long ruleDtlId;

    /**
     * 付款批规则明细ID
     */
    @NotNull
    @Where
    private Long ruleLnsId;

    /**
     * 规则参数（SYSCODE：CSH_PAYMENT_TRX_RULE_PARAMETER）
     */
    @NotEmpty
    @Length(max = 30)
    private String ruleParameter;

}
