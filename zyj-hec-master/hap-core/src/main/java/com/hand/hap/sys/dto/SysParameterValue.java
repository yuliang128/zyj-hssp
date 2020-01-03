package com.hand.hap.sys.dto;

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

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 参数指定dto
 * </p>
 *
 * @author dingwei.ma@hand-china.com
 * @author jialin.xing@hand-china.com
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "sys_parameter_value")
public class SysParameterValue extends BaseDTO {

    public static final String FIELD_PARAMETER_VALUE_ID = "parameterValueId";
    public static final String FIELD_PARAMETER_ID = "parameterId";
    public static final String FIELD_LEVEL_ID = "levelId";
    public static final String FIELD_LEVEL_VALUE = "levelValue";
    public static final String FIELD_PARAMETER_VALUE = "parameterValue";
    public static final String FIELD_VALUE_CODE = "valueCode";
    public static final String FIELD_VALUE_NAME = "valueName";


    @Id
    @GeneratedValue
    private Long parameterValueId;

    @NotNull
    private Long parameterId;

    @NotNull
    private Long levelId;

    /**
     * 值层次
     */
    @NotNull
    private Long levelValue;

    /**
     * 参数值
     */
    @Length(max = 240)
    private String parameterValue;

    /**
     * 值代码
     */
    @Length(max = 1000)
    private String valueCode;

    /**
     * 值名称
     */
    @Length(max = 1000)
    private String valueName;

    @Transient
    private String levelName;

    @Transient
    private String parameterCode;

    @Transient
    private String description;
}
