package com.hand.hec.fnd.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * GldAccountSet
 * </p>
 * 
 * @author guiyu 2019/01/08 15:34
 */
@ExtensionAttribute(disable = true)
@Table(name = "gld_account_set")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@MultiLanguage
public class GldAccountSet extends BaseDTO {

    public static final String FIELD_ACCOUNT_SET_ID = "accountSetId";
    public static final String FIELD_ACCOUNT_SET_CODE = "accountSetCode";
    public static final String FIELD_ACCOUNT_SET_NAME = "accountSetName";
    public static final String FIELD_COA_STRUCTURE_ID = "coaStructureId";
    public static final String FIELD_COA_STRUCTURE_CODE = "coaStructureCode";
    public static final String FIELD_COA_DESCRIPTION = "coaDescription";
    public static final String FIELD_COA_STRUCTURE_FORMAT = "coaStructureFormat";


    /**
     * 科目表ID
     */
    @Id
    @GeneratedValue
    private Long accountSetId;

    /**
     * 科目表代码
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String accountSetCode;

    /**
     * 科目表名称
     */
    @MultiLanguageField
    @Where
    @Length(max = 500)
    private String accountSetName;

    /**
     * 科目结构ID
     */
    @JoinTable(name = "coaStructureJoin", joinMultiLanguageTable = false, target = GldCoaStructure.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = GldCoaStructure.FIELD_COA_STRUCTURE_ID)})
    @NotNull
    private Long coaStructureId;

    /**
     * 科目表结构代码
     */
    @JoinColumn(joinName = "coaStructureJoin", field = GldCoaStructure.FIELD_COA_STRUCTURE_CODE)
    @Transient
    private String coaStructureCode;

    /**
     * 科目表结构名称
     */
    @JoinColumn(joinName = "coaStructureJoin", field = GldCoaStructure.FIELD_DESCRIPTION)
    @Transient
    private String coaDescription;

    /**
     * 科目表结构
     */
    @JoinColumn(joinName = "coaStructureJoin", field = GldCoaStructure.FIELD_STRUCTURE_FORMAT)
    @Transient
    private String coaStructureFormat;

}
