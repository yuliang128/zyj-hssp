package com.hand.hec.exp.dto;

import com.hand.hap.core.BaseConstants;
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
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/2/22 14:10
 * Description:申请单类型页面元素行分配维度
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_req_ele_ref_ln_dim")
public class ExpMoReqEleRefLnDim extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_REQ_PAGE_ELE_REF_ID = "reqPageEleRefId";
    public static final String FIELD_DIMENSION_ID = "dimensionId";
    public static final String FIELD_DIMENSION_CODE = "dimensionCode";
    public static final String FIELD_DIMENSION_NAME = "dimensionName";
    public static final String FIELD_LAYOUT_PRIORITY = "layoutPriority";
    public static final String FIELD_DEFAULT_DIM_VALUE_ID = "defaultDimValueId";
    public static final String FIELD_DEFAULT_DIM_VALUE_CODE = "defaultDimValueCode";
    public static final String FIELD_DEFAULT_DIM_VALUE_NAME = "defaultDimValueName";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级报销单类型下页面元素关联ID
     */
    @NotNull
    @Where
    private Long reqPageEleRefId;

    /**
     * 维度ID
     */
    @NotNull
    @JoinTable(name = "FndDimensionJoin", joinMultiLanguageTable = true, target = FndDimension.class, type = JoinType.LEFT, on = {@JoinOn(joinField = FndDimension.FIELD_DIMENSION_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimensionId;

    /**
     * 维度代码
     */
    @Transient
    @JoinColumn(joinName = "FndDimensionJoin", field = FndDimension.FIELD_DIMENSION_CODE)
    private String dimensionCode;

    /**
     * 维度名称
     */
    @Transient
    @JoinColumn(joinName = "FndDimensionJoin", field = FndDimension.FIELD_DESCRIPTION)
    private String dimensionName;
    /**
     * 布局顺序
     */
    @NotNull
    private Long layoutPriority;

    /**
     * 默认维值ID
     */
    @JoinTable(name = "FndDimensionValueJoin", joinMultiLanguageTable = true, target = FndDimensionValue.class, type = JoinType.LEFT, on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long defaultDimValueId;
    /**
     * 默认维值代码
     */
    @Transient
    @JoinColumn(joinName = "FndDimensionValueJoin", field = FndDimensionValue.FIELD_DIMENSION_VALUE_CODE)
    private String defaultDimValueCode;

    /**
     * 默认维度名称
     */
    @Transient
    @JoinColumn(joinName = "FndDimensionValueJoin", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String defaultDimValueName;
    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
