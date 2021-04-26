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
@Table(name = "bpm_page_tag_lov")
public class PageTagLov extends BaseDTO {

    public static final String FIELD_TAG_ID = "tagId";
    public static final String FIELD_LOVCODE = "lovcode";
    public static final String FIELD_LOVURL = "lovurl";
    public static final String FIELD_LOVAUTOQUERY = "lovautoquery";
    public static final String FIELD_LOVGRIDHEIGHT = "lovgridheight";
    public static final String FIELD_LOVHEIGHT = "lovheight";
    public static final String FIELD_LOVWIDTH = "lovwidth";
    public static final String FIELD_LOVLABELWIDTH = "lovlabelwidth";
    public static final String FIELD_AUTOCOMPLETE = "autocomplete";
    public static final String FIELD_AUTOCOMPLETEFIELD = "autocompletefield";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_SQLTEXT = "sqltext";
    public static final String FIELD_SYSCODE = "syscode";
    public static final String FIELD_DATASOURCE = "datasource";


    @Id
    private Long tagId;//标签ID

    @Length(max = 200)
    private String lovcode;//lov对应的BM

    @Length(max = 200)
    private String lovurl;//自定义lov对应的URL

    @Length(max = 200)
    private String lovautoquery;//lov自动查询

    private Long lovgridheight;//lov的grid高度

    private Long lovheight;//lov高度

    private Long lovwidth;//lov宽度

    private Long lovlabelwidth;//lov上的标签宽度

    @Length(max = 200)
    private String autocomplete;//是否自动补全

    @Length(max = 200)
    private String autocompletefield;//自动补全字段

    @Length(max = 200)
    private String title;//lov的标题

    @Length(max = 200)
    private String sqltext;//lov执行的sql查询

    @Length(max = 200)
    private String syscode;//lov查询的syscode

    @Length(max = 200)
    private String datasource;//SYS_CODE/SQLTEXT/OPTIONS


    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setLovcode(String lovcode) {
        this.lovcode = lovcode;
    }

    public String getLovcode() {
        return lovcode;
    }

    public void setLovurl(String lovurl) {
        this.lovurl = lovurl;
    }

    public String getLovurl() {
        return lovurl;
    }

    public void setLovautoquery(String lovautoquery) {
        this.lovautoquery = lovautoquery;
    }

    public String getLovautoquery() {
        return lovautoquery;
    }

    public void setLovgridheight(Long lovgridheight) {
        this.lovgridheight = lovgridheight;
    }

    public Long getLovgridheight() {
        return lovgridheight;
    }

    public void setLovheight(Long lovheight) {
        this.lovheight = lovheight;
    }

    public Long getLovheight() {
        return lovheight;
    }

    public void setLovwidth(Long lovwidth) {
        this.lovwidth = lovwidth;
    }

    public Long getLovwidth() {
        return lovwidth;
    }

    public void setLovlabelwidth(Long lovlabelwidth) {
        this.lovlabelwidth = lovlabelwidth;
    }

    public Long getLovlabelwidth() {
        return lovlabelwidth;
    }

    public void setAutocomplete(String autocomplete) {
        this.autocomplete = autocomplete;
    }

    public String getAutocomplete() {
        return autocomplete;
    }

    public void setAutocompletefield(String autocompletefield) {
        this.autocompletefield = autocompletefield;
    }

    public String getAutocompletefield() {
        return autocompletefield;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSqltext(String sqltext) {
        this.sqltext = sqltext;
    }

    public String getSqltext() {
        return sqltext;
    }

    public void setSyscode(String syscode) {
        this.syscode = syscode;
    }

    public String getSyscode() {
        return syscode;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getDatasource() {
        return datasource;
    }

}