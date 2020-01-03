package com.hand.hec.csh.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
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
import java.math.BigDecimal;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@MultiLanguage
@Table(name = "csh_offer_format_hds")
public class CshOfferFormatHds extends BaseDTO {

     public static final String FIELD_FORMAT_HDS_ID = "formatHdsId";
     public static final String FIELD_FORMAT_CODE = "formatCode";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_EXPORT_TYPE_CODE = "exportTypeCode";
     public static final String FIELD_EXPORT_SEPARATOR_CODE = "exportSeparatorCode";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private BigDecimal formatHdsId;

    /**
     * 报盘格式代码
     */
     @NotEmpty
     @Length(max = 30)
     private String formatCode;

    /**
     * 报盘格式名称ID
     */
    @Length(max = 500)
    @MultiLanguageField
    private String description;

    /**
     * 导出格式（SYSCODE：CSH_OFFER_EXPORT_TYPE）
     */
     @NotEmpty
     @Length(max = 30)
     private String exportTypeCode;

    /**
     * 分隔符（SYSCODE：CSH_OFFER_EXPORT_SEPARATOR）
     */
     @Length(max = 30)
     private String exportSeparatorCode;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     @Transient
     private String exportSeparator;

     }
