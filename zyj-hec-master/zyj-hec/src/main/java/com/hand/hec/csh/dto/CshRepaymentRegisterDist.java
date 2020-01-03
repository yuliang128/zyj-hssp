package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
/**
 * <p>
 *
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/05/14 10:38
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_repayment_register_dist")
public class CshRepaymentRegisterDist extends BaseDTO {

    public static final String FIELD_REGISTER_DIST_ID = "registerDistId";
    public static final String FIELD_REGISTER_LNS_ID = "registerLnsId";
    public static final String FIELD_PAY_TRX_LINE_ID = "payTrxLineId";
    public static final String FIELD_REPAY_TRX_LINE_ID = "repayTrxLineId";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_REPAYMENT_FLAG = "repaymentFlag";
    public static final String FIELD_REPAYMENT_PAY_TRX_LINE_ID = "repaymentPayTrxLineId";
    public static final String FIELD_REPAYMENT_REPAY_TRX_LINE_ID = "repaymentRepayTrxLineId";
    public static final String FIELD_REVERSE_FLAG = "reverseFlag";
    public static final String FIELD_REVERSE_PAY_TRX_LINE_ID = "reversePayTrxLineId";
    public static final String FIELD_REVERSE_REPAY_TRX_LINE_ID = "reverseRepayTrxLineId";
    public static final String FIELD_BALANCE = "balance";


    @Id
    @GeneratedValue
    private Long registerDistId;

    /**
     * 还款登记单行ID
     */
    @NotNull
    private Long registerLnsId;

    /**
     * 关联付款现金事物行ID
     */
    @NotNull
    private Long payTrxLineId;

    /**
     * 关联付款预付款现金事物行ID
     */
    @NotNull
    private Long repayTrxLineId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 还款标志
     */
    @Length(max = 1)
    private String repaymentFlag;

    /**
     * 付款现金事物ID
     */
    private Long repaymentPayTrxLineId;

    /**
     * 预付款现金事物ID
     */
    private Long repaymentRepayTrxLineId;

    /**
     * 反冲标志
     */
    @Length(max = 1)
    private String reverseFlag;

    /**
     * 反冲现金事物行ID
     */
    private Long reversePayTrxLineId;

    /**
     * 反冲预付款现金事物行ID
     */
    private Long reverseRepayTrxLineId;

    @Transient
    private String payTrxNum;

    @Transient
    private String repayTrxNum;

    @Transient
    private String repaymentPayTrxNum;

    @Transient
    private String repaymentRepayTrxNum;

    @Transient
    private String reversePayTrxNum;

    @Transient
    private String reverseRepayTrxNum;

}
