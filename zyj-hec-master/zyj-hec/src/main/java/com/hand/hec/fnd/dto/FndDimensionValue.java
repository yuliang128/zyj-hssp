package com.hand.hec.fnd.dto;

import javax.persistence.*;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.*;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinTable;
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
 * 系统级维值定义
 * </p>
 * 
 * @author guiyuting 2019/02/27 10:32
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_dimension_value")
@MultiLanguage
public class FndDimensionValue extends BaseDTO {

    public static final String FIELD_DIMENSION_VALUE_ID = "dimensionValueId";
    public static final String FIELD_DIMENSION_ID = "dimensionId";
    public static final String FIELD_DIMENSION_VALUE_CODE = "dimensionValueCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_SUMMARY_FLAG = "summaryFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_DIMENSION_CODE = "dimensionCode";
    public static final String FIELD_DIMENSION_SYSTEM_LEVEL = "systemLevel";
    public static final String FIELD_DIMENSION_SYSTEM_LEVEL_NAME = "systemLevelName";


    @Where
    @Id
    @GeneratedValue
    private Long dimensionValueId;

    /**
     * 维度定义ID
     */
    @Where
    @NotNull
    @JoinTable(name = "dimensionJoin", joinMultiLanguageTable = false, target = FndDimension.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = FndDimension.FIELD_DIMENSION_ID)})
    private Long dimensionId;

    @Transient
    @JoinColumn(joinName = "dimensionJoin", field = FndDimension.FIELD_DIMENSION_CODE)
    private String dimensionCode;

    /**
     * 维值代码
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String dimensionValueCode;

    /**
     * 维值描述ID
     */
    @Where
    @MultiLanguageField
    @Length(max = 500)
    private String description;

    /**
     * 汇总标志
     */
    @Where
    @NotEmpty
    @Length(max = 1)
    private String summaryFlag;

    /**
     * 启用标志
     */
    @Where
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;


    @Transient
    @JoinColumn(joinName = "dimensionJoin", field = FndDimension.FIELD_SYSTEM_LEVEL)
    private String systemLevel;

    @Transient
    @JoinCode(code = "SYS_ORGANIZATION_TYPE",joinKey = FndDimension.FIELD_SYSTEM_LEVEL)
    private String systemLevelName;

}
