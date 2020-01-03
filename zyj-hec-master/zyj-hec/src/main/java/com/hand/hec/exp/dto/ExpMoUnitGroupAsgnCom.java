package com.hand.hec.exp.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.*;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hap.fnd.dto.FndCompany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;

/**
 * <p>
 * ExpMoUnitGroupAsgnCom
 * </p>
 *
 * @author yang.duan 2019/01/10 11:25
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_unit_group_asgn_com")
public class ExpMoUnitGroupAsgnCom extends BaseDTO {

    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_MO_UNIT_GROUP_ID = "moUnitGroupId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_COMPANY_NAME = "companyName";
    public static final String FIELD_COMPANY_CODE = "companyCode";


    @Id
    @GeneratedValue
    private Long assignId;

    /**
     * 管理组织级部门组ID
     */
    @Where
    private Long moUnitGroupId;

    /**
     * 公司ID
     */
    @JoinTable(name = "companyJoin", joinMultiLanguageTable = true, target = FndCompany.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = FndCompany.FIELD_COMPANY_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "companyJoin2", joinMultiLanguageTable = false, target = FndCompany.class, type = JoinType.LEFT,
            on = {@JoinOn(joinField = FndCompany.FIELD_COMPANY_ID)})

    private Long companyId;

    @Transient
    @JoinColumn(joinName = "companyJoin", field = FndCompany.FIELD_COMPANY_SHORT_NAME)
    private String companyName;

    @Transient
    @JoinColumn(joinName = "companyJoin2", field = FndCompany.FIELD_COMPANY_CODE)
    private String companyCode;

    /**
     * 启用标志
     */
    @Length(max = 1)
    private String enabledFlag;

}
