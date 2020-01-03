package com.hand.hec.exp.dto;

import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hec.bgt.dto.BgtCenter;
import com.hand.hec.bgt.dto.BgtEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
 * ExpOrgUnitRefBgtOrg
 * </p>
 *
 * @author yang.duan 2019/01/10 11:16
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Builder
@Table(name = "exp_org_unit_ref_bgt_org")
public class ExpOrgUnitRefBgtOrg extends BaseDTO {

    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
    public static final String FIELD_BGT_CENTER_ID = "bgtCenterId";
    public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_BGT_ENTITY_NAME = "bgtEntityName";
    public static final String FIELD_BGT_CENTER_NAME = "bgtCenterName";


    /**
     * 部门ID
     */
    @Where
    @NotNull
    private Long unitId;

    /**
     * 预算实体ID
     */
    @JoinTable(name = "bgtEntityJoin", joinMultiLanguageTable = true, target = BgtEntity.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = BgtEntity.FIELD_ENTITY_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @NotNull
    private Long bgtEntityId;


    @Transient
    @JoinColumn(joinName = "bgtEntityJoin", field = BgtEntity.FIELD_DESCRIPTION)
    private String bgtEntityName;

    /**
     * 预算中心ID
     */
    @NotNull
    @JoinTable(name = "bgtCenterJoin", joinMultiLanguageTable = true, target = BgtCenter.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = BgtCenter.FIELD_CENTER_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long bgtCenterId;

    @Transient
    @JoinColumn(joinName = "bgtCenterJoin", field = BgtCenter.FIELD_DESCRIPTION)
    private String bgtCenterName;

    /**
     * 默认标志
     */
    @NotEmpty
    @Length(max = 1)
    private String defaultFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @Id
    @GeneratedValue
    private Long refId;

}
