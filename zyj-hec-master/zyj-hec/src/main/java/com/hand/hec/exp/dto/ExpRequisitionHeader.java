package com.hand.hec.exp.dto;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/3/19 11:02
 * Description: 申请单头表DTO
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
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
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_requisition_header")
public class ExpRequisitionHeader extends BaseDTO {

    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_PAYEE_CATEGORY_NAME = "payeeCategoryName";
    public static final String FIELD_PAYEE_ID = "payeeId";
    public static final String FIELD_PAYEE_NAME = "payeeName";
    public static final String FIELD_MO_EXP_REQ_TYPE_ID = "moExpReqTypeId";
    public static final String FIELD_MO_EXP_REQ_TYPE_CODE = "moExpReqTypeCode";
    public static final String FIELD_MO_EXP_REQ_TYPE_NAME = "moExpReqTypeName";
    public static final String FIELD_MO_EMP_GROUP_ID = "moEmpGroupId";
    public static final String FIELD_MO_EMP_GROUP_NAME = "moEmpGroupName";
    public static final String FIELD_PAYMENT_CURRENCY_CODE = "paymentCurrencyCode";
    public static final String FIELD_PAYMENT_CURRENCY_NAME = "paymentCurrencyName";
    public static final String FIELD_PAY2FUN_EXCHANGE_TYPE = "pay2funExchangeType";
    public static final String FIELD_PAY2FUN_EXCHANGE_TYPE_NAME = "pay2funExchangeTypeName";
    public static final String FIELD_PAY2FUN_EXCHANGE_RATE = "pay2funExchangeRate";
    public static final String FIELD_REQUISITION_DATE = "requisitionDate";
    public static final String FIELD_REQUISITION_DATE_TZ = "requisitionDateTz";
    public static final String FIELD_REQUISITION_DATE_LTZ = "requisitionDateLtz";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_REQUISITION_STATUS = "requisitionStatus";
    public static final String FIELD_REQUISITION_STATUS_NAME = "requisitionStatusName";
    public static final String FIELD_JE_CREATION_STATUS = "jeCreationStatus";
    public static final String FIELD_AUDIT_FLAG = "auditFlag";
    public static final String FIELD_GLD_INTERFACE_FLAG = "gldInterfaceFlag";
    public static final String FIELD_ATTACHMENT_NUM = "attachmentNum";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_RELEASED_STATUS = "releasedStatus";
    public static final String FIELD_REVERSED_FLAG = "reversedFlag";
    public static final String FIELD_SOURCE_EXP_REQ_HEADER_ID = "sourceExpReqHeaderId";
    public static final String FIELD_SOURCE_EXP_REQ_HEADER_NUMBER = "sourceExpReqHeaderNumber";
    public static final String FIELD_SOURCE_TYPE = "sourceType";
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
    public static final String FIELD_PAYMENT_FLAG = "paymentFlag";
    public static final String FIELD_DEPARTURE_DATE = "departureDate";
    public static final String FIELD_ARRIVAL_DATE = "arrivalDate";
    public static final String FIELD_EXP_REQUISITION_HEADER_ID = "expRequisitionHeaderId";
    public static final String FIELD_EXP_REQUISITION_NUMBER = "expRequisitionNumber";
    public static final String FIELD_EXP_REQUISITION_BARCODE = "expRequisitionBarcode";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_COMPANY_NAME = "companyName";
    public static final String FIELD_OPERATION_UNIT_ID = "operationUnitId";
    public static final String FIELD_OPERATION_UNIT_NAME = "operationUnitName";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_UNIT_NAME = "unitName";
    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_POSITION_NAME = "positionName";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_EMPLOYEE_NAME = "employeeName";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_ACC_ENTITY_NAME = "accEntityName";
    public static final String FIELD_RESP_CENTER_ID = "respCenterId";
    public static final String FIELD_RESP_CENTER_NAME = "respCenterName";
    public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
    public static final String FIELD_BGT_ENTITY_NAME = "bgtEntityName";
    public static final String FIELD_BGT_CENTER_ID = "bgtCenterId";
    public static final String FIELD_BGT_CENTER_NAME = "bgtCenterName";
    public static final String FIELD_CREATED_BY_NAME = "createdByName";
    public static final String FIELD_REQ_TOTAL_AMOUNT = "reqTotalAmount";
    public static final String FIELD_REQUISITION_DATE_STR = "requisitionDateStr";
    public static final String FIELD_TYPE_DESCRIPTION = "typeDescription";
    public static final String FIELD_CLOSED_DATE = "closedDate";
    public static final String FIELD_QUARTER_NUM = "quarterNum";
    public static final String FIELD_QUARTER_NUM_FUN = "quarterNumFun";
    public static final String FIELD_REIMBURSED_NUM = "reimbursedNum";
    public static final String FIELD_REIMBURSED_ADU_NUM = "reimbursedAduNum";
    public static final String FIELD_REQUISITION_DATE_FROM = "requisitionDateFrom";
    public static final String FIELD_REQUISITION_DATE_TO = "requisitionDateTo";
    public static final String FIELD_TOTAL_PAYMENT_AMOUNT = "totalPaymentAmount";
    public static final String FIELD_PROGRESS_STATUS = "progressStatus";
    public static final String FIELD_PROGRESS_STATUS_NAME = "progressStatusName";
    public static final String FIELD_PROGRESS_COUNT = "progressCount";
    public static final String FIELD_SERVICE_NAME = "serviceName";
    public static final String FIELD_READONLY_SERVICE_NAME = "readonlyServiceName";
    public static final String FIELD_INSTANCE_ID = "instanceId";
    public static final String FIELD_REIMBURSED_AMOUNT = "reimbursedAmount";
    public static final String FIELD_AUTO_APPROVE_FLAG = "autoApproveFlag";
    public static final String FIELD_PAYMENT_CURRENCY_SYMBOL = "paymentCurrencySymbol";

    public static final String FIELD_EXP_REQUISITION = "EXP_REQUISITION";
    public static final String FIELD_EXP_REQ = "EXP_REQ";
    public static final String FIELD_STATUS_GENERATE = "GENERATE";
    public static final String FIELD_STATUS_SUBMITTED = "SUBMITTED";
    public static final String FIELD_STATUS_COMPLETELY_APPROVED = "COMPLETELY_APPROVED";
    public static final String FIELD_STATUS_REJECTED = "REJECTED";
    public static final String FIELD_STATUS_CLOSED = "CLOSED";
    public static final String FIELD_STATUS_TAKEN_BACK = "TAKEN_BACK";
    public static final String FIELD_STATUS_APPROVED = "APPROVED";


    /**
     * 收款方类别
     */
    @Length(max = 30)
    private String payeeCategory;
    /**
     * 收款方类别名称
     */
    @Transient
    private String payeeCategoryName;
    /**
     * 收款方ID
     */
    private Long payeeId;

    /**
     * 收款方名称
     */
    @Transient
    private String payeeName;

    /**
     * 管理组织级费用申请单类型ID
     */
    @Where
    @NotNull
    private Long moExpReqTypeId;

    /**
     * 管理组织级费用申请单类型代码
     */
    @Transient
    private String moExpReqTypeCode;
    /**
     * 管理组织级费用申请单类型名称
     */
    @Transient
    private String moExpReqTypeName;
    /**
     * 员工组ID
     */
    private Long moEmpGroupId;

    /**
     * 员工组名称
     */
    @Transient
    private String moEmpGroupName;
    /**
     * 支付币种
     */
    @NotEmpty
    @Length(max = 30)
    private String paymentCurrencyCode;

    /**
     * 支付币种名称
     */
    @Transient
    private String paymentCurrencyName;
    /**
     * 支付币种->本位币汇率类型
     */
    @Length(max = 30)
    private String pay2funExchangeType;

    /**
     * 支付币种->本位币汇率类型名称
     */
    @Transient
    private String pay2funExchangeTypeName;
    /**
     * 支付币种->本位币汇率
     */
    private BigDecimal pay2funExchangeRate;

    /**
     * 申请日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date requisitionDate;

    /**
     * 申请日期_业务时区
     */
    private Date requisitionDateTz;

    /**
     * 申请日期_查询时区
     */
    private Date requisitionDateLtz;

    /**
     * 期间
     */
    @NotEmpty
    @Length(max = 30)
    private String periodName;

    /**
     * 申请单状态
     */
    @NotEmpty
    @Length(max = 30)
    private String requisitionStatus;

    /**
     * 凭证创建状态
     */
    @NotEmpty
    @Length(max = 30)
    private String jeCreationStatus;

    /**
     * 审核状态
     */
    @NotEmpty
    @Length(max = 1)
    private String auditFlag;

    /**
     * 总账接口标识
     */
    @NotEmpty
    @Length(max = 1)
    private String gldInterfaceFlag;

    /**
     * 附件数
     */
    private Long attachmentNum;

    /**
     * 描述
     */
    @Length(max = 4000)
    private String description;

    /**
     * 下达状态
     */
    @NotEmpty
    @Length(max = 30)
    private String releasedStatus;

    /**
     * 反冲标志
     */
    @NotEmpty
    @Length(max = 1)
    private String reversedFlag;

    /**
     * 来源申请单头ID
     */
    private Long sourceExpReqHeaderId;

    /**
     * 来源申请单头编号
     */
    @Transient
    private Long sourceExpReqHeaderNumber;
    /**
     * 来源类型(MANUAL)
     */
    @NotEmpty
    @Length(max = 30)
    private String sourceType;

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
     * 借款标志
     */
    @NotEmpty
    @Length(max = 1)
    private String paymentFlag;

    /**
     * 预计出发日期
     */
    private Date departureDate;

    /**
     * 预计返抵日期
     */
    private Date arrivalDate;

    @Id
    @GeneratedValue
    private Long expRequisitionHeaderId;

    /**
     * 申请单编号
     */
    @NotEmpty
    @Length(max = 30)
    private String expRequisitionNumber;

    /**
     * 废弃_申请单编码
     */
    @Length(max = 30)
    private String expRequisitionBarcode;

    /**
     * 公司ID
     */
    @NotNull
    private Long companyId;
    /**
     * 公司名称
     */
    @Transient
    private String companyName;
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
     * 岗位名称
     */
    @Transient
    private String positionName;
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
     * 责任中心代码
     */
    @Transient
    private String respCenterName;
    /**
     * 预算主体ID
     */
    @NotNull
    private Long bgtEntityId;

    /**
     * 预算主体名称
     */
    @Transient
    private String bgtEntityName;
    /**
     * 预算中心ID
     */
    @NotNull
    private Long bgtCenterId;

    /**
     * 预算中心名称
     */
    @Transient
    private String bgtCenterName;

    /**
     * 非本表字段  单据类型 weikun.wang  2019/3/8
     */
    @Transient
    private String typeDescription;

    /**
     * 非本表字段  关闭日期 字符串类型 weikun.wang  2019/3/8
     */
    @Transient
    private String closedDate;

    /**
     * 非本表字段  申请日期 字符串类型 weikun.wang  2019/3/8
     */
    @Transient
    private String requisitionDateStr;

    /**
     * 非本表字段  币种名称 字符串类型 weikun.wang  2019/3/8
     */
    @Transient
    private String currencyName;


    /**
     * 非本表字段  申请总金额 long weikun.wang  2019/3/8
     */
    @Transient
    private long quarterNum;

    /**
     * 非本表字段  本币总金额 long weikun.wang  2019/3/8
     */
    @Transient
    private long quarterNumFun;

    /**
     * 非本表字段 已报销金额  long weikun.wang  2019/3/8
     */
    @Transient
    private long reimbursedNum;

    /**
     * 非本表字段 已报销入账金额  long weikun.wang  2019/3/8
     */
    @Transient
    private long reimbursedAduNum;

    /**
     * 非本表字段 申请人  long weikun.wang  2019/3/8
     */
    @Transient
    private String employeeName;


    /**
     * 非本表字段 申请日期从  long weikun.wang  2019/3/8
     */
    @Transient
    private String requisitionDateFrom;

    /**
     * 非本表字段 申请日期到  long weikun.wang  2019/3/8
     */
    @Transient
    private String requisitionDateTo;

    /**
     * 非本表字段 部门名称  long weikun.wang  2019/3/11
     */
    @Transient
    private String unitName;

    /**
     * 非本表字段 核算主体名称  long weikun.wang  2019/3/11
     */
    @Transient
    private String accEntityName;

    /**
     * 非本表字段 状态名称  long weikun.wang  2019/3/11
     */
    @Transient
    private String requisitionStatusName;


    /**
     * 非本表字段 创建者  long weikun.wang  2019/3/11
     */
    @Transient
    private String createdByName;

    /**
     * 非本表字段 总金额  long weikun.wang  2019/3/11
     */
    @Transient
    private long reqTotalAmount;

    /**
     * 付款金额
     */
    @Transient
    private long totalPaymentAmount;

    /**
     * 进度状态
     */
    @Transient
    private String progressStatus;

    /**
     * 进度状态名称
     */
    @Transient
    private String progressStatusName;

    /**
     * 进度数
     */
    @Transient
    private Long progressCount;

    /**
     * 页面url
     */
    @Transient
    private String serviceName;

    /**
     * 只读页面url
     */
    @Transient
    private String readonlyServiceName;

    /**
     * 工作流实例id
     */
    @Transient
    private Long instanceId;

    /**
     * 已付金额
     */
    @Transient
    private Long reimbursedAmount;

    /**
     * 自动审批标识
     */
    @Transient
    private String autoApproveFlag;
    /**
     * 付款币种符号
     */
    @Transient
    private String paymentCurrencySymbol;

    /**
     * 标准申请行
     */
    @Transient
    List<ExpRequisitionLine> standardLines;

    /**
     * 差旅申请行
     */
    @Transient
    List<ExpRequisitionLine> travelLines;

    /**
     * 差旅申请行程行
     */
    @Transient
    List<ExpRequisitionLine> travelDistanceLines;

    /**
     * 招待费用申请行
     */
    @Transient
    List<ExpRequisitionLine> entertainLines;

    /**
     * 机票费用申请行
     */
    @Transient
    List<ExpRequisitionLine> ticketLines;

    /**
     * 差旅费用住宿行
     */
    @Transient
    List<ExpRequisitionLine> travelStayLines;

}
