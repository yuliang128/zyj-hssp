package com.hand.hec.acp.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.gld.dto.GldCurrency;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.*;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.exp.dto.ExpMoWriteCaptionHd;
import lombok.*;
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
 * 付款申请单类型实体类
 * </p>
 * 
 * @author guiyuting 2019/04/28 10:29
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "acp_mo_pay_req_type")
@MultiLanguage
public class AcpMoPayReqType extends BaseDTO {

    public static final String FIELD_MO_PAY_REQ_TYPE_ID = "moPayReqTypeId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_MO_PAY_REQ_TYPE_CODE = "moPayReqTypeCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_AUTO_APPROVE_FLAG = "autoApproveFlag";
    public static final String FIELD_AUTO_AUDIT_FLAG = "autoAuditFlag";
    public static final String FIELD_BUSINESS_FLAG = "businessFlag";
    public static final String FIELD_PAYMENT_TYPE_CODE = "paymentTypeCode";
    public static final String FIELD_REPORT_NAME = "reportName";
    public static final String FIELD_ICON = "icon";
    public static final String FIELD_CAPTION_HDS_ID = "captionHdsId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    // 付款类型
    public static final String PAYMENT_TYPE_SPORADIC = "SPORADIC"; // 零星付款
    public static final String PAYMENT_TYPE_REPORT = "REPORT";     // 报销单结算
    public static final String PAYMENT_TYPE_CONTRACT = "CONTRACT"; // 合同结算


    @Id
    @GeneratedValue
    private Long moPayReqTypeId;

    /**
     * 管理组织ID
     */
    @Where
    @NotNull
    private Long magOrgId;

    /**
     * 付款申请单类型代码
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String moPayReqTypeCode;

    @Transient
    private String moPayReqTypeName;

    /**
     * 付款申请单名称
     */
    @MultiLanguageField
    @Where
    @Length(max = 500)
    private String description;

    /**
     * 币种
     */
    @JoinTable(name = "currencyJoin", joinMultiLanguageTable = true, target = GldCurrency.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = GldCurrency.FIELD_COUNTRY_CODE),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @Length(max = 30)
    private String currencyCode;


    @Transient
    @JoinColumn(joinName = "currencyJoin", field = GldCurrency.FIELD_CURRENCY_NAME)
    private String currencyName;

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
     * 经营类标志
     */
    @NotEmpty
    @Length(max = 1)
    private String businessFlag;

    /**
     * 付款类型（SYSCODE：ACP_PAYMENT_TYPE_CODE）
     */
    @NotEmpty
    @Length(max = 30)
    private String paymentTypeCode;

    @Transient
    @JoinCode(code = "ACP_PAYMENT_TYPE_CODE", joinKey = AcpMoPayReqType.FIELD_PAYMENT_TYPE_CODE)
    private String paymentTypeName;

    /**
     * 报表名称
     */
    @Length(max = 2000)
    private String reportName;

    /**
     * 图标（路径/CSS类）
     */
    @Length(max = 255)
    private String icon;

    /**
     * 填写说明ID
     */
    @JoinTable(name = "captionJoin", joinMultiLanguageTable = true, target = ExpMoWriteCaptionHd.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = ExpMoWriteCaptionHd.FIELD_CAPTION_HDS_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long captionHdsId;

    @Transient
    @JoinColumn(joinName = "captionJoin", field = ExpMoWriteCaptionHd.FIELD_DESCRIPTION)
    private String captionDescription;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;

    @Transient
    private String iconFileName;

}
