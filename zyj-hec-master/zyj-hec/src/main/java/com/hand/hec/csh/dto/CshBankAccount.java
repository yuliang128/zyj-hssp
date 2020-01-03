package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.sys.dto.SysStandardTimezone;
import com.hand.hec.fnd.dto.FndCountryCode;
import com.hand.hec.fnd.dto.OrdSystemCustomer;
import com.hand.hec.gld.dto.GldResponsibilityCenter;
import com.hand.hec.pur.dto.PurSystemVender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

/**
 * <p>
 * 公司付款账户分配账户Dto
 * </p>
 *
 * @author xiuxian.xu 2019-03-15 9:36
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@MultiLanguage
@Table(name = "csh_bank_account")
public class CshBankAccount extends BaseDTO {

    public static final String FIELD_BANK_ACCOUNT_ID = "bankAccountId";
    public static final String FIELD_BANK_BRANCH_ID = "bankBranchId";
    public static final String FIELD_ACCOUNT_TYPE = "accountType";
    public static final String FIELD_BANK_ACCOUNT_NAME = "bankAccountName";
    public static final String FIELD_BANK_ACCOUNT_NUM = "bankAccountNum";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_COUNTRY_CODE = "countryCode";
    public static final String FIELD_TIMEZONE_ID = "timezoneId";
    public static final String FIELD_RESPONSIBILITY_CENTER_ID = "responsibilityCenterId";
    public static final String FIELD_CUSTOMER_ID = "customerId";
    public static final String FIELD_VENDER_ID = "venderId";
    public static final String FIELD_RECEIPT_FLAG = "receiptFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    @Where
    private Long bankAccountId;

    /**
     * 银行分行ID
     */
    @NotNull
    @Where
    private Long bankBranchId;

    /**
     * 账户类型（COMPANY：公司的银行帐户、CUSTOMER：客户的银行帐户、VENDER：供应商的银行帐户）
     */
    @Length(max = 30)
    @Where
    private String accountType;

    /**
     * 账户名称
     */
    @NotEmpty
    @Length(max = 500)
    @MultiLanguageField
    private String bankAccountName;

    /**
     * 银行帐号
     */
    @NotEmpty
    @Length(max = 50)
    private String bankAccountNum;

    /**
     * 币种
     */
    @NotEmpty
    @Length(max = 15)
    private String currencyCode;

    /**
     * 国家
     */
    @Length(max = 30)
    @Where
    @JoinTable(name = "countryJoin",type = JoinType.LEFT,target = FndCountryCode.class,joinMultiLanguageTable = true,on = {@JoinOn(joinField = FndCountryCode.FIELD_COUNTRY_CODE ),@JoinOn(joinField = BaseDTO.FIELD_LANG,joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private String countryCode;

    /**
     * 国家名称
     */
    @Transient
    @JoinColumn(joinName = "countryJoin",field = FndCountryCode.FIELD_DESCRIPTION)
    private String countryName;

    /**
     * 时区
     */
    @Where
    @JoinTable(name = "timezoneJoin",type = JoinType.LEFT,joinMultiLanguageTable = true,
    target = SysStandardTimezone.class,on = {@JoinOn(joinField = SysStandardTimezone.FIELD_STANDARD_TIMEZONE_ID),@JoinOn(joinField = BaseDTO.FIELD_LANG,joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long timezoneId;
    /**
     * 时区名称
     */
    @Transient
    @JoinColumn(joinName = "timezoneJoin",field =SysStandardTimezone.FIELD_DESCRIPTION )
    private String timezoneName;

    /**
     * 责任中心
     */
    @Where
    @JoinTable(name = "responsibilityCenterJoin",target = GldResponsibilityCenter.class,type = JoinType.LEFT,joinMultiLanguageTable = true
    ,on = {@JoinOn(joinField = GldResponsibilityCenter.FIELD_RESPONSIBILITY_CENTER_ID ),@JoinOn(joinField = BaseDTO.FIELD_LANG,joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long responsibilityCenterId;
    /**
     * 责任中心名称
     */
    @Transient
    @JoinColumn(joinName = "responsibilityCenterJoin",field = GldResponsibilityCenter.FIELD_RESPONSIBILITY_CENTER_NAME)
    private String responsibilityCenterName;


    /**
     * 客户ID
     */
    @Where
    @JoinTable(name = "customerJoin",type = JoinType.LEFT,joinMultiLanguageTable = true,target = OrdSystemCustomer.class,on = {@JoinOn(joinField = OrdSystemCustomer.FIELD_CUSTOMER_ID),@JoinOn(joinField = BaseDTO.FIELD_LANG,joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long customerId;
    /**
     * 客户名称
     */
    @Transient
    @JoinColumn(joinName = "customerJoin",field = OrdSystemCustomer.FIELD_DESCRIPTION)
    private String customerName;

    /**
     * 供应商ID
     */
    @Where
    @JoinTable(name = "venderJoin",type = JoinType.LEFT,joinMultiLanguageTable = true,target = PurSystemVender.class,on = {@JoinOn(joinField = PurSystemVender.FIELD_VENDER_ID),@JoinOn(joinField = BaseDTO.FIELD_LANG,joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long venderId;
    /**
     * 供应商名称
     */
    @Transient
    @JoinColumn(joinName = "venderJoin",field = PurSystemVender.FIELD_DESCRIPTION)
    private String venderName;

    /**
     * 回单凭证记录
     */
    @NotEmpty
    @Where
    @Length(max = 1)
    private String receiptFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Where
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 汇率类型
     */
    @Transient
    private String exchangeRateType;

    /**
     * 汇率类型名称
     */
    @Transient
    private String exchangeRateTypeName;
    /**
     * 汇率类型方式
     */
    @Transient
    private String exchangeMethodCode;

    /**
     * 汇率
     */
    @Transient
    private BigDecimal exchangeRate;

    /**
     * 核算主体id
     */
    @Transient
    private Long accEntityId;
}
