package com.hand.hec.vat.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "vat_invoice")
public class VatInvoice extends BaseDTO {

    public static final String FIELD_INVOICE_ID = "invoiceId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_INVOICE_CODE = "invoiceCode";
    public static final String FIELD_INVOICE_NUMBER = "invoiceNumber";
    public static final String FIELD_INVOICE_DATE = "invoiceDate";
    public static final String FIELD_INVOICE_DISTRECT = "invoiceDistrect";
    public static final String FIELD_MACHINE_NUMBER = "machineNumber";
    public static final String FIELD_CHECK_CODE = "checkCode";
    public static final String FIELD_CHECK_CODE_LAST_CHARS = "checkCodeLastChars";
    public static final String FIELD_PURCHASER_NAME = "purchaserName";
    public static final String FIELD_PURCHASER_TAX_NUMBER = "purchaserTaxNumber";
    public static final String FIELD_PURCHASER_ADDRESS_PHONE = "purchaserAddressPhone";
    public static final String FIELD_PURCHASER_BANK_ACCOUNT = "purchaserBankAccount";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_AMOUNT_ZHS = "amountZhs";
    public static final String FIELD_TAX_AMOUNT = "taxAmount";
    public static final String FIELD_WITHOUT_TAX_AMOUNT = "withoutTaxAmount";
    public static final String FIELD_SELLER_NAME = "sellerName";
    public static final String FIELD_SELLER_TAX_NUMBER = "sellerTaxNumber";
    public static final String FIELD_SELLER_ADDRESS_PHONE = "sellerAddressPhone";
    public static final String FIELD_SELLER_BANK_ACCOUNT = "sellerBankAccount";
    public static final String FIELD_INVOICE_TYPE = "invoiceType";
    public static final String FIELD_INVOICE_SOURCE = "invoiceSource";
    public static final String FIELD_AUTHENTICATION_STATUS = "authenticationStatus";
    public static final String FIELD_AUTHENTICATION_DATE = "authenticationDate";
    public static final String FIELD_INVOICE_CATEGORY_ID = "invoiceCategoryId";
    public static final String FIELD_CONFIRM_FLAG = "confirmFlag";
    public static final String FIELD_CONFIRM_DATE = "confirmDate";
    public static final String FIELD_INVOICE_STATUS = "invoiceStatus";
    public static final String FIELD_DESCRIPTION = "description";


    @Id
    @GeneratedValue
    private Long invoiceId;

    /**
     * 员工
     */
    @NotNull
    private Long employeeId;

    /**
     * 核算主体
     */
    private Long accEntityId;

    /**
     * 发票代码
     */
    @Length(max = 200)
    private String invoiceCode;

    /**
     * 发票号
     */
    @Length(max = 200)
    private String invoiceNumber;

    /**
     * 开票日期
     */
    private Date invoiceDate;

    /**
     * 发票地区
     */
    @Length(max = 200)
    private String invoiceDistrect;

    /**
     * 机器号
     */
    @Length(max = 200)
    private String machineNumber;

    /**
     * 校验码
     */
    @Length(max = 200)
    private String checkCode;

    /**
     * 校验码后六位
     */
    @Length(max = 30)
    private String checkCodeLastChars;

    /**
     * 购买方名称
     */
    @Length(max = 2000)
    private String purchaserName;

    /**
     * 购买方税号
     */
    @Length(max = 200)
    private String purchaserTaxNumber;

    /**
     * 购买方地址和电话
     */
    @Length(max = 2000)
    private String purchaserAddressPhone;

    /**
     * 购买方银行账号
     */
    @Length(max = 200)
    private String purchaserBankAccount;

    /**
     * 金额
     */
    @NotNull
    private Long amount;

    /**
     * 金额大写
     */
    @NotEmpty
    @Length(max = 200)
    private String amountZhs;

    /**
     * 税金
     */
    private Long taxAmount;

    /**
     * 不含税金额
     */
    private Long withoutTaxAmount;

    /**
     * 销售方名称
     */
    @Length(max = 2000)
    private String sellerName;

    /**
     * 销售方税号
     */
    @Length(max = 200)
    private String sellerTaxNumber;

    /**
     * 销售方地址和电话
     */
    @Length(max = 2000)
    private String sellerAddressPhone;

    /**
     * 销售方银行账号
     */
    @Length(max = 200)
    private String sellerBankAccount;

    /**
     * 发票类型，SYSCODE：VAT_INVOICE_TYPE
     */
    @Length(max = 30)
    private String invoiceType;

    /**
     * 发票来源，SYSCODE：VAT_INVOICE_SOURCE
     */
    @NotEmpty
    @Length(max = 30)
    private String invoiceSource;

    /**
     * 验证状态，SYSCODE：VAT_INVOICE_AUTH_STATUS
     */
    @Length(max = 30)
    private String authenticationStatus;

    /**
     * 发票认证日期
     */
    private Date authenticationDate;

    /**
     * 发票种类
     */
    private Long invoiceCategoryId;

    /**
     * 发票确认标识
     */
    @NotEmpty
    @Length(max = 1)
    private String confirmFlag;

    /**
     * 发票确认日期
     */
    private Date confirmDate;

    /**
     * 发票状态，SYSCODE：VAT_INVOICE_STATUS
     */
    @NotEmpty
    @Length(max = 30)
    private String invoiceStatus;

    /**
     * 发票描述
     */
    @Length(max = 2000)
    private String description;

    @Transient
    private BigDecimal biz2payExchangeRate;

    @Transient
    private Long invoiceLineId;

    @Transient
    private BigDecimal invoiceAmount;

    @Transient
    private BigDecimal businessAmount;

    @Transient
    private BigDecimal paymentAmount;

    @Transient
    private BigDecimal pay2funExchangeRate;

    @Transient
    private BigDecimal functionalAmount;

    @Transient
    private String relationFlag;

    @Transient
    private Long taxTypeId;

    @Transient
    private Long relationLnsId;

}
