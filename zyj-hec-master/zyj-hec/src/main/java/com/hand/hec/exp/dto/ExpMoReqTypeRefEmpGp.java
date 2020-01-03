package com.hand.hec.exp.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/2/19 15:31
 * Description:申请单类型分配用户DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_req_type_ref_emp_gp")
public class ExpMoReqTypeRefEmpGp extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_MO_EXP_REQ_TYPE_ID = "moExpReqTypeId";
    public static final String FIELD_MO_EMP_GROUP_ID = "moEmpGroupId";
    public static final String FIELD_MO_EXP_GROUP_CODE = "moEmpGroupCode";
    public static final String FIELD_MO_EXP_GROUP_NAME = "moEmpGroupName";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级申请单类型ID
     */
    @NotNull
    @Where
    private Long moExpReqTypeId;

    /**
     * 管理组织级员工组IDN
     */
    @NotNull
    @JoinTable(name = "ExpMoEmpGroupJoin", joinMultiLanguageTable = true, target = ExpMoEmpGroup.class, type = JoinType.LEFT, on = {@JoinOn(joinField = ExpMoEmpGroup.FIELD_MO_EMP_GROUP_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long moEmpGroupId;
    /**
     * 管理组织级员工组代码
     */
    @Transient
    @JoinColumn(joinName = "ExpMoEmpGroupJoin", field = ExpMoEmpGroup.FIELD_MO_EMP_GROUP_CODE)
    private String moEmpGroupCode;
    /**
     * 管理组织级员工组名称
     */
    @Transient
    @JoinColumn(joinName = "ExpMoEmpGroupJoin", field = ExpMoEmpGroup.FIELD_DESCRIPTION)
    private String moEmpGroupName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
