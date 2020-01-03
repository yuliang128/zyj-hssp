package com.hand.hec.exp.dto;

import javax.persistence.*;
import javax.persistence.criteria.JoinType;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.fnd.dto.FndCompany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 报销类型关联公司Dto
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 14:24
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_exp_type_asgn_com")
public class ExpMoExpTypeAsgnCom extends BaseDTO {

    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_MO_EXPENSE_TYPE_ID = "moExpenseTypeId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    @Where
    private Long assignId;

    /**
     * 管理组织级报销类型ID
     */
    @Where
    private Long moExpenseTypeId;

    /**
     * 公司ID
     */
    @Where
    @JoinTable(name = "companyJoin", target = FndCompany.class, type = JoinType.LEFT
            , joinMultiLanguageTable = true, on = {@JoinOn(joinField = FndCompany.FIELD_COMPANY_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long companyId;
    /**
     * 公司代码
     */
    @Transient
    @JoinColumn(joinName = "companyJoin", field = FndCompany.FIELD_COMPANY_CODE)
    private String companyCode;
    /**
     * 公司简称
     */
    @Transient
    @JoinColumn(joinName = "companyJoin", field = FndCompany.FIELD_COMPANY_SHORT_NAME)
    private String companyShortName;

    /**
     * 启用标志
     */
    @Length(max = 1)
    @Where
    private String enabledFlag;

    @Transient
    private Long magOrgId;

}
