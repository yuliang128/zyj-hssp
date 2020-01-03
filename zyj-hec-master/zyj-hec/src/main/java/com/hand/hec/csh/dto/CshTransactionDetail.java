package com.hand.hec.csh.dto;

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
@ExtensionAttribute(disable=true)
@Table(name = "csh_transaction_detail")
public class CshTransactionDetail extends BaseDTO {

     public static final String FIELD_PAYEE_BANK_CODE = "payeeBankCode";
     public static final String FIELD_PAYEE_BANK_NAME = "payeeBankName";
     public static final String FIELD_PAYEE_BANK_LOCATION_CODE = "payeeBankLocationCode";
     public static final String FIELD_PAYEE_BANK_LOCATION = "payeeBankLocation";
     public static final String FIELD_PAYEE_PROVINCE_CODE = "payeeProvinceCode";
     public static final String FIELD_PAYEE_PROVINCE_NAME = "payeeProvinceName";
     public static final String FIELD_PAYEE_CITY_CODE = "payeeCityCode";
     public static final String FIELD_PAYEE_CITY_NAME = "payeeCityName";
     public static final String FIELD_PAYEE_ACCOUNT_NUMBER = "payeeAccountNumber";
     public static final String FIELD_PAYEE_ACCOUNT_NAME = "payeeAccountName";
     public static final String FIELD_PAYEE_ACCOUNT_TYPE = "payeeAccountType";
     public static final String FIELD_SEGMENT1 = "segment1";
     public static final String FIELD_SEGMENT2 = "segment2";
     public static final String FIELD_SEGMENT3 = "segment3";
     public static final String FIELD_SEGMENT4 = "segment4";
     public static final String FIELD_SEGMENT5 = "segment5";
     public static final String FIELD_SEGMENT6 = "segment6";
     public static final String FIELD_SEGMENT7 = "segment7";
     public static final String FIELD_SEGMENT8 = "segment8";
     public static final String FIELD_SEGMENT9 = "segment9";
     public static final String FIELD_SEGMENT10 = "segment10";
     public static final String FIELD_SEGMENT11 = "segment11";
     public static final String FIELD_SEGMENT12 = "segment12";
     public static final String FIELD_SEGMENT13 = "segment13";
     public static final String FIELD_SEGMENT14 = "segment14";
     public static final String FIELD_SEGMENT15 = "segment15";
     public static final String FIELD_SEGMENT16 = "segment16";
     public static final String FIELD_SEGMENT17 = "segment17";
     public static final String FIELD_SEGMENT18 = "segment18";
     public static final String FIELD_SEGMENT19 = "segment19";
     public static final String FIELD_SEGMENT20 = "segment20";
     public static final String FIELD_DETAIL_ID = "detailId";
     public static final String FIELD_CSH_TRANSACTION_LINE_ID = "cshTransactionLineId";
     public static final String FIELD_HEC_BATCH_NUM = "hecBatchNum";
     public static final String FIELD_HEC_DETAIL_NUM = "hecDetailNum";
     public static final String FIELD_ITF_BATCH_NUM = "itfBatchNum";
     public static final String FIELD_ITF_DETAIL_NUM = "itfDetailNum";
     public static final String FIELD_PAYMENT_STATUS = "paymentStatus";
     public static final String FIELD_INTERFACE_STATUS = "interfaceStatus";
     public static final String FIELD_STATUS_DESC = "statusDesc";
     public static final String FIELD_SOURCE_CODE = "sourceCode";
     public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_COMPANY_CODE = "companyCode";
     public static final String FIELD_DOCUMENT_TYPE = "documentType";
     public static final String FIELD_DOCUMENT_ID = "documentId";
     public static final String FIELD_DOCUMENT_NUMBER = "documentNumber";
     public static final String FIELD_DOCUMENT_LINE_ID = "documentLineId";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_PAYMENT_METHOD = "paymentMethod";
     public static final String FIELD_PAYMENT_CHANNEL = "paymentChannel";
     public static final String FIELD_EBANK_CHANNEL = "ebankChannel";
     public static final String FIELD_PAY_DATE = "payDate";
     public static final String FIELD_PAY_DATE_TZ = "payDateTz";
     public static final String FIELD_PAY_DATE_LTZ = "payDateLtz";
     public static final String FIELD_REQUEST_TIME = "requestTime";
     public static final String FIELD_REQUEST_TIME_TZ = "requestTimeTz";
     public static final String FIELD_REQUEST_TIME_LTZ = "requestTimeLtz";
     public static final String FIELD_ACTUAL_PAY_DATE = "actualPayDate";
     public static final String FIELD_ACTUAL_PAY_DATE_TZ = "actualPayDateTz";
     public static final String FIELD_ACTUAL_PAY_DATE_LTZ = "actualPayDateLtz";
     public static final String FIELD_ITF_PAY_DATE = "itfPayDate";
     public static final String FIELD_ITF_PAY_DATE_TZ = "itfPayDateTz";
     public static final String FIELD_ITF_PAY_DATE_LTZ = "itfPayDateLtz";
     public static final String FIELD_REFUND_TIME = "refundTime";
     public static final String FIELD_REFUND_TIME_TZ = "refundTimeTz";
     public static final String FIELD_REFUND_TIME_LTZ = "refundTimeLtz";
     public static final String FIELD_CASH_FLOW_CODE = "cashFlowCode";
     public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
     public static final String FIELD_PAYEE_ID = "payeeId";
     public static final String FIELD_PAYEE_CODE = "payeeCode";
     public static final String FIELD_PAYEE_NAME = "payeeName";
     public static final String FIELD_USEDES = "usedes";
     public static final String FIELD_CURRENCY = "currency";
     public static final String FIELD_AMOUNT = "amount";
     public static final String FIELD_DRAWEE_ID = "draweeId";
     public static final String FIELD_ENCRYPTION_AMOUNT = "encryptionAmount";
     public static final String FIELD_ENCRYPTION_VERSION = "encryptionVersion";
     public static final String FIELD_DRAWEE_BANK_CODE = "draweeBankCode";
     public static final String FIELD_DRAWEE_BANK_NAME = "draweeBankName";
     public static final String FIELD_DRAWEE_BANK_LOCATION_CODE = "draweeBankLocationCode";
     public static final String FIELD_DRAWEE_BANK_LOCATION = "draweeBankLocation";
     public static final String FIELD_DRAWEE_PROVINCE_CODE = "draweeProvinceCode";
     public static final String FIELD_DRAWEE_PROVINCE_NAME = "draweeProvinceName";
     public static final String FIELD_DRAWEE_CITY_CODE = "draweeCityCode";
     public static final String FIELD_DRAWEE_CITY_NAME = "draweeCityName";
     public static final String FIELD_DRAWEE_ACCOUNT_NUMBER = "draweeAccountNumber";
     public static final String FIELD_DRAWEE_ACCOUNT_NAME = "draweeAccountName";


    /**
     * 收款方银行代码
     */
     @Length(max = 30)
     private String payeeBankCode;

    /**
     * 收款方银行名称
     */
     @Length(max = 200)
     private String payeeBankName;

    /**
     * 收款方分行代码
     */
     @Length(max = 30)
     private String payeeBankLocationCode;

    /**
     * 收款方分行名称
     */
     @Length(max = 200)
     private String payeeBankLocation;

    /**
     * 收款方分行所在省
     */
     @Length(max = 30)
     private String payeeProvinceCode;

    /**
     * 收款方省名称
     */
     @Length(max = 200)
     private String payeeProvinceName;

    /**
     * 收款方分行所在市
     */
     @Length(max = 30)
     private String payeeCityCode;

    /**
     * 收款方市名称
     */
     @Length(max = 200)
     private String payeeCityName;

    /**
     * 收款方银行账号
     */
     @Length(max = 200)
     private String payeeAccountNumber;

    /**
     * 收款方银行户名
     */
     @Length(max = 200)
     private String payeeAccountName;

    /**
     * 收款方账户类型，PERSONAL个人账户BUSINESS对公账户
     */
     @Length(max = 30)
     private String payeeAccountType;

    /**
     * 备用字段1
     */
     @Length(max = 200)
     private String segment1;

    /**
     * 备用字段2
     */
     @Length(max = 200)
     private String segment2;

    /**
     * 备用字段3
     */
     @Length(max = 200)
     private String segment3;

    /**
     * 备用字段4
     */
     @Length(max = 200)
     private String segment4;

    /**
     * 备用字段5
     */
     @Length(max = 200)
     private String segment5;

    /**
     * 备用字段6
     */
     @Length(max = 200)
     private String segment6;

    /**
     * 备用字段7
     */
     @Length(max = 200)
     private String segment7;

    /**
     * 备用字段8
     */
     @Length(max = 200)
     private String segment8;

    /**
     * 备用字段9
     */
     @Length(max = 200)
     private String segment9;

    /**
     * 备用字段10
     */
     @Length(max = 200)
     private String segment10;

    /**
     * 备用字段11
     */
     @Length(max = 200)
     private String segment11;

    /**
     * 备用字段12
     */
     @Length(max = 200)
     private String segment12;

    /**
     * 备用字段13
     */
     @Length(max = 200)
     private String segment13;

    /**
     * 备用字段14
     */
     @Length(max = 200)
     private String segment14;

    /**
     * 备用字段15
     */
     @Length(max = 200)
     private String segment15;

    /**
     * 备用字段16
     */
     @Length(max = 200)
     private String segment16;

    /**
     * 备用字段17
     */
     @Length(max = 200)
     private String segment17;

    /**
     * 备用字段18
     */
     @Length(max = 200)
     private String segment18;

    /**
     * 备用字段19
     */
     @Length(max = 200)
     private String segment19;

    /**
     * 备用字段20
     */
     @Length(max = 200)
     private String segment20;

     @Id
     @GeneratedValue
     private Long detailId;

    /**
     * 现金事物行ID
     */
     @NotNull
     private Long cshTransactionLineId;

    /**
     * 费控提请接口批次号
     */
     @Length(max = 200)
     private String hecBatchNum;

    /**
     * 费控提请接口明细批次号
     */
     @Length(max = 200)
     private String hecDetailNum;

    /**
     * 接口返回批次号
     */
     @Length(max = 200)
     private String itfBatchNum;

    /**
     * 接口返回明细批次号
     */
     @Length(max = 200)
     private String itfDetailNum;

    /**
     * 付款状态[PENDING暂挂],[WAITING_SEND等待发送],[PAY_SENT支付传送],[WAITING_CONFIRM_SUCCESS待付确认成功],[PAY_SUCCESS支付成功],[PAY_FAILED支付失败],[REFUNDING退票中],[REFUND_CONFIRM退票确认]
     */
     @Length(max = 30)
     private String paymentStatus;

    /**
     * 接口状态[UNSENT未发送，可以提交接口],[DATA_FORMAT_ERROR数据整理错误,可以提交接口],[SENT已发送],[UNKNOWN传送状态未知],[FAILED传送失败]
     */
     @Length(max = 30)
     private String interfaceStatus;

    /**
     * 接口状态描述
     */
     @Length(max = 2000)
     private String statusDesc;

    /**
     * 业务来源
     */
     @Length(max = 30)
     private String sourceCode;

    /**
     * 核算主体ID
     */
     @NotNull
     private Long accEntityId;

    /**
     * 公司ID
     */
     @NotNull
     private Long companyId;

    /**
     * 公司代码
     */
     @NotEmpty
     @Length(max = 30)
     private String companyCode;

    /**
     * 业务类型
     */
     @Length(max = 30)
     private String documentType;

    /**
     * 单据ID
     */
     private Long documentId;

    /**
     * 单据编号
     */
     @Length(max = 30)
     private String documentNumber;

    /**
     * 单据明细ID
     */
     private Long documentLineId;

    /**
     * 备注字段
     */
     @Length(max = 2000)
     private String description;

    /**
     * 付款方式
     */
     @Length(max = 30)
     private String paymentMethod;

    /**
     * 付款渠道，ENANK(资金系统)、OFFER(报盘)、OTHER(其他)
     */
     @Length(max = 30)
     private String paymentChannel;

    /**
     * 资金系统渠道，CN_PUBLIC(国内对公)，CN_PRIVATE(国内对私)
     */
     @Length(max = 30)
     private String ebankChannel;

    /**
     * 费控内支付日期
     */
     private Date payDate;

    /**
     * 费控内支付日期_TZ
     */
     private Date payDateTz;

    /**
     * 费控内支付日期_LTZ
     */
     private Date payDateLtz;

    /**
     * 费控内支付请求时间
     */
     private Date requestTime;

    /**
     * 费控内支付请求时间_TZ
     */
     private Date requestTimeTz;

    /**
     * 费控内支付请求时间_LTZ
     */
     private Date requestTimeLtz;

    /**
     * 实际支付成功日期
     */
     private Date actualPayDate;

    /**
     * 实际支付成功日期_TZ
     */
     private Date actualPayDateTz;

    /**
     * 实际支付成功日期_LTZ
     */
     private Date actualPayDateLtz;

    /**
     * 传递接口日期
     */
     private Date itfPayDate;

    /**
     * 传递接口日期_TZ
     */
     private Date itfPayDateTz;

    /**
     * 传递接口日期_LTZ
     */
     private Date itfPayDateLtz;

    /**
     * 退票时间
     */
     private Date refundTime;

    /**
     * 退票时间_TZ
     */
     private Date refundTimeTz;

    /**
     * 退票时间_LTZ
     */
     private Date refundTimeLtz;

    /**
     * 现金流量项
     */
     @Length(max = 200)
     private String cashFlowCode;

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
     * 收款方代码
     */
     @NotEmpty
     @Length(max = 30)
     private String payeeCode;

    /**
     * 收款方名称
     */
     @NotEmpty
     @Length(max = 2000)
     private String payeeName;

    /**
     * 付款用途
     */
     @Length(max = 30)
     private String usedes;

    /**
     * 币种
     */
     @NotEmpty
     @Length(max = 30)
     private String currency;

    /**
     * 金额
     */
     private BigDecimal amount;

    /**
     * 付款出纳
     */
     @NotNull
     private Long draweeId;

    /**
     * 加密金额
     */
     @Length(max = 200)
     private String encryptionAmount;

    /**
     * 加密版本
     */
     @Length(max = 50)
     private String encryptionVersion;

    /**
     * 付款方银行代码
     */
     @Length(max = 30)
     private String draweeBankCode;

    /**
     * 付款方银行名称
     */
     @Length(max = 200)
     private String draweeBankName;

    /**
     * 付款方分行代码
     */
     @Length(max = 30)
     private String draweeBankLocationCode;

    /**
     * 付款方分行名称
     */
     @Length(max = 200)
     private String draweeBankLocation;

    /**
     * 付款方分行所在省
     */
     @Length(max = 30)
     private String draweeProvinceCode;

    /**
     * 付款方省名称
     */
     @Length(max = 200)
     private String draweeProvinceName;

    /**
     * 付款方分行所在市
     */
     @Length(max = 30)
     private String draweeCityCode;

    /**
     * 付款方市名称
     */
     @Length(max = 200)
     private String draweeCityName;

    /**
     * 付款方银行账号
     */
     @Length(max = 200)
     private String draweeAccountNumber;

    /**
     * 付款方银行户名
     */
     @Length(max = 200)
     private String draweeAccountName;

     @Transient
     private String columnName;

     @Transient
     private String columnType;

     @Transient
     private String columnComment;


}
