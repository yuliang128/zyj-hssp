package com.hand.hec.fnd.dto;

import javax.persistence.*;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@MultiLanguage
@Table(name = "fnd_uom")
public class FndUom extends BaseDTO {

     public static final String FIELD_UOM_ID = "uomId";
     public static final String FIELD_UOM_TYPE_ID = "uomTypeId";
     public static final String FIELD_UOM_CODE = "uomCode";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_PRECISION = "precision";
     public static final String FIELD_META_CONVERSION_RATE = "metaConversionRate";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";
     public static final String FIELD_UOM_TYPE_DESC = "uomTypeDesc";


     @Id
     @GeneratedValue
     private Long uomId;

    /**
     * 计量单位类型ID
     */
     @NotNull
     private Long uomTypeId;

    /**
     * 计量单位代码
     */
     @NotEmpty
     @Length(max = 30)
     private String uomCode;

    /**
     * 计量单位描述ID
     */
     @Length(max = 500)
     @MultiLanguageField
     private String description;

    /**
     * 精度
     */
    @Column(name= "`precision`")
     private Long precision;

    /**
     * 元单位转换率
     */
     private Long metaConversionRate;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

    /**
     * 计量单位类型描述
     */
    @Transient
     private String uomTypeDesc;

     }
