package com.hand.hec.gld.dto;

import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
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
 * 子成本中心层级dto
 * </p>
 *
 * @author wuxiuxian 2019-01-10
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "gld_sob_resp_center_hierarchy")
public class GldSobRespCenterHierarchy extends BaseDTO {

    public static final String FIELD_HIERARCHY_ID = "hierarchyId";
    public static final String FIELD_PARENT_RESP_CENTER_ID = "parentRespCenterId";
    public static final String FIELD_RESPONSIBILITY_CENTER_ID = "responsibilityCenterId";
    public static final String FIELD_START_PERIOD_NAME = "startPeriodName";
    public static final String FIELD_END_PERIOD_NAME = "endPeriodName";


    @Id
    @GeneratedValue
    @Where
    private Long hierarchyId;

    /**
     * 父责任中心ID
     */
    @NotNull
    @Where
    private Long parentRespCenterId;

    /**
     * 子责任中心ID
     */
    @NotNull
    @Where
    @JoinTable(name = "responsibilityCenterJoin", joinMultiLanguageTable = true, target = GldSobRespCenter.class,
            type = JoinType.INNER, on = {@JoinOn(joinField = GldSobRespCenter.FIELD_RESPONSIBILITY_CENTER_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long responsibilityCenterId;

    /**
     * 子责任中心名称
     */
    @NotNull
    @JoinColumn(joinName = "responsibilityCenterJoin", field = GldSobRespCenter.FIELD_RESPONSIBILITY_CENTER_NAME)
    @Transient
    private String responsibilityCenterName;

    /**
     * 子责任中心代码
     */
    @NotNull
    @JoinColumn(joinName = "responsibilityCenterJoin", field = GldSobRespCenter.FIELD_RESPONSIBILITY_CENTER_CODE)
    @Transient
    private String responsibilityCenterCode;

    /**
     * 启用期间
     */
    @NotEmpty
    @Length(max = 30)
    private String startPeriodName;

    /**
     * 关闭期间
     */
    @Length(max = 30)
    private String endPeriodName;

}
