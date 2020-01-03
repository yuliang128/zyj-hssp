package com.hand.hec.exp.dto;

import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
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
 * 员工组关联公司Dto
 * </p>
 *
 * @author xiuxian.wu 2019/03/06 14:48
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_emp_group_asgn_com")
public class ExpMoEmpGroupAsgnCom extends BaseDTO {

    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_MO_EMP_GROUP_ID = "moEmpGroupId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    @Where
    private Long assignId;

    /**
     * 管理组织级员工组ID
     */
    @NotNull
    @Where
    private Long moEmpGroupId;

    /**
     * 公司ID
     */
    @NotNull
    @Where
    @JoinTable(name = "companyJoin", type = JoinType.LEFT, target = FndCompany.class, joinMultiLanguageTable = true, on = {@JoinOn(joinField = FndCompany.FIELD_COMPANY_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long companyId;
    /**
     * 公司简称
     */
    @Transient
    @JoinColumn(joinName = "companyJoin", field = FndCompany.FIELD_COMPANY_SHORT_NAME)
    private String companyShortName;
    /**
     * 公司代码
     */
    @Transient
    @JoinColumn(joinName = "companyJoin", field = FndCompany.FIELD_COMPANY_CODE)
    private String companyCode;

    /**
     * 启用标志
     */
    @NotEmpty
    @Where
    @Length(max = 1)
    private String enabledFlag;
    /**
     * 管理组织ID
     */
    @Transient
    private Long magOrgId;

}
