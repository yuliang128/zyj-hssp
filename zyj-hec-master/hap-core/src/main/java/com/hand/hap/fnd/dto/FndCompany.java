package com.hand.hap.fnd.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.exp.dto.ExpMoEmployeeType;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 管理公司定义dto
 * </p>
 *
 * @author yang.duan 2019/01/10 11:05
 * @author xiuxian.wu 2019/01/21 13:45
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_company")
@MultiLanguage
@Builder
public class FndCompany extends BaseDTO {

    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_COMPANY_CODE = "companyCode";
    public static final String FIELD_COMPANY_SHORT_NAME = "companyShortName";
    public static final String FIELD_COMPANY_FULL_NAME = "companyFullName";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_COMPANY_LEVEL_ID = "companyLevelId";
    public static final String FIELD_PARENT_COMPANY_ID = "parentCompanyId";
    public static final String FIELD_SYSTEM_TIMEZONE_ID = "systemTimezoneId";
    public static final String FIELD_LANGUAGE = "language";
    public static final String FIELD_MANAGING_CURRENCY = "managingCurrency";
    public static final String FIELD_CHIEF_POSITION_ID = "chiefPositionId";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";
    public static final String FIELD_COMPANY_INFO_URL = "companyInfoUrl";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_COMPANY_CODE_NAME = "companyCodeName";
    public static final String FIELD_MAG_ORG_CODE_NAME = "magOrgCodeName";


    @Id
    @GeneratedValue
    @Where
    private Long companyId;

    /**
     * 公司代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String companyCode;

    /**
     * 公司简称ID
     */
    @NotEmpty
    @Length(max = 500)
    @MultiLanguageField
    @Where
    private String companyShortName;

    /**
     * 公司全称ID
     */
    @Length(max = 500)
    @MultiLanguageField
    private String companyFullName;

    /**
     * add luhui
     * 管理组织ID
     */
    @JoinTable(name = "magOrgJoin", joinMultiLanguageTable = true, target = FndManagingOrganization.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = FndManagingOrganization.FIELD_MAG_ORG_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @NotNull
    @Where
    private Long magOrgId;

    /**
     * add luhui
     * 管理组织代码
     */
    @JoinColumn(joinName = "magOrgJoin", field = FndManagingOrganization.FIELD_DESCRIPTION)
    @Transient
    private String magOrgCode;

    /**
     * 管理组织名称
     */
    @Transient
    private String magOrgName;

    /**
     * 公司地址
     */
    @Length(max = 2000)
    private String address;

    /**
     * 公司级别ID
     */
    private Long companyLevelId;

    /**
     * 父公司ID
     */
    @Where
    private Long parentCompanyId;

    /**
     * 系统时区ID
     */
    private Long systemTimezoneId;

    /**
     * 默认语言
     */
    @Length(max = 30)
    private String language;

    /**
     * 管理币种
     */
    @Length(max = 30)
    private String managingCurrency;

    /**
     * 公司主岗位
     */
    private Long chiefPositionId;

    /**
     * 公司主岗位名
     */
    @Transient
    private String positionName;

    /**
     * 启用日期
     */
    private Date startDateActive;

    /**
     * 失效日期
     */
    private Date endDateActive;

    /**
     * 公司信息网址URL
     */
    @Length(max = 2000)
    private String companyInfoUrl;

    /**
     * add luhui
     * 根据员工类型批量分配公司时的子包装属性
     */
    @Transient
    private List<ExpMoEmployeeType> empTypeDetail;

    /**
     * 默认核算主体
     */
    @Transient
    private String defaultAccEntityName;

    /**
     * 默认核算主体ID
     */
    @Transient
    private Long accEntityId;

	/**
	 * 核算主体名称
	 */
	@Transient
	private String accEntityName;

    /**
     * 默认预算主体
     */
    @Transient
    private String defaultBgtEntityName;

    /**
     * 默认预算主体ID
     */
    @Transient
    private Long bgtEntityId;

    @Transient
    private String enabledFlag;

    public boolean isActive() {
        return (startDateActive == null || startDateActive.getTime() <= System.currentTimeMillis())
                && (endDateActive == null || endDateActive.getTime() >= System.currentTimeMillis());
    }

	/**
	 * 公司名称（code-name）
	 */
	@Transient
	private String companyName;
	/**
	 * 本位币代码
	 */
	@Transient
	private String functionalCurrencyCode;

	/**
	 * 本位币名称
	 */
	@Transient
	private String functionalCurrencyName;

    /**
     * 公司代码名称
     */
	@Transient
    private String companyCodeName;

    /**
     * 管理组织代码名称
     */
	@Transient
    private String magOrgCodeName;

}
