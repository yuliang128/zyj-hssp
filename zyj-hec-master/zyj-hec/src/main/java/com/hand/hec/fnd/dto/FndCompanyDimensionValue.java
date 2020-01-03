package com.hand.hec.fnd.dto;

/**
 * <p>
 * 公司级维值定义
 * </p>
 * 
 * @author guiyuting 2019/02/27 9:58
 */
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@ExtensionAttribute(disable = true)
@Table(name = "fnd_company_dimension_value")
public class FndCompanyDimensionValue extends BaseDTO {

    public static final String FIELD_COMPANY_DIM_VALUE_ID = "companyDimValueId";
    public static final String FIELD_COMPANY_LEVEL = "companyLevel";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_DIMENSION_VALUE_ID = "dimensionValueId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_SOURCE_ID = "sourceId";
    public static final String FIELD_SOURCE_CODE = "sourceCode";
    public static final String FIELD_SOURCE_NAME = "sourceName";
    public static final String  FIELD_SYSTEM_LEVEL= "systemLevel";
    public static final String FIELD_DIMENSION_VALUE_CODE = "dimensionValueCode";
    public static final String FIELD_DIMENSION_VALUE_DESCRIPTION = "dimensionValueDescription";
    public static final String FIELD_DIMENSION_ID = "dimensionId";
    public static final String FIELD_DIMENSION_CODE= "dimensionCode";
    public static final String FIELD_SUMMARY_FLAG = "summaryFlag";


    @Id
    @GeneratedValue
    private Long companyDimValueId;

    /**
     * 公司级类型（SYSCODE：SYS_ORGANIZATION_TYPE）
     */
    @NotEmpty
    @Length(max = 30)
    private String companyLevel;

    /**
     * 值ID（管理公司ID/预算实体ID/核算主体ID）
     */
    @NotNull
    private Long companyId;

    /**
     * 系统级维值定义ID
     */
    private Long dimensionValueId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @Transient
    private Long sourceId;

    @Transient
    private String sourceCode;

    @Transient
    private String sourceName;

    @Transient
    private String systemLevel;

    @Transient
    private String summaryFlag;

    @Transient
    private Long dimensionId;

    @Transient
    private String dimensionCode;

    @Transient
    private String dimensionValueCode;

    @Transient
    private String dimensionValueDescription;

}
