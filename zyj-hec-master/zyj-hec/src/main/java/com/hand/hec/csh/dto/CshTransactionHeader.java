package com.hand.hec.csh.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 现金事务头表DTO
 * </p>
 * 
 * @author Tagin 2019/01/22 10:28
 */

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_transaction_header")
public class CshTransactionHeader extends BaseDTO {

    public static final String FIELD_TRANSACTION_HEADER_ID = "transactionHeaderId";
    public static final String FIELD_TRANSACTION_NUM = "transactionNum";
    public static final String FIELD_TRANSACTION_TYPE = "transactionType";
    public static final String FIELD_MO_CSH_TRX_CLASS_ID = "moCshTrxClassId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ENABLED_WRITE_OFF_FLAG = "enabledWriteOffFlag";
    public static final String FIELD_TRANSACTION_DATE = "transactionDate";
    public static final String FIELD_TRANSACTION_DATE_TZ = "transactionDateTz";
    public static final String FIELD_TRANSACTION_DATE_LTZ = "transactionDateLtz";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_TRANSACTION_CATEGORY = "transactionCategory";
    public static final String FIELD_POSTED_FLAG = "postedFlag";
    public static final String FIELD_REVERSED_FLAG = "reversedFlag";
    public static final String FIELD_REVERSED_DATE = "reversedDate";
    public static final String FIELD_REVERSED_DATE_TZ = "reversedDateTz";
    public static final String FIELD_REVERSED_DATE_LTZ = "reversedDateLtz";
    public static final String FIELD_RETURNED_FLAG = "returnedFlag";
    public static final String FIELD_WRITE_OFF_FLAG = "writeOffFlag";
    public static final String FIELD_WRITE_OFF_COMPLETED_DATE = "writeOffCompletedDate";
    public static final String FIELD_WRITE_OFF_COMPLETED_DATE_TZ = "writeOffCompletedDateTz";
    public static final String FIELD_WRITE_OFF_COMPLETED_DATE_LTZ = "writeOffCompletedDateLtz";
    public static final String FIELD_SOURCE_HEADER_ID = "sourceHeaderId";
    public static final String FIELD_GLD_INTERFACE_FLAG = "gldInterfaceFlag";
    public static final String FIELD_SOURCE_PAYMENT_HEADER_ID = "sourcePaymentHeaderId";
    public static final String FIELD_EBANKING_FLAG = "ebankingFlag";
    public static final String FIELD_DOCUMENT_CATEGORY = "documentCategory";
    public static final String FIELD_DOCUMENT_TYPE_ID = "documentTypeId";
    public static final String FIELD_PAYMENT_USEDE_ID = "paymentUsedeId";
    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_PAYEE_ID = "payeeId";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_BANK_ACCOUNT_ID = "bankAccountId";

    /**
     * 现金事务类型
     * 
     * @TRX_TYPE_PAYMENT 付款现金事务
     * @TRX_TYPE_PREPAYMENT 预付款现金事务
     */
    public static final String TRX_TYPE_PAYMENT = "PAYMENT";
    public static final String TRX_TYPE_PREPAYMENT = "PREPAYMENT";

    /**
     * 现金事务类别
     * 
     * @TRX_CATEGORY_BUSINESS 经营类
     * @TRX_CATEGORY_MISCELLANEOUS 杂项类
     */
    public static final String TRX_CATEGORY_BUSINESS = "BUSINESS";
    public static final String TRX_CATEGORY_MISCELLANEOUS = "MISCELLANEOUS";

    /**
     * 退款标志
     * 
     * @RETURNED_FLAG_N 未退款
     * @RETURNED_FLAG_Y 部分退款
     * @RETURNED_FLAG_C 完全退款
     * @RETURNED_FLAG_R 退款事务
     */
    public static final String RETURNED_FLAG_N = "N";
    public static final String RETURNED_FLAG_Y = "Y";
    public static final String RETURNED_FLAG_C = "C";
    public static final String RETURNED_FLAG_R = "R";

    /**
     * 反冲标志
     * 
     * @REVERSED_FLAG_N 未反冲
     * @REVERSED_FLAG_R 反冲
     * @REVERSED_FLAG_W 被反冲
     */
    public static final String REVERSED_FLAG_N = "N";
    public static final String REVERSED_FLAG_R = "R";
    public static final String REVERSED_FLAG_W = "W";

    /**
     * 过账标志
     * 
     * @POSTED_FLAG_N 未过账
     * @POSTED_FLAG_Y 已过账
     * @POSTED_FLAG_H 挂起
     */
    public static final String POSTED_FLAG_N = "N";
    public static final String POSTED_FLAG_Y = "Y";
    public static final String POSTED_FLAG_H = "H";

    /**
     * 总账标识
     * 
     * @INTERFACE_FLAG_N 未入总账
     * @INTERFACE_FLAG_P 可入总账
     */
    public static final String INTERFACE_FLAG_N = "N";
    public static final String INTERFACE_FLAG_P = "P";
    public static final String INTERFACE_FLAG_H = "H";

    /**
     * ERP 接口类型
     *
     * @SYS_INTERFACE_EBS_AP 借款申请单需审核后付款（接EBS AP模块）
     * @SYS_INTERFACE_EBS_GL 借款申请单无需审核直接付款（接EBS GL模块）
     * @SYS_INTERFACE_SAP_GL 借款申请单无需审核直接付款（接SAP GL模块）
     */
    public static final String SYS_INTERFACE_EBS_AP = "EBS-AP";
    public static final String SYS_INTERFACE_EBS_GL = "EBS-GL";
    public static final String SYS_INTERFACE_SAP_GL = "SAP-GL";

    @Id
    @GeneratedValue
    private Long transactionHeaderId;

    /**
     * 现金事物编号
     */
    @NotEmpty
    @Length(max = 30)
    private String transactionNum;

    /**
     * 现金事物类型(PAYMENT,PREPAYMENT)
     */
    @NotEmpty
    @Length(max = 30)
    private String transactionType;

    /**
     * 现金事物分类ID
     */
    private Long moCshTrxClassId;

    /**
     * 公司ID
     */
    @NotNull
    private Long companyId;

    /**
     * 核算主体ID
     */
    @NotNull
    private Long accEntityId;

    /**
     * 经办人，源单据头上员工
     */
    @NotNull
    private Long employeeId;

    /**
     * 描述
     */
    @Length(max = 2000)
    private String description;

    /**
     * 是否启用核销
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledWriteOffFlag;

    /**
     * 交易日期
     */
    private Date transactionDate;

    /**
     * 交易日期_当地时区
     */
    private Date transactionDateTz;

    /**
     * 交易日期_查询时区
     */
    private Date transactionDateLtz;

    /**
     * 期间
     */
    @NotEmpty
    @Length(max = 30)
    private String periodName;

    /**
     * 付款方式ID
     */
    @NotNull
    private Long paymentMethodId;

    /**
     * 现金事物类别(BUSINESS,MISCELLANEOUS)
     */
    @NotEmpty
    @Length(max = 30)
    private String transactionCategory;

    /**
     * 过账标志
     */
    @NotEmpty
    @Length(max = 1)
    private String postedFlag;

    /**
     * 反冲标志
     */
    @NotEmpty
    @Length(max = 1)
    private String reversedFlag;

    /**
     * 反冲日期
     */
    private Date reversedDate;

    /**
     * 反冲日期_当地时区
     */
    private Date reversedDateTz;

    /**
     * 反冲日期_查询时区
     */
    private Date reversedDateLtz;

    /**
     * 退款标志(R退款事物\Y部分退款\C完全退款\N未退款)
     */
    @Length(max = 1)
    private String returnedFlag;

    /**
     * 核销标志（C完全核销\Y部分核销\N未核销）
     */
    @NotEmpty
    @Length(max = 1)
    private String writeOffFlag;

    /**
     * 核销日期
     */
    private Date writeOffCompletedDate;

    /**
     * 核销日期_当地时区
     */
    private Date writeOffCompletedDateTz;

    /**
     * 核销日期_查询时区
     */
    private Date writeOffCompletedDateLtz;

    /**
     * 反冲、退款的来源现金事物头ID
     */
    private Long sourceHeaderId;

    /**
     * 总账标志（N未入总账\P可入总账）
     */
    @NotEmpty
    @Length(max = 1)
    private String gldInterfaceFlag;

    /**
     * 来源付款现金事物ID
     */
    private Long sourcePaymentHeaderId;

    /**
     * 网银标志
     */
    @NotEmpty
    @Length(max = 1)
    private String ebankingFlag;

    /**
     * 单据类别
     */
    @Length(max = 30)
    private String documentCategory;

    /**
     * 单据类型ID
     */
    private Long documentTypeId;

    /**
     * 付款用途ID
     */
    private Long paymentUsedeId;

    /**
     * 收款方类型
     */
    @Length(max = 30)
    private String payeeCategory;

    /**
     * 收款方
     */
    private Long payeeId;

    /**
     * 币种
     */
    @Length(max = 30)
    private String currencyCode;

    /**
     * 付款方银行账户
     */
    private Long bankAccountId;

}
