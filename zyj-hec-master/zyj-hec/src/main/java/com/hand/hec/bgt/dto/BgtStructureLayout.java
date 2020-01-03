package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 预算表维度布局
 * </p>
 * 
 * @author mouse 2019/01/07 16:46
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_structure_layout")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtStructureLayout extends BaseDTO {

    public static final String FIELD_STRUCTURE_LAYOUT_ID = "structureLayoutId";
    public static final String FIELD_STRUCTURE_ID = "structureId";
    public static final String FIELD_DIMENSION_ID = "dimensionId";
    public static final String FIELD_LAYOUT_POSITION = "layoutPosition";
    public static final String FIELD_LAYOUT_PRIORITY = "layoutPriority";
    public static final String FIELD_DEFAULT_DIM_VALUE_ID = "defaultDimValueId";
    public static final String FIELD_DIMENSION_CODE = "dimensionCode";
    public static final String FIELD_DIMENSION_SEQUENCE = "dimensionSequence";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_DIMENSION_VALUE_CODE = "dimensionValueCode";
    public static final String FIELD_DIMENSION_VALUE_DESC = "dimensionValueDesc";


    public static final String POSITION_HEADER = "DOCUMENTS_HEAD";
    public static final String POSITION_LINE = "DOCUMENTS_LINE";

    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    private Long structureLayoutId;

    /**
     * 预算表ID
     */
    @NotNull
    private Long structureId;

    /**
     * 维度ID
     */
    @NotNull
    private Long dimensionId;

    /**
     * 布局位置
     */
    @NotEmpty
    @Length(max = 30)
    private String layoutPosition;

    /**
     * 布局顺序
     */
    private Long layoutPriority;

    /**
     * 默认维值
     */
    private Long defaultDimValueId;

    @Transient
    private String dimensionCode;

    @Transient
    private String dimensionSequence;

    @Transient
    private String description;

    @Transient
    private String dimensionValueCode;

    @Transient
    private String dimensionValueDesc;



}
