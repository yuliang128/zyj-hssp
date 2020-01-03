package com.hand.hec.exp.dto;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/3/27 11:03
 * Description:申请单标准行DTO
 */

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
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_requisition_line")
public class ExpRequisitionLine extends BaseDTO {

    public static final String FIELD_PLACE_TYPE_ID = "placeTypeId";
    public static final String FIELD_PLACE_TYPE_NAME = "placeTypeName";
    public static final String FIELD_PLACE_ID = "placeId";
    public static final String FIELD_PLACE_NAME = "placeName";
    public static final String FIELD_CITY = "city";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_DATE_FROM = "dateFrom";
    public static final String FIELD_DATE_TO = "dateTo";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_BUSINESS_CURRENCY_CODE = "businessCurrencyCode";
    public static final String FIELD_BUSINESS_CURRENCY_NAME = "businessCurrencyName";
    public static final String FIELD_BIZ2PAY_EXCHANGE_TYPE = "biz2payExchangeType";
    public static final String FIELD_BIZ2PAY_EXCHANGE_TYPE_NAME = "biz2payExchangeTypeName";
    public static final String FIELD_BIZ2PAY_EXCHANGE_RATE = "biz2payExchangeRate";
    public static final String FIELD_PAYMENT_CURRENCY_CODE = "paymentCurrencyCode";
    public static final String FIELD_PAYMENT_CURRENCY_NAME = "paymentCurrencyName";
    public static final String FIELD_PAY2FUN_EXCHANGE_TYPE = "pay2funExchangeType";
    public static final String FIELD_PAY2FUN_EXCHANGE_TYPE_NAME = "pay2funExchangeTypeName";
    public static final String FIELD_PAY2FUN_EXCHANGE_RATE = "pay2funExchangeRate";
    public static final String FIELD_MANAGEMENT_CURRENCY_CODE = "managementCurrencyCode";
    public static final String FIELD_MANAGEMENT_CURRENCY_NAME = "managementCurrencyName";
    public static final String FIELD_PAY2MAG_EXCHANGE_TYPE = "pay2magExchangeType";
    public static final String FIELD_PAY2MAG_EXCHANGE_TYPE_NAME = "pay2magExchangeTypeName";
    public static final String FIELD_PAY2MAG_EXCHANGE_RATE = "pay2magExchangeRate";
    public static final String FIELD_MO_EXPENSE_TYPE_ID = "moExpenseTypeId";
    public static final String FIELD_MO_EXPENSE_TYPE_NAME = "moExpenseTypeName";
    public static final String FIELD_MO_REQ_ITEM_ID = "moReqItemId";
    public static final String FIELD_MO_REQ_ITEM_NAME = "moReqItemName";
    public static final String FIELD_BUDGET_ITEM_ID = "budgetItemId";
    public static final String FIELD_BUDGET_ITEM_NAME = "budgetItemName";
    public static final String FIELD_BUSINESS_PRICE = "businessPrice";
    public static final String FIELD_PAYMENT_PRICE = "paymentPrice";
    public static final String FIELD_MANAGEMENT_PRICE = "managementPrice";
    public static final String FIELD_PRIMARY_QUANTITY = "primaryQuantity";
    public static final String FIELD_PRIMARY_UOM = "primaryUom";
    public static final String FIELD_SECONDARY_QUANTITY = "secondaryQuantity";
    public static final String FIELD_SECONDARY_UOM = "secondaryUom";
    public static final String FIELD_BUSINESS_AMOUNT = "businessAmount";
    public static final String FIELD_PAYMENT_AMOUNT = "paymentAmount";
    public static final String FIELD_MANAGEMENT_AMOUNT = "managementAmount";
    public static final String FIELD_REQUISITION_FUNCTIONAL_AMOUNT = "requisitionFunctionalAmount";
    public static final String FIELD_DISTRIBUTION_SET_ID = "distributionSetId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_COMPANY_NAME = "companyName";
    public static final String FIELD_OPERATION_UNIT_ID = "operationUnitId";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_UNIT_Name = "unitName";
    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_POSITION_NAME = "positionName";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_EMPLOYEE_NAME = "employeeName";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_ACC_ENTITY_Name = "accEntityName";
    public static final String FIELD_RESP_CENTER_ID = "respCenterId";
    public static final String FIELD_RESP_CENTER_NAME = "respCenterName";
    public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
    public static final String FIELD_BGT_ENTITY_Name = "bgtEntityName";
    public static final String FIELD_BGT_CENTER_ID = "bgtCenterId";
    public static final String FIELD_BGT_CENTER_NAME = "bgtCenterName";
    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_PAYEE_CATEGORY_NAME = "payeeCategoryName";
    public static final String FIELD_PAYEE_ID = "payeeId";
    public static final String FIELD_PAYEE_Name = "payeeName";
    public static final String FIELD_PAYMENT_FLAG = "paymentFlag";
    public static final String FIELD_REQUISITION_STATUS = "requisitionStatus";
    public static final String FIELD_AUDIT_FLAG = "auditFlag";
    public static final String FIELD_ATTACHMENT_NUM = "attachmentNum";
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
    public static final String FIELD_DIMENSION1_NAME = "dimension1Name";
    public static final String FIELD_DIMENSION2_NAME = "dimension2Name";
    public static final String FIELD_DIMENSION3_NAME = "dimension3Name";
    public static final String FIELD_DIMENSION4_NAME = "dimension4Name";
    public static final String FIELD_DIMENSION5_NAME = "dimension5Name";
    public static final String FIELD_DIMENSION6_NAME = "dimension6Name";
    public static final String FIELD_DIMENSION7_NAME = "dimension7Name";
    public static final String FIELD_DIMENSION8_NAME = "dimension8Name";
    public static final String FIELD_DIMENSION9_NAME = "dimension9Name";
    public static final String FIELD_DIMENSION10_NAME = "dimension10Name";
    public static final String FIELD_DIMENSION11_NAME = "dimension11Name";
    public static final String FIELD_DIMENSION12_NAME = "dimension12Name";
    public static final String FIELD_DIMENSION13_NAME = "dimension13Name";
    public static final String FIELD_DIMENSION14_NAME = "dimension14Name";
    public static final String FIELD_DIMENSION15_NAME = "dimension15Name";
    public static final String FIELD_DIMENSION16_NAME = "dimension16Name";
    public static final String FIELD_DIMENSION17_NAME = "dimension17Name";
    public static final String FIELD_DIMENSION18_NAME = "dimension18Name";
    public static final String FIELD_DIMENSION19_NAME = "dimension19Name";
    public static final String FIELD_DIMENSION20_NAME = "dimension20Name";
    public static final String FIELD_DIMENSION1_LEVEL = "dimension1Level";
    public static final String FIELD_DIMENSION2_LEVEL = "dimension2Level";
    public static final String FIELD_DIMENSION3_LEVEL = "dimension3Level";
    public static final String FIELD_DIMENSION4_LEVEL = "dimension4Level";
    public static final String FIELD_DIMENSION5_LEVEL = "dimension5Level";
    public static final String FIELD_DIMENSION6_LEVEL = "dimension6Level";
    public static final String FIELD_DIMENSION7_LEVEL = "dimension7Level";
    public static final String FIELD_DIMENSION8_LEVEL = "dimension8Level";
    public static final String FIELD_DIMENSION9_LEVEL = "dimension9Level";
    public static final String FIELD_DIMENSION10_LEVEL = "dimension10Level";
    public static final String FIELD_DIMENSION11_LEVEL = "dimension11Level";
    public static final String FIELD_DIMENSION12_LEVEL = "dimension12Level";
    public static final String FIELD_DIMENSION13_LEVEL = "dimension13Level";
    public static final String FIELD_DIMENSION14_LEVEL = "dimension14Level";
    public static final String FIELD_DIMENSION15_LEVEL = "dimension15Level";
    public static final String FIELD_DIMENSION16_LEVEL = "dimension16Level";
    public static final String FIELD_DIMENSION17_LEVEL = "dimension17Level";
    public static final String FIELD_DIMENSION18_LEVEL = "dimension18Level";
    public static final String FIELD_DIMENSION19_LEVEL = "dimension19Level";
    public static final String FIELD_DIMENSION20_LEVEL = "dimension20Level";
    public static final String FIELD_ENTERTAIN_OBJECT = "entertainObject";
    public static final String FIELD_ENTERTAIN_NUMBER = "entertainNumber";
    public static final String FIELD_EXP_REQUISITION_LINE_ID = "expRequisitionLineId";
    public static final String FIELD_EXP_REQUISITION_HEADER_ID = "expRequisitionHeaderId";
    public static final String FIELD_LINE_NUMBER = "lineNumber";
    public static final String FIELD_REQ_PAGE_ELEMENT_CODE = "reqPageElementCode";


    /**
     * 非本表字段   long weikun.wang  2019/3/11
     */
    public static final String FIELD_CLOSED_DATE = "closedDate";

    public static final String FIELD_TRAVEL_LINE_ID = "travelLineId";
    public static final String FIELD_TRAVEL_LINE_CATEGORY = "travelLineCategory";
    public static final String FIELD_TRANSPORTATION = "transportation";
    public static final String FIELD_TRANSPORTATION_NAME = "transportationName";
    public static final String FIELD_SEAT_CLASS = "seatClass";
    public static final String FIELD_SEAT_CLASS_NAME = "seatClassName";
    public static final String FIELD_DEPARTURE_PLACE_NAME = "departurePlaceName";
    public static final String FIELD_DEPARTURE_PLACE_ID = "departurePlaceId";
    public static final String FIELD_DEPARTURE_DATE = "departureDate";
    public static final String FIELD_ARRIVAL_PLACE_ID = "arrivalPlaceId";
    public static final String FIELD_ARRIVAL_PLACE_NAME = "arrivalPlaceName";
    public static final String FIELD_ARRIVAL_DATE = "arrivalDate";
    public static final String FIELD_ACCOMMODATION_DATE_FROM = "accommodationDateFrom";
    public static final String FIELD_ACCOMMODATION_DATE_TO = "accommodationDateTo";
    public static final String FIELD_ACCOMMODATION_DAYS = "accommodationDays";

    public static final String FIELD_PEER_PEOPLE_NAME = "peerPeopleName";
    public static final String FIELD_MONOPOLIZE_FLAG = "monopolizeFlag";
    public static final String FIELD_MONOPOLIZE_PLATFORM = "monopolizePlatform";
    public static final String FIELD_EXPENSE_STANDARD = "expenseStandard";
    public static final String FIELD_UPPER_LIMIT = "upperLimit";
    public static final String FIELD_LOWER_LIMIT = "lowerLimit";
    public static final String FIELD_COMMIT_FLAG = "commitFlag";
    public static final String FIELD_CHANGEABLE_FLAG = "changeableFlag";

    public static final String FIELD_TRAVEL_DISTANCE_LINES = "TRAVEL_DISTANCE_LINES";
    public static final String FIELD_TICKET_LINES = "TICKET_LINES";
    public static final String FIELD_TRAVEL_STAY_LINES = "TRAVEL_STAY_LINES";


    /**
     * 地点类型ID
     */
    private Long placeTypeId;

    /**
     * 地点类型名称
     */
    @Transient
    private String placeTypeName;

    /**
     * 地点ID
     */
    private Long placeId;

    /**
     * 地点名称
     */
    @Transient
    private String placeName;

    /**
     * 城市，手动输入
     */
    @Length(max = 200)
    private String city;

    /**
     * 描述
     */
    @Length(max = 4000)
    private String description;

    /**
     * 费用申请日期从
     */
    private Date dateFrom;

    /**
     * 费用申请日期到
     */
    private Date dateTo;

    /**
     * 期间
     */
    @NotEmpty
    @Length(max = 30)
    private String periodName;

    /**
     * 业务币种
     */
    @NotEmpty
    @Length(max = 30)
    private String businessCurrencyCode;

    /**
     * 业务币种名称
     */
    @Transient
    private String businessCurrencyName;

    /**
     * 业务币种->支付币种汇率类型
     */
    @Length(max = 30)
    private String biz2payExchangeType;

    /**
     * 业务币种->支付币种汇率类型名称
     */
    @Transient
    private String biz2payExchangeTypeName;
    /**
     * 业务币种->支付币种汇率
     */
    private BigDecimal biz2payExchangeRate;

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
     * 管理币种
     */
    @NotEmpty
    @Length(max = 30)
    private String managementCurrencyCode;

    /**
     * 管理币种名称
     */
    @Transient
    private String managementCurrencyName;
    /**
     * 业务币种->管理币种汇率类型
     */
    @Length(max = 30)
    private String pay2magExchangeType;

    /**
     * 业务币种->管理币种汇率类型名称
     */
    @Transient
    private String pay2magExchangeTypeName;
    /**
     * 业务币种->管理币种汇率
     */
    private BigDecimal pay2magExchangeRate;

    /**
     * 管理组织级报销类型ID
     */
    @NotNull
    private Long moExpenseTypeId;

    /**
     * 管理组织级报销类型名称
     */
    @Transient
    private String moExpenseTypeName;
    /**
     * 管理组织级费用申请项目ID
     */
    @NotNull
    private Long moReqItemId;

    /**
     * 管理组织级费用申请项目名称
     */
    @Transient
    private String moReqItemName;

    /**
     * 预算项目ID
     */
    private Long budgetItemId;

    /**
     * 预算项目名称
     */
    @Transient
    private String budgetItemName;
    /**
     * 业务币种单价
     */
    private BigDecimal businessPrice;

    /**
     * 付款币种单价
     */
    private BigDecimal paymentPrice;

    /**
     * 管理币种单价
     */
    private BigDecimal managementPrice;

    /**
     * 数量
     */
    @NotNull
    private Long primaryQuantity;

    /**
     * 数量单位
     */
    @Length(max = 30)
    private String primaryUom;

    /**
     * 废弃_次要数量
     */
    private Long secondaryQuantity;

    /**
     * 废弃_次要数量单位
     */
    @Length(max = 30)
    private String secondaryUom;

    /**
     * 业务币种金额
     */
    private BigDecimal businessAmount;

    /**
     * 付款币种金额
     */
    private BigDecimal paymentAmount;

    /**
     * 管理币种金额
     */
    private BigDecimal managementAmount;

    /**
     * 本位币金额
     */
    private BigDecimal requisitionFunctionalAmount;

    /**
     * 废弃_分配集
     */
    private Long distributionSetId;

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
     * 部门名称
     */
    @Transient
    private String unitName;
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
     * 员工名称
     */
    @Transient
    private String employeeName;
    /**
     * 核算主体ID
     */
    @NotNull
    private Long accEntityId;

    /**
     * 核算主体名称
     */
    @Transient
    private String accEntityName;

    /**
     * 责任中心ID
     */
    @NotNull
    private Long respCenterId;

    /**
     * 责任中心名称
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
     * 废弃_付款标志
     */
    @Length(max = 1)
    private String paymentFlag;

    /**
     * 废弃_申请状态
     */
    @Length(max = 30)
    private String requisitionStatus;

    /**
     * 废弃_审核标志
     */
    @Length(max = 1)
    private String auditFlag;

    /**
     * 附件数量
     */
    private Long attachmentNum;

    /**
     * 维度1
     */
    private Long dimension1Id;

    /**
     * 维度1名称
     */
    @Transient
    private String dimension1Name;

    /**
     * 维度1类型
     */
    @Transient
    private String dimension1Level;
    /**
     * 维度2
     */
    private Long dimension2Id;

    /**
     * 维度2名称
     */
    @Transient
    private String dimension2Name;

    /**
     * 维度2类型
     */
    @Transient
    private String dimension2Level;
    /**
     * 维度3
     */
    private Long dimension3Id;
    /**
     * 维度3名称
     */
    @Transient
    private String dimension3Name;

    /**
     * 维度3类型
     */
    @Transient
    private String dimension3Level;
    /**
     * 维度4
     */
    private Long dimension4Id;
    /**
     * 维度4名称
     */
    @Transient
    private String dimension4Name;

    /**
     * 维度4类型
     */
    @Transient
    private String dimension4Level;
    /**
     * 维度5
     */
    private Long dimension5Id;
    /**
     * 维度5名称
     */
    @Transient
    private String dimension5Name;

    /**
     * 维度5类型
     */
    @Transient
    private String dimension5Level;
    /**
     * 维度6
     */
    private Long dimension6Id;

    /**
     * 维度6名称
     */
    @Transient
    private String dimension6Name;

    /**
     * 维度6类型
     */
    @Transient
    private String dimension6Level;
    /**
     * 维度7
     */
    private Long dimension7Id;
    /**
     * 维度7名称
     */
    @Transient
    private String dimension7Name;

    /**
     * 维度7类型
     */
    @Transient
    private String dimension7Level;
    /**
     * 维度8
     */
    private Long dimension8Id;
    /**
     * 维度8名称
     */
    @Transient
    private String dimension8Name;

    /**
     * 维度8类型
     */
    @Transient
    private String dimension8Level;
    /**
     * 维度9
     */
    private Long dimension9Id;
    /**
     * 维度9名称
     */
    @Transient
    private String dimension9Name;

    /**
     * 维度9类型
     */
    @Transient
    private String dimension9Level;
    /**
     * 维度10
     */
    private Long dimension10Id;
    /**
     * 维度10名称
     */
    @Transient
    private String dimension10Name;

    /**
     * 维度10类型
     */
    @Transient
    private String dimension10Level;
    /**
     * 维度11
     */
    private Long dimension11Id;
    /**
     * 维度11名称
     */
    @Transient
    private String dimension11Name;

    /**
     * 维度11类型
     */
    @Transient
    private String dimension11Level;
    /**
     * 维度12
     */
    private Long dimension12Id;
    /**
     * 维度12名称
     */
    @Transient
    private String dimension12Name;

    /**
     * 维度12类型
     */
    @Transient
    private String dimension12Level;
    /**
     * 维度13
     */
    private Long dimension13Id;
    /**
     * 维度13名称
     */
    @Transient
    private String dimension13Name;

    /**
     * 维度13类型
     */
    @Transient
    private String dimension13Level;
    /**
     * 维度14
     */
    private Long dimension14Id;
    /**
     * 维度14名称
     */
    @Transient
    private String dimension14Name;

    /**
     * 维度14类型
     */
    @Transient
    private String dimension14Level;
    /**
     * 维度15
     */
    private Long dimension15Id;
    /**
     * 维度15名称
     */
    @Transient
    private String dimension15Name;

    /**
     * 维度15类型
     */
    @Transient
    private String dimension15Level;
    /**
     * 维度16
     */
    private Long dimension16Id;
    /**
     * 维度16名称
     */
    @Transient
    private String dimension16Name;

    /**
     * 维度16类型
     */
    @Transient
    private String dimension16Level;
    /**
     * 维度17
     */
    private Long dimension17Id;
    /**
     * 维度17名称
     */
    @Transient
    private String dimension17Name;

    /**
     * 维度17类型
     */
    @Transient
    private String dimension17Level;
    /**
     * 维度18
     */
    private Long dimension18Id;
    /**
     * 维度18名称
     */
    @Transient
    private String dimension18Name;

    /**
     * 维度18类型
     */
    @Transient
    private String dimension18Level;
    /**
     * 维度19
     */
    private Long dimension19Id;
    /**
     * 维度19名称
     */
    @Transient
    private String dimension19Name;

    /**
     * 维度19类型
     */
    @Transient
    private String dimension19Level;
    /**
     * 维度20
     */
    private Long dimension20Id;
    /**
     * 维度20名称
     */
    @Transient
    private String dimension20Name;

    /**
     * 维度20类型
     */
    @Transient
    private String dimension20Level;
    /**
     * 招待对象
     */
    @Length(max = 30)
    private String entertainObject;

    /**
     * 招待人数
     */
    private Long entertainNumber;

    @Id
    @GeneratedValue
    private Long expRequisitionLineId;

    /**
     * 申请单头ID
     */
    @NotNull
    private Long expRequisitionHeaderId;

    /**
     * 行号
     */
    @NotNull
    private Long lineNumber;

    /**
     * 申请单页面元素代码
     */
    @NotEmpty
    @Length(max = 30)
    private String reqPageElementCode;


    /**
     * 非本表字段 关闭日期  long weikun.wang  2019/3/11
     */
    @Transient
    private Date closedDate;

    /**
     * 差旅行id
     */
    @Transient
    private Long travelLineId;

    /**
     * 差旅行类别
     */
    @Transient
    private String travelLineCategory;

    /**
     * 行程交通工具
     */
    @Transient
    private String transportation;

    /**
     * 行程交通工具名称
     */
    @Transient
    private String transportationName;

    /**
     * 行程_舱位/座位等级
     */
    @Transient
    private String seatClass;
    /**
     * 行程_舱位/座位等级名称
     */
    @Transient
    private String seatClassName;

    /**
     * 行程_出发地
     */
    @Transient
    private String departurePlaceName;

    /**
     * 行程_出发地ID
     */
    @Transient
    private Long departurePlaceId;

    /**
     * 行程_出发日期
     */
    @Transient
    private Date departureDate;

    /**
     * 行程_到达地ID
     */
    @Transient
    private Long arrivalPlaceId;

    /**
     * 行程_到达地
     */
    @Transient
    private String arrivalPlaceName;

    /**
     * 行程_到达日期
     */
    @Transient
    private Date arrivalDate;

    /**
     * 住宿_入住日期
     */
    @Transient
    private Date accommodationDateFrom;

    /**
     * 住宿_离店日期
     */
    @Transient
    private Date accommodationDateTo;

    /**
     * 住宿_住宿天数
     */
    @Transient
    private Long accommodationDays;

    /**
     * 同行人
     */
    @Transient
    private String peerPeopleName;

    /**
     * 统购标志
     */
    @Transient
    private String monopolizeFlag;

    /**
     * 统购平台
     */
    @Transient
    private String monopolizePlatform;
    /**
     * 费用标准
     */
    @Transient
    private BigDecimal expenseStandard;

    /**
     * 上限值
     */
    @Transient
    private BigDecimal upperLimit;
    /**
     * 下限值
     */
    @Transient
    private BigDecimal lowerLimit;
    /**
     * 控制标志
     */
    @Transient
    private String commitFlag;
    /**
     * 申请单类型id
     */
    @Transient
    private Long moExpReqTypeId;

    /**
     * 可修改标识
     */
    @Transient
    private String changeableFlag;


}
