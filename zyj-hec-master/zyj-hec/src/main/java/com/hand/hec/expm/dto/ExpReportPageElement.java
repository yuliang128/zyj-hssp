package com.hand.hec.expm.dto;

import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.function.dto.Resource;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hec.exp.dto.ExpReqPageElement;
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
 * 费用报销单页面元素dto
 * </p>
 *
 * @author yang.duan 2019/01/10 15:00
 * @author xiuxian.wu 2019/01/19 25:25
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@MultiLanguage
@Table(name = "exp_report_page_element")
public class ExpReportPageElement extends BaseDTO {

    public static final String FIELD_SERVICE3_ID = "service3Id";
    public static final String FIELD_SERVICE4_ID = "service4Id";
    public static final String FIELD_SERVICE5_ID = "service5Id";
    public static final String FIELD_REPORT_TYPE_FLAG = "reportTypeFlag";
    public static final String FIELD_EXPENSE_OBJECT_FLAG = "expenseObjectFlag";
    public static final String FIELD_DIMENSION_FLAG = "dimensionFlag";
    public static final String FIELD_INVOICE_FLAG = "invoiceFlag";
    public static final String FIELD_TAX_FLAG = "taxFlag";
    public static final String FIELD_MORE_INVOICE_FLAG = "moreInvoiceFlag";
    public static final String FIELD_MORE_TAX_FLAG = "moreTaxFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_SYSTEM_FLAG = "systemFlag";
    public static final String FIELD_REPORT_PAGE_ELEMENT_ID = "reportPageElementId";
    public static final String FIELD_REPORT_PAGE_ELEMENT_CODE = "reportPageElementCode";
    public static final String FIELD_REQ_PAGE_ELEMENT_ID = "reqPageElementId";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_SERVICE_ID = "serviceId";
    public static final String FIELD_READONLY_SERVICE_ID = "readonlyServiceId";
    public static final String FIELD_SERVICE1_ID = "service1Id";
    public static final String FIELD_SERVICE2_ID = "service2Id";


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
     * 发票标识
     */
    @NotEmpty
    @Length(max = 1)
    private String invoiceFlag;

    /**
     * 税额标识
     */
    @NotEmpty
    @Length(max = 1)
    private String taxFlag;

    /**
     * 更多发票标识
     */
    @NotEmpty
    @Length(max = 1)
    private String moreInvoiceFlag;

    /**
     * 更多税额标识
     */
    @NotEmpty
    @Length(max = 1)
    private String moreTaxFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;

    /**
     * 预置标志
     */
    @NotEmpty
    @Length(max = 1)
    private String systemFlag;

    @Id
    @GeneratedValue
    @Where
    private Long reportPageElementId;

    /**
     * 报销单页面元素代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String reportPageElementCode;

    /**
     * 关联申请单页面元素ID
     */
    @JoinTable(name = "reportPageElementJoin", type = JoinType.LEFT, target = ExpReqPageElement.class, joinMultiLanguageTable = true, on = {@JoinOn(joinField = ExpReqPageElement.FIELD_REQ_PAGE_ELEMENT_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long reqPageElementId;
    /**
     * 申请单描述
     */
    @Transient
    @JoinColumn(joinName = "reportPageElementJoin", field = ExpReqPageElement.FIELD_DESCRIPTION)
    private String reqPageElementName;

    /**
     * 描述
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
    @Where
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
    @Where
    @JoinTable(name = "sysResourceJoinRe", joinMultiLanguageTable = true, target = Resource.class, type = JoinType.LEFT, on = {@JoinOn(joinField = Resource.FIELD_RESOURCE_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long readonlyServiceId;
    /**
     * 只读页面名称
     */
    @Transient
    @JoinColumn(joinName = "sysResourceJoinRe", field = Resource.FIELD_URL)
    private String readonlyServiceName;
    /**
     * 只读页面标题
     */
    @Transient
    @JoinColumn(joinName = "sysResourceJoinRe", field = Resource.FIELD_NAME)
    private String readonlyServiceTitle;

    /**
     * 扩展页面1ID
     */
    private Long service1Id;

    /**
     * 扩展页面2ID
     */
    private Long service2Id;

}
