package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_doc_pay_acc_entity")
public class CshDocPayAccEntity extends BaseDTO {

    public static final String FIELD_ENTITY_ID = "entityId";
    public static final String FIELD_DOC_CATEGORY = "docCategory";
    public static final String FIELD_DOC_COMPANY_ID = "docCompanyId";
    public static final String FIELD_DOC_TYPE_ID = "docTypeId";
    public static final String FIELD_DOC_ID = "docId";
    public static final String FIELD_DOC_LINE_ID = "docLineId";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_PAYMENT_ACC_ENTITY_ID = "paymentAccEntityId";
    public static final String FIELD_PAYMENT_STATUS = "paymentStatus";

    /**
     * 补充单据支付数据相关字段
     */
    public static final String FIELD_DOC_NUMBER = "docNumber";
    public static final String FIELD_DOC_TYPE_CODE = "docTypeCode";
    public static final String FIELD_DOC_TYPE_NAME = "docTypeName";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_ACC_ENTITY_NAME = "accEntityName";
    public static final String FIELD_PAYEE_CATEGORY_NAME = "payeeCategoryName";
    public static final String FIELD_PAYEE_ID = "payeeId";
    public static final String FIELD_PAYEE_CODE = "payeeCode";
    public static final String FIELD_PAYEE_NAME = "payeeName";
    public static final String FIELD_ACCOUNT_NAME = "accountName";
    public static final String FIELD_ACCOUNT_NUMBER = "accountNumber";
    public static final String FIELD_BANK_NAME = "bankName";
    public static final String FIELD_PAYMENT_METHOD_CODE = "paymentMethodCode";
    public static final String FIELD_PAYMENT_METHOD_NAME = "paymentMethodName";
    public static final String FIELD_SCHEDULE_PAYMENT_DATE = "schedulePaymentDate";
    public static final String FIELD_REQUISITION_DATE = "requisitionDate";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_CURRENCY_NAME = "currencyName";
    public static final String FIELD_PAYMENT_USEDE_ID = "paymentUsedeId";
    public static final String FIELD_PAYMENT_USEDE_CODE = "paymentUsedeCode";
    public static final String FIELD_PAYMENT_USEDE_NAME = "paymentUsedeName";
    public static final String FIELD_NEED_PAYMENT_AMOUNT = "needPaymentAmount";
    public static final String FIELD_PAYED_AMOUNT = "payedAmount";
    public static final String FIELD_UNPAYMENT_AMOUNT = "unPaymentAmount";
    public static final String FIELD_PAYMENT_AMOUNT = "paymentAmount";
    public static final String FIELD_CASH_FLOW_ITEM_ID = "cashFlowItemId";
    public static final String FIELD_RULE_ID = "ruleId";
    public static final String FIELD_PAYMENT_CURRENCY_PRECISION = "paymentCurrencyPrecision";
    public static final String FIELD_MO_CSH_TRX_CLASS_ID = "moCshTrxClassId";
    /**
     * 增加单据支付类型
     */
    public static final String DOC_EXP_REPORT = "EXP_REPORT";
    public static final String DOC_PAYMENT_REQUISITION = "PAYMENT_REQUISITION";
    public static final String DOC_ACP_REQUISITION = "ACP_REQUISITION";

    /**
     * 支付状态
     * 
     * @STATUS_FROZEN 冻结
     * @STATUS_NEVER 无需支付
     * @STATUS_ALLOWED 已确认
     * @STATUS_PENDING 待支付
     * @STATUS_PAID 已支付
     */
    public static final String STATUS_FROZEN = "FROZEN";
    public static final String STATUS_NEVER = "NEVER";
    public static final String STATUS_ALLOWED = "ALLOWED";
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_PAID = "PAID";


    @Id
    @GeneratedValue
    private Long entityId;

    /**
     * 单据类别
     */
    @NotEmpty
    @Length(max = 30)
    private String docCategory;

    /**
     * 单据公司ID
     */
    @NotNull
    private Long docCompanyId;

    /**
     * 单据类型ID
     */
    @NotNull
    private Long docTypeId;

    /**
     * 单据ID
     */
    @NotNull
    private Long docId;

    /**
     * 单据行ID
     */
    @NotNull
    private Long docLineId;

    /**
     * 付款方式
     */
    private Long paymentMethodId;

    /**
     * 收款方类别
     */
    @Length(max = 30)
    private String payeeCategory;

    /**
     * 付款核算主体ID
     */
    @NotNull
    private Long paymentAccEntityId;

    /**
     * 支付状态（NEVER无需支付，FROZEN冻结，PENDING待支付，ALLOWED已确认，PAID已支付）
     */
    @NotEmpty
    @Length(max = 30)
    private String paymentStatus;

    /**
     * 单据编号
     */
    @Transient
    private String docNumber;

    /**
     * 单据类型代码
     */
    @Transient
    private String docTypeCode;

    /**
     * 单据类型名称
     */
    @Transient
    private String docTypeName;

    /**
     * 单据核算主体
     */
    @Transient
    private Long accEntityId;

    /**
     * 单据核算主体名称
     */
    @Transient
    private String accEntityName;

    /**
     * 收款对象名称
     */
    @Transient
    private String payeeCategoryName;

    /**
     * 收款方ID
     */
    @Transient
    private Long payeeId;

    /**
     * 收款方名称
     */
    @Transient
    private String payeeName;

    /**
     * 银行户名
     */
    @Transient
    private String accountName;

    /**
     * 账户
     */
    @Transient
    private String accountNumber;


    /**
     * 收款方银行
     */
    @Transient
    private String bankName;

    /**
     * 付款方式代码
     */
    @Transient
    private String paymentMethodCode;

    /**
     * 付款方式名称
     */
    @Transient
    private String paymentMethodName;

    /**
     * 计划付款日期
     */
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date schedulePaymentDate;

    /**
     * 报销日期
     */
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date requisitionDate;

    /**
     * 币种代码
     */
    @Transient
    private String currencyCode;

    /**
     * 币种名称
     */
    @Transient
    private String currencyName;

    /**
     * 付款用途ID
     */
    @Transient
    private Long paymentUsedeId;

    /**
     * 付款用途代码
     */
    @Transient
    private String paymentUsedeCode;

    /**
     * 付款用途名称
     */
    @Transient
    private String paymentUsedeName;

    /**
     * 待付金额
     */
    @Transient
    private BigDecimal needPaymentAmount;

    /**
     * 已付金额
     */
    @Transient
    private BigDecimal payedAmount;

    /**
     * 未付金额
     */
    @Transient
    private BigDecimal unPaymentAmount;

    /**
     * 本次付款金额
     */
    @Transient
    private BigDecimal paymentAmount;

    /**
     * 现金流量项
     */
    @Transient
    private Long cashFlowItemId;

    /**
     * 现金事务生成规则ID
     */
    @Transient
    private Long ruleId;

    /**
     * 支付币种财务精度
     */
    @Transient
    private Long paymentCurrencyPrecision;

    /**
     * 支付币种财务精度
     */
    @Transient
    private Long moCshTrxClassId;

    /**
     * @Description 单据支付核算主体构造方法
     *
     * @Author Tagin
     * @Date 2019-03-15 14:00
     * @param docCategory
     * @param docCompanyId
     * @param docTypeId
     * @param docId
     * @param docLineId
     * @param paymentMethodId
     * @param payeeCategory
     * @param paymentAccEntityId
     * @param paymentStatus
     * @Return
     * @Version 1.0
     **/
    public CshDocPayAccEntity(String docCategory, Long docCompanyId, Long docTypeId, Long docId, Long docLineId,
                    Long paymentMethodId, String payeeCategory, Long paymentAccEntityId, String paymentStatus) {
        this.docCategory = docCategory;
        this.docCompanyId = docCompanyId;
        this.docTypeId = docTypeId;
        this.docId = docId;
        this.docLineId = docLineId;
        this.paymentMethodId = paymentMethodId;
        this.payeeCategory = payeeCategory;
        this.paymentAccEntityId = paymentAccEntityId;
        this.paymentStatus = paymentStatus;
    }

}
