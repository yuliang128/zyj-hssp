package com.hand.hec.csh.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.Children;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/04/29 14:35
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_repayment_register_hd")
public class CshRepaymentRegisterHd extends BaseDTO {

    public static final String FIELD_REGISTER_HDS_ID = "registerHdsId";
    public static final String FIELD_REGISTER_NUMBER = "registerNumber";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_MO_REPAYMENT_REG_TYPE_ID = "moRepaymentRegTypeId";
    public static final String FIELD_BANK_ACCOUNT_ID = "bankAccountId";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_REPAYMENT_DATE = "repaymentDate";
    public static final String FIELD_REPAYMENT_DATE_TZ = "repaymentDateTz";
    public static final String FIELD_REPAYMENT_DATE_LTZ = "repaymentDateLtz";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_REPAYMENT_BANK_NOTE = "repaymentBankNote";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_REPAYMENT_STATUS = "repaymentStatus";
    public static final String FIELD_CASHIER_USER_ID = "cashierUserId";
    public static final String FIELD_CASHIER_CONFIRM_DATE = "cashierConfirmDate";
    public static final String FIELD_ACCOUNTING_USER_ID = "accountingUserId";
    public static final String FIELD_ACCOUNTING_CONFIRM_DATE = "accountingConfirmDate";
    public static final String FIELD_REPAYMENT_DATE_FROM = "repaymentDateFrom";
    public static final String FIELD_REPAYMENT_DATE_TO = "repaymentDateTo";
    public static final String FIELD_AMOUNT_FROM = "amountFrom";
    public static final String FIELD_AMOUNT_TO = "amountTo";
    public static final String FIELD_ACCOUNTING_EMPLOYEE_NAME = "accountingEmployeeName";
    public static final String FIELD_CASHIER_EMPLOYEE_NAME ="cashierEmployeeName";



    public static final String CSH_REPAYMENT_REGISTER = "CSH_REPAYMENT_REGISTER";


    public static final String STATUS_GENERATE = "GENERATE";
    public static final String STATUS_TAKEN_BACK = "TAKEN_BACK";
    public static final String STATUS_SUBMITTED = "SUBMITTED";
    public static final String STATUS_CASHIER_CONFIRM = "CASHIER_CONFIRM";
    public static final String STATUS_CASHIER_REJECT = "CASHIER_REJECTED";
    public static final String STATUS_ACCOUNTING_CONFIRM = "ACCOUNTING_CONFIRM";
    public static final String STATUS_ACCOUNTING_REJECT = "ACCOUNTING_REJECTED";
    public static final String STATUS_COMPLETELY_CONFIRM = "COMPLETELY_CONFIRM";

    @Id
    @GeneratedValue
    private Long registerHdsId;

    /**
     * 还款登记单号
     */
    @Length(max = 200)
    private String registerNumber;

    /**
     * 管理公司ID
     */
    @NotNull
    private Long companyId;

    @Transient
    private String companyName;

    /**
     * 部门ID
     */
    @NotNull
    private Long unitId;

    @Transient
    private String unitName;

    /**
     * 岗位ID
     */
    @NotNull
    private Long positionId;

    @Transient
    private String positionName;

    /**
     * 员工ID
     */
    @NotNull
    private Long employeeId;

    @Transient
    private String employeeName;

    /**
     * 核算主体ID
     */
    @NotNull
    private Long accEntityId;

    @Transient
    private String accEntityName;

    /**
     * 还款登记单类型ID
     */
    @NotNull
    private Long moRepaymentRegTypeId;

    @Transient
    private String moRepaymentRegTypeName;

    /**
     * 银行账户ID
     */
    @NotNull
    private Long bankAccountId;

    @Transient
    private String bankAccountNum;

    @Transient
    private String currencySymbol;

    @Transient
    private String currencyCode;

    /**
     * 支付方式ID
     */
    @NotNull
    private Long paymentMethodId;

    @Transient
    private String paymentMethodName;

    /**
     * 还款日期
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date repaymentDate;

    /**
     * 还款日期_业务时区
     */
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Timestamp repaymentDateTz;

    /**
     * 还款日期_查询时区
     */
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Timestamp repaymentDateLtz;

    /**
     * 还款金额
     */
    private BigDecimal amount;

    /**
     * 银行备注
     */
    @Length(max = 200)
    private String repaymentBankNote;

    /**
     * 说明
     */
    @Length(max = 2000)
    private String description;

    /**
     * 状态（SYSCODE：CSH_REPAYMENT_REGISTER_STATUS）
     */
    @NotEmpty
    @Length(max = 30)
    private String repaymentStatus;

    @Transient
    private String repaymentStatusName;

    /**
     * 出纳确认用户ID
     */
    private Long cashierUserId;

    @Transient
    private String cashierUserName;

    /**
     * 出纳确认日期
     */
    private Date cashierConfirmDate;

    /**
     * 财务确认用户ID
     */
    private Long accountingUserId;

    @Transient
    private String accountingUserName;

    /**
     * 财务确认日期
     */
    private Date accountingConfirmDate;

    @Transient
    private String progressStatusName;

    @Transient
    private String createdName;

    @Transient
    private String corssEntityFlag;

    /**
     * 收款日期从
     */
    @Transient
    private Date repaymentDateFrom;

    /**
     * 收款日期到
     */
    @Transient
    private Date repaymentDateTo;

    /**
     * 收款金额从
     */
    @Transient
    private BigDecimal amountFrom;

    /**
     * 收款金额到
     */
    @Transient
    private BigDecimal amountTo;



    @Transient
    @Children
    private List<CshRepaymentRegisterLn> lines;

    @Transient
    private Float progressCount;

    public Float getProgressCount() {
        if(repaymentStatus == null){
            return 0F;
        }
        switch (repaymentStatus) {
            case STATUS_GENERATE:
            case STATUS_TAKEN_BACK:
            case STATUS_CASHIER_REJECT:
                return 16.67F;
            case STATUS_SUBMITTED:
                return 33.35F;
            case STATUS_CASHIER_CONFIRM:
                return 50.01F;
            case STATUS_ACCOUNTING_REJECT:
                return 66.68F;
            case STATUS_ACCOUNTING_CONFIRM:
                return 83.35F;
            case STATUS_COMPLETELY_CONFIRM:
                return 100F;
            default:
                return 0F;
        }
    }
}
