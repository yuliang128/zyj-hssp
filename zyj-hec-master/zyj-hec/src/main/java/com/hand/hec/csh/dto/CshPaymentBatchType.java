package com.hand.hec.csh.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Comparison;
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
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@MultiLanguage
@Table(name = "csh_payment_batch_type")
public class CshPaymentBatchType extends BaseDTO {

    public static final String FIELD_TYPE_ID = "typeId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_TYPE_CODE = "typeCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_AUDIT_METHOD = "auditMethod";
    public static final String FIELD_POSTING_METHOD = "postingMethod";
    public static final String FIELD_ACCOUNT_METHOD = "accountMethod";
    public static final String FIELD_ACCOUNT_FLAG = "accountFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    /**
     * 审核方式
     * 
     * @AUDIT_METHOD_AUTO_AUDIT 自审核
     * @AUDIT_METHOD_NEED_AUDIT 需审核
     */
    public static final String AUDIT_METHOD_AUTO_AUDIT = "AUTO_AUDIT";
    public static final String AUDIT_METHOD_NEED_AUDIT = "NEED_AUDIT";

    /**
     * 过账方式
     *
     * @POSTING_METHOD_AUTOMATIC 自动
     * @POSTING_METHOD_MANUAL 手动
     */
    public static final String POSTING_METHOD_AUTOMATIC = "AUTOMATIC";
    public static final String POSTING_METHOD_MANUAL = "MANUAL";

    /**
     * 凭证方式
     *
     * @ACCOUNT_METHOD_BATCH_HEADER 付款批头
     * @ACCOUNT_METHOD_BATCH_LINE 付款批行
     */
    public static final String ACCOUNT_METHOD_BATCH_HEADER = "BATCH_HEADER";
    public static final String ACCOUNT_METHOD_BATCH_LINE = "BATCH_LINE";


    @Where
    @Id
    @GeneratedValue
    private Long typeId;

    /**
     * 管理组织ID
     */
    @NotNull
    @Where(comparison = Comparison.EQUAL)
    private Long magOrgId;

    /**
     * 付款批类型代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where(comparison = Comparison.LIKE)
    private String typeCode;

    /**
     * 付款批类型描述
     */
    @Length(max = 500)
    @Where(comparison = Comparison.LIKE)
    @MultiLanguageField
    private String description;

    /**
     * 审核方式（SYSCODE：CSH_PAY_BATCH_TYPE_AUDIT_METHOD)
     */
    @Length(max = 30)
    private String auditMethod;

    /**
     * 过账方式（SYSCODE：CSH_PAY_BATCH_TYPE_POST_METHOD）
     */
    @Length(max = 30)
    private String postingMethod;

    /**
     * 凭证方式（SYSCODE：CSH_PAY_BATCH_TYPE_ACCOUNT_METHOD）
     */
    @Length(max = 30)
    private String accountMethod;

    /**
     * 合并凭证
     */
    @NotEmpty
    @Length(max = 1)
    private String accountFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
