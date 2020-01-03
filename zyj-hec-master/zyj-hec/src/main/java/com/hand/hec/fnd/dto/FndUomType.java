package com.hand.hec.fnd.dto;

/**
 * <p>
 * 计量单位类型实体类
 * </p>
 * 
 * @author guiyuting 2019/04/23 15:43
 */
import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.*;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinTable;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.persistence.criteria.JoinType;

import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_uom_type")
@MultiLanguage
public class FndUomType extends BaseDTO {

    public static final String FIELD_UOM_TYPE_ID = "uomTypeId";
    public static final String FIELD_UOM_TYPE_CODE = "uomTypeCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_META_UOM_ID = "metaUomId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_META_UOM_NAME = "metaUomName";


    @Id
    @GeneratedValue
    private Long uomTypeId;

    /**
     * 计量单位类型代码
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String uomTypeCode;

    /**
     * 计量单位类型名称
     */
    @Where
    @MultiLanguageField
    @Length(max = 500)
    private String description;

    /**
     * 元单位
     */
    @JoinTable(name = "uomJoin", joinMultiLanguageTable = true, target = FndUom.class, type = JoinType.LEFT,
            on = {@JoinOn(joinField = FndUom.FIELD_UOM_ID),
                    @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long metaUomId;

    @Transient
    @JoinColumn(joinName = "uomJoin", field = FndUom.FIELD_DESCRIPTION)
    private String metaUomName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
