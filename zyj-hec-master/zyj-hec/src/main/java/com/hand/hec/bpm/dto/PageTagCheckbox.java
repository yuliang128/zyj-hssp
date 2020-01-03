package com.hand.hec.bpm.dto;

/**Auto Generated By Code Generator
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
@Table(name = "bpm_page_tag_checkbox")
public class PageTagCheckbox extends BaseDTO {

    public static final String FIELD_TAG_ID = "tagId";
    public static final String FIELD_CHECKEDVALUE = "checkedvalue";
    public static final String FIELD_UNCHECKEDVALUE = "uncheckedvalue";
    public static final String FIELD_LABEL = "label";

    @Id
    private Long tagId;//标签ID

    @Length(max = 200)
    private String checkedvalue;//选中的值

    @Length(max = 200)
    private String uncheckedvalue;//未选中的值

    @Length(max = 200)
    private String label;//显示在checkBox右边的描述


    public void setTagId(Long tagId){
        this.tagId = tagId;
    }

    public Long getTagId(){
        return tagId;
    }

    public void setCheckedvalue(String checkedvalue){
        this.checkedvalue = checkedvalue;
    }

    public String getCheckedvalue(){
        return checkedvalue;
    }

    public void setUncheckedvalue(String uncheckedvalue){
        this.uncheckedvalue = uncheckedvalue;
    }

    public String getUncheckedvalue(){
        return uncheckedvalue;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }

}
