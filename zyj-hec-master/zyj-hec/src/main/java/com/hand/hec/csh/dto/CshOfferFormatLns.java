package com.hand.hec.csh.dto;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_offer_format_lns")
public class CshOfferFormatLns extends BaseDTO {

     public static final String FIELD_FORMAT_LNS_ID = "formatLnsId";
     public static final String FIELD_FORMAT_HDS_ID = "formatHdsId";
     public static final String FIELD_LINE_NUMBER = "lineNumber";
     public static final String FIELD_COLUMN_DESC = "columnDesc";
     public static final String FIELD_COLUMN_NAME = "columnName";
     public static final String FIELD_COLUMN_SQL_TEXT = "columnSqlText";
     public static final String FIELD_COLUMN_FORMAT = "columnFormat";
     public static final String FIELD_GROUP_FLAG = "groupFlag";
     public static final String FIELD_OFFER_COLUMN_NAME = "offerColumnName";
     public static final String FIELD_OFFER_VALUE_TYPE = "offerValueType";
     public static final String FIELD_SPLICE_TYPE = "spliceType";
     public static final String FIELD_SPLICE_VALUE = "spliceValue";


     @Id
     @GeneratedValue
     private Long formatLnsId;

    /**
     * 报盘文件导出格式定义头ID
     */
     @NotNull
     private Long formatHdsId;

    /**
     * 行号
     */
     @NotNull
     private Long lineNumber;

    /**
     * 字段名称
     */
     @Length(max = 1000)
     private String columnDesc;

    /**
     * 字段代码
     */
     @NotEmpty
     @Length(max = 200)
     private String columnName;

    /**
     * 字段格式值
     */
     @Length(max = 4000)
     private String columnSqlText;

    /**
     * 格式（SYSCODE：CSH_OFFER_COLUMN_FORMAT）
     */
     @Length(max = 100)
     private String columnFormat;

    /**
     * 分组标志
     */
     @NotEmpty
     @Length(max = 1)
     private String groupFlag;

    /**
     * 报盘列名
     */
     @Length(max = 300)
     private String offerColumnName;

    /**
     * 取值类型（SYSCODE：CSH_OFFER_VALUE_TYPE）
     */
     @Length(max = 30)
     private String offerValueType;

    /**
     * 拼接位置(PREFIX 附加前缀/SUFFIX 附加后缀 SYSCODE：CSH_OFFER_SPLICE_TYPE)
     */
     @Length(max = 30)
     private String spliceType;

    /**
     * 拼接值
     */
     @Length(max = 300)
     private String spliceValue;

     @Transient
     private Long fixedMax;

     @Transient
     private  String columnNameDesc;

     }
