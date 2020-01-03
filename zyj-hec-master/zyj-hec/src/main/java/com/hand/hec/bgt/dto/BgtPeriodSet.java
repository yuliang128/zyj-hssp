package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinCode;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 预算期间集
 * </p>
 * 
 * @author mouse 2019/01/07 16:45
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_period_set")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@MultiLanguage
public class BgtPeriodSet extends BaseDTO {

    public static final String FIELD_PERIOD_SET_ID = "periodSetId";
    public static final String FIELD_PERIOD_SET_CODE = "periodSetCode";
    public static final String FIELD_PERIOD_SET_NAME = "periodSetName";
    public static final String FIELD_TOTAL_PERIOD_NUM = "totalPeriodNum";
    public static final String FIELD_PERIOD_ADDITIONAL_FLAG = "periodAdditionalFlag";
    public static final String FIELD_PERIOD_ADDITIONAL_FLAG_DESC = "periodAdditionalFlagDesc";


    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    @Where
    private Long periodSetId;

    /**
     * 预算期代码
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String periodSetCode;

    /**
     * 预算期描述
     */
    @Where
    @MultiLanguageField
    @NotEmpty
    @Length(max = 500)
    private String periodSetName;

    /**
     * 期间总数
     */
    @NotNull
    private Long totalPeriodNum;

    /**
     * 名称附加（SYSCODE：PERIOD_ADDITIONAL_FLAG）
     */
    @NotEmpty
    @Length(max = 1)
    private String periodAdditionalFlag;

    @Transient
    @JoinCode(code = "PERIOD_ADDITIONAL_FLAG", joinKey = BgtPeriodSet.FIELD_PERIOD_ADDITIONAL_FLAG)
    private String periodAdditionalFlagDesc;

}
