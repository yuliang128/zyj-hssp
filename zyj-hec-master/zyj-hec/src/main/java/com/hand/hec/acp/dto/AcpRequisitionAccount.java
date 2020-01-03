package com.hand.hec.acp.dto;

import javax.persistence.*;

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
@Table(name = "acp_requisition_account")
public class AcpRequisitionAccount extends BaseDTO {

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
    public static final String FIELD_ACCOUNT_SEGMENT21 = "accountSegment21";
    public static final String FIELD_ACCOUNT_SEGMENT22 = "accountSegment22";
    public static final String FIELD_ACCOUNT_SEGMENT23 = "accountSegment23";
    public static final String FIELD_ACCOUNT_SEGMENT24 = "accountSegment24";
    public static final String FIELD_ACCOUNT_SEGMENT25 = "accountSegment25";
    public static final String FIELD_ACCOUNT_SEGMENT26 = "accountSegment26";
    public static final String FIELD_ACCOUNT_SEGMENT27 = "accountSegment27";
    public static final String FIELD_ACCOUNT_SEGMENT28 = "accountSegment28";
    public static final String FIELD_ACCOUNT_SEGMENT29 = "accountSegment29";
    public static final String FIELD_ACCOUNT_SEGMENT30 = "accountSegment30";
    public static final String FIELD_ACCOUNT_SEGMENT31 = "accountSegment31";
    public static final String FIELD_ACCOUNT_SEGMENT32 = "accountSegment32";
    public static final String FIELD_ACCOUNT_SEGMENT33 = "accountSegment33";
    public static final String FIELD_ACCOUNT_SEGMENT34 = "accountSegment34";
    public static final String FIELD_ACCOUNT_SEGMENT35 = "accountSegment35";
    public static final String FIELD_ACCOUNT_SEGMENT36 = "accountSegment36";
    public static final String FIELD_ACCOUNT_SEGMENT37 = "accountSegment37";
    public static final String FIELD_ACCOUNT_SEGMENT38 = "accountSegment38";
    public static final String FIELD_ACCOUNT_SEGMENT39 = "accountSegment39";
    public static final String FIELD_ACCOUNT_SEGMENT40 = "accountSegment40";
    public static final String FIELD_ACCOUNT_SEGMENT41 = "accountSegment41";
    public static final String FIELD_ACCOUNT_SEGMENT42 = "accountSegment42";
    public static final String FIELD_ACCOUNT_SEGMENT43 = "accountSegment43";
    public static final String FIELD_ACCOUNT_SEGMENT44 = "accountSegment44";
    public static final String FIELD_ACCOUNT_SEGMENT45 = "accountSegment45";
    public static final String FIELD_ACCOUNT_SEGMENT46 = "accountSegment46";
    public static final String FIELD_ACCOUNT_SEGMENT47 = "accountSegment47";
    public static final String FIELD_ACCOUNT_SEGMENT48 = "accountSegment48";
    public static final String FIELD_ACCOUNT_SEGMENT49 = "accountSegment49";
    public static final String FIELD_ACCOUNT_SEGMENT50 = "accountSegment50";
    public static final String FIELD_ACP_REQ_JE_LINE_ID = "acpReqJeLineId";
    public static final String FIELD_REQUISITION_LNS_ID = "requisitionLnsId";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_JOURNAL_DATE = "journalDate";
    public static final String FIELD_PERIOD_NAME = "periodName";
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
    public static final String FIELD_USAGE_CODE = "usageCode";


    /**
     * 核算段1
     */
    @Length(max = 200)
    private String accountSegment1;

    /**
     * 核算段2
     */
    @Length(max = 200)
    private String accountSegment2;

    /**
     * 核算段3
     */
    @Length(max = 200)
    private String accountSegment3;

    /**
     * 核算段4
     */
    @Length(max = 200)
    private String accountSegment4;

    /**
     * 核算段5
     */
    @Length(max = 200)
    private String accountSegment5;

    /**
     * 核算段6
     */
    @Length(max = 200)
    private String accountSegment6;

    /**
     * 核算段7
     */
    @Length(max = 200)
    private String accountSegment7;

    /**
     * 核算段8
     */
    @Length(max = 200)
    private String accountSegment8;

    /**
     * 核算段9
     */
    @Length(max = 200)
    private String accountSegment9;

    /**
     * 核算段10
     */
    @Length(max = 200)
    private String accountSegment10;

    /**
     * 核算段11
     */
    @Length(max = 200)
    private String accountSegment11;

    /**
     * 核算段12
     */
    @Length(max = 200)
    private String accountSegment12;

    /**
     * 核算段13
     */
    @Length(max = 200)
    private String accountSegment13;

    /**
     * 核算段14
     */
    @Length(max = 200)
    private String accountSegment14;

    /**
     * 核算段15
     */
    @Length(max = 200)
    private String accountSegment15;

    /**
     * 核算段16
     */
    @Length(max = 200)
    private String accountSegment16;

    /**
     * 核算段17
     */
    @Length(max = 200)
    private String accountSegment17;

    /**
     * 核算段18
     */
    @Length(max = 200)
    private String accountSegment18;

    /**
     * 核算段19
     */
    @Length(max = 200)
    private String accountSegment19;

    /**
     * 核算段20
     */
    @Length(max = 200)
    private String accountSegment20;

    /**
     * 核算段21
     */
    @Length(max = 200)
    private String accountSegment21;

    /**
     * 核算段22
     */
    @Length(max = 200)
    private String accountSegment22;

    /**
     * 核算段23
     */
    @Length(max = 200)
    private String accountSegment23;

    /**
     * 核算段24
     */
    @Length(max = 200)
    private String accountSegment24;

    /**
     * 核算段25
     */
    @Length(max = 200)
    private String accountSegment25;

    /**
     * 核算段26
     */
    @Length(max = 200)
    private String accountSegment26;

    /**
     * 核算段27
     */
    @Length(max = 200)
    private String accountSegment27;

    /**
     * 核算段28
     */
    @Length(max = 200)
    private String accountSegment28;

    /**
     * 核算段29
     */
    @Length(max = 200)
    private String accountSegment29;

    /**
     * 核算段30
     */
    @Length(max = 200)
    private String accountSegment30;

    /**
     * 核算段31
     */
    @Length(max = 200)
    private String accountSegment31;

    /**
     * 核算段32
     */
    @Length(max = 200)
    private String accountSegment32;

    /**
     * 核算段33
     */
    @Length(max = 200)
    private String accountSegment33;

    /**
     * 核算段34
     */
    @Length(max = 200)
    private String accountSegment34;

    /**
     * 核算段35
     */
    @Length(max = 200)
    private String accountSegment35;

    /**
     * 核算段36
     */
    @Length(max = 200)
    private String accountSegment36;

    /**
     * 核算段37
     */
    @Length(max = 200)
    private String accountSegment37;

    /**
     * 核算段38
     */
    @Length(max = 200)
    private String accountSegment38;

    /**
     * 核算段39
     */
    @Length(max = 200)
    private String accountSegment39;

    /**
     * 核算段40
     */
    @Length(max = 200)
    private String accountSegment40;

    /**
     * 核算段41
     */
    @Length(max = 200)
    private String accountSegment41;

    /**
     * 核算段42
     */
    @Length(max = 200)
    private String accountSegment42;

    /**
     * 核算段43
     */
    @Length(max = 200)
    private String accountSegment43;

    /**
     * 核算段44
     */
    @Length(max = 200)
    private String accountSegment44;

    /**
     * 核算段45
     */
    @Length(max = 200)
    private String accountSegment45;

    /**
     * 核算段46
     */
    @Length(max = 200)
    private String accountSegment46;

    /**
     * 核算段47
     */
    @Length(max = 200)
    private String accountSegment47;

    /**
     * 核算段48
     */
    @Length(max = 200)
    private String accountSegment48;

    /**
     * 核算段49
     */
    @Length(max = 200)
    private String accountSegment49;

    /**
     * 核算段50
     */
    @Length(max = 200)
    private String accountSegment50;

    @Id
    @GeneratedValue
    private Long acpReqJeLineId;

    /**
     * 付款申请单行ID
     */
    @NotNull
    private Long requisitionLnsId;

    /**
     * 描述
     */
    @Length(max = 2000)
    private String description;

    /**
     * 凭证日期
     */
    private Date journalDate;

    /**
     * 期间
     */
    @NotEmpty
    @Length(max = 30)
    private String periodName;

    /**
     * 核算主体ID
     */
    @NotNull
    private Long accEntityId;

    /**
     * 责任中心ID
     */
    @NotNull
    private Long respCenterId;

    /**
     * 科目ID
     */
    @NotNull
    private Long accountId;

    /**
     * 币种
     */
    @NotEmpty
    @Length(max = 10)
    private String currencyCode;

    /**
     * 汇率类型
     */
    @Length(max = 30)
    private String exchangeRateType;

    /**
     * 汇率
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
     * 总账标志
     */
    @NotEmpty
    @Length(max = 1)
    private String gldInterfaceFlag;

    /**
     * 用途代码
     */
    @NotEmpty
    @Length(max = 30)
    private String usageCode;

    /**
     * 账套Id
     */
    @Transient
    private Long setOfBooksId;

}
