package com.hand.hec.fnd.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 归口管理dto
 * </p>
 *
 * @author YHL 2019/01/21 19:40
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_centralized_managing")
public class FndCentralizedManaging extends BaseDTO {

    public static final String FIELD_CENTRALIZED_MAG_ID = "centralizedMagId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_COMPANY_CODE = "companyCode";
    public static final String FIELD_COMPANY_SHORT_NAME = "companyShortName";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_APPROVE_TYPE = "approveType";
    public static final String FIELD_CONDITION_TYPE = "conditionType";
    public static final String FIELD_CONDITION_TYPE_NAME = "conditionTypeName";
    public static final String FIELD_CONDITION_VALUE_ID = "conditionValueId";
    public static final String FIELD_CONDITION_VALUE_CODE = "conditionValueCode";
    public static final String FIELD_CONDITION_VALUE_DESC = "conditionValueDesc";
    public static final String FIELD_CENTRALIZED_MAG_TYPE = "centralizedMagType";
    public static final String FIELD_CENTRALIZED_MAG_VALUE_ID = "centralizedMagValueId";
    public static final String FIELD_CENTRALIZED_MAG_VALUE_CODE = "centralizedMagValueCode";
    public static final String FIELD_CENTRALIZED_MAG_VALUE_DESC = "centralizedMagValueDesc";

    /**
     * 归口管理ID
     */
    @Id
    @GeneratedValue
    private Long centralizedMagId;

    /**
     * 管理公司ID
     */
    @Where
    @NotNull
    private Long companyId;

    /**
     * 公司代码
     */
    @Transient
    @Length(max = 30)
    private String companyCode;

    /**
     * 公司名称
     */
    @Transient
    @Length(max = 500)
    private String companyShortName;

    /**
     * 管理组织ID
     */
    @Where
    @Transient
    @NotNull
    private Long magOrgId;

    /**
     * 审批规则
     */
    @NotEmpty
    @Length(max = 30)
    private String approveType;

    /**
     * 归口条件类型
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String conditionType;

    /**
     * 归口条件类型名称
     */
    @Transient
    @Length(max = 500)
    private String conditionTypeName;

    /**
     * 归口条件值ID
     */
    @NotNull
    private Long conditionValueId;

    /**
     * 归口条件值代码
     */
    @Transient
    @Length(max = 30)
    private String conditionValueCode;

    /**
     * 归口条件值描述
     */
    @Transient
    @Length(max = 500)
    private String conditionValueDesc;

    /**
     * 归口管理类型
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String centralizedMagType;

    /**
     * 归口管理明细值ID
     */
    @NotNull
    private Long centralizedMagValueId;

    /**
     * 归口管理明细值代码
     */
    @Transient
    @Length(max = 30)
    private String centralizedMagValueCode;

    /**
     * 归口管理明细值描述
     */
    @Transient
    @Length(max = 500)
    private String centralizedMagValueDesc;

}
