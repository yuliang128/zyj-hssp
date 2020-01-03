package com.hand.hec.fnd.dto;

import javax.persistence.*;

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
@Table(name = "fnd_coding_rule_detail")
public class FndCodingRuleDetail extends BaseDTO {

     public static final String FIELD_CODING_RULE_LINE_ID = "codingRuleLineId";
     public static final String FIELD_CODING_RULE_ID = "codingRuleId";
     public static final String FIELD_SEQUENCE = "sequence";
     public static final String FIELD_SEGMENT_TYPE = "segmentType";
     public static final String FIELD_SEGMENT_VALUE = "segmentValue";
     public static final String FIELD_DATE_FORMAT = "dateFormat";
     public static final String FIELD_LENGTH = "length";
     public static final String FIELD_INCREMENTAL = "incremental";
     public static final String FIELD_START_VALUE = "startValue";
     public static final String FIELD_DISPLAY_FLAG = "displayFlag";


     @Id
     @GeneratedValue
     private Long codingRuleLineId;

    /**
     * 编码规则ID
     */
     @NotNull
     private Long codingRuleId;

    /**
     * 顺序号
     */
     @NotNull
     @OrderBy
     private Long sequence;

    /**
     * 段
     */
     @NotEmpty
     @Length(max = 30)
     private String segmentType;

    /**
     * 段值
     */
     @Length(max = 30)
     private String segmentValue;

    /**
     * 日期格式
     */
     @Length(max = 30)
     private String dateFormat;

    /**
     * 位数
     */
     private Long length;

    /**
     * 步长
     */
     private Long incremental;

    /**
     * 开始值
     */
     private Long startValue;

    /**
     * 显示标志
     */
    @Length(max = 1)
     private String displayFlag;

     }
