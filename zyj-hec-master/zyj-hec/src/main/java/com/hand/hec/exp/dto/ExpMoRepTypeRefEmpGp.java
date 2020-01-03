package com.hand.hec.exp.dto;

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
 * <p>
 * ExpMoRepTypeRefEmpGp
 * </p>
 *
 * @author yang.duan 2019/01/10 14:45
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_rep_type_ref_emp_gp")
public class ExpMoRepTypeRefEmpGp extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_MO_EXP_REPORT_TYPE_ID = "moExpReportTypeId";
    public static final String FIELD_MO_EMP_GROUP_ID = "moEmpGroupId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    public static final String FIELD_MO_EMP_GROUP_CODE = "moEmpGroupCode";
    public static final String FIELD_MO_EMP_GROUP_NAME = "moEmpGroupName";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级报销单类型ID
     */
    @NotNull
    @Where
    private Long moExpReportTypeId;

    /**
     * 管理组织级员工组ID
     *
     */
    @NotNull
    @JoinTable(name = "empGroupJoin", target = ExpMoEmpGroup.class, joinMultiLanguageTable = true, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = ExpMoEmpGroup.FIELD_MO_EMP_GROUP_ID)})
    private Long moEmpGroupId;

    @Transient
    @JoinColumn(joinName = "empGroupJoin",field = ExpMoEmpGroup.FIELD_MO_EMP_GROUP_CODE)
    @Where
    private String moEmpGroupCode;


    @Transient
    @JoinColumn(joinName = "empGroupJoin",field = ExpMoEmpGroup.FIELD_DESCRIPTION)
    @Where
    private String moEmpGroupName;
    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
