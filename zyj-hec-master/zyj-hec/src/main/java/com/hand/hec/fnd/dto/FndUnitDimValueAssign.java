package com.hand.hec.fnd.dto;

import javax.persistence.*;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_unit_dim_value_assign")
@Builder
public class FndUnitDimValueAssign extends BaseDTO {

    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_DIM_ASSIGN_ID = "dimAssignId";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_DIMENSION_ID = "dimensionId";
    public static final String FIELD_DIMENSION_VALUE_ID = "dimensionValueId";
    public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
    public static final String FIELD_DIMENSION_VALUE_CODE = "dimensionValueCode";
    public static final String FIELD_DIMENSION_VALUE_DESC = "dimensionValueDesc";


    @Id
    @GeneratedValue
    private Long assignId;

    @NotNull
    private Long dimAssignId;

    /**
     * 部门ID
     */
    @NotNull
    private Long unitId;

    /**
     * 维度ID
     */
    @NotNull
    private Long dimensionId;

    /**
     * 维值ID
     */
    @NotNull
    private Long dimensionValueId;

    /**
     * 默认标志
     */
    @NotEmpty
    @Length(max = 1)
    private String defaultFlag;

    @Transient
    private String dimensionValueCode;

    @Transient
    private String dimensionValueDesc;


}
