package com.hand.hec.fnd.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_unit_dim_assign")
@Builder
@AllArgsConstructor
public class FndUnitDimAssign extends BaseDTO {

    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_DIMENSION_ID = "dimensionId";
    public static final String FIELD_DEFAULT_DIMENSION_VALUE_ID = "defaultDimensionValueId";


    @Where
    @Id
    @GeneratedValue
    private Long assignId;

    /**
     * 部门ID
     */
    private Long unitId;

    /**
     * 维度ID
     */
    @JoinTable(name = "dimensionJoin", joinMultiLanguageTable = true, target = FndDimension.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = FndDimension.FIELD_DIMENSION_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "dimensionJoin2", joinMultiLanguageTable = false, target = FndDimension.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = FndDimension.FIELD_DIMENSION_ID)})
    private Long dimensionId;

    @JoinColumn(joinName = "dimensionJoin", field = FndDimension.FIELD_DESCRIPTION)
    @Transient
    private String dimensionDesc;

    @JoinColumn(joinName = "dimensionJoin2", field = FndDimension.FIELD_DIMENSION_CODE)
    @Transient
    private String dimensionCode;

    /**
     * 默认维值ID
     */
    @JoinTable(name = "valueJoin", joinMultiLanguageTable = true, target = FndDimensionValue.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "valueJoin2", joinMultiLanguageTable = false, target = FndDimensionValue.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID)})
    private Long defaultDimensionValueId;


    @JoinColumn(joinName = "valueJoin", field = FndDimensionValue.FIELD_DESCRIPTION)
    @Transient
    private String defaultDimensionValueDesc;

    @JoinColumn(joinName = "valueJoin2", field = FndDimensionValue.FIELD_DIMENSION_VALUE_CODE)
    @Transient
    private String defaultDimensionValueCode;
}
