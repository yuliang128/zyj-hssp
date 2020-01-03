package com.hand.hec.fnd.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 供应商类型dto
 * </p>
 *
 * @author YHL 2019/01/28 18:41
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "pur_vender_type")
@MultiLanguage
public class PurVenderType extends BaseDTO {

    public static final String FIELD_VENDER_TYPE_ID = "venderTypeId";
    public static final String FIELD_VENDER_TYPE_CODE = "venderTypeCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ONE_OFF_FLAG = "oneOffFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    /**
     * 供应商类型ID
     */
    @Id
    @GeneratedValue
    private Long venderTypeId;

    /**
     * 供应商类型代码
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String venderTypeCode;

    /**
     * 供应商类型名称
     */
    @MultiLanguageField
    @Where
    @Length(max = 500)
    private String description;

    /**
     * 一次性标志
     */
    @NotEmpty
    @Length(max = 1)
    private String oneOffFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
