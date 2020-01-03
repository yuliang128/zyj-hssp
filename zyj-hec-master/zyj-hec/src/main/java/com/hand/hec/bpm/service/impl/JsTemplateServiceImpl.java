package com.hand.hec.bpm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.*;
import com.hand.hec.bpm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hao.zhou@hand-china.com
 * @date Description
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class JsTemplateServiceImpl extends BaseServiceImpl<JsTemplate> implements IJsTemplateService {

    @Autowired
    IPageTagBasicService tagBasicService;

    @Autowired
    IPageLayoutBasicService layoutBasicService;

    @Autowired
    IPageGroupFieldService pageGroupFieldService;

    @Autowired
    IPageGroupFieldService groupFieldService;

    @Autowired
    IPageTagComboboxService comboboxService;


    @Override
    public String getCurLogicFieldName(IRequest request, PageTagDataGuide dataGuide) {
        PageTagBasic tag = new PageTagBasic();
        tag.setTagId(dataGuide.getTagId());
        PageTagBasic tagBasic = tagBasicService.selectByPrimaryKey(request, tag);

        //如果当前field_id为空，返回null
        if (tagBasic.getFieldId() == null) {
            return null;
        }

        //如果当前数据向导的逻辑类型为空，说明使用字段本身，直接返回字段本身的值
        if (dataGuide.getCurrentLogicType() == null) {
            return tagBasic.getName();
        }

        //获取当前字段的逻辑归属字段
        PageGroupField field = new PageGroupField();
        field.setFieldId(tagBasic.getFieldId());
        field = pageGroupFieldService.selectByPrimaryKey(request, field);

        //如果当前字段的逻辑归属字段ID为空，返回null
        if (field.getLogicBelongFieldId() == null) {
            return null;
        }

        //根据逻辑归属字段和逻辑类型获取字段名称
        PageGroupField logicField = new PageGroupField();
        logicField.setLogicBelongFieldId(field.getLogicBelongFieldId());
        logicField.setLogicType(dataGuide.getCurrentLogicType());
        Criteria criteria = new Criteria(logicField);
        criteria.where(new WhereField(PageGroupField.FIELD_LOGIC_BELONG_FIELD_ID, Comparison.EQUAL), new WhereField(PageGroupField.FIELD_LOGIC_TYPE, Comparison.EQUAL));
        logicField = pageGroupFieldService.selectOptions(request, logicField, criteria, 0, 0).get(0);

        return logicField.getFieldName();
    }

    private String getBelongFieldName(IRequest request, Long fieldId) {
        PageGroupField field = new PageGroupField();
        field.setFieldId(fieldId);
        field = groupFieldService.selectByPrimaryKey(request, field);

        if (field.getLogicBelongFieldId() == null) {
            return null;
        }

        PageGroupField belongField = new PageGroupField();
        belongField.setFieldId(field.getLogicBelongFieldId());
        belongField = groupFieldService.selectByPrimaryKey(request, belongField);

        return belongField.getFieldName();
    }

    private PageTagBasic getPageTagBasic(IRequest request, Long tagId) {
        PageTagBasic tag = new PageTagBasic();
        tag.setTagId(tagId);
        tag = tagBasicService.selectByPrimaryKey(request, tag);
        return tag;
    }

    private PageLayoutBasic getPageLayoutBasic(IRequest request, Long layoutId) {
        PageLayoutBasic layout = new PageLayoutBasic();
        layout.setLayoutId(layoutId);
        layout = layoutBasicService.selectByPrimaryKey(request, layout);
        return layout;
    }

    private String getJsContent(IRequest request, String templateCode) {
        JsTemplate template = new JsTemplate();
        template.setJsTemplateCode(templateCode);
        Criteria criteria = new Criteria(template);
        criteria.where(new WhereField(JsTemplate.FIELD_JS_TEMPLATE_CODE, Comparison.EQUAL));
        template = self().selectOptions(request, template, criteria, 0, 0).get(0);
        return template.getJsContent();
    }

    @Override
    public String getSumGridNum(IRequest request, PageTagDataGuide dataGuide) {
        String jsContent = getJsContent(request, JsTemplate.JS_TYPE_SUM_GRID_NUM);
        return jsContent;
    }

    @Override
    public String getChangeReadonly(IRequest request, PageTagDataGuide dataGuide) {
        PageTagBasic tag = getPageTagBasic(request, dataGuide.getTagId());
        String logicFiledName = getCurLogicFieldName(request, dataGuide);
        PageTagBasic targetTag = getPageTagBasic(request, dataGuide.getTargetTagId());
        String jsContent = getJsContent(request, JsTemplate.JS_TYPE_CHANGE_READONLY);

        jsContent = jsContent.replaceAll("\\$\\{@triggerCondition\\}", dataGuide.getTriggerCondition() == null ? "" : dataGuide.getTriggerCondition());
        jsContent = jsContent.replaceAll("\\$\\{@conditionValue\\}", dataGuide.getConditionValue() == null ? "" : dataGuide.getConditionValue());
        jsContent = jsContent.replaceAll("\\$\\{@fieldName\\}", logicFiledName == null ? "" : logicFiledName);
        jsContent = jsContent.replaceAll("\\$\\{@targetFieldName\\}", targetTag.getName() == null ? "" : targetTag.getName());
        jsContent = jsContent.replaceAll("\\$\\{@readOnlyFlag\\}", dataGuide.getReadonlyFlag() == null ? "" : dataGuide.getReadonlyFlag());
        return jsContent;
    }

    @Override
    public String getChangeRequired(IRequest request, PageTagDataGuide dataGuide) {
        PageTagBasic tag = getPageTagBasic(request, dataGuide.getTagId());
        String logicFiledName = getCurLogicFieldName(request, dataGuide);
        PageTagBasic targetTag = getPageTagBasic(request, dataGuide.getTargetTagId());
        String jsContent = getJsContent(request, JsTemplate.JS_TYPE_CHANGE_REQUIRED);

        jsContent = jsContent.replaceAll("\\$\\{@triggerCondition\\}", dataGuide.getTriggerCondition() == null ? "" : dataGuide.getTriggerCondition());
        jsContent = jsContent.replaceAll("\\$\\{@conditionValue\\}", dataGuide.getConditionValue() == null ? "" : dataGuide.getConditionValue());
        jsContent = jsContent.replaceAll("\\$\\{@fieldName\\}", logicFiledName == null ? "" : logicFiledName);
        jsContent = jsContent.replaceAll("\\$\\{@targetFieldName\\}", targetTag.getName() == null ? "" : targetTag.getName());
        jsContent = jsContent.replaceAll("\\$\\{@requiredFlag\\}", dataGuide.getRequiredFlag() == null ? "" : dataGuide.getRequiredFlag());
        return jsContent;
    }

    @Override
    public String getChangeClear(IRequest request, PageTagDataGuide dataGuide) {
        PageTagBasic tag = getPageTagBasic(request, dataGuide.getTagId());
        PageTagBasic targetTag = getPageTagBasic(request, dataGuide.getTargetTagId());
        PageLayoutBasic sourceLayout = getPageLayoutBasic(request, tag.getLayoutId());
        PageLayoutBasic targetLayout = getPageLayoutBasic(request, targetTag.getLayoutId());
        String logicFiledName = getCurLogicFieldName(request, dataGuide);
        String jsContent = getJsContent(request, JsTemplate.JS_TYPE_CHANGE_CLEAR);

        jsContent = jsContent.replaceAll("\\$\\{@triggerCondition\\}", dataGuide.getTriggerCondition() == null ? "" : dataGuide.getTriggerCondition());
        jsContent = jsContent.replaceAll("\\$\\{@conditionValue\\}", dataGuide.getConditionValue() == null ? "" : dataGuide.getConditionValue());
        jsContent = jsContent.replaceAll("\\$\\{@fieldName\\}", logicFiledName == null ? "" : logicFiledName);
        jsContent = jsContent.replaceAll("\\$\\{@targetFieldName\\}", targetTag.getName() == null ? "" : targetTag.getName());
        jsContent = jsContent.replaceAll("\\$\\{@clearFlag\\}", dataGuide.getClearFlag() == null ? "" : dataGuide.getClearFlag());
        jsContent = jsContent.replaceAll("\\$\\{@targetDs\\}", targetLayout.getDataset() == null ? "" : targetLayout.getDataset());
        jsContent = jsContent.replaceAll("\\$\\{@sourceDs\\}", sourceLayout.getDataset() == null ? "" : sourceLayout.getDataset());

        return jsContent;
    }

    @Override
    public String getChangeHidden(IRequest request, PageTagDataGuide dataGuide) {
        PageTagBasic tag = getPageTagBasic(request, dataGuide.getTagId());
        PageTagBasic targetTag = getPageTagBasic(request, dataGuide.getTargetTagId());
        PageLayoutBasic targetLayout = getPageLayoutBasic(request, dataGuide.getTargetLayoutId());
        String logicFiledName = getCurLogicFieldName(request, dataGuide);
        String jsContent = getJsContent(request, JsTemplate.JS_TYPE_CHANGE_HIDDEN);

        jsContent = jsContent.replaceAll("\\$\\{@triggerCondition\\}", dataGuide.getTriggerCondition() == null ? "" : dataGuide.getTriggerCondition());
        jsContent = jsContent.replaceAll("\\$\\{@conditionValue\\}", dataGuide.getConditionValue() == null ? "" : dataGuide.getConditionValue());
        jsContent = jsContent.replaceAll("\\$\\{@fieldName\\}", logicFiledName == null ? "" : logicFiledName);
        jsContent = jsContent.replaceAll("\\$\\{@targetLayoutId\\}", targetLayout.getId() == null ? "" : targetLayout.getId());
        jsContent = jsContent.replaceAll("\\$\\{@targetTagId\\}", targetTag.getId() == null ? "" : targetTag.getId());
        jsContent = jsContent.replaceAll("\\$\\{@targetLayoutType\\}", targetLayout.getLayoutType() == null ? "" : targetLayout.getLayoutType());
        jsContent = jsContent.replaceAll("\\$\\{@hidden_flag\\}", dataGuide.getHiddenFlag() == null ? "" : dataGuide.getHiddenFlag());

        return jsContent;
    }

    @Override
    public String getChangeLayoutHideShow(IRequest request, PageTagDataGuide dataGuide) {
        PageTagBasic tag = getPageTagBasic(request, dataGuide.getTagId());
        PageTagBasic targetTag = getPageTagBasic(request, dataGuide.getTargetTagId());
        PageLayoutBasic targetLayout = getPageLayoutBasic(request, dataGuide.getTargetLayoutId());
        String logicFiledName = getCurLogicFieldName(request, dataGuide);
        String jsContent = getJsContent(request, JsTemplate.JS_TYPE_CHANGE_LAYOUT_HIDDEN);

        jsContent = jsContent.replaceAll("\\$\\{@triggerCondition\\}", dataGuide.getTriggerCondition() == null ? "" : dataGuide.getTriggerCondition());
        jsContent = jsContent.replaceAll("\\$\\{@conditionValue\\}", dataGuide.getConditionValue() == null ? "" : dataGuide.getConditionValue());
        jsContent = jsContent.replaceAll("\\$\\{@fieldName\\}", logicFiledName == null ? "" : logicFiledName);
        jsContent = jsContent.replaceAll("\\$\\{@targetLayoutId\\}", targetLayout.getId() == null ? "" : targetLayout.getId());
        jsContent = jsContent.replaceAll("\\$\\{@targetTagId\\}", targetTag.getId() == null ? "" : targetTag.getId());
        jsContent = jsContent.replaceAll("\\$\\{@targetLayoutType\\}", targetLayout.getLayoutType() == null ? "" : targetLayout.getLayoutType());
        jsContent = jsContent.replaceAll("\\$\\{@hidden_flag\\}", dataGuide.getHiddenFlag() == null ? "" : dataGuide.getHiddenFlag());

        return jsContent;
    }

    @Override
    public String getChangeLovParam(IRequest request, PageTagDataGuide dataGuide) {
        PageTagBasic tag = getPageTagBasic(request, dataGuide.getTagId());
        PageTagBasic targetTag = getPageTagBasic(request, dataGuide.getTargetTagId());
        PageLayoutBasic targetLayout = getPageLayoutBasic(request, dataGuide.getTargetLayoutId());
        String targetDsName = "";
        if (PageTagBasic.TAG_TYPE_COMBOBOX.equals(targetTag.getTagType())) {
            PageTagCombobox combobox = new PageTagCombobox();
            combobox.setTagId(targetTag.getTagId());
            combobox = comboboxService.selectByPrimaryKey(request, combobox);
            if (PageTagCombobox.DATASOURCE_SYSCODE.equals(combobox.getDatasource())) {
                targetDsName = targetLayout.getId() + '_' + targetTag.getName() + "_syscode_" + combobox.getSyscode()
                        + "_ds";
            } else if (PageTagCombobox.DATASOURCE_LOVCODE.equals(combobox.getDatasource())) {
                targetDsName = targetLayout.getId() + '_' + targetTag.getName() + "_lovcode_" + combobox.getLovcode()
                        + "_ds";
            }
        }
        String logicFiledName = getCurLogicFieldName(request, dataGuide);
        String belongFieldName = getBelongFieldName(request, tag.getFieldId());
        String jsContent = getJsContent(request, JsTemplate.JS_TYPE_CHANGE_LOV_PARA);


        jsContent = jsContent.replaceAll("\\$\\{@triggerCondition\\}", dataGuide.getTriggerCondition() == null ? "" : dataGuide.getTriggerCondition());
        jsContent = jsContent.replaceAll("\\$\\{@conditionValue\\}", dataGuide.getConditionValue() == null ? "" : dataGuide.getConditionValue());
        jsContent = jsContent.replaceAll("\\$\\{@fieldName\\}", belongFieldName != null ? belongFieldName : logicFiledName);
        jsContent = jsContent.replaceAll("\\$\\{@targetFieldName\\}", targetTag.getName() == null ? "" : targetTag.getName());
        jsContent = jsContent.replaceAll("\\$\\{@filterName\\}", dataGuide.getFilterName() == null ? "" : dataGuide.getFilterName());
        jsContent = jsContent.replaceAll("\\$\\{@targetTagType\\}", targetTag.getTagType() == null ? "" : targetTag.getTagType());
        jsContent = jsContent.replaceAll("\\$\\{@targetDS\\}", targetDsName == null ? "" : targetDsName);

        return jsContent;
    }

    @Override
    public String getChangeGridColHideShow(IRequest request, PageTagDataGuide dataGuide) {
        PageTagBasic tag = getPageTagBasic(request, dataGuide.getTagId());
        PageTagBasic targetTag = getPageTagBasic(request, dataGuide.getTargetTagId());
        PageLayoutBasic targetLayout = getPageLayoutBasic(request, dataGuide.getTargetLayoutId());
        String logicFiledName = getCurLogicFieldName(request, dataGuide);
        String jsContent = getJsContent(request, JsTemplate.JS_TYPE_CHANGE_GRID_COL_HIDE_SHOW);

        jsContent = jsContent.replaceAll("\\$\\{@triggerCondition\\}", dataGuide.getTriggerCondition() == null ? "" : dataGuide.getTriggerCondition());
        jsContent = jsContent.replaceAll("\\$\\{@conditionValue\\}", dataGuide.getConditionValue() == null ? "" : dataGuide.getConditionValue());
        jsContent = jsContent.replaceAll("\\$\\{@fieldName\\}", logicFiledName == null ? "" : logicFiledName);
        jsContent = jsContent.replaceAll("\\$\\{@targetFieldName\\}", targetTag.getName() == null ? "" : targetTag.getName());
        jsContent = jsContent.replaceAll("\\$\\{@targetLayoutId\\}", targetLayout.getId() == null ? "" : targetLayout.getId());
        jsContent = jsContent.replaceAll("\\$\\{@hidden_flag\\}", dataGuide.getHiddenFlag() == null ? "" : dataGuide.getHiddenFlag());

        return jsContent;
    }

    @Override
    public String getLoadReadonly(IRequest request, PageTagDataGuide dataGuide) {
        PageTagBasic tag = getPageTagBasic(request, dataGuide.getTagId());
        String logicFiledName = getCurLogicFieldName(request, dataGuide);
        PageTagBasic targetTag = getPageTagBasic(request, dataGuide.getTargetTagId());
        String jsContent = getJsContent(request, JsTemplate.JS_TYPE_LOAD_READONLY);

        jsContent = jsContent.replaceAll("\\$\\{@triggerCondition\\}", dataGuide.getTriggerCondition() == null ? "" : dataGuide.getTriggerCondition());
        jsContent = jsContent.replaceAll("\\$\\{@conditionValue\\}", dataGuide.getConditionValue() == null ? "" : dataGuide.getConditionValue());
        jsContent = jsContent.replaceAll("\\$\\{@fieldName\\}", logicFiledName == null ? "" : logicFiledName);
        jsContent = jsContent.replaceAll("\\$\\{@targetFieldName\\}", targetTag.getName() == null ? "" : targetTag.getName());
        jsContent = jsContent.replaceAll("\\$\\{@readOnlyFlag\\}", dataGuide.getReadonlyFlag() == null ? "" : dataGuide.getReadonlyFlag());
        return jsContent;
    }

    @Override
    public String getLoadRequired(IRequest request, PageTagDataGuide dataGuide) {
        PageTagBasic tag = getPageTagBasic(request, dataGuide.getTagId());
        String logicFiledName = getCurLogicFieldName(request, dataGuide);
        PageTagBasic targetTag = getPageTagBasic(request, dataGuide.getTargetTagId());
        String jsContent = getJsContent(request, JsTemplate.JS_TYPE_LOAD_REQUIRED);

        jsContent = jsContent.replaceAll("\\$\\{@triggerCondition\\}", dataGuide.getTriggerCondition() == null ? "" : dataGuide.getTriggerCondition());
        jsContent = jsContent.replaceAll("\\$\\{@conditionValue\\}", dataGuide.getConditionValue() == null ? "" : dataGuide.getConditionValue());
        jsContent = jsContent.replaceAll("\\$\\{@fieldName\\}", logicFiledName == null ? "" : logicFiledName);
        jsContent = jsContent.replaceAll("\\$\\{@targetFieldName\\}", targetTag.getName() == null ? "" : targetTag.getName());
        jsContent = jsContent.replaceAll("\\$\\{@requiredFlag\\}", dataGuide.getRequiredFlag() == null ? "" : dataGuide.getRequiredFlag());
        return jsContent;
    }

    @Override
    public String getLoadClear(IRequest request, PageTagDataGuide dataGuide) {
        PageTagBasic tag = getPageTagBasic(request, dataGuide.getTagId());
        PageTagBasic targetTag = getPageTagBasic(request, dataGuide.getTargetTagId());
        PageLayoutBasic sourceLayout = getPageLayoutBasic(request, tag.getLayoutId());
        PageLayoutBasic targetLayout = getPageLayoutBasic(request, targetTag.getLayoutId());
        String logicFiledName = getCurLogicFieldName(request, dataGuide);
        String jsContent = getJsContent(request, JsTemplate.JS_TYPE_LOAD_CLEAR);

        jsContent = jsContent.replaceAll("\\$\\{@triggerCondition\\}", dataGuide.getTriggerCondition() == null ? "" : dataGuide.getTriggerCondition());
        jsContent = jsContent.replaceAll("\\$\\{@conditionValue\\}", dataGuide.getConditionValue() == null ? "" : dataGuide.getConditionValue());
        jsContent = jsContent.replaceAll("\\$\\{@fieldName\\}", logicFiledName == null ? "" : logicFiledName);
        jsContent = jsContent.replaceAll("\\$\\{@targetFieldName\\}", targetTag.getName() == null ? "" : targetTag.getName());
        jsContent = jsContent.replaceAll("\\$\\{@clearFlag\\}", dataGuide.getClearFlag() == null ? "" : dataGuide.getClearFlag());
        jsContent = jsContent.replaceAll("\\$\\{@targetDs\\}", targetLayout.getDataset() == null ? "" : targetLayout.getDataset());
        jsContent = jsContent.replaceAll("\\$\\{@sourceDs\\}", sourceLayout.getDataset() == null ? "" : sourceLayout.getDataset());

        return jsContent;
    }

    @Override
    public String getLoadHidden(IRequest request, PageTagDataGuide dataGuide) {
        PageTagBasic tag = getPageTagBasic(request, dataGuide.getTagId());
        PageTagBasic targetTag = getPageTagBasic(request, dataGuide.getTargetTagId());
        PageLayoutBasic targetLayout = getPageLayoutBasic(request, dataGuide.getTargetLayoutId());
        String logicFiledName = getCurLogicFieldName(request, dataGuide);
        String jsContent = getJsContent(request, JsTemplate.JS_TYPE_LOAD_HIDDEN);

        jsContent = jsContent.replaceAll("\\$\\{@triggerCondition\\}", dataGuide.getTriggerCondition() == null ? "" : dataGuide.getTriggerCondition());
        jsContent = jsContent.replaceAll("\\$\\{@conditionValue\\}", dataGuide.getConditionValue() == null ? "" : dataGuide.getConditionValue());
        jsContent = jsContent.replaceAll("\\$\\{@fieldName\\}", logicFiledName == null ? "" : logicFiledName);
        jsContent = jsContent.replaceAll("\\$\\{@targetLayoutId\\}", targetLayout.getId() == null ? "" : targetLayout.getId());
        jsContent = jsContent.replaceAll("\\$\\{@targetTagId\\}", targetTag.getId() == null ? "" : targetTag.getId());
        jsContent = jsContent.replaceAll("\\$\\{@targetLayoutType\\}", targetLayout.getLayoutType() == null ? "" : targetLayout.getLayoutType());
        jsContent = jsContent.replaceAll("\\$\\{@hidden_flag\\}", dataGuide.getHiddenFlag() == null ? "" : dataGuide.getHiddenFlag());

        return jsContent;
    }

    @Override
    public String getLoadLayoutHideShow(IRequest request, PageTagDataGuide dataGuide) {
        PageTagBasic tag = getPageTagBasic(request, dataGuide.getTagId());
        PageTagBasic targetTag = getPageTagBasic(request, dataGuide.getTargetTagId());
        PageLayoutBasic targetLayout = getPageLayoutBasic(request, dataGuide.getTargetLayoutId());
        String logicFiledName = getCurLogicFieldName(request, dataGuide);
        String jsContent = getJsContent(request, JsTemplate.JS_TYPE_LOAD_LAYOUT_HIDDEN);

        jsContent = jsContent.replaceAll("\\$\\{@triggerCondition\\}", dataGuide.getTriggerCondition() == null ? "" : dataGuide.getTriggerCondition());
        jsContent = jsContent.replaceAll("\\$\\{@conditionValue\\}", dataGuide.getConditionValue() == null ? "" : dataGuide.getConditionValue());
        jsContent = jsContent.replaceAll("\\$\\{@fieldName\\}", logicFiledName == null ? "" : logicFiledName);
        jsContent = jsContent.replaceAll("\\$\\{@targetLayoutId\\}", targetLayout.getId() == null ? "" : targetLayout.getId());
        jsContent = jsContent.replaceAll("\\$\\{@targetTagId\\}", targetTag.getId() == null ? "" : targetTag.getId());
        jsContent = jsContent.replaceAll("\\$\\{@targetLayoutType\\}", targetLayout.getLayoutType() == null ? "" : targetLayout.getLayoutType());
        jsContent = jsContent.replaceAll("\\$\\{@hidden_flag\\}", dataGuide.getHiddenFlag() == null ? "" : dataGuide.getHiddenFlag());

        return jsContent;
    }

    @Override
    public String getLoadLovParam(IRequest request, PageTagDataGuide dataGuide) {
        PageTagBasic tag = getPageTagBasic(request, dataGuide.getTagId());
        PageTagBasic targetTag = getPageTagBasic(request, dataGuide.getTargetTagId());
        PageLayoutBasic targetLayout = getPageLayoutBasic(request, dataGuide.getTargetLayoutId());
        String targetDsName = "";
        if (PageTagBasic.TAG_TYPE_COMBOBOX.equals(targetTag.getTagType())) {
            PageTagCombobox combobox = new PageTagCombobox();
            combobox.setTagId(targetTag.getTagId());
            combobox = comboboxService.selectByPrimaryKey(request, combobox);
            if (PageTagCombobox.DATASOURCE_SYSCODE.equals(combobox.getDatasource())) {
                targetDsName = targetLayout.getId() + '_' + targetTag.getName() + "_syscode_" + combobox.getSyscode()
                        + "_ds";
            } else if (PageTagCombobox.DATASOURCE_LOVCODE.equals(combobox.getDatasource())) {
                targetDsName = targetLayout.getId() + '_' + targetTag.getName() + "_lovcode_" + combobox.getLovcode()
                        + "_ds";
            }
        }
        String logicFiledName = getCurLogicFieldName(request, dataGuide);
        String belongFieldName = getBelongFieldName(request, tag.getFieldId());
        String jsContent = getJsContent(request, JsTemplate.JS_TYPE_LOAD_LOV_PARA);


        jsContent = jsContent.replaceAll("\\$\\{@triggerCondition\\}", dataGuide.getTriggerCondition() == null ? "" : dataGuide.getTriggerCondition());
        jsContent = jsContent.replaceAll("\\$\\{@conditionValue\\}", dataGuide.getConditionValue() == null ? "" : dataGuide.getConditionValue());
        jsContent = jsContent.replaceAll("\\$\\{@fieldName\\}", belongFieldName != null ? belongFieldName : logicFiledName);
        jsContent = jsContent.replaceAll("\\$\\{@targetFieldName\\}", targetTag.getName() == null ? "" : targetTag.getName());
        jsContent = jsContent.replaceAll("\\$\\{@filterName\\}", dataGuide.getFilterName() == null ? "" : dataGuide.getFilterName());
        jsContent = jsContent.replaceAll("\\$\\{@targetTagType\\}", targetTag.getTagType() == null ? "" : targetTag.getTagType());
        jsContent = jsContent.replaceAll("\\$\\{@targetDS\\}", targetDsName == null ? "" : targetDsName);

        return jsContent;
    }

    @Override
    public String getLoadGridColHideShow(IRequest request, PageTagDataGuide dataGuide) {
        PageTagBasic tag = getPageTagBasic(request, dataGuide.getTagId());
        PageTagBasic targetTag = getPageTagBasic(request, dataGuide.getTargetTagId());
        PageLayoutBasic targetLayout = getPageLayoutBasic(request, dataGuide.getTargetLayoutId());
        String logicFiledName = getCurLogicFieldName(request, dataGuide);
        String jsContent = getJsContent(request, JsTemplate.JS_TYPE_LOAD_GRID_COL_HIDE_SHOW);

        jsContent = jsContent.replaceAll("\\$\\{@triggerCondition\\}", dataGuide.getTriggerCondition() == null ? "" : dataGuide.getTriggerCondition());
        jsContent = jsContent.replaceAll("\\$\\{@conditionValue\\}", dataGuide.getConditionValue() == null ? "" : dataGuide.getConditionValue());
        jsContent = jsContent.replaceAll("\\$\\{@fieldName\\}", logicFiledName == null ? "" : logicFiledName);
        jsContent = jsContent.replaceAll("\\$\\{@targetFieldName\\}", targetTag.getName() == null ? "" : targetTag.getName());
        jsContent = jsContent.replaceAll("\\$\\{@targetLayoutId\\}", targetLayout.getId() == null ? "" : targetLayout.getId());
        jsContent = jsContent.replaceAll("\\$\\{@hidden_flag\\}", dataGuide.getHiddenFlag() == null ? "" : dataGuide.getHiddenFlag());

        return jsContent;
    }

    @Override
    public String getCellClickLovPara(IRequest request, PageTagDataGuide dataGuide) {
        PageTagBasic tag = getPageTagBasic(request, dataGuide.getTagId());
        PageTagBasic targetTag = getPageTagBasic(request, dataGuide.getTargetTagId());
        PageLayoutBasic targetLayout = getPageLayoutBasic(request, dataGuide.getTargetLayoutId());
        PageLayoutBasic sourceLayout = getPageLayoutBasic(request, tag.getLayoutId());
        String targetDsName = "";
        if (PageTagBasic.TAG_TYPE_COMBOBOX.equals(targetTag.getTagType())) {
            PageTagCombobox combobox = new PageTagCombobox();
            combobox.setTagId(targetTag.getTagId());
            combobox = comboboxService.selectByPrimaryKey(request, combobox);
            if (PageTagCombobox.DATASOURCE_SYSCODE.equals(combobox.getDatasource())) {
                targetDsName = targetLayout.getId() + '_' + targetTag.getName() + "_syscode_" + combobox.getSyscode()
                        + "_ds";
            } else if (PageTagCombobox.DATASOURCE_LOVCODE.equals(combobox.getDatasource())) {
                targetDsName = targetLayout.getId() + '_' + targetTag.getName() + "_lovcode_" + combobox.getLovcode()
                        + "_ds";
            }
        }
        String logicFiledName = getCurLogicFieldName(request, dataGuide);
        String belongFieldName = getBelongFieldName(request, tag.getFieldId());
        String jsContent = getJsContent(request, JsTemplate.JS_TYPE_CELL_CLICK_LOV_PARA);


        jsContent = jsContent.replaceAll("\\$\\{@triggerCondition\\}", dataGuide.getTriggerCondition() == null ? "" : dataGuide.getTriggerCondition());
        jsContent = jsContent.replaceAll("\\$\\{@conditionValue\\}", dataGuide.getConditionValue() == null ? "" : dataGuide.getConditionValue());
        jsContent = jsContent.replaceAll("\\$\\{@fieldName\\}", belongFieldName != null ? belongFieldName : logicFiledName);
        jsContent = jsContent.replaceAll("\\$\\{@targetFieldName\\}", targetTag.getName() == null ? "" : targetTag.getName());
        jsContent = jsContent.replaceAll("\\$\\{@filterName\\}", dataGuide.getFilterName() == null ? "" : dataGuide.getFilterName());
        jsContent = jsContent.replaceAll("\\$\\{@targetTagType\\}", targetTag.getTagType() == null ? "" : targetTag.getTagType());
        jsContent = jsContent.replaceAll("\\$\\{@targetDS\\}", targetDsName == null ? "" : targetDsName);
        jsContent = jsContent.replaceAll("\\$\\{@sourceDsId\\}", sourceLayout.getDataset() == null ? "" : sourceLayout.getDataset());

        return jsContent;
    }

    @Override
    public String getChangeFormula(IRequest request, PageTagDataGuide dataGuide,PageTagBasic pageTagBasic,PageTagBasic resultTagBasic,String formulaType,String precision){
        String jsContent = getJsContent(request,JsTemplate.JS_TYPE_CHANGE_FORMULA);
        jsContent = jsContent.replaceAll("\\$\\{@fieldName\\}", pageTagBasic.getName() == null ? "" : pageTagBasic.getName());
        jsContent = jsContent.replaceAll("\\$\\{@resultField\\}", resultTagBasic.getName() == null ? "" : resultTagBasic.getName());
        jsContent = jsContent.replaceAll("\\$\\{@formula\\}", dataGuide.getFormula() == null ? "" : dataGuide.getFormula());
        jsContent = jsContent.replaceAll("\\$\\{@formulaType\\}", formulaType);
        jsContent = jsContent.replaceAll("\\$\\{@precision\\}", precision);
        return jsContent;
    }
}