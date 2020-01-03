package com.hand.hap.sys.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hap.system.dto.Lov;

/**
 * <p>
 * 参数定义dto
 * </p>
 *
 * @author dingwei.ma@hand-china.com
 * @author jialin.xing@hand-china.com
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "sys_parameter")
@MultiLanguage
public class SysParameter extends BaseDTO {

    public static final String FIELD_PARAMETER_ID = "parameterId";
    public static final String FIELD_PARAMETER_CODE = "parameterCode";
    public static final String FIELD_PARAMETER_TYPE = "parameterType";
    public static final String FIELD_LOV_ID = "lovId";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_USER_CHANGEABLE_FLAG = "userChangeableFlag";
    public static final String FIELD_USER_VISIBLE_FLAG = "userVisibleFlag";
    public static final String FIELD_SYSTEM_ENABLED_FLAG = "systemEnabledFlag";
    public static final String FIELD_ROLE_ENABLED_FLAG = "roleEnabledFlag";
    public static final String FIELD_USER_ENABLED_FLAG = "userEnabledFlag";
    public static final String FIELD_COMPANY_ENABLED_FLAG = "companyEnabledFlag";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";
    public static final String FIELD_PARAMETER_NAME = "parameterName";
    public static final String FIELD_ENCRYPT_VALUE_FLAG = "encryptValueFlag";
    public static final String FIELD_APP_ENABLED_FLAG = "appEnabledFlag";
    public static final String FIELD_ACC_ENTITY_ENABLED_FLAG = "accEntityEnabledFlag";
    public static final String FIELD_MAG_ORG_ENABLED_FLAG = "magOrgEnabledFlag";
    public static final String FIELD_SET_OF_BOOKS_ENABLED_FLAG = "setOfBooksEnabledFlag";
    public static final String FIELD_BGT_ORG_ENABLED_FLAG = "bgtOrgEnabledFlag";
    public static final String FIELD_LOV_CODE = "code";

    /**
     * 参数代码
     */
    public static final String PARAM_FND_ALL_ORGANIZATIONAL = "FND_ALL_ORGANIZATIONAL";
    public static final String PARAM_DEFAULT_EXCHANGE_RATE_TYPE = "DEFAULT_EXCHANGE_RATE_TYPE";

    @Id
    @GeneratedValue
    private Long parameterId;

    /**
     * 参数代码
     */
    @NotEmpty
    @Length(max = 100)
    private String parameterCode;

    /**
     * 验证类型
     */
    @NotEmpty
    @Length(max = 100)
    private String parameterType;

    /**
     * Lov ID
     */
    @JoinTable(name = "lov", target = Lov.class, type = JoinType.LEFT, on = {@JoinOn(joinField = FIELD_LOV_ID)})
    private String lovId;

    /**
     * 参数名称
     */
    @NotEmpty
    @Length(max = 2000)
    @MultiLanguageField
    private String description;

    /**
     * 用户可更新
     */
    @NotEmpty
    @Length(max = 1)
    private String userChangeableFlag;

    /**
     * 用户可查看
     */
    @NotEmpty
    @Length(max = 1)
    private String userVisibleFlag;

    /**
     * 参数级别：全局
     */
    @NotEmpty
    @Length(max = 1)
    private String systemEnabledFlag;

    /**
     * 参数级别：角色
     */
    @NotEmpty
    @Length(max = 1)
    private String roleEnabledFlag;

    /**
     * 参数级别：用户
     */
    @NotEmpty
    @Length(max = 1)
    private String userEnabledFlag;

    /**
     * 参数级别：公司
     */
    @NotEmpty
    @Length(max = 1)
    private String companyEnabledFlag;

    /**
     * 有效日期从
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT,timezone = "GMT+8")
    private Date startDateActive;

    /**
     * 有效日期至
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date endDateActive;

    @MultiLanguageField
    @Length(max = 500)
    private String parameterName;

    /**
     * 加密数据
     */
    @Length(max = 1)
    private String encryptValueFlag;

    /**
     * APP 启用
     */
    @Length(max = 1)
    private String appEnabledFlag;

    /**
     * 参数级别：核算主体
     */
    @NotEmpty
    @Length(max = 1)
    private String accEntityEnabledFlag;

    /**
     * 参数级别：管理组织
     */
    @NotEmpty
    @Length(max = 1)
    private String magOrgEnabledFlag;

    /**
     * 参数级别：账套
     */
    @NotEmpty
    @Length(max = 1)
    private String setOfBooksEnabledFlag;

    /**
     * 参数级别：预算组织
     */
    @NotEmpty
    @Length(max = 1)
    private String bgtOrgEnabledFlag;

    /**
     * LOV编码
     */
    @Transient
    @JoinColumn(joinName = "lov", field = FIELD_LOV_CODE)
    private String lovCode;

    @Transient
    private Long parameterValueId;

    @Transient
    private Long levelId;

    @Transient
    private Long levelValue;

    @Transient
    private String parameterValue;

    @Transient
    private String valueCode;

    @Transient
    private String valueName;

    @Transient
    private String levelName;

}
