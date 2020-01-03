package com.hand.hec.fnd.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * FndCountryCode
 * </p>
 *
 * @author guiyu 2019/01/29 15:28
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_country_code")
@MultiLanguage
public class FndCountryCode extends BaseDTO {

    public static final String FIELD_COUNTRY_CODE_ID = "countryCodeId";
    public static final String FIELD_COUNTRY_CODE = "countryCode";
    public static final String FIELD_DESCRIPTION = "description";


    @Id
    @GeneratedValue
    private Long countryCodeId;

    /**
     * 国家代码
     */
    @NotEmpty
    @Length(max = 30)
    private String countryCode;

    /**
     * 描述
     */
    @MultiLanguageField
    @Length(max = 500)
    private String description;

}
