package com.hand.hec.exp.dto;

import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.*;

import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Table;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * ExpOrgUnit
 * </p>
 *
 * @author yang.duan 2019/01/10 11:16
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_org_unit")
@MultiLanguage
@Builder
public class ExpOrgUnit extends BaseDTO {

    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_UNIT_CODE = "unitCode";
    public static final String FIELD_UNIT_TYPE_ID = "unitTypeId";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PARENT_UNIT_ID = "parentUnitId";
    public static final String FIELD_CHIEF_POSITION_ID = "chiefPositionId";
    public static final String FIELD_ORG_UNIT_LEVEL_ID = "orgUnitLevelId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_UNIT_NAME = "unitName";
    public static final String FIELD_COMPANY_SHORT_NAME = "companyShortName";
    public static final String FIELD_COMPANY_CODE = "companyCode";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_UNIT_GROUP_COM_ASSIGN_ID = "unitGroupComAssignId";
    public static final String FIELD_ORG_UNIT_LEVEL_DISPLAY = "orgUnitLevelDisplay";
    public static final String FIELD_UNIT_TYPE_DISPLAY = "unitTypeDisplay";
    public static final String FIELD_DEFAULT_ACC_ENTITY_ID = "defaultAccEntityId";
    public static final String FIELD_DEFAULT_BGT_ENTITY_ID = "defaultBgtEntityId";
    public static final String FIELD_DEFAULT_RES_CENTER_ID = "defaultResCenterId";
    public static final String FIELD_DEFAULT_BGT_CENTER_ID = "defaultBgtCenterId";
    public static final String FIELD_DEFAULT_ACC_ENTITY_DISPLAY = "defaultAccEntityDisplay";
    public static final String FIELD_DEFAULT_BGT_ENTITY_DISPLAY = "defaultBgtEntityDisplay";
    public static final String FIELD_DEFAULT_RES_CENTER_DISPLAY = "defaultResCenterDisplay";
    public static final String FIELD_DEFAULT_BGT_CENTER_DISPLAY = "defaultBgtCenterDisplay";


    @Id
    @GeneratedValue
    @Where
    private Long unitId;

    /**
     * 公司ID
     */
    @NotNull
    @Where
    private Long companyId;

    @Transient
    private String companyShortName;

    @Transient
    private String companyCode;

    /**
     * 部门代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where(comparison = Comparison.LIKE)
    private String unitCode;

    /**
     * 部门类型ID
     */
    @JoinTable(name = "unitTypeJoin", joinMultiLanguageTable = true, target = ExpOrgUnitType.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = ExpOrgUnitType.FIELD_UNIT_TYPE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long unitTypeId;

    @Transient
    @JoinColumn(joinName = "unitTypeJoin", field = ExpOrgUnitType.FIELD_DESCRIPTION)
    private String unitTypeDisplay;

    /**
     * 描述
     */
    @MultiLanguageField
    @NotEmpty
    @Length(max = 500)
    @Where(comparison = Comparison.LIKE)
    private String description;

    @Transient
    private String unitName;

    /**
     * 上级部门代码
     */
    @JoinTable(name = "parentUnitJoin", joinMultiLanguageTable = true, target = ExpOrgUnit.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = ExpOrgUnit.FIELD_UNIT_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long parentUnitId;

    @Transient
    @JoinColumn(joinName = "parentUnitJoin", field = ExpOrgUnit.FIELD_DESCRIPTION)
    private String parentUnitDisplay;

    /**
     * 主岗位ID
     */
    @JoinTable(name = "parentUnitJoin", joinMultiLanguageTable = true, target = ExpOrgPosition.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = ExpOrgPosition.FIELD_POSITION_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long chiefPositionId;

    @Transient
    @JoinColumn(joinName = "parentUnitJoin", field = ExpOrgPosition.FIELD_DESCRIPTION)
    private String chiefPositionDisplay;

    /**
     * 部门级别ID
     */
    @JoinTable(name = "unitLevelJoin", joinMultiLanguageTable = true, target = ExpOrgUnitLevel.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = ExpOrgUnitLevel.FIELD_ORG_UNIT_LEVEL_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long orgUnitLevelId;

    @Transient
    @JoinColumn(joinName = "unitLevelJoin", field = ExpOrgUnitLevel.FIELD_DESCRIPTION)
    private String orgUnitLevelDisplay;

    @Transient
    private Long magOrgId;

    @Transient
    private Long unitGroupComAssignId;

    @Transient
    private Long defaultAccEntityId;

    @Transient
    private String defaultAccEntityDisplay;

    @Transient
    private Long defaultBgtEntityId;

    @Transient
    private String defaultBgtEntityDisplay;

    @Transient
    private Long defaultResCenterId;

    @Transient
    private String defaultResCenterDisplay;

    @Transient
    private Long defaultBgtCenterId;

    @Transient
    private String defaultBgtCenterDisplay;

    @Transient
    private Long positionId;

    @Transient
    private String positionCode;

    @Transient
    private String positionName;


    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
