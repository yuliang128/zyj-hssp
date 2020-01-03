package com.hand.hec.fnd.dto;

import javax.persistence.*;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.JoinCode;
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
@MultiLanguage
@ExtensionAttribute(disable=true)
@Table(name = "fnd_coding_rule")
public class FndCodingRule extends BaseDTO {

     public static final String FIELD_CODING_RULE_OBJECT_ID = "codingRuleObjectId";
     public static final String FIELD_CODING_RULE_ID = "codingRuleId";
     public static final String FIELD_CODING_RULE_CODE = "codingRuleCode";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_NOTE = "note";
     public static final String FIELD_RESET_FREQUENCE = "resetFrequence";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    /**
     * 编码规则对象ID
     */
     @NotNull
     private Long codingRuleObjectId;

     @Id
     @GeneratedValue
     private Long codingRuleId;

    /**
     * 编码规则代码
     */
     @NotEmpty
     @Length(max = 30)
     private String codingRuleCode;

    /**
     * 描述
     */
     @NotEmpty
     @Length(max = 500)
     @MultiLanguageField
     private String description;

    /**
     * 备注
     */
     @Length(max = 2000)
     private String note;

    /**
     * 重置频率
     */
     @NotEmpty
     @Length(max = 30)
     private String resetFrequence;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     /**
     * 重置频率描述
     */
     @Transient
     @JoinCode(code = "CODING_RULE_RESET_FREQUENCY", joinKey = FIELD_RESET_FREQUENCE)
     private String resetFrequencyDisplay;

}
