package com.hand.hec.expm.dto;

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
import java.util.Date;
import java.util.List;

/**
 * <p>
 * ExpReportHeader
 * </p>
 *
 * @author yang.duan 2019/01/10 14:59
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_report_header")
public class ExpReportHeader extends BaseDTO {

    public static final String FIELD_ATTACHMENT_NUM = "attachmentNum";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_WRITE_OFF_STATUS = "writeOffStatus";
    public static final String FIELD_WRITE_OFF_COMPLETED_DATE = "writeOffCompletedDate";
    public static final String FIELD_WRITE_OFF_COMPLETED_DATE_TZ = "writeOffCompletedDateTz";
    public static final String FIELD_WRITE_OFF_COMPLETED_DATE_LTZ = "writeOffCompletedDateLtz";
    public static final String FIELD_AMORTIZATION_FLAG = "amortizationFlag";
    public static final String FIELD_REVERSED_FLAG = "reversedFlag";
    public static final String FIELD_SOURCE_EXP_REP_HEADER_ID = "sourceExpRepHeaderId";
    public static final String FIELD_SOURCE_TYPE = "sourceType";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_RELEASE_TYPE = "releaseType";
    public static final String FIELD_EXP_REQUISITION_HEADER_ID = "expRequisitionHeaderId";
    public static final String FIELD_DIMENSION1_ID = "dimension1Id";
    public static final String FIELD_DIMENSION2_ID = "dimension2Id";
    public static final String FIELD_DIMENSION3_ID = "dimension3Id";
    public static final String FIELD_DIMENSION4_ID = "dimension4Id";
    public static final String FIELD_DIMENSION5_ID = "dimension5Id";
    public static final String FIELD_DIMENSION6_ID = "dimension6Id";
    public static final String FIELD_DIMENSION7_ID = "dimension7Id";
    public static final String FIELD_DIMENSION8_ID = "dimension8Id";
    public static final String FIELD_DIMENSION9_ID = "dimension9Id";
    public static final String FIELD_DIMENSION10_ID = "dimension10Id";
    public static final String FIELD_DIMENSION11_ID = "dimension11Id";
    public static final String FIELD_DIMENSION12_ID = "dimension12Id";
    public static final String FIELD_DIMENSION13_ID = "dimension13Id";
    public static final String FIELD_DIMENSION14_ID = "dimension14Id";
    public static final String FIELD_DIMENSION15_ID = "dimension15Id";
    public static final String FIELD_DIMENSION16_ID = "dimension16Id";
    public static final String FIELD_DIMENSION17_ID = "dimension17Id";
    public static final String FIELD_DIMENSION18_ID = "dimension18Id";
    public static final String FIELD_DIMENSION19_ID = "dimension19Id";
    public static final String FIELD_DIMENSION20_ID = "dimension20Id";
    public static final String FIELD_VAT_INVOICE_FLAG = "vatInvoiceFlag";
    public static final String FIELD_AUTHENTICATING_FLAG = "authenticatingFlag";
    public static final String FIELD_DOC_STATUS = "docStatus";
    public static final String FIELD_EXP_REPORT_HEADER_ID = "expReportHeaderId";
    public static final String FIELD_EXP_REPORT_NUMBER = "expReportNumber";
    public static final String FIELD_EXP_REPORT_BARCODE = "expReportBarcode";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_OPERATION_UNIT_ID = "operationUnitId";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_RESP_CENTER_ID = "respCenterId";
    public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
    public static final String FIELD_BGT_CENTER_ID = "bgtCenterId";
    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_PAYEE_ID = "payeeId";
    public static final String FIELD_MO_EXP_REPORT_TYPE_ID = "moExpReportTypeId";
    public static final String FIELD_MO_EMP_GROUP_ID = "moEmpGroupId";
    public static final String FIELD_PAYMENT_CURRENCY_CODE = "paymentCurrencyCode";
    public static final String FIELD_PAY2FUN_EXCHANGE_TYPE = "pay2funExchangeType";
    public static final String FIELD_PAY2FUN_EXCHANGE_RATE = "pay2funExchangeRate";
    public static final String FIELD_REPORT_DATE = "reportDate";
    public static final String FIELD_REPORT_DATE_TZ = "reportDateTz";
    public static final String FIELD_REPORT_DATE_LTZ = "reportDateLtz";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_REPORT_STATUS = "reportStatus";
    public static final String FIELD_JE_CREATION_STATUS = "jeCreationStatus";
    public static final String FIELD_AUDIT_FLAG = "auditFlag";
    public static final String FIELD_AUDIT_DATE = "auditDate";
    public static final String FIELD_AUDIT_DATE_TZ = "auditDateTz";
    public static final String FIELD_AUDIT_DATE_LTZ = "auditDateLtz";
    public static final String FIELD_GLD_INTERFACE_FLAG = "gldInterfaceFlag";
    public static final String FIELD_PAYMENT_AMOUNT = "paymentAmount";

    // Modify Tagin/2018.02.18 增加报销单状态变量
    public static final String GENERATE = "GENERATE"; // 新建
    public static final String SUBMITTED = "SUBMITTED"; // 提交
    public static final String COMPLETELY_APPROVED = "COMPLETELY_APPROVED"; // 审批通过
    public static final String REJECTED = "REJECTED"; // 拒绝
    public static final String TAKEN_BACK = "TAKEN_BACK"; // 收回
    public static final String EXP_REPORT = "EXP_REPORT"; // 单据类型



    /**
     * 附件数量
     */
    private Long attachmentNum;

    /**
     * 报销单头描述
     */
    @Length(max = 4000)
    private String description;

    /**
     * 核销状态
     */
    @NotEmpty
    @Length(max = 30)
    private String writeOffStatus;

    /**
     * 核销日期
     */
    private Date writeOffCompletedDate;

    /**
     * 核销日期_业务时区
     */
    private Date writeOffCompletedDateTz;

    /**
     * 核销日期_查询时区
     */
    private Date writeOffCompletedDateLtz;

    /**
     * 摊销标志
     */
    @NotEmpty
    @Length(max = 1)
    private String amortizationFlag;

    /**
     * 反冲标志
     */
    @NotEmpty
    @Length(max = 1)
    private String reversedFlag;

    /**
     * 来源报销单ID
     */
    private Long sourceExpRepHeaderId;

    /**
     * 来源类型(MANUAL)
     */
    @Length(max = 30)
    private String sourceType;

    /**
     * 付款方式
     */
    private Long paymentMethodId;

    /**
     * 申请单关联方式，DOCUMENT/LINE，整单/按行
     */
    @Length(max = 30)
    private String releaseType;

    /**
     * 整单关联时的申请单头ID
     */
    private Long expRequisitionHeaderId;

    /**
     * 维度1
     */
    private Long dimension1Id;

    /**
     * 维度2
     */
    private Long dimension2Id;

    /**
     * 维度3
     */
    private Long dimension3Id;

    /**
     * 维度4
     */
    private Long dimension4Id;

    /**
     * 维度5
     */
    private Long dimension5Id;

    /**
     * 维度6
     */
    private Long dimension6Id;

    /**
     * 维度7
     */
    private Long dimension7Id;

    /**
     * 维度8
     */
    private Long dimension8Id;

    /**
     * 维度9
     */
    private Long dimension9Id;

    /**
     * 维度10
     */
    private Long dimension10Id;

    /**
     * 维度11
     */
    private Long dimension11Id;

    /**
     * 维度12
     */
    private Long dimension12Id;

    /**
     * 维度13
     */
    private Long dimension13Id;

    /**
     * 维度14
     */
    private Long dimension14Id;

    /**
     * 维度15
     */
    private Long dimension15Id;

    /**
     * 维度16
     */
    private Long dimension16Id;

    /**
     * 维度17
     */
    private Long dimension17Id;

    /**
     * 维度18
     */
    private Long dimension18Id;

    /**
     * 维度19
     */
    private Long dimension19Id;

    /**
     * 维度20
     */
    private Long dimension20Id;

    /**
     * 是否有增值税专票
     */
    @Length(max = 1)
    private String vatInvoiceFlag;

    /**
     * 增值税专票是否完全认证
     */
    @Length(max = 30)
    private String authenticatingFlag;

    /**
     * 退回状态（SYSCODE：CSH_DOC_BACK）
     */
    @Length(max = 30)
    private String docStatus;

    @Id
    @GeneratedValue
    private Long expReportHeaderId;

    /**
     * 报销单编号
     */
    @NotEmpty
    @Length(max = 30)
    private String expReportNumber;

    /**
     * 废弃：报销单编码
     */
    @Length(max = 30)
    private String expReportBarcode;

    /**
     * 公司ID
     */
    @NotNull
    private Long companyId;

    /**
     * 废弃：经营单位
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
    private Long employeeId;

    /**
     * 核算主体ID
     */
    @NotNull
    private Long accEntityId;

    /**
     * 责任中心ID
     */
    @NotNull
    private Long respCenterId;

    /**
     * 预算实体ID
     */
    @NotNull
    private Long bgtEntityId;

    /**
     * 预算中心ID
     */
    @NotNull
    private Long bgtCenterId;

    /**
     * 收款方类型
     */
    @NotEmpty
    @Length(max = 30)
    private String payeeCategory;

    /**
     * 收款方ID
     */
    @NotNull
    private Long payeeId;

    /**
     * 报销单类型ID
     */
    @NotNull
    private Long moExpReportTypeId;

    /**
     * 管理组织级员工组ID
     */
    private Long moEmpGroupId;

    /**
     * 支付币种
     */
    @NotEmpty
    @Length(max = 10)
    private String paymentCurrencyCode;

    /**
     * 支付币种->本位币汇率类型
     */
    @Length(max = 30)
    private String pay2funExchangeType;

    /**
     * 支付币种->本位币汇率
     */
    private BigDecimal pay2funExchangeRate;

    /**
     * 报销日期
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT,timezone = "GMT+8")
    private Date reportDate;

    /**
     * 报销日期_业务时区
     */
    private Date reportDateTz;

    /**
     * 报销日期_查询时区
     */
    private Date reportDateLtz;

    /**
     * 期间
     */
    @NotEmpty
    @Length(max = 30)
    private String periodName;

    /**
     * 报销单状态
     */
    @NotEmpty
    @Length(max = 30)
    private String reportStatus;

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
     * 总账接口标志
     */
    @Length(max = 1)
    private String gldInterfaceFlag;
    /*
     * 非主表字段
     */

    /*
     * 标准行
     * */
    @Transient
    List<ExpReportLine> standardLines;

    /*
     * 精简行
     * */
    @Transient
    List<ExpReportLine> streamlinedLines;

    /*
     * 差旅报销行程行
     * */
    @Transient
    List<ExpReportTravelLine> travelLines;

    /*
     * 差旅报销住宿行
     * */
    @Transient
    List<ExpReportTravelLine> travelStayLines;

    /*
     * 差旅报销补贴行
     * */
    @Transient
    List<ExpReportTravelLine> travelSubsidyLines;

    /*
     * 住宿统报行
     * */
    @Transient
    List<ExpReportTravelLine> travelStayUnified;

    /*
     * 机票统报行
     * */
    @Transient
    List<ExpReportTravelLine> ticketUnifiedLines;

    /*
     * 计划付款行
     * */
    @Transient
    List<ExpReportPmtSchedule> pmtSchedules;

    /*
     * 报销单类型描述
     * */
    @Transient
    private String moExpReportTypeName;

    /*
     * 公司描述
     * */
    @Transient
    private String companyName;

    /*
     * 核算主体描述
     * */
    @Transient
    private String accEntityName;

    /*
     * 责任中心描述
     * */
    @Transient
    private String respCenterName;

    /*
     * 员工姓名
     * */
    @Transient
    private String employeeName;

    /*
     * 岗位描述
     * */
    @Transient
    private String positionName;

    /*
     * 部门描述
     * */
    @Transient
    private String unitName;

    /*
     * 预算实体描述
     * */
    @Transient
    private String bgtEntityName;

    /*
     * 预算中心描述
     * */
    @Transient
    private String bgtCenterName;

    /*
     * 收款方类型描述
     * */
    @Transient
    private String payeeCategoryName;

    /*
     * 收款方描述
     * */
    @Transient
    private String payeeName;


    @Transient
    private String accountName;

    @Transient
    private String accountNumber;

    @Transient
    private String backName;

    @Transient
    private String backCode;

    @Transient
    private String bankLocationName;

    @Transient
    private String bankLocationCode;

    @Transient
    private String provinceName;

    @Transient
    private String provinceCode;

    @Transient
    private String cityName;

    @Transient
    private String cityCode;

    @Transient
    private Long paymentCurrencyPrecision;

    @Transient
    private String pay2funExchangeTypeName;

    @Transient
    private String managementCurrencyCode;

    @Transient
    private String managementCurrencyName;

    @Transient
    private Long managementCurrencyPrecision;

    @Transient
    private String pay2magExchangeType;

    @Transient
    private String pay2magExchangeTypeName;

    @Transient
    private BigDecimal pay2magExchangeRate;

    @Transient
    private String functionalCurrencyName;

    @Transient
    private Long functionalCurrencyPrecision;

    @Transient
    private Long paymentTypeId;

    @Transient
    private String paymentTypeName;

    @Transient
    private Long paymentUsedeId;

    @Transient
    private String paymentUsedeName;

    @Transient
    private String createdByName;

    @Transient
    private String paymentMethodName;

    @Transient
    private String dimension1Name;

    @Transient
    private String dimension2Name;

    @Transient
    private String dimension3Name;

    @Transient
    private String dimension4Name;

    @Transient
    private String dimension5Name;


    @Transient
    private String dimension6Name;

    @Transient
    private String dimension7Name;

    @Transient
    private String dimension8Name;

    @Transient
    private String dimension9Name;

    @Transient
    private String dimension10Name;

    @Transient
    private String dimension11Name;

    @Transient
    private String dimension12Name;

    @Transient
    private String dimension13Name;

    @Transient
    private String dimension14Name;


    @Transient
    private String dimension15Name;

    @Transient
    private String dimension16Name;

    @Transient
    private String dimension17Name;

    @Transient
    private String dimension18Name;

    @Transient
    private String dimension19Name;

    @Transient
    private String dimension20Name;

    @Transient
    private String relationMethodCode;

    @Transient
    private String reqRequiredFlag;

    @Transient
    private String reqRequired;

    @Transient
    private String expRequisitionNumber;

    @Transient
    private String taxpayerType;

    @Transient
    private Long docId;

    @Transient
    private String refDocCategory;

    @Transient
    private BigDecimal repTotalAmount;

    @Transient
    private String monopolizeFlag;

    @Transient
    private String reqReadonlyServiceName;

    @Transient
    private String adjustmentFlag;

    @Transient
    private String autoApproveFlag;


    /*
     * 支付币种描述
     * */
    @Transient
    private String paymentCurrencyName;

    /*
     * 支付币种符号
     * */
    @Transient
    private String paymentCurrencySymbol;

    /*
     * 报销单状态描述
     * */
    @Transient
    private String reportStatusName;

    /*
     * 费用报销单总金额
     * */
    @Transient
    private BigDecimal totalPaymentAmount;

    /*
     * 已支付总金额
     * */
    @Transient
    private BigDecimal paidAmount;

    /*
     * 已核销金额
     * */
    @Transient
    private BigDecimal totalPaidAmount;

    /*
     * 进度状态
     * */
    @Transient
    private String progressStatus;

    /*
     * 进度状态描述
     * */
    @Transient
    private String progressStatusName;

    /*
     * 进度百分比
     * */
    @Transient
    private Long progressCount;

    /*
     * 页面路径
     * */
    @Transient
    private String serviceName;

    /*
     * 只读页面路径
     * */
    @Transient
    private String readonlyServiceName;

    /*
     * 工作流实例ID
     * */
    @Transient
    private Long instanceId;

    /*
     * 查询字段
     */

    /*
     * 创建日期范围
     * */
    @Transient
    private String expReportDateScope;


    /*
     * 状态
     * */
    @Transient
    private String status;

    /*
     * 创建日期从
     * */
    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date createdDateFrom;

    /*
     * 创建日期到
     * */
    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date createdDateTo;

    /*
     * 报销日期从
     * */
    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date reportDateFrom;

    /*
     * 报销日期到
     * */
    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date reportDateTo;

    /*
     * 报销金额从
     * */
    @Transient
    private BigDecimal amountFrom;

    /*
     * 报销金额到
     * */
    @Transient
    private BigDecimal amountTo;
    /**
     * 管理组织ID
     */
    @Transient
    private Long magOrgId;

    /**
     * 账套ID
     */
    @Transient
    private Long setOfBooksId;

    /**
     * 员工类型ID
     */
    @Transient
    private Long employeeTypeId;

    /**
     * 核算主体对应的本位币
     */
    @Transient
    private String functionalCurrencyCode;
    /**
     * 付款总金额
     */
    private BigDecimal paymentAmount;

    /**
     * 业务总金额
     * */
    private BigDecimal businessAmount;

    @Transient
    List<ExpReportHeader> details;

    @Transient
    private String reportName;
}
