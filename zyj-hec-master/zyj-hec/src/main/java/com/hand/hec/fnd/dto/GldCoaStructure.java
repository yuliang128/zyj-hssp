package com.hand.hec.fnd.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 科目结构dto
 * </p>
 *
 * @author YHL 2019/01/08 16:07
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "gld_coa_structure")
@MultiLanguage
public class GldCoaStructure extends BaseDTO {

    public static final String FIELD_COA_STRUCTURE_ID = "coaStructureId";
    public static final String FIELD_COA_STRUCTURE_CODE = "coaStructureCode";
    public static final String FIELD_STRUCTURE_FORMAT = "structureFormat";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    /**
     * 科目结构ID
     */
    @Id
    @GeneratedValue
    private Long coaStructureId;

    /**
     * 科目结构代码
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String coaStructureCode;

    /**
     * 科目结构
     */
    @NotEmpty
    @Length(max = 100)
    private String structureFormat;

    /**
     * 描述
     */
    @MultiLanguageField
    @Where
    @Length(max = 500)
    private String description;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}