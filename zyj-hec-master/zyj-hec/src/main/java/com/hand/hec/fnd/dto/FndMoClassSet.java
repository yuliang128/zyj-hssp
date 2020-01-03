package com.hand.hec.fnd.dto;

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

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 *
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/04/23 19:22
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_mo_class_set")
@MultiLanguage
public class FndMoClassSet extends BaseDTO {

    public static final String FIELD_MO_CLASS_SET_ID = "moClassSetId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_CLASS_SET_CODE = "classSetCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_LEVEL_DEPTH = "levelDepth";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long moClassSetId;

    /**
     * 管理组织ID
     */
    @NotNull
    private Long magOrgId;

    /**
     * 分类集代码
     */
    @NotEmpty
    @Length(max = 30)
    private String classSetCode;

    /**
     * 分类集描述
     */
    @Length(max = 500)
    @MultiLanguageField
    private String description;

    /**
     * 分类层级数 syscode(FND_MO_CLASS_SETS_LEVEL_DEPTH)
     */
    @NotEmpty
    @Length(max = 1)
    private String levelDepth;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @Transient
    private String codeName;

}
