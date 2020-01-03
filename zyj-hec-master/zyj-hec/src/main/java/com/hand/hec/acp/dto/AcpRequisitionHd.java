package com.hand.hec.acp.dto;

/**
 * <p>
 * 付款申请头信息
 * </p>
 * 
 * @author guiyuting 2019/04/28 15:02
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
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
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "acp_requisition_hd")
public class AcpRequisitionHd extends BaseDTO {

    public static final String FIELD_REQUISITION_HDS_ID = "requisitionHdsId";
    public static final String FIELD_REQUISITION_NUMBER = "requisitionNumber";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_COMPANY_NAME = "companyName";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_UNIT_NAME = "unitName";
    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_POSITION_NAME = "positionName";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_EMPLOYEE_NAME = "employeeName";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_ACC_ENTITY_NAME = "accEntityName";
    public static final String FIELD_REQUISITION_DATE = "requisitionDate";
    public static final String FIELD_REQUISITION_DATE_TZ = "requisitionDateTz";
    public static final String FIELD_REQUISITION_DATE_LTZ = "requisitionDateLtz";
    public static final String FIELD_MO_PAY_REQ_TYPE_ID = "moPayReqTypeId";
    public static final String FIELD_MO_PAY_REQ_TYPE_NAME = "moPayReqTypeName";
    public static final String FIELD_TRANSACTION_CATEGORY = "transactionCategory";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_PAYMENT_METHOD_NAME = "paymentMethodName";
    public static final String FIELD_PAYMENT_USEDE_ID = "paymentUsedeId";
    public static final String FIELD_PAYMENT_USEDE_NAME = "paymentUsedeName";
    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_PAYEE_CATEGORY_NAME = "payeeCategoryName";
    public static final String FIELD_PAYEE_ID = "payeeId";
    public static final String FIELD_PAYEE_NAME = "payeeName";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_CURRENCY_NAME = "currencyName";
    public static final String FIELD_CURRENCY_SYMBOL = "currencySymbol";
    public static final String FIELD_TOTAL_AMOUNT = "totalAmount";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_REQUISITION_STATUS_NAME = "requisitionStatusName";
    public static final String FIELD_APPROVAL_DATE = "approvalDate";
    public static final String FIELD_APPROVAL_DATE_TZ = "approvalDateTz";
    public static final String FIELD_APPROVAL_DATE_LTZ = "approvalDateLtz";
    public static final String FIELD_APPROVED_BY = "approvedBy";
    public static final String FIELD_CLOSED_DATE = "closedDate";
    public static final String FIELD_CLOSED_DATE_TZ = "closedDateTz";
    public static final String FIELD_CLOSED_DATE_LTZ = "closedDateLtz";
    public static final String FIELD_JE_CREATION_STATUS = "jeCreationStatus";
    public static final String FIELD_AUDIT_FLAG = "auditFlag";
    public static final String FIELD_AUDIT_DATE = "auditDate";
    public static final String FIELD_AUDIT_DATE_TZ = "auditDateTz";
    public static final String FIELD_AUDIT_DATE_LTZ = "auditDateLtz";
    public static final String FIELD_REVERSED_FLAG = "reversedFlag";
    public static final String FIELD_SOURCE_TYPE = "sourceType";
    public static final String FIELD_SOURCE_HEADER_ID = "sourceHeaderId";
    public static final String FIELD_DOC_STATUS = "docStatus";
    public static final String FIELD_STATUS_NAME = "statusName";
    public static final String FIELD_CREATED_BY_NAME = "createdByName";
    public static final String FIELD_PAYMENT_AMOUNT = "paymentAmount";
    public static final String FIELD_PAYMENT_CURRENCY_NAME = "paymentCurrencyName";
    public static final String FIELD_PAYMENT_CURRENCY_CODE = "paymentCurrencyCode";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
    public static final String FIELD_FUNCTIONAL_CURRENCY_CODE = "functionalCurrencyCode";
    public static final String FIELD_RESP_CENTER_ID = "respCenterId";
    public static final String FIELD_ALREADY_PAYING = "alreadyPaying";

    // 付款申请头状态
    public static final String STATUS_GENERATE = "GENERATE";
    public static final String STATUS_SUBMITTED = "SUBMITTED";
    public static final String STATUS_APPROVED = "COMPLETELY_APPROVED";
    public static final String STATUS_REJECTED = "REJECTED";
    public static final String STATUS_CLOSED = "CLOSED";
    public static final String STATUS_TAKEN_BACK = "TAKEN_BACK";
    public static final String STATUS_PAID = "PAID";
    public static final String STATUS_COMPLETELY_PAID = "COMPLETELY_PAID";

    // 付款申请单头事务类型
    public static final String PAY_REQ_TYPE_BUSINESS = "BUSINESS";
    public static final String PAY_REQ_TYPE_MISCELLANEOUS = "MISCELLANEOUS";

    public static final String ACP_REQUISITION = "ACP_REQUISITION";

    public static final String PAYMENT_TYPE_REPORT = "REPORT";
    public static final String PAYMENT_TYPE_CONTRACT = "CONTRACT";


    @Id
    @GeneratedValue
    private Long requisitionHdsId;

    /**
     * 申请编号
     */
    @Length(max = 30)
    private String requisitionNumber;

    /**
     * 管理公司ID
     */
    @NotNull
    private Long companyId;

    @Transient
    private String companyName;

    /**
     * 部门ID
     */
    @NotNull
    private Long unitId;

    @Transient
    private String unitName;

    /**
     * 岗位ID
     */
    @NotNull
    private Long positionId;

    @Transient
    private String positionName;

    /**
     * 员工ID
     */
    @NotNull
    private Long employeeId;

    @Transient
    private String employeeName;

    /**
     * 核算主体ID
     */
    @NotNull
    private Long accEntityId;

    @Transient
    private String accEntityName;

    /**
     * 申请日期
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    private Date requisitionDate;

    /**
     * 申请日期_业务时区
     */
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Timestamp requisitionDateTz;

    /**
     * 申请日期_查询时区
     */
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Timestamp requisitionDateLtz;

    /**
     * 付款申请单类型ID
     */
    @NotNull
    private Long moPayReqTypeId;

    @Transient
    private String moPayReqTypeName;

    /**
     * 事务类型（BUSINESS经营类，MISCELLANEOUS杂项类）
     */
    @NotEmpty
    @Length(max = 30)
    private String transactionCategory;

    /**
     * 付款方式ID
     */
    private Long paymentMethodId;

    @Transient
    private String paymentMethodName;

    /**
     * 付款用途ID
     */
    private Long paymentUsedeId;

    @Transient
    private String paymentUsedeName;

    /**
     * 对象类型（EMPLOYEE员工，VENDER供应商，CUSTOMER客户）
     */
    @NotEmpty
    @Length(max = 30)
    private String payeeCategory;

    @Transient
    private String payeeCategoryName;

    /**
     * 收款对象ID
     */
    @NotNull
    private Long payeeId;

    @Transient
    private String payeeName;

    /**
     * 金额
     */
    private BigDecimal amount;

    @Transient
    private BigDecimal totalAmount;

    /**
     * 币种
     */
    @NotEmpty
    @Length(max = 30)
    private String currencyCode;

    @Transient
    private String currencyName;

    @Transient
    private String currencySymbol;

    /**
     * 描述
     */
    @Length(max = 2000)
    private String description;

    /**
     * 状态（APPROVED审批通过，CLOSED关闭，GENERATE新建，REJECTED拒绝，SUBMITTED提交，TAKEN_BACK收回）
     */
    @NotEmpty
    @Length(max = 30)
    private String status;

    /**
     * 审批
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date approvalDate;

    /**
     * 审批_业务时区
     */
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Timestamp approvalDateTz;

    /**
     * 审批_查询时区
     */
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Timestamp approvalDateLtz;

    /**
     * 审批人
     */
    private Long approvedBy;

    /**
     * 关闭日期
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date closedDate;

    /**
     * 关闭日期_业务时区
     */
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Timestamp closedDateTz;

    /**
     * 关闭日期_查询时区
     */
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Timestamp closedDateLtz;

    /**
     * 凭证创建状态
     */
    @Length(max = 30)
    private String jeCreationStatus;

    /**
     * 审核标志
     */
    @NotEmpty
    @Length(max = 1)
    private String auditFlag;

    /**
     * 审核日期
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date auditDate;

    /**
     * 审核日期_业务时区
     */
    private Date auditDateTz;

    /**
     * 审核日期_查询时区
     */
    private Date auditDateLtz;

    /**
     * 反冲标志
     */
    @NotEmpty
    @Length(max = 1)
    private String reversedFlag;

    /**
     * 来源类型
     */
    @Length(max = 30)
    private String sourceType;

    /**
     * 来源单据ID
     */
    private Long sourceHeaderId;

    /**
     * 退回状态（SYSCODE：CSH_DOC_BACK）
     */
    @Length(max = 30)
    private String docStatus;

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date approvedDateFrom;

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date approvedDateTo;

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date creationDateFrom;

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date creationDateTo;

    @Transient
    private String statusName;

    @Transient
    private String createdByName;

    @Transient
    private String phone;

    @Transient
    private String paymentType;

    @Transient
    private List<AcpRequisitionLn> lines;

    @Transient
    private String progressStatus;

    @Transient
    private String progressStatusName;

    @Transient
    private BigDecimal paidAmount;

    @Transient
    private Long progressCount;

    /**
     * 查询字段--创建日期范围
     */
    @Transient
    private String acpRequisitionDateScope;

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
     * 金额从
     */
    @Transient
    private BigDecimal amountFrom;

    /**
     * 金额到
     */
    @Transient
    private BigDecimal amountTo;

    @Transient
    private BigDecimal paymentAmount;

    @Transient
    private String paymentCurrencyName;

    @Transient
    private String paymentCurrencyCode;

    /**
     * 管理组织
     */
    @Transient
    private Long magOrgId;

    /**
     * 核算主体默认账套
     */
    @Transient
    private Long setOfBooksId;

    /**
     * 本位币
     */
    @Transient
    private String functionalCurrencyCode;

    /**
     * 部门下核算主体默认成本中心
     */
    @Transient
    private Long respCenterId;

    @Transient
    private String accountNumber;

    @Transient
    private String accountName;

    @Transient
    private String requisitionStatusName;

    /**
     * 已付金额
     */
    @Transient
    private BigDecimal alreadyPaying;

    @Transient
    private BigDecimal alreadyPayingFrom;

    @Transient
    private  BigDecimal alreadyPayingTo;

    public Long getProgressCount() {
        if(progressStatus == null){
            return 0L;
        }
        switch (progressStatus) {
            case STATUS_GENERATE:
            case STATUS_REJECTED:
            case STATUS_TAKEN_BACK:
                return 20L;
            case STATUS_SUBMITTED:
                return 40L;
            case STATUS_APPROVED:
                return 60L;
            case STATUS_PAID:
                return 80L;
            case STATUS_COMPLETELY_PAID:
                return 100L;
            default:
                return 0L;
        }
    }

}
