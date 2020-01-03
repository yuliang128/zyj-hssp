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
@Table(name = "mod_model")
public class ModModel extends BaseDTO {

    public static final String FIELD_MODEL_ID = "modelId";
    public static final String FIELD_MODEL_CODE = "modelCode";
    public static final String FIELD_MODEL_NAME = "modelName";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long modelId;

    /**
     * 模型代码
     */
    @NotEmpty
    @Length(max = 30)
    private String modelCode;

    /**
     * 模型名称
     */
    @NotEmpty
    @Length(max = 200)
    private String modelName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
