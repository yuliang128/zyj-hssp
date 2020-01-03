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
import com.hand.hap.mybatis.common.query.Where;
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
@Table(name = "csh_payment_batch_header")
public class CshPaymentBatchHeader extends BaseDTO {

    public static final String FIELD_BATCH_HEADER_ID = "batchHeaderId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_BATCH_TYPE_ID = "batchTypeId";
    public static final String FIELD_BATCH_NUMBER = "batchNumber";
    public static final String FIELD_BATCH_DATE = "batchDate";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_PAYMENT_CURRENCY_CODE = "paymentCurrencyCode";
    public static final String FIELD_BANK_ACCOUNT_ID = "bankAccountId";
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
    public static final String FIELD_TOTAL_NUMBER = "totalNumber";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_PAYMENT_TOTAL_NUMBER = "paymentTotalNumber";
    public static final String FIELD_PAYMENT_AMOUNT = "paymentAmount";
    public static final String FIELD_EXPORT_NUMBER = "exportNumber";
    public static final String FIELD_EXPORT_USER_ID = "exportUserId";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_PAYMENT_COMPLETED_DATE = "paymentCompletedDate";
    public static final String FIELD_PAYMENT_COMPLETED_DATE_TZ = "paymentCompletedDateTz";
    public static final String FIELD_PAYMENT_COMPLETED_DATE_LTZ = "paymentCompletedDateLtz";
    public static final String FIELD_REVERSED_FLAG = "reversedFlag";

    /**
     * 付款批状态
     * 
     * @STATUS_AUDITED 已审核
     * @STATUS_BOOKED 已记账
     * @STATUS_CANCELLED 已取消
     * @STATUS_COMPLETED_PAYMENT 付款完成
     * @STATUS_GENERATE 待提交
     * @STATUS_REJECTED 已拒绝
     * @STATUS_SUBMITTED 已提交
     */
    public static final String STATUS_AUDITED = "AUDITED";
    public static final String STATUS_BOOKED = "BOOKED";
    public static final String STATUS_CANCELLED = "CANCELLED";
    public static final String STATUS_COMPLETED_PAYMENT = "COMPLETED_PAYMENT";
    public static final String STATUS_GENERATE = "GENERATE";
    public static final String STATUS_REJECTED = "REJECTED";
    public static final String STATUS_SUBMITTED = "SUBMITTED";

    @Id
    @GeneratedValue
    @Where
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
     * 付款批类型ID
     */
    @NotNull
    private Long batchTypeId;

    /**
     * 付款批号
     */
    @NotEmpty
    @Length(max = 30)
    private String batchNumber;

    /**
     * 付款批时间
     */
    private Date batchDate;

    /**
     * 付款方式
     */
    @NotNull
    private Long paymentMethodId;

    /**
     * 支付币种
     */
    @NotEmpty
    @Length(max = 30)
    private String paymentCurrencyCode;

    /**
     * 银行账户ID
     */
    @NotNull
    private Long bankAccountId;

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

    /**
     * 付款批总笔数
     */
    @NotNull
    private Long totalNumber;

    /**
     * 付款批总金额
     */
    private BigDecimal amount;

    /**
     * 支付总笔数
     */
    @NotNull
    private Long paymentTotalNumber;

    /**
     * 支付总金额
     */
    private BigDecimal paymentAmount;

    /**
     * 导出次数
     */
    @NotNull
    private Long exportNumber;

    /**
     * 导出用户
     */
    private Long exportUserId;

    /**
     * 状态（SYSCODE：CSH_PAYMENT_BATCH_STATUS）
     */
    @NotEmpty
    @Length(max = 30)
    private String status;

    /**
     * 付款完成日期
     */
    private Date paymentCompletedDate;

    /**
     * 付款完成日期_业务时区
     */
    private Date paymentCompletedDateTz;

    /**
     * 付款完成日期_查询时区
     */
    private Date paymentCompletedDateLtz;

    /**
     * 反冲标志（N未被反冲/Y部分反冲/C完全反冲）
     */
    @NotEmpty
    @Length(max = 1)
    private String reversedFlag;

}
