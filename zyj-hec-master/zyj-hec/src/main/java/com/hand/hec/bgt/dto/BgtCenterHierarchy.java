package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * 预算中心层次
 * </p>
 * 
 * @author mouse 2019/01/07 16:22
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_center_hierarchy")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtCenterHierarchy extends BaseDTO {

    public static final String FIELD_HIERARCHY_ID = "hierarchyId";
    public static final String FIELD_PARENT_CENTER_ID = "parentCenterId";
    public static final String FIELD_CENTER_ID = "centerId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    @Id
    @GeneratedValue
    private Long hierarchyId;

    /**
     * 父预算中心ID
     */
    @NotNull
    @Where
    private Long parentCenterId;

    /**
     * 子预算中心ID
     */
    @NotNull
    @JoinTable(name = "centerJoin", joinMultiLanguageTable = true, target = BgtCenter.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = BgtCenter.FIELD_CENTER_ID)})
    private Long centerId;

    /**
     * 子预算中心code
     */
    @JoinColumn(joinName = "centerJoin", field = BgtCenter.FIELD_CENTER_CODE)
    @Transient
    private String centerCode;

    /**
     * 子预算中心描述
     */
    @JoinColumn(joinName = "centerJoin", field = BgtCenter.FIELD_DESCRIPTION)
    @Transient
    private String description;

    @Transient
    private Long bgtOrgId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;


}
