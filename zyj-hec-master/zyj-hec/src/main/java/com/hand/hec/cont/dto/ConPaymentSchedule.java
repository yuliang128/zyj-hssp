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
@Table(name = "con_payment_schedule")
public class ConPaymentSchedule extends BaseDTO {

     public static final String FIELD_CONTRACT_HEADER_ID = "contractHeaderId";
     public static final String FIELD_PAYMENT_SCHEDULE_LINE_ID = "paymentScheduleLineId";
     public static final String FIELD_LINE_NUMBER = "lineNumber";
     public static final String FIELD_EXPENSE_TYPE_ID = "expenseTypeId";
     public static final String FIELD_REQ_ITEM_ID = "reqItemId";
     public static final String FIELD_AMOUNT = "amount";
     public static final String FIELD_PAYMENT_AMOUNT = "paymentAmount";
     public static final String FIELD_PAYMENT_METHOD = "paymentMethod";
     public static final String FIELD_PAYMENT_TERM_ID = "paymentTermId";
     public static final String FIELD_PARTNER_CATEGORY = "partnerCategory";
     public static final String FIELD_PARTNER_ID = "partnerId";
     public static final String FIELD_START_DATE = "startDate";
     public static final String FIELD_DUE_DATE = "dueDate";
     public static final String FIELD_ACTUAL_DATE = "actualDate";
     public static final String FIELD_MEMO = "memo";
     public static final String FIELD_CURRENCY_CODE = "currencyCode";
     public static final String FIELD_PAYMENT_CURRENCY_CODE = "paymentCurrencyCode";
     public static final String FIELD_REQ2PAY_EXCHANGE_TYPE = "req2payExchangeType";
     public static final String FIELD_REQ2PAY_EXCHANGE_RATE = "req2payExchangeRate";
     public static final String FIELD_LANDMARK_PHASE = "landmarkPhase";
     public static final String FIELD_ACCEPTED_FLAG = "acceptedFlag";
     public static final String FIELD_PAYMENT_NODE_FLAG = "paymentNodeFlag";
     public static final String FIELD_PAYMENT_TERM = "paymentTerm";
     public static final String FIELD_PAYMENT_NODE_TYPE = "paymentNodeType";
     public static final String FIELD_PAYMENT_RATIO = "paymentRatio";
     public static final String FIELD_STATUS = "status";


    /**
     * 合同头ID
     */
     @NotNull
     private Long contractHeaderId;

     @Id
     @GeneratedValue
     private Long paymentScheduleLineId;

    /**
     * 行号
     */
     @NotNull
     private Long lineNumber;

    /**
     * 报销类型ID
     */
     private Long expenseTypeId;

    /**
     * 申请项目ID
     */
     private Long reqItemId;

    /**
     * 金额
     */
     private BigDecimal amount;

    /**
     * 支付金额
     */
     private BigDecimal paymentAmount;

    /**
     * 付款方法
     */
     @Length(max = 30)
     private String paymentMethod;

    /**
     * 付款条款ID
     */
     private Long paymentTermId;

    /**
     * 对象类型
     */
     @Length(max = 30)
     private String partnerCategory;

    /**
     * 对象ID
     */
     private Long partnerId;

    /**
     * 开始日期
     */
     private Date startDate;

    /**
     * 计划付款日期(预计完成时间)
     */
     private Date dueDate;

    /**
     * 实际付款日期
     */
     private Date actualDate;

    /**
     * 备注
     */
     @Length(max = 2000)
     private String memo;

    /**
     * 币种
     */
     @NotEmpty
     @Length(max = 10)
     private String currencyCode;

    /**
     * 里程碑行付款币种
     */
     @Length(max = 10)
     private String paymentCurrencyCode;

    /**
     * 头币种到里程碑币种汇率类型
     */
     @Length(max = 30)
     private String req2payExchangeType;

    /**
     * 头币种到里程碑币种汇率
     */
     private BigDecimal req2payExchangeRate;

    /**
     * 合同里程碑阶段
     */
     @Length(max = 100)
     private String landmarkPhase;

    /**
     * 是否需要验收
     */
     @Length(max = 1)
     private String acceptedFlag;

    /**
     * 是否付款节点
     */
     @Length(max = 1)
     private String paymentNodeFlag;

    /**
     * 付款条件
     */
     @Length(max = 4000)
     private String paymentTerm;

    /**
     * 付款节点属性
     */
     @Length(max = 30)
     private String paymentNodeType;

    /**
     * 付款比例
     */
     @Length(max = 30)
     private String paymentRatio;

    /**
     * 验收状态
     */
     @Length(max = 30)
     private String status;

     }
