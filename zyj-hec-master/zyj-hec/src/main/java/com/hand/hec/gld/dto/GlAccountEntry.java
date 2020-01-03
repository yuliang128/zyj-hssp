package com.hand.hec.gld.dto;

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
@Table(name = "gl_account_entry")
public class GlAccountEntry extends BaseDTO {

    public static final String FIELD_QUERY_BATCH_CODE = "queryBatchCode";
    public static final String FIELD_SEGMENT33 = "segment33";
    public static final String FIELD_SEGMENT34 = "segment34";
    public static final String FIELD_SEGMENT35 = "segment35";
    public static final String FIELD_SEGMENT36 = "segment36";
    public static final String FIELD_SEGMENT37 = "segment37";
    public static final String FIELD_SEGMENT38 = "segment38";
    public static final String FIELD_SEGMENT39 = "segment39";
    public static final String FIELD_SEGMENT40 = "segment40";
    public static final String FIELD_SEGMENT41 = "segment41";
    public static final String FIELD_SEGMENT42 = "segment42";
    public static final String FIELD_SEGMENT43 = "segment43";
    public static final String FIELD_SEGMENT44 = "segment44";
    public static final String FIELD_SEGMENT45 = "segment45";
    public static final String FIELD_SEGMENT46 = "segment46";
    public static final String FIELD_SEGMENT47 = "segment47";
    public static final String FIELD_SEGMENT48 = "segment48";
    public static final String FIELD_SEGMENT49 = "segment49";
    public static final String FIELD_SEGMENT50 = "segment50";
    public static final String FIELD_SEGMENT_DESC1 = "segmentDesc1";
    public static final String FIELD_SEGMENT_DESC2 = "segmentDesc2";
    public static final String FIELD_SEGMENT_DESC3 = "segmentDesc3";
    public static final String FIELD_SEGMENT_DESC4 = "segmentDesc4";
    public static final String FIELD_SEGMENT_DESC5 = "segmentDesc5";
    public static final String FIELD_SEGMENT_DESC6 = "segmentDesc6";
    public static final String FIELD_SEGMENT_DESC7 = "segmentDesc7";
    public static final String FIELD_SEGMENT_DESC8 = "segmentDesc8";
    public static final String FIELD_SEGMENT_DESC9 = "segmentDesc9";
    public static final String FIELD_SEGMENT_DESC10 = "segmentDesc10";
    public static final String FIELD_SEGMENT_DESC11 = "segmentDesc11";
    public static final String FIELD_SEGMENT_DESC12 = "segmentDesc12";
    public static final String FIELD_SEGMENT_DESC13 = "segmentDesc13";
    public static final String FIELD_SEGMENT_DESC14 = "segmentDesc14";
    public static final String FIELD_SEGMENT_DESC15 = "segmentDesc15";
    public static final String FIELD_SEGMENT_DESC16 = "segmentDesc16";
    public static final String FIELD_SEGMENT_DESC17 = "segmentDesc17";
    public static final String FIELD_SEGMENT_DESC18 = "segmentDesc18";
    public static final String FIELD_SEGMENT_DESC19 = "segmentDesc19";
    public static final String FIELD_SEGMENT_DESC20 = "segmentDesc20";
    public static final String FIELD_SEGMENT_DESC21 = "segmentDesc21";
    public static final String FIELD_SEGMENT_DESC22 = "segmentDesc22";
    public static final String FIELD_SEGMENT_DESC23 = "segmentDesc23";
    public static final String FIELD_SEGMENT_DESC24 = "segmentDesc24";
    public static final String FIELD_SEGMENT_DESC25 = "segmentDesc25";
    public static final String FIELD_SEGMENT_DESC26 = "segmentDesc26";
    public static final String FIELD_SEGMENT_DESC27 = "segmentDesc27";
    public static final String FIELD_SEGMENT_DESC28 = "segmentDesc28";
    public static final String FIELD_SEGMENT_DESC29 = "segmentDesc29";
    public static final String FIELD_SEGMENT_DESC30 = "segmentDesc30";
    public static final String FIELD_SEGMENT_DESC31 = "segmentDesc31";
    public static final String FIELD_SEGMENT_DESC32 = "segmentDesc32";
    public static final String FIELD_SEGMENT_DESC33 = "segmentDesc33";
    public static final String FIELD_SEGMENT_DESC34 = "segmentDesc34";
    public static final String FIELD_SEGMENT_DESC35 = "segmentDesc35";
    public static final String FIELD_SEGMENT_DESC36 = "segmentDesc36";
    public static final String FIELD_SEGMENT_DESC37 = "segmentDesc37";
    public static final String FIELD_SEGMENT_DESC38 = "segmentDesc38";
    public static final String FIELD_SEGMENT_DESC39 = "segmentDesc39";
    public static final String FIELD_SEGMENT_DESC40 = "segmentDesc40";
    public static final String FIELD_SEGMENT_DESC41 = "segmentDesc41";
    public static final String FIELD_SEGMENT_DESC42 = "segmentDesc42";
    public static final String FIELD_SEGMENT_DESC43 = "segmentDesc43";
    public static final String FIELD_SEGMENT_DESC44 = "segmentDesc44";
    public static final String FIELD_SEGMENT_DESC45 = "segmentDesc45";
    public static final String FIELD_SEGMENT_DESC46 = "segmentDesc46";
    public static final String FIELD_SEGMENT_DESC47 = "segmentDesc47";
    public static final String FIELD_SEGMENT_DESC48 = "segmentDesc48";
    public static final String FIELD_SEGMENT_DESC49 = "segmentDesc49";
    public static final String FIELD_SEGMENT_DESC50 = "segmentDesc50";
    public static final String FIELD_BATCH_CODE = "batchCode";
    public static final String FIELD_GL_INTERFACE_ID = "glInterfaceId";
    public static final String FIELD_IMPORTED_FLAG = "importedFlag";
    public static final String FIELD_IMPORT_DATE = "importDate";
    public static final String FIELD_JOURNAL_NUMBER = "journalNumber";
    public static final String FIELD_ERROR_CODE = "errorCode";
    public static final String FIELD_ERROR_MESSAGE = "errorMessage";
    public static final String FIELD_ACCOUNT_ENTRY_ID = "accountEntryId";
    public static final String FIELD_HEC_SOB_CODE = "hecSobCode";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_ACC_ENTITY_CODE = "accEntityCode";
    public static final String FIELD_TRANSACTION_TYPE = "transactionType";
    public static final String FIELD_TRANSACTION_HEADER_ID = "transactionHeaderId";
    public static final String FIELD_TRANSACTION_LINE_ID = "transactionLineId";
    public static final String FIELD_TRANSACTION_DIST_ID = "transactionDistId";
    public static final String FIELD_TRANSACTION_JE_LINE_ID = "transactionJeLineId";
    public static final String FIELD_TRANSACTION_NUMBER = "transactionNumber";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_PERIOD_YEAR = "periodYear";
    public static final String FIELD_PERIOD_NUM = "periodNum";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_TRANSACTION_DATE = "transactionDate";
    public static final String FIELD_ACCOUNTING_DATE = "accountingDate";
    public static final String FIELD_ATTACHMENT_NUM = "attachmentNum";
    public static final String FIELD_LINE_DESCRIPTION = "lineDescription";
    public static final String FIELD_ACCOUNT_ID = "accountId";
    public static final String FIELD_ACCOUNT_CODE = "accountCode";
    public static final String FIELD_ENTERED_AMOUNT_DR = "enteredAmountDr";
    public static final String FIELD_ENTERED_AMOUNT_CR = "enteredAmountCr";
    public static final String FIELD_FUNCTIONAL_AMOUNT_DR = "functionalAmountDr";
    public static final String FIELD_FUNCTIONAL_AMOUNT_CR = "functionalAmountCr";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_CURRENCY_CONVERSION_DATE = "currencyConversionDate";
    public static final String FIELD_CURRENCY_CONVERSION_RATE = "currencyConversionRate";
    public static final String FIELD_CURRENCY_CONVERSION_TYPE = "currencyConversionType";
    public static final String FIELD_REVERSE_FLAG = "reverseFlag";
    public static final String FIELD_REVERSE_TRANSACTION_ID = "reverseTransactionId";
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
    public static final String FIELD_SEGMENT21 = "segment21";
    public static final String FIELD_SEGMENT22 = "segment22";
    public static final String FIELD_SEGMENT23 = "segment23";
    public static final String FIELD_SEGMENT24 = "segment24";
    public static final String FIELD_SEGMENT25 = "segment25";
    public static final String FIELD_SEGMENT26 = "segment26";
    public static final String FIELD_SEGMENT27 = "segment27";
    public static final String FIELD_SEGMENT28 = "segment28";
    public static final String FIELD_SEGMENT29 = "segment29";
    public static final String FIELD_SEGMENT30 = "segment30";
    public static final String FIELD_SEGMENT31 = "segment31";
    public static final String FIELD_SEGMENT32 = "segment32";

    /**
     * 事务类型
     */
    public static final String TRX_TYPE_ACP_REQ = "ACP_REQ";
    public static final String TRX_TYPE_CSH_WRITE_OFF = "CSH_WRITE_OFF";
    public static final String TRX_TYPE_CSH_TRANSACTION = "CSH_TRANSACTION";
    public static final String TRX_TYPE_CSH_PMT_REQ = "CSH_PMT_REQ";
    public static final String TRX_TYPE_EXP_REPORT = "EXP_REPORT";
    public static final String TRX_TYPE_PAYMENT_BATCH = "PAYMENT_BATCH";
    public static final String TRX_TYPE_VAT_INVOICE = "VAT_INVOICE";

    public static final String RULE_TYPE_EXP_REPORT = "EXP_REPORT";
    public static final String RULE_TYPE_CSH_TRANSACTION = "CSH_TRANSACTION";
    public static final String RULE_TYPE_CSH_WRITE_OFF = "CSH_WRITE_OFF";
    public static final String RULE_TYPE_CSH_PMT_REQ = "CSH_PMT_REQ";
    public static final String RULE_TYPE_WORK_ORDER = "WORK_ORDER";
    public static final String RULE_TYPE_PAYMENT_BATCH = "PAYMENT_BATCH";
    public static final String RULE_TYPE_VAT_INVOICE = "VAT_INVOICE";
    public static final String RULE_TYPE_ACP_REQ = "ACP_REQ";

    /**
     * 查询批次代码
     */
    @Length(max = 50)
    private String queryBatchCode;

    /**
     * 属性33
     */
    @Length(max = 150)
    private String segment33;

    /**
     * 属性34
     */
    @Length(max = 150)
    private String segment34;

    /**
     * 属性35
     */
    @Length(max = 150)
    private String segment35;

    /**
     * 属性36
     */
    @Length(max = 150)
    private String segment36;

    /**
     * 属性37
     */
    @Length(max = 150)
    private String segment37;

    /**
     * 属性38
     */
    @Length(max = 150)
    private String segment38;

    /**
     * 属性39
     */
    @Length(max = 150)
    private String segment39;

    /**
     * 属性40
     */
    @Length(max = 150)
    private String segment40;

    /**
     * 属性41
     */
    @Length(max = 150)
    private String segment41;

    /**
     * 属性42
     */
    @Length(max = 150)
    private String segment42;

    /**
     * 属性43
     */
    @Length(max = 150)
    private String segment43;

    /**
     * 属性44
     */
    @Length(max = 150)
    private String segment44;

    /**
     * 属性45
     */
    @Length(max = 150)
    private String segment45;

    /**
     * 属性46
     */
    @Length(max = 150)
    private String segment46;

    /**
     * 属性47
     */
    @Length(max = 150)
    private String segment47;

    /**
     * 属性48
     */
    @Length(max = 150)
    private String segment48;

    /**
     * 属性49
     */
    @Length(max = 150)
    private String segment49;

    /**
     * 属性50
     */
    @Length(max = 150)
    private String segment50;

    /**
     * 属性描述1
     */
    @Length(max = 200)
    private String segmentDesc1;

    /**
     * 属性描述2
     */
    @Length(max = 200)
    private String segmentDesc2;

    /**
     * 属性描述3
     */
    @Length(max = 200)
    private String segmentDesc3;

    /**
     * 属性描述4
     */
    @Length(max = 200)
    private String segmentDesc4;

    /**
     * 属性描述5
     */
    @Length(max = 200)
    private String segmentDesc5;

    /**
     * 属性描述6
     */
    @Length(max = 200)
    private String segmentDesc6;

    /**
     * 属性描述7
     */
    @Length(max = 200)
    private String segmentDesc7;

    /**
     * 属性描述8
     */
    @Length(max = 200)
    private String segmentDesc8;

    /**
     * 属性描述9
     */
    @Length(max = 200)
    private String segmentDesc9;

    /**
     * 属性描述10
     */
    @Length(max = 200)
    private String segmentDesc10;

    /**
     * 属性描述11
     */
    @Length(max = 200)
    private String segmentDesc11;

    /**
     * 属性描述12
     */
    @Length(max = 200)
    private String segmentDesc12;

    /**
     * 属性描述13
     */
    @Length(max = 200)
    private String segmentDesc13;

    /**
     * 属性描述14
     */
    @Length(max = 200)
    private String segmentDesc14;

    /**
     * 属性描述15
     */
    @Length(max = 200)
    private String segmentDesc15;

    /**
     * 属性描述16
     */
    @Length(max = 200)
    private String segmentDesc16;

    /**
     * 属性描述17
     */
    @Length(max = 200)
    private String segmentDesc17;

    /**
     * 属性描述18
     */
    @Length(max = 200)
    private String segmentDesc18;

    /**
     * 属性描述19
     */
    @Length(max = 200)
    private String segmentDesc19;

    /**
     * 属性描述20
     */
    @Length(max = 200)
    private String segmentDesc20;

    /**
     * 属性描述21
     */
    @Length(max = 200)
    private String segmentDesc21;

    /**
     * 属性描述22
     */
    @Length(max = 200)
    private String segmentDesc22;

    /**
     * 属性描述23
     */
    @Length(max = 200)
    private String segmentDesc23;

    /**
     * 属性描述24
     */
    @Length(max = 200)
    private String segmentDesc24;

    /**
     * 属性描述25
     */
    @Length(max = 200)
    private String segmentDesc25;

    /**
     * 属性描述26
     */
    @Length(max = 200)
    private String segmentDesc26;

    /**
     * 属性描述27
     */
    @Length(max = 200)
    private String segmentDesc27;

    /**
     * 属性描述28
     */
    @Length(max = 200)
    private String segmentDesc28;

    /**
     * 属性描述29
     */
    @Length(max = 200)
    private String segmentDesc29;

    /**
     * 属性描述30
     */
    @Length(max = 200)
    private String segmentDesc30;

    /**
     * 属性描述31
     */
    @Length(max = 200)
    private String segmentDesc31;

    /**
     * 属性描述32
     */
    @Length(max = 200)
    private String segmentDesc32;

    /**
     * 属性描述33
     */
    @Length(max = 200)
    private String segmentDesc33;

    /**
     * 属性描述34
     */
    @Length(max = 200)
    private String segmentDesc34;

    /**
     * 属性描述35
     */
    @Length(max = 200)
    private String segmentDesc35;

    /**
     * 属性描述36
     */
    @Length(max = 200)
    private String segmentDesc36;

    /**
     * 属性描述37
     */
    @Length(max = 200)
    private String segmentDesc37;

    /**
     * 属性描述38
     */
    @Length(max = 200)
    private String segmentDesc38;

    /**
     * 属性描述39
     */
    @Length(max = 200)
    private String segmentDesc39;

    /**
     * 属性描述40
     */
    @Length(max = 200)
    private String segmentDesc40;

    /**
     * 属性描述41
     */
    @Length(max = 200)
    private String segmentDesc41;

    /**
     * 属性描述42
     */
    @Length(max = 200)
    private String segmentDesc42;

    /**
     * 属性描述43
     */
    @Length(max = 200)
    private String segmentDesc43;

    /**
     * 属性描述44
     */
    @Length(max = 200)
    private String segmentDesc44;

    /**
     * 属性描述45
     */
    @Length(max = 200)
    private String segmentDesc45;

    /**
     * 属性描述46
     */
    @Length(max = 200)
    private String segmentDesc46;

    /**
     * 属性描述47
     */
    @Length(max = 200)
    private String segmentDesc47;

    /**
     * 属性描述48
     */
    @Length(max = 200)
    private String segmentDesc48;

    /**
     * 属性描述49
     */
    @Length(max = 200)
    private String segmentDesc49;

    /**
     * 属性描述50
     */
    @Length(max = 200)
    private String segmentDesc50;

    /**
     * 发送批号
     */
    @Length(max = 50)
    private String batchCode;

    /**
     * 返回生成的接口ID
     */
    private Long glInterfaceId;

    /**
     * 导入标志
     */
    @Length(max = 15)
    private String importedFlag;

    /**
     * 导入日期
     */
    private Date importDate;

    /**
     * 凭证编号
     */
    @Length(max = 50)
    private String journalNumber;

    /**
     * 错误代码
     */
    @Length(max = 150)
    private String errorCode;

    /**
     * 错误信息
     */
    @Length(max = 1000)
    private String errorMessage;

    @Id
    @GeneratedValue
    private Long accountEntryId;

    /**
     * HEC帐套代码
     */
    @NotEmpty
    @Length(max = 30)
    private String hecSobCode;

    /**
     * 核算主体ID
     */
    @NotNull
    private Long accEntityId;

    /**
     * 核算主体代码
     */
    @NotEmpty
    @Length(max = 30)
    private String accEntityCode;

    /**
     * 事务类型
     */
    @NotEmpty
    @Length(max = 30)
    private String transactionType;

    /**
     * 事务头ID
     */
    private Long transactionHeaderId;

    /**
     * 事务行ID
     */
    private Long transactionLineId;

    /**
     * 事务分配行ID
     */
    private Long transactionDistId;

    /**
     * 事务凭证行ID
     */
    private Long transactionJeLineId;

    /**
     * 事务编码
     */
    @Length(max = 30)
    private String transactionNumber;

    /**
     * 期间名称
     */
    @Length(max = 30)
    private String periodName;

    /**
     * 期间年
     */
    private Long periodYear;

    /**
     * 期间号
     */
    private Long periodNum;

    /**
     * 描述
     */
    @Length(max = 1000)
    private String description;

    /**
     * 事务日期
     */
    private Date transactionDate;

    /**
     * 凭证日期
     */
    private Date accountingDate;

    /**
     * 附件数
     */
    private Long attachmentNum;

    /**
     * 行描述
     */
    @Length(max = 1000)
    private String lineDescription;

    /**
     * 科目ID
     */
    private BigDecimal accountId;

    /**
     * 科目代码
     */
    @Length(max = 30)
    private String accountCode;

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
     * 币种代码
     */
    @Length(max = 10)
    private String currencyCode;

    /**
     * 币种汇率日期
     */
    private Date currencyConversionDate;

    /**
     * 币种汇率
     */
    private Long currencyConversionRate;

    /**
     * 币种汇率类型
     */
    @Length(max = 30)
    private String currencyConversionType;

    /**
     * 反冲标志
     */
    @Length(max = 1)
    private String reverseFlag;

    /**
     * 反冲事务ID
     */
    private Long reverseTransactionId;

    /**
     * 属性1
     */
    @Length(max = 150)
    private String segment1;

    /**
     * 属性2
     */
    @Length(max = 150)
    private String segment2;

    /**
     * 属性3
     */
    @Length(max = 150)
    private String segment3;

    /**
     * 属性4
     */
    @Length(max = 150)
    private String segment4;

    /**
     * 属性5
     */
    @Length(max = 150)
    private String segment5;

    /**
     * 属性6
     */
    @Length(max = 150)
    private String segment6;

    /**
     * 属性7
     */
    @Length(max = 150)
    private String segment7;

    /**
     * 属性8
     */
    @Length(max = 150)
    private String segment8;

    /**
     * 属性9
     */
    @Length(max = 150)
    private String segment9;

    /**
     * 属性10
     */
    @Length(max = 150)
    private String segment10;

    /**
     * 属性11
     */
    @Length(max = 150)
    private String segment11;

    /**
     * 属性12
     */
    @Length(max = 150)
    private String segment12;

    /**
     * 属性13
     */
    @Length(max = 150)
    private String segment13;

    /**
     * 属性14
     */
    @Length(max = 150)
    private String segment14;

    /**
     * 属性15
     */
    @Length(max = 150)
    private String segment15;

    /**
     * 属性16
     */
    @Length(max = 150)
    private String segment16;

    /**
     * 属性17
     */
    @Length(max = 150)
    private String segment17;

    /**
     * 属性18
     */
    @Length(max = 150)
    private String segment18;

    /**
     * 属性19
     */
    @Length(max = 150)
    private String segment19;

    /**
     * 属性20
     */
    @Length(max = 150)
    private String segment20;

    /**
     * 属性21
     */
    @Length(max = 150)
    private String segment21;

    /**
     * 属性22
     */
    @Length(max = 150)
    private String segment22;

    /**
     * 属性23
     */
    @Length(max = 150)
    private String segment23;

    /**
     * 属性24
     */
    @Length(max = 150)
    private String segment24;

    /**
     * 属性25
     */
    @Length(max = 150)
    private String segment25;

    /**
     * 属性26
     */
    @Length(max = 150)
    private String segment26;

    /**
     * 属性27
     */
    @Length(max = 150)
    private String segment27;

    /**
     * 属性28
     */
    @Length(max = 150)
    private String segment28;

    /**
     * 属性29
     */
    @Length(max = 150)
    private String segment29;

    /**
     * 属性30
     */
    @Length(max = 150)
    private String segment30;

    /**
     * 属性31
     */
    @Length(max = 150)
    private String segment31;

    /**
     * 属性32
     */
    @Length(max = 150)
    private String segment32;

}
