package com.hand.hec.gld.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinCode;
import com.hand.hap.mybatis.common.query.Where;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@MultiLanguage
@ExtensionAttribute(disable=true)
@Table(name = "gld_sob_segment")
public class GldSobSegment extends BaseDTO {

     public static final String FIELD_SEGMENT_ID = "segmentId";
     public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
     public static final String FIELD_SEGMENT_CODE = "segmentCode";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_SEGMENT_FIELD = "segmentField";
     public static final String FIELD_DR_CR_FLAG = "drCrFlag";
     public static final String FIELD_SEGMENT_TYPE = "segmentType";
     public static final String FIELD_SQL_QUERY = "sqlQuery";
     public static final String FIELD_SQL_VALIDATE = "sqlValidate";
     public static final String FIELD_DEFAULT_TEXT = "defaultText";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     @Where
     private Long segmentId;

    /**
     * 账套ID
     */
     @NotNull
     @Where
     private Long setOfBooksId;

    /**
     * 科目段代码
     */
     @NotEmpty
     @Length(max = 150)
     @Where
     private String segmentCode;

    /**
     * 科目段名称ID
     */
     @NotEmpty
     @Length(max = 500)
     @MultiLanguageField
     @Where
     private String description;

    /**
     * 科目段字段代码（SYSCODE：GL_SEGMENT_FIELD）
     */
     @NotEmpty
     @Length(max = 30)
     @Where
     private String segmentField;

    /**
     * 科目段字段描述
     */
     @Transient
     @JoinCode(joinKey = GldSobSegment.FIELD_SEGMENT_FIELD,code = "GL_SEGMENT_FIELD")
     private String segmentFieldDesc;

    /**
     * 借贷方标志（DR：借方、CR：贷方）
     */
     @Length(max = 2)
     private String drCrFlag;

    /**
     * 科目段取值类型（SYSCODE：GLD_SEGMENT_TYPE）
     */
     @NotEmpty
     @Length(max = 30)
     @Where
     private String segmentType;

    /**
     * SQL语句
     */
     @Length(max = 2000)
     private String sqlQuery;

    /**
     * SQL验证
     */
     @Length(max = 2000)
     private String sqlValidate;

    /**
     * 默认值
     */
     @Length(max = 200)
     private String defaultText;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
