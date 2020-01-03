package com.hand.hec.exp.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.*;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.gld.dto.GldCurrency;
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
 * @date 2019/2/19 15:30
 * Description: 申请单类型定义DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_req_type")
@MultiLanguage
public class ExpMoReqType extends BaseDTO {

    public static final String FIELD_MO_EXP_REQ_TYPE_ID = "moExpReqTypeId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_MAG_ORG_CODE = "magOrgCode";
    public static final String FIELD_MAG_ORG_NAME = "magOrgName";
    public static final String FIELD_MO_EXP_REQ_TYPE_CODE = "moExpReqTypeCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_DOCUMENT_PAGE_TYPE = "documentPageType";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_CURRENCY_NAME = "currencyName";
    public static final String FIELD_ACCRUED_FLAG = "accruedFlag";
    public static final String FIELD_LINE_NUMBER_BEGINNING = "lineNumberBeginning";
    public static final String FIELD_STEP_LENGTH = "stepLength";
    public static final String FIELD_AUTO_APPROVE_FLAG = "autoApproveFlag";
    public static final String FIELD_AUTO_AUDIT_FLAG = "autoAuditFlag";
    public static final String FIELD_ONE_OFF_FLAG = "oneOffFlag";
    public static final String FIELD_TOLERANCE_FLAG = "toleranceFlag";
    public static final String FIELD_TOLERANCE_RANGE = "toleranceRange";
    public static final String FIELD_TOLERANCE_RANGE_NAME = "toleranceRangeName";
    public static final String FIELD_TOLERANCE_RATIO = "toleranceRatio";
    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_PAYEE_CATEGORY_NAME = "payeeCategoryName";
    public static final String FIELD_REPORT_NAME = "reportName";
    public static final String FIELD_RESERVE_BUDGET = "reserveBudget";
    public static final String FIELD_BUDGET_CONTROL_ENABLED = "budgetControlEnabled";
    public static final String FIELD_ICON = "icon";
    public static final String FIELD_CAPTION_HDS_ID = "captionHdsId";
    public static final String FIELD_CAPTION_HDS_NAME = "captionDescription";
    public static final String FIELD_APP_PAGE_CODE = "appPageCode";
    public static final String FIELD_APP_PAGE_NAME = "appPageName";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_SERVICE_NAME = "serviceName";
    public static final String FIELD_ICON_FILE_NAME = "iconFileName";

    @Id
    @GeneratedValue
    private Long moExpReqTypeId;

    /**
     * 组织架构ID
     */
    @NotNull
    @JoinTable(name = "FndMagOrgJoin", joinMultiLanguageTable = true, target = FndManagingOrganization.class, type = JoinType.LEFT, on = {@JoinOn(joinField = FndManagingOrganization.FIELD_MAG_ORG_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @Where
    private Long magOrgId;
    /**
     * 组织架构代码
     */
    @Transient
    @JoinColumn(joinName = "FndMagOrgJoin", field = FndManagingOrganization.FIELD_MAG_ORG_CODE)
    private String magOrgCode;
    /**
     * 组织架构名称
     */
    @Transient
    @JoinColumn(joinName = "FndMagOrgJoin", field = FndManagingOrganization.FIELD_DESCRIPTION)
    private String magOrgName;
    /**
     * 组织架构级申请单类型代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String moExpReqTypeCode;

    /**
     * 描述ID
     */
    @NotEmpty
    @Length(max = 500)
    @MultiLanguageField
    @Where
    private String description;

    /**
     * 单据页面类型
     */
    @NotEmpty
    @Length(max = 30)
    private String documentPageType;

    /**
     * 币种
     */
    @Length(max = 30)
    @JoinTable(name = "gldCurrencyJoin", joinMultiLanguageTable = false, target = GldCurrency.class, type = JoinType.LEFT, on = {@JoinOn(joinField = GldCurrency.FIELD_CURRENCY_CODE), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private String currencyCode;


    /**
     * 币种描述
     */
    @Transient
    @JoinColumn(joinName = "gldCurrencyJoin", field = GldCurrency.FIELD_CURRENCY_NAME)
    private String currencyName;
    /**
     * 弃用，增长标志
     */
    @Length(max = 1)
    private String accruedFlag;

    /**
     * 开始行号
     */
    private Long lineNumberBeginning;

    /**
     * 步长
     */
    private Long stepLength;

    /**
     * 自审批标志
     */
    @NotEmpty
    @Length(max = 1)
    private String autoApproveFlag;

    /**
     * 自审核标志
     */
    @NotEmpty
    @Length(max = 1)
    private String autoAuditFlag;

    /**
     * 一次性标志
     */
    @NotEmpty
    @Length(max = 1)
    private String oneOffFlag;

    /**
     * 容限控制标志
     */
    @NotEmpty
    @Length(max = 1)
    private String toleranceFlag;

    /**
     * 容限控制范围
     */
    @Length(max = 30)
    private String toleranceRange;

    /**
     * 容限控制范围名称
     */
    @Transient
    @JoinCode(code = "TOLERANCE_CONTROL_TYPE", joinKey = FIELD_TOLERANCE_RANGE)
    private String toleranceRangeName;
    /**
     * 容限控制比例
     */
    private Long toleranceRatio;

    /**
     * 收款方类别
     */
    @Length(max = 30)
    private String payeeCategory;
    /**
     * 收款方名称
     */
    @Transient
    @JoinCode(code = "PAYMENT_OBJECT", joinKey = FIELD_PAYEE_CATEGORY)
    private String payeeCategoryName;
    /**
     * 报表名称
     */
    @Length(max = 2000)
    private String reportName;


    /**
     * 预算占用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String reserveBudget;

    /**
     * 预算控制标志
     */
    @NotEmpty
    @Length(max = 1)
    private String budgetControlEnabled;

    /**
     * 图标（路径/CSS类）
     */
    @Length(max = 255)
    private String icon;

    /**
     * 填写说明ID
     */
    @JoinTable(name = "expMoWriteCaptionHdJoin", joinMultiLanguageTable = true, target = ExpMoWriteCaptionHd.class, type = JoinType.LEFT, on = {@JoinOn(joinField = ExpMoWriteCaptionHd.FIELD_CAPTION_HDS_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long captionHdsId;
    /**
     * 填写说明内容
     */
    @Transient
    @JoinColumn(joinName = "expMoWriteCaptionHdJoin",field = ExpMoWriteCaptionHd.FIELD_DESCRIPTION)
    private String captionDescription;
    /**
     * APP端页面类型(SYSCODE:APP_PAGE_TYPE)
     */
    @Length(max = 30)
    private String appPageCode;
    /**
     * APP端页面类型名称
     */
    @Transient
    @JoinCode(code = "APP_PAGE_TYPE", joinKey = FIELD_APP_PAGE_CODE)
    private String appPageName;
    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 图片路径地址
     */
    @Transient
    private String iconFileName;

    /**
     * 页面url
     */
    @Transient
    private String serviceName;

}
