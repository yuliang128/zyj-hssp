package com.hand.hec.cont.dto;

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

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "con_contract_header")
public class ConContractHeader extends BaseDTO {

     public static final String FIELD_CONTRACT_HEADER_ID = "contractHeaderId";
     public static final String FIELD_CONTRACT_TYPE_ID = "contractTypeId";
     public static final String FIELD_CONTRACT_NUMBER = "contractNumber";
     public static final String FIELD_MAG_ORG_ID = "magOrgId";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_UNIT_ID = "unitId";
     public static final String FIELD_POSITION_ID = "positionId";
     public static final String FIELD_EMPLOYEE_ID = "employeeId";
     public static final String FIELD_REQUISITION_DATE = "requisitionDate";
     public static final String FIELD_PERIOD_NAME = "periodName";
     public static final String FIELD_PROJECT_ID = "projectId";
     public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
     public static final String FIELD_BGT_CENTER_ID = "bgtCenterId";
     public static final String FIELD_PARTNER_CATEGORY = "partnerCategory";
     public static final String FIELD_PARTNER_ID = "partnerId";
     public static final String FIELD_DOCUMENT_NUMBER = "documentNumber";
     public static final String FIELD_DOCUMENT_DESC = "documentDesc";
     public static final String FIELD_START_DATE = "startDate";
     public static final String FIELD_END_DATE = "endDate";
     public static final String FIELD_SIGN_DATE = "signDate";
     public static final String FIELD_SIGN_PLACE = "signPlace";
     public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
     public static final String FIELD_RESP_CENTER_ID = "respCenterId";
     public static final String FIELD_RESP_EMPLOYEE_ID = "respEmployeeId";
     public static final String FIELD_RESP_UNIT_ID = "respUnitId";
     public static final String FIELD_APPLY_UNIT_ID = "applyUnitId";
     public static final String FIELD_CURRENCY_CODE = "currencyCode";
     public static final String FIELD_EXCHANGE_TYPE = "exchangeType";
     public static final String FIELD_EXCHANGE_RATE = "exchangeRate";
     public static final String FIELD_AMOUNT = "amount";
     public static final String FIELD_FUNCTION_AMOUNT = "functionAmount";
     public static final String FIELD_STATUS = "status";
     public static final String FIELD_SOURCE_TYPE = "sourceType";
     public static final String FIELD_SOURCE_CONTRACT_HEADER_ID = "sourceContractHeaderId";
     public static final String FIELD_PRICE_AGREEMENT_FLAG = "priceAgreementFlag";
     public static final String FIELD_MORE_PAYMENT_ENTITY_FLAG = "morePaymentEntityFlag";
     public static final String FIELD_CONTRACT_FORM = "contractForm";
     public static final String FIELD_FORMAT_FLAG = "formatFlag";
     public static final String FIELD_FORMAT_TYPE = "formatType";
     public static final String FIELD_TENDER_METHOD = "tenderMethod";
     public static final String FIELD_FREE_TENDER_NUMBER = "freeTenderNumber";
     public static final String FIELD_ARCHIVE_STATUS = "archiveStatus";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_PUR_ORG_ID = "purOrgId";
     public static final String FIELD_MO_PUR_GROUP_ID = "moPurGroupId";
     public static final String FIELD_BUYER_ID = "buyerId";
     public static final String FIELD_VERSION_NUMBER = "versionNumber";
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


     @Id
     @GeneratedValue
     private Long contractHeaderId;

    /**
     * 合同类型ID
     */
     @NotNull
     private Long contractTypeId;

    /**
     * 合同编号
     */
     @NotEmpty
     @Length(max = 30)
     private String contractNumber;

    /**
     * 管理组织
     */
     private Long magOrgId;

    /**
     * 公司
     */
     @NotNull
     private Long companyId;

    /**
     * 部门
     */
     @NotNull
     private Long unitId;

    /**
     * 岗位
     */
     @NotNull
     private Long positionId;

    /**
     * 员工
     */
     @NotNull
     private Long employeeId;

    /**
     * 申请日期
     */
     private Date requisitionDate;

    /**
     * 期间
     */
     @Length(max = 30)
     private String periodName;

     private Long projectId;

    /**
     * 预算实体
     */
     private Long bgtEntityId;

    /**
     * 预算中心
     */
     private Long bgtCenterId;

    /**
     * 对象类型
     */
     @Length(max = 30)
     private String partnerCategory;

    /**
     * 合同方
     */
     private Long partnerId;

    /**
     * 合同号
     */
     @Length(max = 30)
     private String documentNumber;

    /**
     * 合同名称
     */
     @Length(max = 100)
     private String documentDesc;

    /**
     * 开始时间
     */
     private Date startDate;

    /**
     * 结束时间
     */
     private Date endDate;

    /**
     * 签订日期
     */
     private Date signDate;

    /**
     * 签订地点
     */
     @Length(max = 30)
     private String signPlace;

    /**
     * 核算实体
     */
     @NotNull
     private Long accEntityId;

    /**
     * 责任中心
     */
     @NotNull
     private Long respCenterId;

    /**
     * 责任人
     */
     private Long respEmployeeId;

    /**
     * 责任部门
     */
     private Long respUnitId;

    /**
     * 使用部门
     */
     private Long applyUnitId;

    /**
     * 币种
     */
     @NotEmpty
     @Length(max = 10)
     private String currencyCode;

    /**
     * 汇率类型
     */
     @Length(max = 30)
     private String exchangeType;

    /**
     * 汇率
     */
     private BigDecimal exchangeRate;

    /**
     * 金额
     */
     private BigDecimal amount;

    /**
     * 本币金额
     */
     private BigDecimal functionAmount;

    /**
     * 状态
     */
     @NotEmpty
     @Length(max = 30)
     private String status;

    /**
     * 来源类型
     */
     @Length(max = 30)
     private String sourceType;

    /**
     * 来源合同ID
     */
     private Long sourceContractHeaderId;

    /**
     * 是否有价格协议
     */
     @Length(max = 1)
     private String priceAgreementFlag;

    /**
     * 多付款主体
     */
     @Length(max = 1)
     private String morePaymentEntityFlag;

    /**
     * 合同形式
     */
     @Length(max = 100)
     private String contractForm;

    /**
     * 是否是格式合同
     */
     @Length(max = 1)
     private String formatFlag;

    /**
     * 格式合同类型
     */
     @Length(max = 30)
     private String formatType;

    /**
     * 招标类型
     */
     @Length(max = 30)
     private String tenderMethod;

    /**
     * 招标单号
     */
     @Length(max = 100)
     private String freeTenderNumber;

    /**
     * 归档状态
     */
     @Length(max = 30)
     private String archiveStatus;

    /**
     * 描述
     */
     @Length(max = 4000)
     private String description;

    /**
     * 采购组织
     */
     private Long purOrgId;

    /**
     * 采购组
     */
     private Long moPurGroupId;

    /**
     * 采购员
     */
     private Long buyerId;

    /**
     * 版本号
     */
     @Length(max = 30)
     private String versionNumber;

     private Long dimension1Id;

     private Long dimension2Id;

     private Long dimension3Id;

     private Long dimension4Id;

     private Long dimension5Id;

     private Long dimension6Id;

     private Long dimension7Id;

     private Long dimension8Id;

     private Long dimension9Id;

     private Long dimension10Id;

     private Long dimension11Id;

     private Long dimension12Id;

     private Long dimension13Id;

     private Long dimension14Id;

     private Long dimension15Id;

     private Long dimension16Id;

     private Long dimension17Id;

     private Long dimension18Id;

     private Long dimension19Id;

     private Long dimension20Id;

     }
