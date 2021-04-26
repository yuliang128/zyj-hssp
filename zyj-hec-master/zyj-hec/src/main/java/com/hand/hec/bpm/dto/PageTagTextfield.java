package com.hand.hec.bpm.dto;

/**
 * Auto Generated By Code Generator
 * Description:
 */

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import javax.persistence.Table;

@Component
@ExtensionAttribute(disable = true)
@Table(name = "bpm_page_tag_textfield")
public class PageTagTextfield extends BaseDTO {

    public static final String FIELD_TAG_ID = "tagId";
    public static final String FIELD_TYPECASE = "typecase";


    @Id
    private Long tagId;

    @Length(max = 200)
    private String typecase;


    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTypecase(String typecase) {
        this.typecase = typecase;
    }

    public String getTypecase() {
        return typecase;
    }

}