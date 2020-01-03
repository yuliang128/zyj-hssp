package com.hand.hec.csh.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_transaction_line")

/**
 * <p>
 * 现金事务行DTO
 * </p>
 * 
 * @author Tagin 2019/01/22 19:58
 */
public class CshTransactionLine extends BaseDTO {

    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_EXCHANGE_RATE_TYPE = "exchangeRateType";
    public static final String FIELD_EXCHANGE_RATE = "exchangeRate";
    public static final String FIELD_BANK_ACCOUNT_ID = "bankAccountId";
    public static final String FIELD_DOCUMENT_NUM = "documentNum";
    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_PAYEE_ID = "payeeId";
    public static final String FIELD_PAYEE_BANK_ACCOUNT_ID = "payeeBankAccountId";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_HANDLING_CHARGE = "handlingCharge";
    public static final String FIELD_INTEREST = "interest";
    public static final String FIELD_AGENT_EMPLOYEE_ID = "agentEmployeeId";
    public static final String FIELD_TRANS_IN_OUT_TYPE = "transInOutType";
    public static final String FIELD_DOCUMENT_CATEGORY = "documentCategory";
    public static final String FIELD_DOCUMENT_TYPE_ID = "documentTypeId";
    public static final String FIELD_PAYMENT_USEDE_ID = "paymentUsedeId";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_TRANSACTION_LINE_ID = "transactionLineId";
    public static final String FIELD_TRANSACTION_HEADER_ID = "transactionHeaderId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_TRANSACTION_AMOUNT = "transactionAmount";

    /**
     * 增加付款批类型ID字段
     */
    public static final String FIELD_BATCH_TYPE_ID = "typeId";
    public static final String FIELD_ACCOUNT_NAME = "accountName";
    public static final String FIELD_ACCOUNT_NUMBER = "accountNumber";

    /**
     * 收款方类型
     */
    public static final String PAYEE_CATEGORY_EMPLOYEE = "EMPLOYEE";
    public static final String PAYEE_CATEGORY_VENDER = "VENDER";
    public static final String PAYEE_CATEGORY_CUSTOMER = "CUSTOMER";

    /**
     * 币种
     */
    @NotEmpty
    @Length(max = 30)
    private String currencyCode;

    /**
     * 汇率类型
     */
    @Length(max = 30)
    private String exchangeRateType;

    /**
     * 汇率
     */
    private BigDecimal exchangeRate;

    /**
     * 付款方银行账户
     */
    private Long bankAccountId;

    /**
     * 单据编号
     */
    @Length(max = 30)
    private String documentNum;

    /**
     * 收款方类别
     */
    @Length(max = 30)
    private String payeeCategory;

    /**
     * 收款方ID
     */
    private Long payeeId;

    /**
     * 收款方银行账户
     */
    private Long payeeBankAccountId;

    /**
     * 描述
     */
    @Length(max = 2000)
    private String description;

    /**
     * 手续费
     */
    private BigDecimal handlingCharge;

    /**
     * 利息
     */
    private BigDecimal interest;

    /**
     * 经办人
     */
    @NotNull
    private Long agentEmployeeId;

    /**
     * 转入转出标志
     */
    @Length(max = 30)
    private String transInOutType;

    /**
     * 单据类别
     */
    @Length(max = 30)
    private String documentCategory;

    /**
     * 单据类型ID
     */
    private Long documentTypeId;

    /**
     * 付款用途ID
     */
    private Long paymentUsedeId;

    /**
     * 付款方式ID
     */
    private Long paymentMethodId;

    @Id
    @GeneratedValue
    private Long transactionLineId;

    /**
     * 现金事务头ID
     */
    @NotNull
    private Long transactionHeaderId;

    /**
     * 公司
     */
    @NotNull
    private Long companyId;

    /**
     * 核算主体
     */
    @NotNull
    private Long accEntityId;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;

    /**
     * 付款批类型
     */
    @Transient
    private Long typeId;

    /**
     * 收方银行户名
     */
    @Transient
    private String accountName;

    /**
     * 收房银行账号
     */
    @Transient
    private String accountNumber;


    @Transient
    private String payeeCategoryName;

    @Transient
    private String payeeName;

    @Transient
    private String agentEmployeeName;

    @Transient
    private String paymentEmployeeName;

    @Transient
    private String returnedFlagName;

    @Transient
    private String reversedFlagName;

    @Transient
    private String transactionNum;

    @Transient
    private Date transactionDate;
}
