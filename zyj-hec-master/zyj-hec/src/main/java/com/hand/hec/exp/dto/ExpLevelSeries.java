package com.hand.hec.exp.dto;

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
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@MultiLanguage
@ExtensionAttribute(disable = true)
@Table(name = "exp_level_series")
public class ExpLevelSeries extends BaseDTO {

    public static final String FIELD_LEVEL_SERIES_CODE = "levelSeriesCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_LEVEL_SERIES_ID = "levelSeriesId";


    /**
     * 级别系列代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String levelSeriesCode;

    /**
     * 描述ID
     */
    @NotEmpty
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

    @Id
    @GeneratedValue
    private Long levelSeriesId;

    /**
     * 级别系列描述
     */
    @Transient
    private String levelSeriesCodeDisplay;

}
