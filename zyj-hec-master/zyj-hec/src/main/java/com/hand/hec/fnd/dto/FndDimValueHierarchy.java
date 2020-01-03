package com.hand.hec.fnd.dto;

import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 维值层次定义
 * </p>
 * 
 * @author guiyuting 2019/02/26 19:37
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_dim_value_hierarchy")
public class FndDimValueHierarchy extends BaseDTO {

    public static final String FIELD_HIERARCHY_ID = "hierarchyId";
    public static final String FIELD_PARENT_DIM_VALUE_ID = "parentDimValueId";
    public static final String FIELD_DIMENSION_VALUE_ID = "dimensionValueId";
    public static final String FIELD_DIMENSION_VALUE_CODE = "dimensionValueCode";
    public static final String FIELD_DESCRIPTION = "description";


    @Id
    @GeneratedValue
    private Long hierarchyId;

    /**
     * 父维值定义ID
     */
    @Where
    @NotNull
    private Long parentDimValueId;

    /**
     * 子维值定义ID
     */
    @Where
    @NotNull
    @JoinTable(name = "dimensionJoin", joinMultiLanguageTable = false, target = FndDimensionValue.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID)})
    @JoinTable(name = "dimensionJoin2", joinMultiLanguageTable = true, target = FndDimensionValue.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimensionValueId;

    @Transient
    @JoinColumn(joinName = "dimensionJoin", field = FndDimensionValue.FIELD_DIMENSION_VALUE_CODE)
    private String dimensionValueCode;

    @Transient
    @JoinColumn(joinName = "dimensionJoin2", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String description;

}
