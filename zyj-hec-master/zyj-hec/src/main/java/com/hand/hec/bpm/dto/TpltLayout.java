package com.hand.hec.bpm.dto;

import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: MouseZhou
 * \* Date: 2018/9/6
 * \* Time: 12:20
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */

public class TpltLayout extends BaseDTO {


    @Id
    @GeneratedValue
    @Where
    private Long layoutId;//布局组件ID

    @Where
    private Long templateId;//所属模板ID

    private Long layoutSequence;//布局组件顺序

    @NotEmpty
    @Length(max = 200)
    @Where(comparison = Comparison.LIKE)
    private String layoutCode;//布局组件代码

    @Length(max = 200)
    @Where(comparison = Comparison.LIKE)
    private String layoutDesc;//布局组件描述

    @Length(max = 200)
    private String layoutType;//布局组件类型，formieldSetoxvBoxhBoxgrid	able	abPanel	ab

    private String layoutTypeDesc;

    private Long parentLayoutId;//上级布局组件ID

    private String parentLayoutDesc;

    private Long layoutLevel;//布局组件级别

    @Length(max = 200)
    private String dataset;//所属数据源

    @Length(max = 200)
    private String id;//ID

    private Long tabGroupNumber;//Tab页分组编码

    private Long width;//宽度

    private Long height;//高度

    private Long marginwidth;//自适应宽度

    private Long marginheight;//自适应高度

    @Length(max = 200)
    private String style;//样式

    @Length(max = 200)
    private String hidden;//是否隐藏

    @Length(max = 200)
    private String businessCategory;//业务规则代码

    @Length(max = 200)
    private String parentBusinessCategory;//父级业务规则代码

    @Length(max = 200)
    private String refTable;//关联表

    @Length(max = 200)
    private String refField;//关联字段


    //Form Extends
    @Length(max = 200)
    private String prompt;//描述

    @Length(max = 200)
    private String title;//标题

    private Long columnNum;//列数

    private Long rowNum;//行数

    private Long labelwidth;//标签宽度

    @Length(max = 200)
    private String labelseparator;//标签分割符


    //Grid Extends
    @Length(max = 200)
    private String navbar;//是否启用导航


    //Tab Extends
    @Length(max = 200)
    private String selected;//默认选中

    @Length(max = 200)
    private String closeable;//可关闭

    @Length(max = 200)
    private String disabled;//启用

    @Length(max = 200)
    private String ref;//引用页面地址

    @Length(max = 200)
    private String tabstyle;//Tab样式

    @Length(max = 200)
    private String bodystyle;//内容样式


    public Long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Long layoutId) {
        this.layoutId = layoutId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getLayoutSequence() {
        return layoutSequence;
    }

    public void setLayoutSequence(Long layoutSequence) {
        this.layoutSequence = layoutSequence;
    }

    public String getLayoutCode() {
        return layoutCode;
    }

    public void setLayoutCode(String layoutCode) {
        this.layoutCode = layoutCode;
    }

    public String getLayoutDesc() {
        return layoutDesc;
    }

    public void setLayoutDesc(String layoutDesc) {
        this.layoutDesc = layoutDesc;
    }

    public String getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }


    public Long getParentLayoutId() {
        return parentLayoutId;
    }

    public void setParentLayoutId(Long parentLayoutId) {
        this.parentLayoutId = parentLayoutId;
    }

    public Long getLayoutLevel() {
        return layoutLevel;
    }

    public void setLayoutLevel(Long layoutLevel) {
        this.layoutLevel = layoutLevel;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTabGroupNumber() {
        return tabGroupNumber;
    }

    public void setTabGroupNumber(Long tabGroupNumber) {
        this.tabGroupNumber = tabGroupNumber;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getMarginwidth() {
        return marginwidth;
    }

    public void setMarginwidth(Long marginwidth) {
        this.marginwidth = marginwidth;
    }

    public Long getMarginheight() {
        return marginheight;
    }

    public void setMarginheight(Long marginheight) {
        this.marginheight = marginheight;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getParentBusinessCategory() {
        return parentBusinessCategory;
    }

    public void setParentBusinessCategory(String parentBusinessCategory) {
        this.parentBusinessCategory = parentBusinessCategory;
    }

    public String getRefTable() {
        return refTable;
    }

    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    public String getRefField() {
        return refField;
    }

    public void setRefField(String refField) {
        this.refField = refField;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(Long columnNum) {
        this.columnNum = columnNum;
    }

    public Long getRowNum() {
        return rowNum;
    }

    public void setRowNum(Long rowNum) {
        this.rowNum = rowNum;
    }

    public Long getLabelwidth() {
        return labelwidth;
    }

    public void setLabelwidth(Long labelwidth) {
        this.labelwidth = labelwidth;
    }

    public String getLabelseparator() {
        return labelseparator;
    }

    public void setLabelseparator(String labelseparator) {
        this.labelseparator = labelseparator;
    }

    public String getNavbar() {
        return navbar;
    }

    public void setNavbar(String navbar) {
        this.navbar = navbar;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getCloseable() {
        return closeable;
    }

    public void setCloseable(String closeable) {
        this.closeable = closeable;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTabstyle() {
        return tabstyle;
    }

    public void setTabstyle(String tabstyle) {
        this.tabstyle = tabstyle;
    }

    public String getBodystyle() {
        return bodystyle;
    }

    public void setBodystyle(String bodystyle) {
        this.bodystyle = bodystyle;
    }

    public TpltLayoutBasic convertBasic() {
        TpltLayoutBasic basic = new TpltLayoutBasic();
        basic.setLayoutId(this.getLayoutId());
        basic.setTemplateId(this.getTemplateId());
        basic.setLayoutSequence(this.getLayoutSequence());
        basic.setLayoutCode(this.getLayoutCode());
        basic.setLayoutDesc(this.getLayoutDesc());
        basic.setLayoutType(this.getLayoutType());
        basic.setParentLayoutId(this.getParentLayoutId());
        basic.setLayoutLevel(this.getLayoutLevel());
        basic.setDataset(this.getDataset());
        basic.setId(this.getId());
        basic.setTabGroupNumber(this.getTabGroupNumber());
        basic.setWidth(this.getWidth());
        basic.setHeight(this.getHeight());
        basic.setMarginwidth(this.getMarginwidth());
        basic.setMarginheight(this.getMarginheight());
        basic.setStyle(this.getStyle());
        basic.setHidden(this.getHidden());
        basic.setBusinessCategory(this.getBusinessCategory());
        basic.setParentBusinessCategory(this.getParentBusinessCategory());
        basic.setRefTable(this.getRefTable());
        basic.setRefField(this.getRefField());

        basic.set__id(this.get__id());
        basic.set__status(this.get__status());
        basic.set__tls(this.get__tls());
        basic.setSortname(this.getSortname());
        basic.setSortorder(this.getSortorder());
        basic.set_token(this.get_token());
        basic.setRequestId(this.getRequestId());
        basic.setProgramId(this.getProgramId());
        basic.setObjectVersionNumber(this.getObjectVersionNumber());
        basic.setLastUpdateLogin(this.getLastUpdateLogin());
        basic.setCreatedBy(this.getCreatedBy());
        basic.setCreationDate(this.getCreationDate());
        basic.setLastUpdatedBy(this.getLastUpdatedBy());
        basic.setLastUpdateDate(this.getLastUpdateDate());
        basic.setAttributeCategory(this.getAttributeCategory());
        basic.setAttribute1(this.getAttribute1());
        basic.setAttribute2(this.getAttribute2());
        basic.setAttribute3(this.getAttribute3());
        basic.setAttribute4(this.getAttribute4());
        basic.setAttribute5(this.getAttribute5());
        basic.setAttribute6(this.getAttribute6());
        basic.setAttribute7(this.getAttribute7());
        basic.setAttribute8(this.getAttribute8());
        basic.setAttribute9(this.getAttribute9());
        basic.setAttribute10(this.getAttribute10());
        basic.setAttribute11(this.getAttribute11());
        basic.setAttribute12(this.getAttribute12());
        basic.setAttribute13(this.getAttribute13());
        basic.setAttribute14(this.getAttribute14());
        basic.setAttribute15(this.getAttribute15());

        return basic;
    }

    public TpltLayoutForm convertForm() {
        TpltLayoutForm form = new TpltLayoutForm();
        form.setLayoutId(this.getLayoutId());
        form.setPrompt(this.getPrompt());
        form.setTitle(this.getTitle());
        form.setColumnNum(this.getColumnNum());
        form.setRowNum(this.getRowNum());
        form.setLabelwidth(this.getLabelwidth());
        form.setLabelseparator(this.getLabelseparator());

        form.set__id(this.get__id());
        form.set__status(this.get__status());
        form.set__tls(this.get__tls());
        form.setSortname(this.getSortname());
        form.setSortorder(this.getSortorder());
        form.set_token(this.get_token());
        form.setRequestId(this.getRequestId());
        form.setProgramId(this.getProgramId());
        form.setObjectVersionNumber(this.getObjectVersionNumber());
        form.setLastUpdateLogin(this.getLastUpdateLogin());
        form.setCreatedBy(this.getCreatedBy());
        form.setCreationDate(this.getCreationDate());
        form.setLastUpdatedBy(this.getLastUpdatedBy());
        form.setLastUpdateDate(this.getLastUpdateDate());
        form.setAttributeCategory(this.getAttributeCategory());
        form.setAttribute1(this.getAttribute1());
        form.setAttribute2(this.getAttribute2());
        form.setAttribute3(this.getAttribute3());
        form.setAttribute4(this.getAttribute4());
        form.setAttribute5(this.getAttribute5());
        form.setAttribute6(this.getAttribute6());
        form.setAttribute7(this.getAttribute7());
        form.setAttribute8(this.getAttribute8());
        form.setAttribute9(this.getAttribute9());
        form.setAttribute10(this.getAttribute10());
        form.setAttribute11(this.getAttribute11());
        form.setAttribute12(this.getAttribute12());
        form.setAttribute13(this.getAttribute13());
        form.setAttribute14(this.getAttribute14());
        form.setAttribute15(this.getAttribute15());

        return form;
    }

    public TpltLayoutGrid convertGrid() {
        TpltLayoutGrid grid = new TpltLayoutGrid();
        grid.setLayoutId(this.getLayoutId());
        grid.setNavbar(this.getNavbar());

        grid.set__id(this.get__id());
        grid.set__status(this.get__status());
        grid.set__tls(this.get__tls());
        grid.setSortname(this.getSortname());
        grid.setSortorder(this.getSortorder());
        grid.set_token(this.get_token());
        grid.setRequestId(this.getRequestId());
        grid.setProgramId(this.getProgramId());
        grid.setObjectVersionNumber(this.getObjectVersionNumber());
        grid.setLastUpdateLogin(this.getLastUpdateLogin());
        grid.setCreatedBy(this.getCreatedBy());
        grid.setCreationDate(this.getCreationDate());
        grid.setLastUpdatedBy(this.getLastUpdatedBy());
        grid.setLastUpdateDate(this.getLastUpdateDate());
        grid.setAttributeCategory(this.getAttributeCategory());
        grid.setAttribute1(this.getAttribute1());
        grid.setAttribute2(this.getAttribute2());
        grid.setAttribute3(this.getAttribute3());
        grid.setAttribute4(this.getAttribute4());
        grid.setAttribute5(this.getAttribute5());
        grid.setAttribute6(this.getAttribute6());
        grid.setAttribute7(this.getAttribute7());
        grid.setAttribute8(this.getAttribute8());
        grid.setAttribute9(this.getAttribute9());
        grid.setAttribute10(this.getAttribute10());
        grid.setAttribute11(this.getAttribute11());
        grid.setAttribute12(this.getAttribute12());
        grid.setAttribute13(this.getAttribute13());
        grid.setAttribute14(this.getAttribute14());
        grid.setAttribute15(this.getAttribute15());

        return grid;
    }

    public TpltLayoutTab convertTab() {
        TpltLayoutTab tab = new TpltLayoutTab();
        tab.setLayoutId(this.getLayoutId());
        tab.setCloseable(this.getCloseable());
        tab.setDisabled(this.getDisabled());
        tab.setRef(this.getRef());
        tab.setSelected(this.getSelected());
        tab.setBodystyle(this.getBodystyle());
        tab.setTabstyle(this.getTabstyle());

        tab.set__id(this.get__id());
        tab.set__status(this.get__status());
        tab.set__tls(this.get__tls());
        tab.setSortname(this.getSortname());
        tab.setSortorder(this.getSortorder());
        tab.set_token(this.get_token());
        tab.setRequestId(this.getRequestId());
        tab.setProgramId(this.getProgramId());
        tab.setObjectVersionNumber(this.getObjectVersionNumber());
        tab.setLastUpdateLogin(this.getLastUpdateLogin());
        tab.setCreatedBy(this.getCreatedBy());
        tab.setCreationDate(this.getCreationDate());
        tab.setLastUpdatedBy(this.getLastUpdatedBy());
        tab.setLastUpdateDate(this.getLastUpdateDate());
        tab.setAttributeCategory(this.getAttributeCategory());
        tab.setAttribute1(this.getAttribute1());
        tab.setAttribute2(this.getAttribute2());
        tab.setAttribute3(this.getAttribute3());
        tab.setAttribute4(this.getAttribute4());
        tab.setAttribute5(this.getAttribute5());
        tab.setAttribute6(this.getAttribute6());
        tab.setAttribute7(this.getAttribute7());
        tab.setAttribute8(this.getAttribute8());
        tab.setAttribute9(this.getAttribute9());
        tab.setAttribute10(this.getAttribute10());
        tab.setAttribute11(this.getAttribute11());
        tab.setAttribute12(this.getAttribute12());
        tab.setAttribute13(this.getAttribute13());
        tab.setAttribute14(this.getAttribute14());
        tab.setAttribute15(this.getAttribute15());

        return tab;
    }

    public TpltLayout convertLayout(TpltLayoutBasic basic, TpltLayoutForm form, TpltLayoutGrid grid, TpltLayoutTab tab) {
        this.setLayoutId(basic.getLayoutId());
        this.setTemplateId(basic.getTemplateId());
        this.setLayoutSequence(basic.getLayoutSequence());
        this.setLayoutCode(basic.getLayoutCode());
        this.setLayoutDesc(basic.getLayoutDesc());
        this.setLayoutType(basic.getLayoutType());
        this.setParentLayoutId(basic.getParentLayoutId());
        this.setLayoutLevel(basic.getLayoutLevel());
        this.setDataset(basic.getDataset());
        this.setId(basic.getId());
        this.setTabGroupNumber(basic.getTabGroupNumber());
        this.setWidth(basic.getWidth());
        this.setHeight(basic.getHeight());
        this.setMarginwidth(basic.getMarginwidth());
        this.setMarginheight(basic.getMarginheight());
        this.setStyle(basic.getStyle());
        this.setHidden(basic.getHidden());
        this.setBusinessCategory(basic.getBusinessCategory());
        this.setParentBusinessCategory(basic.getParentBusinessCategory());
        this.setRefTable(basic.getRefTable());
        this.setRefField(basic.getRefField());

        this.setLayoutId(form.getLayoutId());
        this.setPrompt(form.getPrompt());
        this.setTitle(form.getTitle());
        this.setColumnNum(form.getColumnNum());
        this.setRowNum(form.getRowNum());
        this.setLabelwidth(form.getLabelwidth());
        this.setLabelseparator(form.getLabelseparator());

        this.setLayoutId(grid.getLayoutId());
        this.setNavbar(grid.getNavbar());

        this.setLayoutId(tab.getLayoutId());
        this.setCloseable(tab.getCloseable());
        this.setDisabled(tab.getDisabled());
        this.setRef(tab.getRef());
        this.setSelected(tab.getSelected());
        this.setBodystyle(tab.getBodystyle());
        this.setTabstyle(tab.getTabstyle());


        this.set__id(basic.get__id());
        this.set__status(basic.get__status());
        this.set__tls(basic.get__tls());
        this.setSortname(basic.getSortname());
        this.setSortorder(basic.getSortorder());
        this.set_token(basic.get_token());
        this.setRequestId(basic.getRequestId());
        this.setProgramId(basic.getProgramId());
        this.setObjectVersionNumber(basic.getObjectVersionNumber());
        this.setLastUpdateLogin(basic.getLastUpdateLogin());
        this.setCreatedBy(basic.getCreatedBy());
        this.setCreationDate(basic.getCreationDate());
        this.setLastUpdatedBy(basic.getLastUpdatedBy());
        this.setLastUpdateDate(basic.getLastUpdateDate());
        this.setAttributeCategory(basic.getAttributeCategory());
        this.setAttribute1(basic.getAttribute1());
        this.setAttribute2(basic.getAttribute2());
        this.setAttribute3(basic.getAttribute3());
        this.setAttribute4(basic.getAttribute4());
        this.setAttribute5(basic.getAttribute5());
        this.setAttribute6(basic.getAttribute6());
        this.setAttribute7(basic.getAttribute7());
        this.setAttribute8(basic.getAttribute8());
        this.setAttribute9(basic.getAttribute9());
        this.setAttribute10(basic.getAttribute10());
        this.setAttribute11(basic.getAttribute11());
        this.setAttribute12(basic.getAttribute12());
        this.setAttribute13(basic.getAttribute13());
        this.setAttribute14(basic.getAttribute14());
        this.setAttribute15(basic.getAttribute15());

        return this;
    }

    public String getLayoutTypeDesc() {
        return layoutTypeDesc;
    }

    public void setLayoutTypeDesc(String layoutTypeDesc) {
        this.layoutTypeDesc = layoutTypeDesc;
    }

    public String getParentLayoutDesc() {
        return parentLayoutDesc;
    }

    public void setParentLayoutDesc(String parentLayoutDesc) {
        this.parentLayoutDesc = parentLayoutDesc;
    }
}