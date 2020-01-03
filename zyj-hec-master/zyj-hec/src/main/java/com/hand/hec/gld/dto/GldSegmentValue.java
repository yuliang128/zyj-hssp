package com.hand.hec.gld.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "gld_segment_value")
public class GldSegmentValue extends BaseDTO {

     public static final String FIELD_SEGMENT_ID = "segmentId";
     public static final String FIELD_VALUE_ID = "valueId";
     public static final String FIELD_VALUE_CODE = "valueCode";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    /**
     * 科目段ID
     */
     @NotNull
     private Long segmentId;

    /**
     * 值ID，主键
     */
     @Id
     @GeneratedValue
     private Long valueId;

    /**
     * 值代码
     */
     @NotEmpty
     @Length(max = 30)
     private String valueCode;

    /**
     * 值描述
     */
     @NotEmpty
     @Length(max = 200)
     private String description;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
