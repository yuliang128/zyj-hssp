package com.hand.hec.gld.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.fnd.dto.GldAccount;
import com.hand.hec.fnd.dto.PurVenderType;

/**
 * <p>
 * 核算主体定义
 * </p>
 *
 * @author ngls.luhui 2019/01/08 13:50
 */
@ExtensionAttribute(disable = true)
@Table(name = "gld_accounting_entity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@MultiLanguage
public class GldAccountingEntity extends BaseDTO {

    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_ACC_ENTITY_CODE = "accEntityCode";
    public static final String FIELD_ACC_ENTITY_NAME = "accEntityName";
    public static final String FIELD_FUNCTIONAL_CURRENCY_CODE = "functionalCurrencyCode";
    public static final String FIELD_PAY_CURRENCY_CODE = "payCurrencyCode";
    public static final String FIELD_COMPANY_TYPE = "companyType";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";
    public static final String FIELD_DEFAULT_TIMEZONE_ID = "defaultTimezoneId";
    public static final String FIELD_DEFAULT_LANGUAGE = "defaultLanguage";
    public static final String FIELD_DEFAULT_COUNTRY_CODE = "defaultCountryCode";
    public static final String FIELD_TAXPAYER_TYPE = "taxpayerType";
    public static final String FIELD_TAXPAYER_NUMBER = "taxpayerNumber";
    public static final String FIELD_PHONE = "phone";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_BANK_ACCOUNT = "bankAccount";

    /**
     * 核算主体ID
     */
    @Id
    @GeneratedValue
    @Where
    private Long accEntityId;

    /**
     * 核算主体代码
     */
    @NotEmpty
    @Where
    @Length(max = 30)
    private String accEntityCode;

    /**
     * 核算主体代码从
     */
    @Transient
    @Where
    @Length(max = 30)
    private String accEntityCodeFrom;

    /**
     * 核算主体代码到
     */
    @Transient
    @Where
    @Length(max = 30)
    private String accEntityCodeTo;

    /**
     * 核算主体名称
     */
    @NotEmpty
    @Where
    @Length(max = 500)
    @MultiLanguageField
    private String accEntityName;

    /**
     * 本位币描述
     */
    @Transient
    private String functionalCurrencyName;

    /**
     * 本位币代码
     */
    @Length(max = 10)
    private String functionalCurrencyCode;

    /**
     * 支付币描述
     */
    @Transient
    private String payCurrencyName;

    /**
     * 支付币代码
     */
    @NotEmpty
    @Length(max = 10)
    private String payCurrencyCode;

    /**
     * 公司类型代码
     */
    @Transient
    private String companyTypeDisplay;

    /**
     * 公司类型代码
     */
    @NotEmpty
    @Where
    @Length(max = 30)
    private String companyType;

    /**
     * 默认时区ID
     */
    private Long defaultTimezoneId;

    /**
     * 默认时区描述
     */
    @Transient
    private String defaultTimezoneName;

    /**
     * 默认语言代码
     */
    @Length(max = 30)
    private String defaultLanguage;

    /**
     * 默认语言描述
     */
    @Transient
    private String defaultLanguageDisplay;

    /**
     * 默认国家
     */
    @Length(max = 30)
    private String defaultCountryCode;

    /**
     * 默认国家描述
     */
    @Transient
    private String defaultCountryName;

    /**
     * 纳税人类型
     */
    @Length(max = 30)
    private String taxpayerType;

    /**
     * 纳税人描述
     */
    @Transient
    private String taxpayerTypeDisplay;

    /**
     * 账套名称
     */
    @Transient
    @Where
    private String setOfBooksName;

    /**
     * 账套ID
     */
    @Transient
    @Where
    private Long setOfBooksId;

    /**
     * 核算-账套关联主键
     */
    @Transient
    private Long sobRefId;

    /**
     * 预算实体名称
     */
    @Transient
    private String bgtEntityName;

    /**
     * 预算实体ID
     */
    @Transient
    private String bgtEntityId;

    /**
     * 核算-预算关联主键
     */
    @Transient
    private Long beRefId;

    /**
     * 有效日期从
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDateActive;

    /**
     * 有效日期至
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDateActive;

    /**
     * 纳税人识别号
     */
    @Length(max = 30)
    private String taxpayerNumber;

    /**
     * 联系电话
     */
    @Length(max = 30)
    private String phone;

    /**
     * 地址
     */
    @Length(max = 255)
    private String address;

    /**
     * 开户行及账号
     */
    @Length(max = 50)
    private String bankAccount;

    @Transient
    private Long responsibilityCenterId;

    /**
     * 核算主体编码-名称
     */
    @Transient
    private String accEntityCodeName;

    /**
     * 根据供应商类型批量分配核算主体时的子包装属性
     */
    @Transient
    private List<PurVenderType> venderTypeDetail;

    /**
     * 核算主体多对多批量分配科目
     */
    @Transient
    private List<GldAccount> accountList;

    @Transient
    private String defaultFlag;

    @Transient
    private String companyId;

}
