package com.hand.hec.csh.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_write_off")

/**
 * <p>
 * 核销表DTO
 * </p>
 * 
 * @author Tagin 2019.01.22 09:47
 */
public class CshWriteOff extends BaseDTO {

    public static final String FIELD_WRITE_OFF_ID = "writeOffId";
    public static final String FIELD_WRITE_OFF_TYPE = "writeOffType";
    public static final String FIELD_CSH_TRANSACTION_LINE_ID = "cshTransactionLineId";
    public static final String FIELD_CSH_WRITE_OFF_AMOUNT = "cshWriteOffAmount";
    public static final String FIELD_DOCUMENT_SOURCE = "documentSource";
    public static final String FIELD_DOCUMENT_HEADER_ID = "documentHeaderId";
    public static final String FIELD_DOCUMENT_LINE_ID = "documentLineId";
    public static final String FIELD_DOCUMENT_WRITE_OFF_AMOUNT = "documentWriteOffAmount";
    public static final String FIELD_WRITE_OFF_DATE = "writeOffDate";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_SOURCE_CSH_TRX_LINE_ID = "sourceCshTrxLineId";
    public static final String FIELD_SOURCE_WRITE_OFF_AMOUNT = "sourceWriteOffAmount";
    public static final String FIELD_GLD_INTERFACE_FLAG = "gldInterfaceFlag";
    public static final String FIELD_DOCUMENT_ID = "documentId";
    /**
     * 增加报销单核销借款相关字段
     */
    public static final String FIELD_TRANSACTION_HEADER_ID = "transactionHeaderId";
    public static final String FIELD_REQUISITION_HEADER_ID = "requisitionHeaderId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_TRANSACTION_NUM = "transactionNum";
    public static final String FIELD_TRANSACTION_DATE = "transactionDate";
    public static final String FIELD_TRANSACTION_AMOUNT = "transactionAmount";
    public static final String FIELD_UNWRITE_OFF_AMOUNT = "unWriteOffAmount";
    public static final String FIELD_WRITE_OFF_TYPE_NAME = "writeOffTypeName";
    public static final String FIELD_REQUISITION_NUMBER = "requisitionNumber";
    public static final String FIELD_REQUISITION_TYPE_NAME = "requisitionTypeName";
    public static final String FIELD_PAYEE_CATEGORY_NAME = "payeeCategoryName";
    public static final String FIELD_PAYEE_NAME = "payeeName";
    public static final String FIELD_DUE_AMOUNT = "dueAmount";
    public static final String FIELD_CAN_AMOUNT = "canAmount";
    public static final String FIELD_CONTRACT_NUMBER = "contractNumber";
    public static final String FIELD_LINE_NUMBER = "lineNumber";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_AUDIT_DATE = "auditDate";
    public static final String FIELD_MO_CSH_TRX_CLASS_ID = "moCshTrxClassId";
    public static final String FIELD_MO_CSH_TRX_CLASS_NAME = "moCshTrxClassName";


    /**
     * 核销类别
     * 
     * @ACP_PAYMENT 付款单支付核销
     * @ACP_PREPAYMENT 付款单支付核销预付款
     * @CSH_RETURN 现金退款
     * @PAYMENT_EXPENSE_REPORT 付款核销报销单
     * @PAYMENT_PREPAYMENT 付款核销预付款
     * @PREPAYMENT_ACP_REQUISITION 付款单预付款核销报销单/合同
     * @PREPAYMENT_EXPENSE_REPORT 预付款核销报销单
     */
    public static final String ACP_PAYMENT = "ACP_PAYMENT";
    public static final String ACP_PREPAYMENT = "ACP_PREPAYMENT";
    public static final String CSH_RETURN = "CSH_RETURN";
    public static final String PAYMENT_EXPENSE_REPORT = "PAYMENT_EXPENSE_REPORT";
    public static final String PAYMENT_PREPAYMENT = "PAYMENT_PREPAYMENT";
    public static final String PREPAYMENT_ACP_REQUISITION = "PREPAYMENT_ACP_REQUISITION";
    public static final String PREPAYMENT_EXPENSE_REPORT = "PREPAYMENT_EXPENSE_REPORT";

    /**
     * 核销标志
     * 
     * @WRITE_OFF_FLAG_N 未核销
     * @WRITE_OFF_FLAG_Y 部分核销
     * @WRITE_OFF_FLAG_C 完全核销
     */
    public static final String WRITE_OFF_FLAG_N = "N";
    public static final String WRITE_OFF_FLAG_Y = "Y";
    public static final String WRITE_OFF_FLAG_C = "C";

    public static final String GLD_INTERFACE_FLAG_Y = "Y";
    public static final String GLD_INTERFACE_FLAG_P = "P";
    public static final String GLD_INTERFACE_FLAG_N = "N";

    /**
     * 来源单据类型
     * 
     * @DOC_SOURCE_PAYMENT_REQUISITION 借款申请单
     * @DOC_SOURCE_ACP_REQUISITION 付款申请单
     * @DOC_SOURCE_EXPENSE_REPORT 费用报销单
     * @DOC_SOURCE_CON_CONTRACT 合同
     */
    public static final String DOC_SOURCE_PAYMENT_REQUISITION = "PAYMENT_REQUISITION";
    public static final String DOC_SOURCE_ACP_REQUISITION = "ACP_REQUISITION";
    public static final String DOC_SOURCE_EXPENSE_REPORT = "EXPENSE_REPORT";
    public static final String DOC_SOURCE_CON_CONTRACT = "CON_CONTRACT";


    @Id
    @GeneratedValue
    private Long writeOffId;

    /**
     * 核销类型
     */
    @NotEmpty
    @Length(max = 30)
    private String writeOffType;

    /**
     * 现金事务行ID
     */
    private Long cshTransactionLineId;

    /**
     * 核销金额
     */
    private BigDecimal cshWriteOffAmount;

    /**
     * 单据来源
     */
    @Length(max = 30)
    private String documentSource;

    /**
     * 单据头ID
     */
    private Long documentHeaderId;

    /**
     * 单据行ID
     */
    private Long documentLineId;

    /**
     * 单据核销金额
     */
    private BigDecimal documentWriteOffAmount;

    /**
     * 核销日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date writeOffDate;

    /**
     * 期间
     */
    @NotEmpty
    @Length(max = 30)
    private String periodName;

    /**
     * 来源现金事务行ID
     */
    private Long sourceCshTrxLineId;

    /**
     * 来源核销金额
     */
    private BigDecimal sourceWriteOffAmount;

    /**
     * 总账接口标志（P已过，N未过）
     */
    @Length(max = 1)
    private String gldInterfaceFlag;

    /**
     * 现金事务头ID
     */
    @Transient
    private Long transactionHeaderId;

    /**
     * 借款申请单、付款申请单头ID
     */
    @Transient
    private Long requisitionHeaderId;

    /**
     * 核算主体ID
     */
    @Transient
    private Long accEntityId;

    /**
     * 现金事务编号
     */
    @Transient
    private String transactionNum;

    /**
     * 现金事务日期
     */
    @Transient
    private Date transactionDate;

    /**
     * 现金事务金额
     */
    @Transient
    private BigDecimal transactionAmount;

    /**
     * 未核销金额
     */
    @Transient
    private BigDecimal unWriteOffAmount;

    /**
     * 核销类型名称
     */
    @Transient
    private String writeOffTypeName;

    /**
     * 单据编号
     */
    @Transient
    private String requisitionNumber;

    /**
     * 单据类型名称
     */
    @Transient
    private String requisitionTypeName;

    /**
     * 收款方类型
     */
    @Transient
    private String payeeCategoryName;

    /**
     * 收款方
     */
    @Transient
    private String payeeName;

    /**
     * 报销金额
     */
    @Transient
    private BigDecimal dueAmount;

    /**
     * 可核销金额
     */
    @Transient
    private BigDecimal canAmount;

    /**
     * 合同编号
     */
    @Transient
    private String contractNumber;

    /**
     * 合同计划付款行号
     */
    @Transient
    private Long lineNumber;

    /**
     * 本次核销金额
     */
    @Transient
    private BigDecimal amount;

    /**
     * 审核日期
     */
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date auditDate;

    /**
     * 核销类别
     */
    @Transient
    private Long moCshTrxClassId;

    /**
     * 核销类别
     */
    @Transient
    private String moCshTrxClassName;

    /**
     * 单据 ID(单据头 ID、单据行 ID)
     */
    @Transient
    private Long documentId;

	/**
	 * 报销单头ID
	 */
	@Transient
	private String expReportId;

    /**
	 * 报销单编号
	 */
	@Transient
	private String expReportNumber;

	/**
	 * 反冲日期
	 */
	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date reverseDate;

}
