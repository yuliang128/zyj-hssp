package com.hand.hec.csh.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.annotation.Children;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_payment_requisition_hd")
public class CshPaymentRequisitionHd extends BaseDTO {
    //单据状态
    public static final String STATUS_GENERATE = "GENERATE";
    public static final String STATUS_SUBMITTED = "SUBMITTED";
    public static final String STATUS_COMPLETELY_APPROVED = "COMPLETELY_APPROVED";
    public static final String STATUS_REJECTED = "REJECTED";
    public static final String STATUS_CLOSED = "CLOSED";
    public static final String STATUS_TAKEN_BACK = "TAKEN_BACK";
    public static final String STATUS_RETURN_BACK = "RETURN_BACK";
    public static final String STATUS_CANCEL_RETURN_BACK = "CANCEL_RETURN_BACK";
    public static final String STATUS_PAY_BACK = "PAY_BACK";
    public static final String STATUS_CANCEL_PAY_BACK = "CANCEL_PAY_BACK";

    /**
     * 付款状态
     */
    public static final String STATUS_NEVER = "NEVER";
    public static final String STATUS_PARTIALLY = "PARTIALLY";
    public static final String STATUS_COMPLETELY = "COMPLETELY";
    /**
     * 事件触发Module
     */
    public static final String CSH_PAYMENT_REQUISITION = "CSH_PAYMENT_REQUISITION";
    /**
     * 工作流类型
     */
    public static final String PAYMENT_REQUISITION = "PAYMENT_REQUISITION";

    public static final String FIELD_DOC_STATUS = "docStatus";
    public static final String FIELD_PAYMENT_REQUISITION_HEADER_ID = "paymentRequisitionHeaderId";
    public static final String FIELD_REQUISITION_NUMBER = "requisitionNumber";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_OPERATION_UNIT_ID = "operationUnitId";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_REQUISITION_DATE = "requisitionDate";
    public static final String FIELD_REQUISITION_DATE_TZ = "requisitionDateTz";
    public static final String FIELD_REQUISITION_DATE_LTZ = "requisitionDateLtz";
    public static final String FIELD_PAYMENT_REQ_TYPE_ID = "paymentReqTypeId";
    public static final String FIELD_TRANSACTION_CATEGORY = "transactionCategory";
    public static final String FIELD_DISTRIBUTION_SET_ID = "distributionSetId";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_PAYEE_ID = "payeeId";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_REQUISITION_PAYMENT_DATE = "requisitionPaymentDate";
    public static final String FIELD_REQUISITION_PAYMENT_DATE_TZ = "requisitionPaymentDateTz";
    public static final String FIELD_REQUISITION_PAYMENT_DAstatusTE_LTZ = "requisitionPaymentDateLtz";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_APPROVAL_DATE = "approvalDate";
    public static final String FIELD_APPROVED_BY = "approvedBy";
    public static final String FIELD_CLOSED_DATE = "closedDate";
    public static final String FIELD_CLOSED_DATE_TZ = "closedDateTz";
    public static final String FIELD_CLOSED_DATE_LTZ = "closedDateLtz";
    public static final String FIELD_SOURCE_TYPE = "sourceType";
    public static final String FIELD_AUDIT_FLAG = "auditFlag";
    public static final String FIELD_AUDIT_DATE = "auditDate";
    public static final String FIELD_REVERSED_FLAG = "reversedFlag";
    public static final String FIELD_SOURCE_PMT_REQ_HEADER_ID = "sourcePmtReqHeaderId";
    public static final String FIELD_JE_CREATION_STATUS = "jeCreationStatus";
    public static final String FIELD_EXPENSE_REQUISITION_HEADER_ID = "expenseRequisitionHeaderId";
    public static final String FIELD_ATTACHMENT_NUM = "attachmentNum";
    public static final String FIELD_SCHEDULE_REPAYMENT_DATE = "scheduleRepaymentDate";
    public static final String FIELD_PAYMENT_REQUISITION_LNS = "paymentRequisitionLns";

	public static final String FIELD_REQUISITOR ="requisitor";
	public static final String FIELD_CURRENCY_NAME ="currencyName";
	public static final String FIELD_PAYEE_CATEGORY_VALUE ="payeeCategoryName";
	public static final String FIELD_PAYEE_NAME ="payeeName";

    public static final String FIELD_REQ_TYPE_DESC ="reqTypeDesc";
    public static final String FIELD_EMPLOYEE_NAME ="employeeName";
    public static final String FIELD_CATEGORY_DESC ="categoryDesc";
    public static final String FIELD_PAR_PARTNER_NAME ="parPartnerName";
    public static final String FIELD_AMOUNT_FROM ="amountFrom";
    public static final String FIELD_AMOUNT_TO ="amountTo";


    /**
     * 退回状态（SYSCODE：CSH_DOC_BACK）
     */
    @Length(max = 30)
    private String docStatus;

    @Id
    @GeneratedValue
    private Long paymentRequisitionHeaderId;

    /**
     * 申请编号
     */
    @NotEmpty
    @Length(max = 30)
    private String requisitionNumber;

    /**
     * 管理公司ID
     */
    @NotNull
    private Long companyId;

    /**
     * 废弃_经营单位ID
     */
    private Long operationUnitId;

    /**
     * 部门ID
     */
    @NotNull
    private Long unitId;

    /**
     * 岗位ID
     */
    @NotNull
    private Long positionId;

    /**
     * 员工ID
     */
    @NotNull
	@Where
	@JoinTable(name = "employeeJoin", target = ExpEmployee.class, type = JoinType.LEFT,
			on = {@JoinOn(joinField = ExpEmployee.FIELD_EMPLOYEE_ID)})
    private Long employeeId;

    /**
     * 核算主体ID
     */
    @NotNull
    private Long accEntityId;

    /**
     * 申请日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date requisitionDate;

    /**
     * 申请日期_业务时区
     */
    private Timestamp requisitionDateTz;

    /**
     * 申请日期_查询时区
     */
    private Timestamp requisitionDateLtz;

    /**
     * 借款申请类型ID
     */
    private Long paymentReqTypeId;

    /**
     * 事务类型（BUSINESS经营类，MISCELLANEOUS杂项类）
     */
    @NotEmpty
    @Length(max = 30)
    private String transactionCategory;

    /**
     * 废弃_分配集
     */
    private Long distributionSetId;

    /**
     * 支付方式ID
     */
    private Long paymentMethodId;

    /**
     * 对象类型（EMPLOYEE员工，VENDER供应商）
     */
    @NotEmpty
    @Length(max = 30)
    private String payeeCategory;

    /**
     * 收款对象ID
     */
    @NotNull
    private Long payeeId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    @NotEmpty
    @Length(max = 30)
    private String currencyCode;

    /**
     * 付款日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date requisitionPaymentDate;

    /**
     * 付款日期_业务时区
     */
    private Date requisitionPaymentDateTz;

    /**
     * 付款日期_查询时区
     */
    private Date requisitionPaymentDateLtz;

    /**
     * 描述
     */
    @Length(max = 4000)
    private String description;

    /**
     * 状态（COMPLETELY_APPROVED审批通过，CLOSED关闭，GENERATE新建，REJECTED拒绝，SUBMITTED提交，TAKEN_BACK收回）
     */
    @NotEmpty
    @Length(max = 30)
    private String status;

    /**
     * 审批日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date approvalDate;

    /**
     * 审批人
     */
    private Long approvedBy;

    /**
     * 关闭日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date closedDate;

    /**
     * 关闭日期_业务时区
     */
    private Date closedDateTz;

    /**
     * 关闭日期_查询时区
     */
    private Date closedDateLtz;

    /**
     * 来源类型
     */
    @Length(max = 30)
    private String sourceType;

    /**
     * 审核标志
     */
    @NotEmpty
    @Length(max = 1)
    private String auditFlag;

    /**
     * 审核日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date auditDate;

    /**
     * 反冲标志
     */
    @NotEmpty
    @Length(max = 1)
    private String reversedFlag;

    /**
     * 申请单头id
     */
    private Long sourcePmtReqHeaderId;

    /**
     * 凭证标识
     */
    @Length(max = 30)
    private String jeCreationStatus;

    /**
     * 所属费用申请单头ID
     */
    private Long expenseRequisitionHeaderId;

    /**
     * 附件数
     */
    private Long attachmentNum;

    /**
     * 计划还款日
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date scheduleRepaymentDate;

    /**
     * 行信息
     */
    @Transient
    @Children
    private List<CshPaymentRequisitionLn> paymentRequisitionLns;

	/**
	 * 员工名称
	 */
	@Transient
	public String requisitor;

	/**
	 * 币种名称
	 */
	@Transient
	public String currencyName;

	/**
	 * 对象类型
	 */
	@Transient
	public String payeeCategoryName;

	/**
	 * 收款方名称
	 */
	@Transient
	public String payeeName;

	/**
	 * 申请日期从
	 */
	@Transient
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public String requisitionDateFrom;

	/**
	 * 申请日期到
	 */
	@Transient
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public String requisitionDateTo;

	/**
	 * 单据类型
	 */
	@Transient
	public String paymentReqTypeName;

	/**
	 * 反冲日期
	 */
	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date reverseDate;

    @Transient
    private String reqTypeDesc;

    @Transient
	@JoinColumn(joinName = "employeeJoin", field = ExpEmployee.FIELD_NAME)
    private String employeeName;

    @Transient
    private String categoryDesc;

    @Transient
    private String parPartnerName;

	/**
	 * 查询字段--创建日期范围
	 */
	@Transient
	private String cshPayReqDateScope;

	/**
	 * 查询字段--创建日期从
	 */
	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date createdDateFrom;

	/**
	 * 查询字段--创建日期到
	 */
	@Transient
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date createdDateTo;

	/**
	 * 制单人名称
	 */
	@Transient
	private String createdByName;

	/**
	 * 部门名称
	 */
	@Transient
	private String unitName;

	/**
	 * 核算主体名称
	 */
	@Transient
	private String accEntityName;

	/**
	 * 状态名称
	 */
	@Transient
	private String statusName;

	/**
	 * 付款方式名称
	 */
	@Transient
	private String paymentMethodName;

	/**
	 * 公司名称
	 */
	@Transient
	private String companyName;

    /**
     * 金额从
     */
    @Transient
    private BigDecimal amountFrom;

    /**
     * 金额到
     */
    @Transient
    private BigDecimal amountTo;

    /**
     * 银行户名
     */
    @Transient
    private String accountName;

    /**
     * 银行账号
     */
    @Transient
    private String accountNumber;

    /**
     * 岗位描述
     */
    @Transient
    private String positionIdDisplay;


}


