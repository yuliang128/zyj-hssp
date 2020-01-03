package com.hand.hec.exp.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.function.dto.Resource;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
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
 * @date 2019/1/30 11:00
 * Description: 申请单页面配置dto
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_req_page_element")
@MultiLanguage
public class ExpReqPageElement extends BaseDTO {

    public static final String FIELD_REQ_PAGE_ELEMENT_ID = "reqPageElementId";
    public static final String FIELD_REQ_PAGE_ELEMENT_CODE = "reqPageElementCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_SERVICE_ID = "serviceId";
    public static final String FIELD_SERVICE_NAME = "serviceName";
    public static final String FIELD_SERVICE_TITLE = "serviceTitle";
    public static final String FIELD_READONLY_SERVICE_ID = "readonlyServiceId";
    public static final String FIELD_READONLY_SERVICE_NAME = "readonlyServiceName";
    public static final String FIELD_READONLY_SERVICE_TITLE = "readonlyServiceTitle";
    public static final String FIELD_SERVICE1_ID = "service1Id";
    public static final String FIELD_SERVICE2_ID = "service2Id";
    public static final String FIELD_SERVICE3_ID = "service3Id";
    public static final String FIELD_SERVICE4_ID = "service4Id";
    public static final String FIELD_SERVICE5_ID = "service5Id";
    public static final String FIELD_REPORT_TYPE_FLAG = "reportTypeFlag";
    public static final String FIELD_EXPENSE_OBJECT_FLAG = "expenseObjectFlag";
    public static final String FIELD_DIMENSION_FLAG = "dimensionFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_SYSTEM_FLAG = "systemFlag";


    @Id
    @GeneratedValue
    private Long reqPageElementId;

    /**
     * 页面元素代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String reqPageElementCode;

    /**
     * 描述ID
     */
    @NotEmpty
    @Length(max = 500)
    @Where
    @MultiLanguageField
    private String description;

    /**
     * 创建页面ID
     */
    @NotNull
    @JoinTable(name = "sysResourceJoin", joinMultiLanguageTable = true, target = Resource.class, type = JoinType.LEFT, on = {@JoinOn(joinField = Resource.FIELD_RESOURCE_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long serviceId;
    /**
     * 创建页面名称
     */
    @Transient
    @JoinColumn(joinName = "sysResourceJoin", field = Resource.FIELD_URL)
    private String serviceName;
    /**
     * 创建页面标题
     */
    @Transient
    @JoinColumn(joinName = "sysResourceJoin", field = Resource.FIELD_NAME)
    private String serviceTitle;


    /**
     * 只读页面ID
     */
    @JoinTable(name = "sysResourceReadonlyJoin", joinMultiLanguageTable = true, target = Resource.class, type = JoinType.LEFT, on = {@JoinOn(joinField = Resource.FIELD_RESOURCE_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long readonlyServiceId;

    /**
     * 创建页面名称
     */
    @Transient
    @JoinColumn(joinName = "sysResourceReadonlyJoin", field = Resource.FIELD_URL)
    private String readonlyServiceName;
    /**
     * 创建页面标题
     */
    @Transient
    @JoinColumn(joinName = "sysResourceReadonlyJoin", field = Resource.FIELD_NAME)
    private String readonlyServiceTitle;
    /**
     * 扩展页面1ID
     */
    private Long service1Id;

    /**
     * 扩展页面2ID
     */
    private Long service2Id;

    /**
     * 扩展页面3ID
     */
    private Long service3Id;

    /**
     * 扩展页面4ID
     */
    private Long service4Id;

    /**
     * 扩展页面5ID
     */
    private Long service5Id;

    /**
     * 报销类型标识
     */
    @NotEmpty
    @Length(max = 1)
    private String reportTypeFlag;

    /**
     * 费用对象标识
     */
    @NotEmpty
    @Length(max = 1)
    private String expenseObjectFlag;

    /**
     * 维度标识
     */
    @NotEmpty
    @Length(max = 1)
    private String dimensionFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 预置标志
     */
    @NotEmpty
    @Length(max = 1)
    private String systemFlag;

}
