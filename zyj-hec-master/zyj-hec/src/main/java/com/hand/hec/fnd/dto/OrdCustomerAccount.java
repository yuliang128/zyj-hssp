package com.hand.hec.fnd.dto;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 客户银行账户表
 * </p>
 * 
 * @author guiyuting 2019/02/22 16:53
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "ord_customer_account")
public class OrdCustomerAccount extends BaseDTO {

    public static final String FIELD_ACCOUNT_ID = "accountId";
    public static final String FIELD_CUSTOMER_ID = "customerId";
    public static final String FIELD_BANK_ID = "bankId";
    public static final String FIELD_CNAPS_CODE = "cnapsCode";
    public static final String FIELD_CNAPS_NAME = "cnapsName";
    public static final String FIELD_PROVINCE_CODE = "provinceCode";
    public static final String FIELD_PROVINCE_NAME = "provinceName";
    public static final String FIELD_CITY_CODE = "cityCode";
    public static final String FIELD_CITY_NAME = "cityName";
    public static final String FIELD_ACCOUNT_NAME = "accountName";
    public static final String FIELD_ACCOUNT_NUMBER = "accountNumber";
    public static final String FIELD_NOTES = "notes";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long accountId;

    /**
     * 客户ID
     */
    @NotNull
    private Long customerId;

    /**
     * 银行ID
     */
    @NotNull
    private Long bankId;

    @Transient
    private String bankCode;

    @Transient
    private String bankName;

    /**
     * 联行号/SWIFT
     */
    @NotEmpty
    @Length(max = 50)
    private String cnapsCode;

    /**
     * 分行名称
     */
    @Transient
    private String cnapsName;

    /**
     * 省代码
     */
    @Length(max = 30)
    private String provinceCode;

    /**
     * 省名称
     */
    @Length(max = 200)
    private String provinceName;

    /**
     * 市代码
     */
    @Length(max = 30)
    private String cityCode;

    /**
     * 市名称
     */
    @Length(max = 200)
    private String cityName;

    /**
     * 银行户名
     */
    @Length(max = 200)
    private String accountName;

    /**
     * 银行帐号
     */
    @Length(max = 200)
    private String accountNumber;

    /**
     * 备注
     */
    @Length(max = 4000)
    private String notes;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;


    @Transient
    private Long accEntityId;


    @Transient
    @Length(max = 1)
    private String primaryFlag;

}
