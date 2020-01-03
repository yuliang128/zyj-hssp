package com.hand.hec.csh.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
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
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_mo_payment_req_type")
@MultiLanguage
public class CshMoPaymentReqType extends BaseDTO {

    public static final String FIELD_MO_PAYMENT_REQ_TYPE_ID = "moPaymentReqTypeId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_MO_PAYMENT_REQ_TYPE_CODE = "moPaymentReqTypeCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_AUTO_APPROVE_FLAG = "autoApproveFlag";
    public static final String FIELD_AUTO_AUDIT_FLAG = "autoAuditFlag";
    public static final String FIELD_REPORT_NAME = "reportName";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_ICON = "icon";
    public static final String FIELD_CAPTION_HDS_ID = "captionHdsId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    @Where
    private Long moPaymentReqTypeId;

    /**
     * 管理组织ID
     */
    @NotNull
    @Where
    private Long magOrgId;

    /**
     * 管理组织级借款单类型代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String moPaymentReqTypeCode;

    /**
     * 描述ID
     */
    @NotEmpty
    @Length(max = 500)
    @MultiLanguageField
    @Where
    private String description;

    /**
     * 币种
     */
    @Length(max = 30)
    @Where
    private String currencyCode;

    /**
     * 自审批标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String autoApproveFlag;

    /**
     * 自审核标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String autoAuditFlag;

    /**
     * 报表名称
     */
    @Length(max = 2000)
    @Where
    private String reportName;

    /**
     * 付款方式ID
     */
    @Where
    private Long paymentMethodId;

    /**
     * 图标（路径/CSS类）
     */
    @Length(max = 255)
    @Where
    private String icon;

    /**
     * 填写说明ID
     */
    @Where
    private Long captionHdsId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;

	/**
	 * 图标名称（路径/CSS类）
	 */
	@Transient
	private String iconFileName;

}
