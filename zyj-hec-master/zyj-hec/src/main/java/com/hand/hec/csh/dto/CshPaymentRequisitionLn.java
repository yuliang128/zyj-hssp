package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_payment_requisition_ln")
public class CshPaymentRequisitionLn extends BaseDTO {

    public static final String FIELD_PAYMENT_REQUISITION_LINE_ID = "paymentRequisitionLineId";
    public static final String FIELD_PAYMENT_REQUISITION_HEADER_ID = "paymentRequisitionHeaderId";
    public static final String FIELD_LINE_NUMBER = "lineNumber";
    public static final String FIELD_PAYMENT_REQUISITION_LINE_TYPE = "paymentRequisitionLineType";
    public static final String FIELD_REF_DOCUMENT_ID = "refDocumentId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_PAYEE_ID = "payeeId";
    public static final String FIELD_PAYEE_CODE = "payeeCode";
    public static final String FIELD_ACCOUNT_NAME = "accountName";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_ACCOUNT_NUMBER = "accountNumber";
    public static final String FIELD_MO_CSH_TRX_CLASS_ID = "moCshTrxClassId";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PAYMENT_STATUS = "paymentStatus";
    public static final String FIELD_PAYMENT_COMPLETED_DATE = "paymentCompletedDate";
    public static final String FIELD_CASH_FLOW_ITEM_ID = "cashFlowItemId";
    public static final String FIELD_BANK_CODE = "bankCode";
    public static final String FIELD_BANK_NAME = "bankName";
    public static final String FIELD_BANK_LOCATION_CODE = "bankLocationCode";
    public static final String FIELD_BANK_LOCATION_NAME = "bankLocationName";
    public static final String FIELD_PROVINCE_CODE = "provinceCode";
    public static final String FIELD_PROVINCE_NAME = "provinceName";
    public static final String FIELD_CITY_CODE = "cityCode";
    public static final String FIELD_CITY_NAME = "cityName";


    @Id
    @GeneratedValue
    private Long paymentRequisitionLineId;

    /**
     * 借款申请单头表ID
     */
    @NotNull
    @Where
    private Long paymentRequisitionHeaderId;

    /**
     * 行号
     */
    @NotNull
    private Long lineNumber;

    /**
     * 行类型（OTHERS其他，EXP_REQUISITION费用申请单）
     */
    @NotEmpty
    @Length(max = 30)
    private String paymentRequisitionLineType;

    /**
     * 关联单据ID
     */
    private Long refDocumentId;

    /**
     * 公司ID
     */
    @NotNull
    private Long companyId;

    /**
     * 核算主体
     */
    @NotNull
    private Long accEntityId;

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
     * 会计科目描述
     */
    @Length(max = 2000)
    private String accountName;

    /**
     * 支付方式
     */
    private Long paymentMethodId;

    /**
     * 会计科目编码
     */
    @Length(max = 30)
    private String accountNumber;

    /**
     * 现金事务分类ID
     */
    @NotNull
    private Long moCshTrxClassId;

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
     * 付款状态（COMPLETELY全部付款，NEVER从未付款，PARTIALLY部分付款）
     */
    @Length(max = 10)
    private String paymentStatus;

    /**
     * 付款完成日期
     */
    private Date paymentCompletedDate;

    /**
     * 现金流量项ID（现金事务分类定义中设置的现金流量项）
     */
    private Long cashFlowItemId;

    /**
     * 银行代码
     */
    @Length(max = 30)
    private String bankCode;

    /**
     * 银行名称
     */
    @Length(max = 200)
    private String bankName;

    /**
     * 分行代码
     */
    @Length(max = 30)
    private String bankLocationCode;

    /**
     * 分行名称
     */
    @Length(max = 200)
    private String bankLocationName;

    /**
     * 分行所在省
     */
    @Length(max = 30)
    private String provinceCode;

    /**
     * 省名称
     */
    @Length(max = 200)
    private String provinceName;

    /**
     * 分行所在市
     */
    @Length(max = 30)
    private String cityCode;

    /**
     * 市名称
     */
    @Length(max = 200)
    private String cityName;

    /**
     * 管理组织Id
     */
    @Transient
    private Long magOrgId;

    /**
     * 收款对象类型Id
     */
    @Transient
    private Long payeeTypeId;

    /**
     * 账套Id
     */
    @Transient
    private Long setOfBooksId;

    /**
     * 岗位Id
     */
    @Transient
    private Long positionId;

    /**
     * 市名称
     */
    @Transient
    private Long headerAccEntityId;

    /**
     * 现金事物分类代码
     */
    @Transient
    private String moCshTrxClassCode;

    /**
     * 默认责任中心Id
     */
    @Transient
    private Long respCenterId;

    /**
     * 现金事物分类代码
     */
    @Transient
    private String currencyCode;

    /**
     * 现金事物分类代码
     */
    @Transient
    private String headerDescription;
}
