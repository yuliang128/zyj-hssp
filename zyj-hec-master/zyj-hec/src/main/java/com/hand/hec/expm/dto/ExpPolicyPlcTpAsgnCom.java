package com.hand.hec.expm.dto;

import javax.persistence.*;

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

import javax.persistence.Table;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 费用政策地点类型分配公司Dto
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 15:50
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_policy_plc_tp_asgn_com")
public class ExpPolicyPlcTpAsgnCom extends BaseDTO {

    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_PLACE_TYPE_ID = "placeTypeId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    @Where
    private Long assignId;

    /**
     * 地点类型ID
     */
    @NotNull
    @Where
    private Long placeTypeId;

    /**
     * 公司ID
     */
    @NotNull
    @Where
    @JoinTable(name = "companyJoin", target = FndCompany.class, type = JoinType.LEFT
            , joinMultiLanguageTable = true, on = {@JoinOn(joinField = FndCompany.FIELD_COMPANY_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long companyId;
    /**
     * 管理公司代码
     */
    @Transient
    @JoinColumn(joinName = "companyJoin", field = FndCompany.FIELD_COMPANY_CODE)
    private String companyCode;
    /**
     * 管理公司简称
     */
    @Transient
    @JoinColumn(joinName = "companyJoin", field = FndCompany.FIELD_COMPANY_SHORT_NAME)
    private String companyShortName;

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
