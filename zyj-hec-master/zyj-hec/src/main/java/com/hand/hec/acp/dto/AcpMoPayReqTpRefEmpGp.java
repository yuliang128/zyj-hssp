package com.hand.hec.acp.dto;

import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hec.exp.dto.ExpMoEmpGroup;
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

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "acp_mo_pay_req_tp_ref_emp_gp")
public class AcpMoPayReqTpRefEmpGp extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_MO_PAY_REQ_TYPE_ID = "moPayReqTypeId";
    public static final String FIELD_MO_EMP_GROUP_ID = "moEmpGroupId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级付款申请单类型ID
     */
    @NotNull
    @Where
    private Long moPayReqTypeId;

    /**
     * 管理组织级员工组ID
     */
    @NotNull
    @JoinTable(name = "groupJoin", joinMultiLanguageTable = true, target = ExpMoEmpGroup.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = ExpMoEmpGroup.FIELD_MO_EMP_GROUP_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "groupJoin2", joinMultiLanguageTable = false, target = ExpMoEmpGroup.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = ExpMoEmpGroup.FIELD_MO_EMP_GROUP_ID)})
    private Long moEmpGroupId;

    @Transient
    @JoinColumn(joinName = "groupJoin2", field = ExpMoEmpGroup.FIELD_MO_EMP_GROUP_CODE)
    private String moEmpGroupCode;

    @Transient
    @JoinColumn(joinName = "groupJoin", field = ExpMoEmpGroup.FIELD_DESCRIPTION)
    private String moEmpGroupName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
