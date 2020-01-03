package com.hand.hec.pur.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 供应商银行账户dto
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/21 14:25
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "pur_vender_account")
public class PurVenderAccount extends BaseDTO {

    public static final String FIELD_ACCOUNT_ID = "accountId";
    public static final String FIELD_VENDER_ID = "venderId";
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
     * 供应商ID
     */
    @NotNull
    private Long venderId;

    /**
     * 银行ID
     */
    @NotNull
    private Long bankId;

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

    /**
     * 银行代码
     */
    @Transient
    private String bankCode;

    /**
     * 银行名称
     */
    @Transient
    private String bankName;

    /**
     * 核算主体ID
     */
    @Transient
    private Long accEntityId;

    /**
     * 主账号标志
     */
    @Transient
    private String primaryFlag;
}
