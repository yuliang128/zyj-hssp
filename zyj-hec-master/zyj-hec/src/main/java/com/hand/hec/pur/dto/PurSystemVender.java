package com.hand.hec.pur.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 系统级供应商主数据dto
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/18 14:46
 */

@Getter
@Setter
@Builder
@ToString
@MultiLanguage
@NoArgsConstructor
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "pur_system_vender")
public class PurSystemVender extends BaseDTO {

    public static final String FIELD_VENDER_ID = "venderId";
    public static final String FIELD_VENDER_CODE = "venderCode";
    public static final String FIELD_VENDER_TYPE_ID = "venderTypeId";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_ARTIFICIAL_PERSON = "artificialPerson";
    public static final String FIELD_TAX_ID_NUMBER = "taxIdNumber";
    public static final String FIELD_BANK_BRANCH_CODE = "bankBranchCode";
    public static final String FIELD_BANK_ACCOUNT_CODE = "bankAccountCode";
    public static final String FIELD_PAYMENT_TERM_ID = "paymentTermId";
    public static final String FIELD_PAYMENT_METHOD = "paymentMethod";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_TAX_TYPE_ID = "taxTypeId";
    public static final String FIELD_ENABLED_ITEM_FLAG = "enabledItemFlag";
    public static final String FIELD_APPROVED_VENDER_FLAG = "approvedVenderFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long venderId;

    /**
     * 供应商代码
     */
    @NotEmpty
    @Length(max = 30)
    private String venderCode;

    /**
     * 供应商类型ID
     */
    private Long venderTypeId;

    /**
     * 描述ID
     */
    @Length(max = 500)
    @MultiLanguageField
    private String description;

    /**
     * 地址
     */
    @Length(max = 2000)
    private String address;

    /**
     * 法人代表
     */
    @Length(max = 30)
    private String artificialPerson;

    /**
     * 税务登记号
     */
    @Length(max = 1000)
    private String taxIdNumber;

    /**
     * 开户银行
     */
    @Length(max = 2000)
    private String bankBranchCode;

    /**
     * 银行账号
     */
    @Length(max = 2000)
    private String bankAccountCode;

    /**
     * 付款条款ID
     */
    private Long paymentTermId;

    /**
     * 付款方式
     */
    @Length(max = 30)
    private String paymentMethod;

    /**
     * 币种
     */
    @Length(max = 30)
    private String currencyCode;

    /**
     * 税种
     */
    private Long taxTypeId;

    /**
     * 启用物料管理标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledItemFlag;

    /**
     * 合格供应商标志
     */
    @Length(max = 1)
    private String approvedVenderFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 供应商类型代码
     */
    @Transient
    private String venderTypeCode;

    /**
     * 供应商类型
     */
    @Transient
    private String venderTypeName;

    /**
     * 币种类型
     */
    @Transient
    private String currencyCodeName;

    /**
     * 税率类型
     */
    @Transient
    private String taxTypeName;

    /**
     * 付款方式
     */
    @Transient
    private String paymentMethodName;

    /**
     * 付款条款
     */
    @Transient
    private String paymentTermName;

    /**
     * 供应商ID
     */
    @Transient
    private Long accEntityId;

}
