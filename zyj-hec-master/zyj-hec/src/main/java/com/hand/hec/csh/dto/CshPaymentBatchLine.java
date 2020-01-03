package com.hand.hec.csh.dto;

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
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_payment_batch_line")
public class CshPaymentBatchLine extends BaseDTO {

    public static final String FIELD_BATCH_LINE_ID = "batchLineId";
    public static final String FIELD_BATCH_HEADER_ID = "batchHeaderId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_TRANSACTION_LINE_ID = "transactionLineId";
    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_PAYEE_ID = "payeeId";
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
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_PAYMENT_STATUS = "paymentStatus";
    public static final String FIELD_REVERSED_FLAG = "reversedFlag";
    public static final String FIELD_REVERSED_DATE = "reversedDate";
    public static final String FIELD_REVERSED_DATE_TZ = "reversedDateTz";
    public static final String FIELD_REVERSED_DATE_LTZ = "reversedDateLtz";
    public static final String FIELD_RETURNED_FLAG = "returnedFlag";
    public static final String FIELD_SOURCE_LINE_ID = "sourceLineId";

    /**
     * 付款状态
     * 
     * @PAYMENT_STATUS_UNPAID 未支付
     * @PAYMENT_STATUS_SUCCESS 支付成功
     * @PAYMENT_STATUS_FAILED 支付失败
     * @PAYMENT_STATUS_CANCEL 已取消
     */
    public static final String PAYMENT_STATUS_UNPAID = "UNPAID";
    public static final String PAYMENT_STATUS_SUCCESS = "PAY_SUCCESS";
    public static final String PAYMENT_STATUS_FAILED = "PAY_FAILED";
    public static final String PAYMENT_STATUS_CANCEL = "CANCELLED";

    @Id
    @GeneratedValue
    private Long batchLineId;

    /**
     * 付款批头ID
     */
    @NotNull
    private Long batchHeaderId;

    /**
     * 核算主体ID
     */
    @NotNull
    private Long accEntityId;

    /**
     * 管理公司ID
     */
    @NotNull
    private Long companyId;

    /**
     * 现金事物行表ID
     */
    @NotNull
    private Long transactionLineId;

    /**
     * 收款对象
     */
    @NotEmpty
    @Length(max = 30)
    private String payeeCategory;

    /**
     * 收款方
     */
    @NotNull
    private Long payeeId;

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
     * 金额
     */
    private BigDecimal amount;

    /**
     * 付款状态
     */
    @NotEmpty
    @Length(max = 30)
    private String paymentStatus;

    /**
     * 反冲标志（N未被反冲\R反冲\W被反冲）
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
     * 退款标志（R退款事物\Y部分退款\C完全退款\N未退款）
     */
    @NotEmpty
    @Length(max = 1)
    private String returnedFlag;

    /**
     * 反冲、退款的来源付款批头ID
     */
    private Long sourceLineId;

}
