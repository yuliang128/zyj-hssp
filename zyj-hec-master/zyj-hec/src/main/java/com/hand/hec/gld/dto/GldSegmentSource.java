package com.hand.hec.gld.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@Table(name = "gld_segment_source")
public class GldSegmentSource extends BaseDTO {

     public static final String FIELD_SEGMENT_SOURCE_ID = "segmentSourceId";
     public static final String FIELD_SEGMENT_ID = "segmentId";
     public static final String FIELD_DOCUMENT_CATEGORY = "documentCategory";
     public static final String FIELD_VALUE_TABLE = "valueTable";
     public static final String FIELD_VALUE_ITEM = "valueItem";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long segmentSourceId;

    /**
     * 科目段ID
     */
     @NotNull
     private Long segmentId;

    /**
     * 单据类别
     */

     @Length(max = 30)
     private String documentCategory;

    /**
     * 取值方式
     */
     @NotEmpty
     @Length(max = 30)
     private String valueTable;

    /**
     * 取值项
     */
     @NotEmpty
     @Length(max = 30)
     private String valueItem;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
