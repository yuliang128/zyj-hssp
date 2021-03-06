package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 预算表分配管理组织
 * </p>
 * 
 * @author mouse 2019/01/07 16:46
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_structure_asgn_mo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtStructureAsgnMo extends BaseDTO {

    public static final String FIELD_ASSIGN_MO_ID = "assignMoId";
    public static final String FIELD_STRUCTURE_ID = "structureId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_MAG_ORG_CODE = "magOrgCode";
    public static final String FIELD_MAG_ORG_DESC = "magOrgName";


    @Id
    @GeneratedValue
    private Long assignMoId;

    /**
     * 预算表ID
     */
    @Where
    @NotNull
    private Long structureId;

    /**
     * 管理组织ID
     */
    @NotNull
    @JoinTable(name = "magJoin", joinMultiLanguageTable = true, target = FndManagingOrganization.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = FndManagingOrganization.FIELD_MAG_ORG_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "magJoin2", target = FndManagingOrganization.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = FndManagingOrganization.FIELD_MAG_ORG_ID)})
    private Long magOrgId;

    @Transient
    @JoinColumn(joinName = "magJoin2", field = FndManagingOrganization.FIELD_MAG_ORG_CODE)
    private String magOrgCode;

    @Transient
    @JoinColumn(joinName = "magJoin", field = FndManagingOrganization.FIELD_DESCRIPTION)
    private String magOrgName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;


}
