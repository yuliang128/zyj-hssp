package com.hand.hec.exp.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.csh.dto.CshPaymentMethod;
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
 * ExpMoReportType
 * </p>
 *
 * @author yang.duan 2019/01/10 14:44
 */
@Getter
@Setter
@ToString
@MultiLanguage
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_report_type")
public class ExpMoReportType extends BaseDTO {

    public static final String FIELD_ADJUSTMENT_FLAG = "adjustmentFlag";
    public static final String FIELD_PAYMENT_FLAG = "paymentFlag";
    public static final String FIELD_REPORT_NAME = "reportName";
    public static final String FIELD_AMORTIZATION_FLAG = "amortizationFlag";
    public static final String FIELD_TEMPLATE_FLAG = "templateFlag";
    public static final String FIELD_RESERVE_BUDGET = "reserveBudget";
    public static final String FIELD_BUDGET_CONTROL_ENABLED = "budgetControlEnabled";
    public static final String FIELD_DATA_REVERSE_FLAG = "dataReverseFlag";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_DOCUMENT_TYPE = "documentType";
    public static final String FIELD_ICON = "icon";
    public static final String FIELD_CAPTION_HDS_ID = "captionHdsId";
    public static final String FIELD_APP_PAGE_CODE = "appPageCode";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_MO_EXP_REPORT_TYPE_ID = "moExpReportTypeId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_MO_EXP_REPORT_TYPE_CODE = "moExpReportTypeCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_DOCUMENT_PAGE_TYPE = "documentPageType";
    public static final String FIELD_MO_EXP_REQ_TYPE_ID = "moExpReqTypeId";
    public static final String FIELD_RELATION_MODE_CODE = "relationModeCode";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_CODING_RULE = "codingRule";
    public static final String FIELD_LINE_NUMBER_BEGINNING = "lineNumberBeginning";
    public static final String FIELD_STEP_LENGTH = "stepLength";
    public static final String FIELD_AUTO_APPROVE_FLAG = "autoApproveFlag";
    public static final String FIELD_AUTO_AUDIT_FLAG = "autoAuditFlag";
    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_REQ_REQUIRED_FLAG = "reqRequiredFlag";

    public static final String FIELD_PAYMENT_METHOD_NAME = "paymentMethodName";
    public static final String FIELD_MO_REQ_TYPE_CODE = "moExpReqTypeCode";
    public static final String FIELD_MO_REQ_TYPE_NAME = "moExpReqTypeName";


    /**
     * 调整类报销单标志
     */
    @NotEmpty
    @Length(max = 1)
    private String adjustmentFlag;

    /**
     * 是否需要支付标志
     */
    @NotEmpty
    @Length(max = 1)
    private String paymentFlag;

    /**
     * 报表名称
     */
    @Length(max = 2000)
    private String reportName;

    /**
     * 摊销标志
     */
    @NotEmpty
    @Length(max = 1)
    private String amortizationFlag;

    /**
     * 摊销模板标志
     */
    @NotEmpty
    @Length(max = 1)
    private String templateFlag;

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
     * 数据反转标志
     */
    @NotEmpty
    @Length(max = 1)
    private String dataReverseFlag;

    /**
     * 付款方式
     */
    @JoinTable(name = "paymentMethodJoin", type = JoinType.LEFT, joinMultiLanguageTable = true,
                    target = CshPaymentMethod.class,
                    on = {@JoinOn(joinField = CshPaymentMethod.FIELD_PAYMENT_METHOD_ID)})
    private Long paymentMethodId;

    /*
     * 付款方式描述
     */
    @Transient
    @JoinColumn(joinName = "paymentMethodJoin", field = CshPaymentMethod.FIELD_DESCRIPTION)
    @Where
    private String paymentMethodName;

    /**
     * 单据类型
     */
    @Length(max = 100)
    private String documentType;

    /**
     * 图标（路径/CSS类）
     */
    @Length(max = 255)
    private String icon;

    /**
     * 填写说明ID
     */
    private Long captionHdsId;

    /**
     * APP端页面类型(SYSCODE:APP_PAGE_TYPE)
     */
    @Length(max = 30)
    private String appPageCode;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @Id
    @GeneratedValue
    private Long moExpReportTypeId;

    /**
     * 组织架构ID
     */
    @NotNull
    @Where
    private Long magOrgId;

    /**
     * 组织架构级报销单代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String moExpReportTypeCode;

    /**
     * 描述
     */
    @NotEmpty
    @Length(max = 500)
    @Where
    @MultiLanguageField
    private String description;

    /**
     * 单据页面类型
     */
    @NotEmpty
    @Length(max = 30)
    private String documentPageType;

    /**
     * 组织架构级申请单类型ID
     */
    @JoinTable(name = "expReqTypeJoin", target = ExpMoReqType.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true, on = {@JoinOn(joinField = ExpMoReqType.FIELD_MO_EXP_REQ_TYPE_ID)})
    private Long moExpReqTypeId;

    /*
     * 组织架构级申请单类型Code
     */
    @Transient
    @JoinColumn(joinName="expReqTypeJoin",field = ExpMoReqType.FIELD_MO_EXP_REQ_TYPE_CODE)
    private String moExpReqTypeCode;

    /*
     * 组织架构级申请单类型Name
     */
    @Transient
    @JoinColumn(joinName="expReqTypeJoin",field = ExpMoReqType.FIELD_DESCRIPTION)
    private String moExpReqTypeName;

    /**
     * 关联方式（SYSCODE：EXP_MO_REPORT_TYPE_RELATION_MODE）
     */
    @Length(max = 30)
    private String relationModeCode;

    /**
     * 币种
     */
    @Length(max = 30)
    private String currencyCode;

    /**
     * 编码规则
     */
    @Length(max = 30)
    private String codingRule;

    /**
     * 起始值
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
     * 收款方类别
     */
    @NotEmpty
    @Length(max = 30)
    private String payeeCategory;

    /**
     * 必须从申请单创建
     */
    @NotEmpty
    @Length(max = 1)
    private String reqRequiredFlag;

    /*
     * 币种描述
     */
    @Transient
    private String currencyName;


    /*
     * 单据类型对应头页面路径
     */
    @Transient
    private String serviceName;

}
