package com.hand.hec.acp.dto;

/**
 * <p>
 * 付款申请单行信息
 * </p>
 * 
 * @author guiyuting 2019/04/30 10:56
 */
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "acp_requisition_ln")
public class AcpRequisitionLn extends BaseDTO {

    public static final String FIELD_REQUISITION_LNS_ID = "requisitionLnsId";
    public static final String FIELD_REQUISITION_HDS_ID = "requisitionHdsId";
    public static final String FIELD_LINE_NUMBER = "lineNumber";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_MO_CSH_TRX_CLASS_ID = "moCshTrxClassId";
    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_PAYEE_ID = "payeeId";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PAYMENT_STATUS = "paymentStatus";
    public static final String FIELD_PAYMENT_COMPLETED_DATE = "paymentCompletedDate";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_PAYMENT_USEDE_ID = "paymentUsedeId";
    public static final String FIELD_CASH_FLOW_ITEM_ID = "cashFlowItemId";
    public static final String FIELD_REQUISITION_PAYMENT_DATE = "requisitionPaymentDate";
    public static final String FIELD_ACCOUNT_NAME = "accountName";
    public static final String FIELD_ACCOUNT_NUMBER = "accountNumber";
    public static final String FIELD_BANK_CODE = "bankCode";
    public static final String FIELD_BANK_NAME = "bankName";
    public static final String FIELD_BANK_LOCATION_CODE = "bankLocationCode";
    public static final String FIELD_BANK_LOCATION_NAME = "bankLocationName";
    public static final String FIELD_PROVINCE_CODE = "provinceCode";
    public static final String FIELD_PROVINCE_NAME = "provinceName";
    public static final String FIELD_CITY_CODE = "cityCode";
    public static final String FIELD_CITY_NAME = "cityName";

    /**
     * 付款状态
     *
     * @PAYMENT_STATUS_NEVER 未付款
     * @PAYMENT_STATUS_PARTIALLY 部分付款
     * @PAYMENT_STATUS_COMPLETELY 完全付款
     */
    public static final String PAYMENT_STATUS_NEVER = "NEVER";
    public static final String PAYMENT_STATUS_PARTIALLY = "PARTIALLY";
    public static final String PAYMENT_STATUS_COMPLETELY = "COMPLETELY";


    public static final String REF_DOCUMENT_TYPE_REPORT = "REPORT";
    public static final String REF_DOCUMENT_TYPE_CONTRACT = "CONTRACT";


    @Id
    @GeneratedValue
    private Long requisitionLnsId;

    /**
     * 付款申请单头ID
     */
    @NotNull
    private Long requisitionHdsId;

    /**
     * 行号
     */
    @NotNull
    private Long lineNumber;

    /**
     * 管理公司ID
     */
    @NotNull
    private Long companyId;

    /**
     * 核算主体ID
     */
    @NotNull
    private Long accEntityId;

    /**
     * 现金事务分类ID
     */
    private Long moCshTrxClassId;

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
    @Length(max = 30)
    private String paymentStatus;

    /**
     * 付款完成日期
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date paymentCompletedDate;

    /**
     * 付款方式ID
     */
    private Long paymentMethodId;

    /**
     * 付款用途ID
     */
    private Long paymentUsedeId;

    /**
     * 现金流量项ID（现金事务分类定义中设置的现金流量项）
     */
    private Long cashFlowItemId;

    /**
     * 计划付款日
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date requisitionPaymentDate;

    /**
     * 银行户名
     */
    @Length(max = 200)
    private String accountName;

    /**
     * 银行帐号
     */
    @Length(max = 200)
    private String accountNumber;

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
     * 分行所在城市
     */
    @Length(max = 30)
    private String cityCode;

    /**
     * 市名称
     */
    @Length(max = 200)
    private String cityName;

    @Transient
    private String moCshTrxClassName;

    @Transient
    private String payeeName;

    @Transient
    private String headerDescription;

    @Transient
    private String currencyCode;

    @Transient
    private Long payeeTypeId;

    @Transient
    private Long magOrgId;

    @Transient
    private Long setOfBooksId;

    @Transient
    private Long positionId;

    @Transient
    private Long respCenterId;

    @Transient
    private String cshTransactionTypeCode;

    @Transient
    private List<AcpRequisitionDtl> report;

    @Transient
    private List<AcpRequisitionDtl> contract;

}
