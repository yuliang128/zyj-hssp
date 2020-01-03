package com.hand.hec.exp.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.fnd.dto.FndDimension;
import com.hand.hec.fnd.dto.FndDimensionValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * ExpMoRepEleRefLnDim
 * </p>
 *
 * @author yang.duan 2019/01/10 14:44
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_rep_ele_ref_ln_dim")
public class ExpMoRepEleRefLnDim extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_REP_PAGE_ELE_REF_ID = "repPageEleRefId";
    public static final String FIELD_DIMENSION_ID = "dimensionId";
    public static final String FIELD_LAYOUT_PRIORITY = "layoutPriority";
    public static final String FIELD_DEFAULT_DIM_VALUE_ID = "defaultDimValueId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    public static final String FIELD_DIMENSION_CODE = "dimensionCode";
    public static final String FIELD_DIMENSION_NAME = "dimensionName";

    public static final String FIELD_DEFAULT_DIM_VALUE_CODE = "defaultDimValueCode";
    public static final String FIELD_DEFAULT_DIM_VALUE_NAME = "defaultDimValueName";

    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级报销单类型下页面元素关联ID
     */
    @NotNull
    @Where
    private Long repPageEleRefId;

    /**
     * 维度ID
     */
    @NotNull
    @JoinTable(name = "dimensionJoin", target = FndDimension.class, type = JoinType.LEFT, joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimension.FIELD_DIMENSION_ID)})
    private Long dimensionId;

    /**
     * 维度Code
     *
     */
    @Transient
    @Where
    @JoinColumn(joinName = "dimensionJoin", field = FndDimension.FIELD_DIMENSION_CODE)
    private String dimensionCode;


    /**
     * 维度Name
     *
     */
    @Transient
    @Where
    @JoinColumn(joinName = "dimensionJoin", field = FndDimension.FIELD_DESCRIPTION)
    private String dimensionName;

    /*
     * 系统级别(维度)
     */
    @Transient
    @Where
    @JoinColumn(joinName = "dimensionJoin", field = FndDimension.FIELD_SYSTEM_LEVEL)
    private String systemLevel;

    /*
     * 公司级别(维度)
     */
    @Transient
    @Where
    @JoinColumn(joinName = "dimensionJoin", field = FndDimension.FIELD_COMPANY_LEVEL)
    private String companyLevel;

    /**
     * 布局顺序
     */
    @NotNull
    private Long layoutPriority;

    /**
     * 默认维值ID
     */
    @JoinTable(name = "dimValueJoin", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID)})
    private Long defaultDimValueId;

    /*
     * 默认维值Code
     */
    @Transient
    @Where
    @JoinColumn(joinName = "dimValueJoin", field = FndDimensionValue.FIELD_DIMENSION_VALUE_CODE)
    private String defaultDimValueCode;

    /*
     * 默认维值Name
     */
    @Transient
    @Where
    @JoinColumn(joinName = "dimValueJoin", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String defaultDimValueName;

    @Transient
    private Long dimensionSequence;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @Transient
    private String displayField;

    @Transient
    private String returnField;

    @Transient
    private String levelField;

}
