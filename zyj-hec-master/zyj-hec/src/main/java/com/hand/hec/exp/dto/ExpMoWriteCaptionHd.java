package com.hand.hec.exp.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
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
import javax.validation.constraints.NotNull;
/**
 * <p>
 * ExpMoWriteCaptionHd
 * </p>
 *
 * @author yang.duan 2019/02/12 9:34
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@MultiLanguage
@ExtensionAttribute(disable=true)
@Table(name = "exp_mo_write_caption_hd")
public class ExpMoWriteCaptionHd extends BaseDTO {

     public static final String FIELD_CAPTION_HDS_ID = "captionHdsId";
     public static final String FIELD_CAPTION_CODE = "captionCode";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_MAG_ORG_ID = "magOrgId";
     public static final String FIELD_DOC_CATEGORY_CODE = "docCategoryCode";


     @Id
     @GeneratedValue
     private Long captionHdsId;

    /**
     * 填写说明代码
     */
     @NotEmpty
     @Length(max = 30)
     @Where
     private String captionCode;

    /**
     * 填写说明描述
     */
     @Length(max = 500)
     @MultiLanguageField
     @Where
     private String description;

    /**
     * 管理组织ID
     */
     @NotNull
     @Where
     private Long magOrgId;

    /**
     * 单据类别
     */
     @NotEmpty
     @Length(max = 30)
     @Where
     private String docCategoryCode;

     }
