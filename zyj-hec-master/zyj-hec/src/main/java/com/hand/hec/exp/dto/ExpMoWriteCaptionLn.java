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

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
/**
 * <p>
 * ExpMoWriteCaptionLn
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
@Table(name = "exp_mo_write_caption_ln")
public class ExpMoWriteCaptionLn extends BaseDTO {

     public static final String FIELD_CAPTION_LNS_ID = "captionLnsId";
     public static final String FIELD_CAPTION_HDS_ID = "captionHdsId";
     public static final String FIELD_LINE_STEP_NUMBER = "lineStepNumber";
     public static final String FIELD_STEP_TITLE = "stepTitle";
     public static final String FIELD_STEP_CONTENT = "stepContent";


     @Id
     @GeneratedValue
     private Long captionLnsId;

    /**
     * 填写说明头表ID
     */
     @NotNull
     @Where
     private Long captionHdsId;

    /**
     * 步骤序号
     */
     @NotNull
     private Long lineStepNumber;

    /**
     * 步骤标题描述
     */
     @Length(max = 500)
     @MultiLanguageField
     private String stepTitle;

    /**
     * 步骤内容描述
     */
     @Length(max = 500)
     @MultiLanguageField
     private String stepContent;

    @Transient
    private String docCategory;

    @Transient
    private Long docTypeId;

    @Transient
    private String lineStepTitle;

}
