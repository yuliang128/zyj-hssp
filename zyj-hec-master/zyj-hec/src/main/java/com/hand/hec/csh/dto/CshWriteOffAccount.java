package com.hand.hec.csh.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.fnd.dto.GldAccount;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.dto.GldResponsibilityCenter;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_write_off_account")

/**
 * <p>
 * 核销凭证表DTO
 * </p>
 *
 * @author Tagin 2019/01/22 10:23
 */
public class CshWriteOffAccount extends BaseDTO {

    public static final String FIELD_WRITE_OFF_JE_LINE_ID = "writeOffJeLineId";
    public static final String FIELD_WRITE_OFF_ID = "writeOffId";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_SOURCE_CODE = "sourceCode";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_RESP_CENTER_ID = "respCenterId";
    public static final String FIELD_ACCOUNT_ID = "accountId";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_EXCHANGE_RATE_TYPE = "exchangeRateType";
    public static final String FIELD_EXCHANGE_RATE = "exchangeRate";
    public static final String FIELD_ENTERED_AMOUNT_DR = "enteredAmountDr";
    public static final String FIELD_ENTERED_AMOUNT_CR = "enteredAmountCr";
    public static final String FIELD_FUNCTIONAL_AMOUNT_DR = "functionalAmountDr";
    public static final String FIELD_FUNCTIONAL_AMOUNT_CR = "functionalAmountCr";
    public static final String FIELD_GLD_INTERFACE_FLAG = "gldInterfaceFlag";
    public static final String FIELD_CASH_CLEARING_FLAG = "cashClearingFlag";
    public static final String FIELD_JOURNAL_DATE = "journalDate";
    public static final String FIELD_USAGE_CODE = "usageCode";
    public static final String FIELD_ACCOUNT_SEGMENT1 = "accountSegment1";
    public static final String FIELD_ACCOUNT_SEGMENT2 = "accountSegment2";
    public static final String FIELD_ACCOUNT_SEGMENT3 = "accountSegment3";
    public static final String FIELD_ACCOUNT_SEGMENT4 = "accountSegment4";
    public static final String FIELD_ACCOUNT_SEGMENT5 = "accountSegment5";
    public static final String FIELD_ACCOUNT_SEGMENT6 = "accountSegment6";
    public static final String FIELD_ACCOUNT_SEGMENT7 = "accountSegment7";
    public static final String FIELD_ACCOUNT_SEGMENT8 = "accountSegment8";
    public static final String FIELD_ACCOUNT_SEGMENT9 = "accountSegment9";
    public static final String FIELD_ACCOUNT_SEGMENT10 = "accountSegment10";
    public static final String FIELD_ACCOUNT_SEGMENT11 = "accountSegment11";
    public static final String FIELD_ACCOUNT_SEGMENT12 = "accountSegment12";
    public static final String FIELD_ACCOUNT_SEGMENT13 = "accountSegment13";
    public static final String FIELD_ACCOUNT_SEGMENT14 = "accountSegment14";
    public static final String FIELD_ACCOUNT_SEGMENT15 = "accountSegment15";
    public static final String FIELD_ACCOUNT_SEGMENT16 = "accountSegment16";
    public static final String FIELD_ACCOUNT_SEGMENT17 = "accountSegment17";
    public static final String FIELD_ACCOUNT_SEGMENT18 = "accountSegment18";
    public static final String FIELD_ACCOUNT_SEGMENT19 = "accountSegment19";
    public static final String FIELD_ACCOUNT_SEGMENT20 = "accountSegment20";

    @Id
    @GeneratedValue
    private Long writeOffJeLineId;

    /**
     * 核销ID
     */
    @NotNull
    @Where
    private Long writeOffId;

    /**
     * 描述
     */
    @Length(max = 2000)
    private String description;

    /**
     * 期间
     */
    @NotEmpty
    @Length(max = 30)
    private String periodName;

    /**
     * 来源
     */
    @NotEmpty
    @Length(max = 30)
    private String sourceCode;

    /**
     * 公司
     */
    @NotNull
    private Long companyId;

    /**
     * 核算主体
     */
    @NotNull
    @JoinTable(name = "accEntityJoin", type = JoinType.LEFT, target = GldAccountingEntity.class,
                    joinMultiLanguageTable = true, on = {@JoinOn(joinField = GldAccountingEntity.FIELD_ACC_ENTITY_ID)})
    private Long accEntityId;

    /**
     * 核算主体名称
     */
    @Transient
    @Length(max = 500)
    @JoinColumn(joinName = "accEntityJoin", field = GldAccountingEntity.FIELD_ACC_ENTITY_NAME)
    private String accEntityName;

    /**
     * 责任中心
     */
    @NotNull
    @JoinTable(name = "respCenterJoin", type = JoinType.LEFT, target = GldResponsibilityCenter.class,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = GldResponsibilityCenter.FIELD_RESPONSIBILITY_CENTER_ID)})
    private Long respCenterId;

    /**
     * 责任中心名称
     */
    @Transient
    @Length(max = 500)
    @JoinColumn(joinName = "respCenterJoin", field = GldResponsibilityCenter.FIELD_RESPONSIBILITY_CENTER_NAME)
    private String respCenterName;

    /**
     * 科目
     */
    @NotNull
    @JoinTable(name = "accountJoin", type = JoinType.LEFT, target = GldAccount.class, joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = GldAccount.FIELD_ACCOUNT_ID)})
    private Long accountId;

    /**
     * 科目代码
     */
    @Transient
    @Length(max = 500)
    @JoinColumn(joinName = "accountJoin", field = GldAccount.FIELD_ACCOUNT_CODE)
    private String accountCode;

    /**
     * 科目名称
     */
    @Transient
    @Length(max = 500)
    @JoinColumn(joinName = "accountJoin", field = GldAccount.FIELD_DESCRIPTION)
    private String accountName;

    /**
     * 币种
     */
    @NotEmpty
    @Length(max = 30)
    private String currencyCode;

    /**
     * 汇率类型
     */
    @Length(max = 30)
    private String exchangeRateType;

    /**
     * 汇率类型
     */
    private BigDecimal exchangeRate;

    /**
     * 原币借方金额
     */
    private BigDecimal enteredAmountDr;

    /**
     * 原币贷方金额
     */
    private BigDecimal enteredAmountCr;

    /**
     * 本币借方金额
     */
    private BigDecimal functionalAmountDr;

    /**
     * 本币贷方金额
     */
    private BigDecimal functionalAmountCr;

    /**
     * 总账接口标志
     */
    @NotEmpty
    @Length(max = 1)
    private String gldInterfaceFlag;

    /**
     * 现金清算标志
     */
    @NotEmpty
    @Length(max = 1)
    private String cashClearingFlag;

    /**
     * 制证日期
     */
    private Date journalDate;

    /**
     * 用途代码
     */
    @NotEmpty
    @Length(max = 30)
    private String usageCode;

    /**
     * 科目段1
     */
    @Length(max = 200)
    private String accountSegment1;

    /**
     * 科目段2
     */
    @Length(max = 200)
    private String accountSegment2;

    /**
     * 科目段3
     */
    @Length(max = 200)
    private String accountSegment3;

    /**
     * 科目段4
     */
    @Length(max = 200)
    private String accountSegment4;

    /**
     * 科目段5
     */
    @Length(max = 200)
    private String accountSegment5;

    /**
     * 科目段6
     */
    @Length(max = 200)
    private String accountSegment6;

    /**
     * 科目段7
     */
    @Length(max = 200)
    private String accountSegment7;

    /**
     * 科目段8
     */
    @Length(max = 200)
    private String accountSegment8;

    /**
     * 科目段9
     */
    @Length(max = 200)
    private String accountSegment9;

    /**
     * 科目段10
     */
    @Length(max = 200)
    private String accountSegment10;

    /**
     * 科目段11
     */
    @Length(max = 200)
    private String accountSegment11;

    /**
     * 科目段12
     */
    @Length(max = 200)
    private String accountSegment12;

    /**
     * 科目段13
     */
    @Length(max = 200)
    private String accountSegment13;

    /**
     * 科目段14
     */
    @Length(max = 200)
    private String accountSegment14;

    /**
     * 科目段15
     */
    @Length(max = 200)
    private String accountSegment15;

    /**
     * 科目段16
     */
    @Length(max = 200)
    private String accountSegment16;

    /**
     * 科目段17
     */
    @Length(max = 200)
    private String accountSegment17;

    /**
     * 科目段18
     */
    @Length(max = 200)
    private String accountSegment18;

    /**
     * 科目段19
     */
    @Length(max = 200)
    private String accountSegment19;

    /**
     * 科目段20
     */
    @Length(max = 200)
    private String accountSegment20;

}
