package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hec.exp.dto.ExpMoEmpGroup;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 预算日记账类型关联员工组
 * </p>
 * 
 * @author mouse 2019/01/07 16:39
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_journal_type_ref_gp")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtJournalTypeRefGp extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_BGT_JOURNAL_TYPE_ID = "bgtJournalTypeId";
    public static final String FIELD_MO_EMP_GROUP_ID = "moEmpGroupId";
    public static final String FIELD_MO_EMP_GROUP_CODE = "moEmpGroupCode";
    public static final String FIELD_MO_EMP_GROUP_NAME = "moEmpGroupName";


    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 预算日记账类型ID
     */
    @Where
    @NotNull
    private Long bgtJournalTypeId;

    /**
     * 员工组ID
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


}
