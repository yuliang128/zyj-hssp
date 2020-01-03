package com.hand.hec.fnd.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * GldAccount
 * </p>
 * 
 * @author guiyu 2019/01/08 15:31
 */
@ExtensionAttribute(disable = true)
@Table(name = "gld_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@MultiLanguage
public class GldAccount extends BaseDTO {

    public static final String FIELD_ACCOUNT_SET_ID = "accountSetId";
    public static final String FIELD_ACCOUNT_ID = "accountId";
    public static final String FIELD_ACCOUNT_CODE = "accountCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ACCOUNT_TYPE = "accountType";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_SUMMARY_FLAG = "summaryFlag";
    public static final String FIELD_ACCOUNT_CODE_FROM = "accountCodeFrom";
    public static final String FIELD_ACCOUNT_CODE_TO = "accountCodeTo";


    /**
     * 科目表ID
     */
    @Where
    @NotNull
    private Long accountSetId;

    @Where
    @Id
    @GeneratedValue
    private Long accountId;

    /**
     * 科目代码
     */
    @OrderBy("ASC")
    @Where
    @NotEmpty
    @Length(max = 100)
    private String accountCode;

    /**
     * 描述
     */
    @Where
    @MultiLanguageField
    @Length(max = 1000)
    private String description;

    /**
     * 类型
     */
    @NotEmpty
    @Length(max = 1)
    private String accountType;

    /**
     * 启用标志
     */
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 汇总
     */
    @Where
    @Length(max = 1)
    private String summaryFlag;

    @Transient
    @Where(expression = "account_code <= #{accountCodeTo}")
    private String accountCodeTo;

    @Transient
    @Where(expression = "account_code >= #{accountCodeFrom}")
    private String accountCodeFrom;
}
