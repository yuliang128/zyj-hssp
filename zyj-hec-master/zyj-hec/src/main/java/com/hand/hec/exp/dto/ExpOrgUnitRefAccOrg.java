package com.hand.hec.exp.dto;

import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hec.bgt.dto.BgtCenter;
import com.hand.hec.bgt.dto.BgtEntity;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.dto.GldResponsibilityCenter;
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
 * ExpOrgUnitRefAccOrg
 * </p>
 *
 * @author yang.duan 2019/01/10 11:16
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_org_unit_ref_acc_org")
public class ExpOrgUnitRefAccOrg extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_RESP_CENTER_ID = "respCenterId";
    public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
    public static final String FIELD_BGT_CENTER_ID = "bgtCenterId";
    public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_ACC_ENTITY_NAME = "accEntityName";
    public static final String FIELD_RESP_CENTER_NAME = "respCenterName";
    public static final String FIELD_BGT_ENTITY_NAME = "bgtEntityName";
    public static final String FIELD_BGT_CENTER_NAME = "bgtCenterName";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 部门id
     */
    @Where
    @NotNull
    private Long unitId;

    /**
     * 核算主体ID
     */
    @JoinTable(name = "accEntityJoin", joinMultiLanguageTable = true, target = GldAccountingEntity.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = GldAccountingEntity.FIELD_ACC_ENTITY_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @NotNull
    private Long accEntityId;

    @JoinColumn(joinName = "accEntityJoin", field = GldAccountingEntity.FIELD_ACC_ENTITY_NAME)
    @Transient
    private String accEntityName;

    /**
     * 责任中心ID
     */
    @JoinTable(name = "respCenterJoin", joinMultiLanguageTable = true, target = GldResponsibilityCenter.class,
                    type = JoinType.LEFT,
                    on = {@JoinOn(joinField = GldResponsibilityCenter.FIELD_RESPONSIBILITY_CENTER_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @NotNull
    private Long respCenterId;


    @Transient
    @JoinColumn(joinName = "respCenterJoin", field = GldResponsibilityCenter.FIELD_RESPONSIBILITY_CENTER_NAME)
    private String respCenterName;

    /**
     * 预算实体ID
     */
    @JoinTable(name = "bgtEntityJoin", joinMultiLanguageTable = true, target = BgtEntity.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = BgtEntity.FIELD_ENTITY_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long bgtEntityId;


    @Transient
    @JoinColumn(joinName = "bgtEntityJoin", field = BgtEntity.FIELD_DESCRIPTION)
    private String bgtEntityName;

    /**
     * 预算中心ID
     */
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

}
