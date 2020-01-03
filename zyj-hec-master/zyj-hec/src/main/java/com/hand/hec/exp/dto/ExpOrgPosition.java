package com.hand.hec.exp.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.*;
import com.hand.hap.system.dto.BaseDTO;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_org_position")
@MultiLanguage
public class ExpOrgPosition extends BaseDTO {

    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_POSITION_CODE = "positionCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PARENT_POSITION_ID = "parentPositionId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_EMPLOYEE_JOB_ID = "employeeJobId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_UNIT_NAME = "unitName";
    public static final String FIELD_PARENT_POSITION_NAME = "parentPositionName";
    public static final String FIELD_COMPANY_SHORT_NAME = "companyShortName";
    public static final String FIELD_EMPLOYEE_JOB_NAME = "employeeJobName";
    public static final String FIELD_POSITION_NAME = "positionName";


    @Id
    @GeneratedValue
    @Where
    private Long positionId;

    /**
     * 部门ID
     */
    @JoinTable(name = "unitJoin", joinMultiLanguageTable = false, target = ExpOrgUnit.class, type = JoinType.LEFT, on = {@JoinOn(joinField = ExpOrgUnit.FIELD_UNIT_ID)})
    private Long unitId;

    @Transient
    @JoinColumn(joinName = "unitJoin", field = ExpOrgUnit.FIELD_DESCRIPTION)
    private String unitName;

    @Transient
    private String positionName;

    /**
     * 岗位
     */
    @Where
    @Length(max = 30)
    private String positionCode;

    /**
     * 描述
     */
    @MultiLanguageField
    @Where(comparison = Comparison.LIKE)
    @Length(max = 500)
    private String description;

    /**
     * 上级岗位ID
     */
    @Where
    @JoinTable(name = "positionJoin", joinMultiLanguageTable = true, target = ExpOrgPosition.class, type = JoinType.LEFT, on = {@JoinOn(joinField = ExpOrgPosition.FIELD_POSITION_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long parentPositionId;

    @Transient
    @JoinColumn(joinName = "positionJoin", field = ExpOrgPosition.FIELD_DESCRIPTION)
    private String parentPositionName;

    /**
     * 公司ID
     */
    @Where
    @JoinTable(name = "companyJoin", joinMultiLanguageTable = false, target = FndCompany.class, type = JoinType.LEFT, on = {@JoinOn(joinField = FndCompany.FIELD_COMPANY_ID)})
    private Long companyId;

    @Transient
    @JoinColumn(joinName = "companyJoin", field = FndCompany.FIELD_COMPANY_SHORT_NAME)
    private String companyShortName;

    /**
     * 员工职务ID
     */
    @Where
    @JoinTable(name = "jobJoin", joinMultiLanguageTable = false, target = ExpEmployeeJob.class, type = JoinType.LEFT, on = {@JoinOn(joinField = ExpEmployeeJob.FIELD_EMPLOYEE_JOB_ID)})
    private Long employeeJobId;


    @Transient
    @JoinColumn(joinName = "jobJoin", field = ExpEmployeeJob.FIELD_DESCRIPTION)
    private String employeeJobName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Where
    @Length(max = 1)
    private String enabledFlag;


    @Transient
    private Long accEntityId;

    @Transient
    private String accEntityName;


    @Transient
    private Long respCenterId;


    @Transient
    private String respCenterName;


    @Transient
    private Long bgtEntityId;

    @Transient
    private Long bgtCenterId;

}
