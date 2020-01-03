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
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_payment_message")
public class CshPaymentMessage extends BaseDTO {

    public static final String FIELD_MESSAGE_ID = "messageId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_PAYEE_ID = "payeeId";
    public static final String FIELD_PAYEE_DATE = "payeeDate";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_ACCOUNT_NAME = "accountName";
    public static final String FIELD_ACCOUNT_NUMBER = "accountNumber";
    public static final String FIELD_DOCUMENT_TYPE = "documentType";
    public static final String FIELD_DOCUMENT_ID = "documentId";
    public static final String FIELD_DOCUMENT_NUMBER = "documentNumber";
    public static final String FIELD_DOCUMENT_LINE_ID = "documentLineId";
    public static final String FIELD_EBANKING_FLAG = "ebankingFlag";
    public static final String FIELD_OFFER_FLAG = "offerFlag";


    @Id
    @GeneratedValue
    private Long messageId;

    /**
     * 核算主体ID
     */
    private Long accEntityId;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 币种
     */
    @Length(max = 30)
    private String currencyCode;

    /**
     * 收款对象
     */
    @Length(max = 30)
    private String payeeCategory;

    /**
     * 收款方
     */
    private Long payeeId;

    /**
     * 费控提请付款时间
     */
    private Date payeeDate;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 描述
     */
    @Length(max = 2000)
    private String description;

    /**
     * 付款方式ID
     */
    private Long paymentMethodId;

    /**
     * 银行户名
     */
    @Length(max = 100)
    private String accountName;

    /**
     * 银行账号
     */
    @Length(max = 100)
    private String accountNumber;

    /**
     * 单据类型（EXP_REPORT/费用报销单，PAYMENT_REQUISITION/借款申请单，ACP_REQUISITION/付款申请单）
     */
    @Length(max = 100)
    private String documentType;

    /**
     * 单据ID
     */
    private Long documentId;

    /**
     * 单据编号
     */
    @Length(max = 30)
    private String documentNumber;

    /**
     * 单据明细ID（费用报销单为计划付款行ID，借款申请单/付款申请单为行ID）
     */
    private Long documentLineId;

    /**
     * 网银标志
     */
    @NotEmpty
    @Length(max = 1)
    private String ebankingFlag;

    /**
     * 报盘标志
     */
    @NotEmpty
    @Length(max = 1)
    private String offerFlag;

}
