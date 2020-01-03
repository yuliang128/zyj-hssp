package com.hand.hap.fnd.dto;

import javax.persistence.*;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 管理组织dto
 * </p>
 *
 * @author MouseZhou 2019/01/04 18:41
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fnd_managing_organization")
@ExtensionAttribute(disable = true)
@MultiLanguage
@Builder
public class FndManagingOrganization extends BaseDTO {

    public static final  String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_MAG_ORG_CODE = "magOrgCode";
    public static final String FIELD_MANAGING_CURRENCY_CODE = "managingCurrencyCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_CURRENCY_NAME = "managingCurrencyName";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
    public static final String FIELD_SET_OF_BOOKS_NAME = "setOfBooksName";
    public static final String FIELD_MAG_ORG_CODE_NAME = "magOrgCodeName";
    public static final String FIELD_MAG_ORG_NAME = "magOrgName";


    @Id
    @GeneratedValue
    @Where
    private Long magOrgId;

    @NotEmpty
    @OrderBy
    @Where
    private String magOrgCode;

    @Where
    @MultiLanguageField
    private String description;

    @NotEmpty
    private String managingCurrencyCode;

    @Transient
    private String managingCurrencyName;

    @Transient
    private String defaultBgtOrgName;

    @Transient
    private String setOfBooksName;

    private String enabledFlag;

    @Transient
    private String magOrgCodeName;

    @Transient
    private Long bgtRefId;

    @Transient
    private Long bgtOrgId;

    @Transient
    private Long sobRefId;

    @Transient
    private Long setOfBooksId;

    @Transient
    private String magOrgName;

    @Transient
    private String parameterValue;

    @Transient
    private String encryptValueFlag;
}
