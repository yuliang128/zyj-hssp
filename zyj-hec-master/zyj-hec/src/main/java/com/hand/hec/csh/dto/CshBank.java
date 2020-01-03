package com.hand.hec.csh.dto;

import javax.persistence.*;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.JoinCode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>
 * CshBank
 * </p>
 *
 * @author guiyu 2019/01/29 14:20
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_bank")
@MultiLanguage
public class CshBank extends BaseDTO {

    public static final String FIELD_BANK_ID = "bankId";
    public static final String FIELD_BANK_CODE = "bankCode";
    public static final String FIELD_BANK_NAME = "bankName";
    public static final String FIELD_BANK_NAME_ALT = "bankNameAlt";
    public static final String FIELD_COUNTRY_CODE = "countryCode";
    public static final String FIELD_BANK_TYPE = "bankType";
    public static final String FIELD_BANK_TYPE_DESC = "bankTypeDesc";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_ZERO_AMOUNTS_ALLOWED = "zeroAmountsAllowed";


    @Id
    @GeneratedValue
    private Long bankId;

    /**
     * 银行代码
     */
    @NotEmpty
    @Length(max = 30)
    private String bankCode;

    /**
     * 银行名称
     */
    @MultiLanguageField
    @Length(max = 500)
    private String bankName;

    /**
     * 银行简称
     */
    @MultiLanguageField
    @Length(max = 500)
    private String bankNameAlt;

    /**
     * 国家
     */
    @Length(max = 30)
    private String countryCode;

    @Transient
    private String countryName;

    /**
     * 银行类型（CASH：现金银行、CLEARING：清算银行、INTERNAL：内部银行、NORMAL：一般银行）
     */
    @NotEmpty
    @Length(max = 30)
    private String bankType;

    @JoinCode(code = "CSH.BANK_TYPE", joinKey = FIELD_BANK_TYPE)
    @Transient
    private String bankTypeDesc;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 允许零付款
     */
    @NotEmpty
    @Length(max = 1)
    private String zeroAmountsAllowed;

    @Transient
    private List<CshCnaps> cnaps;

}
