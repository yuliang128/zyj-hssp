package com.hand.hec.mod.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "mod_field_lov")
public class ModFieldLov extends BaseDTO {

    public static final String FIELD_FIELD_ID = "fieldId";
    public static final String FIELD_LOV_CODE = "lovCode";
    public static final String FIELD_LOV_FIELD_NAME = "lovFieldName";
    public static final String FIELD_LOV_PARAM_NAME = "lovParamName";
    public static final String FIELD_LOV_PARAM_DATA_TYPE = "lovParamDataType";


    @Id
    private Long fieldId;

    /**
     * LOV代码
     */
    @NotEmpty
    @Length(max = 200)
    private String lovCode;

    /**
     * LOV字段名
     */
    @NotEmpty
    @Length(max = 200)
    private String lovFieldName;

    /**
     * LOV参数名
     */
    @NotEmpty
    @Length(max = 200)
    private String lovParamName;

    /**
     * LOV参数数据类型
     */
    @NotEmpty
    @Length(max = 30)
    private String lovParamDataType;

}
