package com.hand.hec.fnd.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 汇率类型dto
 * </p>
 *
 * @author xingjialin 2019/01/04 18:41
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "gld_exchangerate_type")
@MultiLanguage
public class GldExchangerateType extends BaseDTO {

    public static final String FIELD_TYPE_ID = "typeId";
    public static final String FIELD_TYPE_CODE = "typeCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_METHOD_CODE = "methodCode";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
    public static final String FIELD_PERIOD_TYPE = "periodType";

    @Id
    @GeneratedValue
    private Long typeId;

    /**
     * 汇率类型代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String typeCode;

    /**
     * 汇率类型名称
     */
    @Length(max = 500)
    @MultiLanguageField
    @Where
    private String description;

    /**
     * 方式
     */
    @Length(max = 30)
    private String methodCode;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;

    /**
     * 账套ID
     */
    @NotNull
    @Where
    private Long setOfBooksId;

    /**
     * 期间类型
     */
    private String periodType;

}
