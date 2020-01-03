package com.hand.hec.exp.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hap.fnd.dto.FndCompany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/2/21 10:20
 * Description:申请单类型定义-分配公司dto
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_req_type_asgn_com")
public class ExpMoReqTypeAsgnCom extends BaseDTO {

    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_MO_EXP_REQ_TYPE_ID = "moExpReqTypeId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_COMPANY_CODE = "companyCode";
    public static final String FIELD_COMPANY_NAME = "companyName";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long assignId;

    /**
     * 管理组织级申请单类型ID
     */
    @NotNull
    @Where
    private Long moExpReqTypeId;

    /**
     * 公司ID
     */
    @NotNull
    @Where
    @JoinTable(name = "FndCompanyJoin", joinMultiLanguageTable = true, target = FndCompany.class, type = JoinType.LEFT, on = {@JoinOn(joinField = FndCompany.FIELD_COMPANY_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long companyId;

    /**
     * 公司代码
     */
    @Transient
    @JoinColumn(joinName = "FndCompanyJoin", field = FndCompany.FIELD_COMPANY_CODE)
    private String companyCode;

    /**
     * 公司名称
     */
    @Transient
    @JoinColumn(joinName = "FndCompanyJoin", field = FndCompany.FIELD_COMPANY_SHORT_NAME)
    private String companyName;
    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
