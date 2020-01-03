package com.hand.hec.fnd.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
 * @author jialin.xing@hand-china.com 2019/04/24 15:59
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_mo_class")
@MultiLanguage
public class FndMoClass extends BaseDTO {

    public static final String FIELD_CLASS_ID = "classId";
    public static final String FIELD_MO_CLASS_SET_ID = "moClassSetId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_CLASS_CODE = "classCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CLASS_LEVEL = "classLevel";
    public static final String FIELD_PARENT_CLASS_ID = "parentClassId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long classId;

    /**
     * 分类集ID
     */
    @NotNull
    private Long moClassSetId;

    /**
     * 管理组织ID
     */
    @NotNull
    private Long magOrgId;

    /**
     * 分类代码
     */
    @NotEmpty
    @Length(max = 30)
    private String classCode;

    /**
     * 分类描述
     */
    @Length(max = 500)
    @MultiLanguageField
    private String description;

    /**
     * 分类层级
     */
    @NotEmpty
    @Length(max = 1)
    private String classLevel;

    /**
     * 上级分类ID
     */
    private BigDecimal parentClassId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @Transient
    private String codeName;

}
