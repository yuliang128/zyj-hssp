package com.hand.hec.bpm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.*;
import com.hand.hec.bpm.service.*;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageTagDataGuideService extends BaseServiceImpl<PageTagDataGuide> implements IPageTagDataGuideService {

    @Autowired
    IPageLayoutBasicService layoutBasicService;

    @Autowired
    IPageTagBasicService tagBasicService;

    @Autowired
    IJsTemplateService jsTemplateService;

    @Autowired
    IPageTagEventService tagEventService;

    @Autowired
    IPageLayoutEventService layoutEventService;


    @Override
    public void clearDataGuideEvent(IRequest request, PageTagBasic tag, List<PageTagDataGuide> list) {
        PageTagDataGuide queryDataGuide = new PageTagDataGuide();
        queryDataGuide.setTagId(tag.getTagId());
        Criteria criteria = new Criteria(queryDataGuide);
        criteria.where(new WhereField(PageTagDataGuide.FIELD_TAG_ID, Comparison.EQUAL));
        List<PageTagDataGuide> dataGuideList = new ArrayList<>();
        if(list == null){
            dataGuideList = selectOptions(request, queryDataGuide, criteria, 0, 0);
        }
        else{
            dataGuideList = list;
        }


        for (PageTagDataGuide dataGuide : dataGuideList) {
            PageTagEvent queryTagEvent = new PageTagEvent();
            queryTagEvent.setGuideId(dataGuide.getGuideId());
            Criteria tagEventCtr = new Criteria(queryTagEvent);
            criteria.where(new WhereField(PageTagEvent.FIELD_GUIDE_ID, Comparison.EQUAL));
            if(dataGuide.getGuideId() != null){
                List<PageTagEvent> tagEventList = tagEventService.selectOptions(request, queryTagEvent, tagEventCtr, 0, 0);
                tagEventService.batchDelete(tagEventList);
            }


            PageLayoutEvent queryLayoutEvent = new PageLayoutEvent();
            queryLayoutEvent.setGuideId(dataGuide.getGuideId());
            Criteria layoutEventCtr = new Criteria(queryLayoutEvent);
            criteria.where(new WhereField(PageLayoutEvent.FIELD_GUIDE_ID, Comparison.EQUAL));
            if(dataGuide.getGuideId() != null){
                List<PageLayoutEvent> layoutEventList = layoutEventService.selectOptions(request, queryLayoutEvent, layoutEventCtr, 0, 0);
                layoutEventService.batchDelete(layoutEventList);
            }

        }
    }

    @Override
    public void generateDataGuideEvent(IRequest request, PageTagBasic tag, List<PageTagDataGuide> list) {
        PageTagDataGuide queryDataGuide = new PageTagDataGuide();
        queryDataGuide.setTagId(tag.getTagId());
        Criteria criteria = new Criteria(queryDataGuide);
        criteria.where(new WhereField(PageTagDataGuide.FIELD_TAG_ID, Comparison.EQUAL));
        List<PageTagDataGuide> dataGuideList = new ArrayList<>();
        if(list == null){
            dataGuideList = selectOptions(request, queryDataGuide, criteria, 0, 0);
        }else{
            dataGuideList = list;
        }

        for (PageTagDataGuide dataGuide : dataGuideList) {
            if(!DTOStatus.DELETE.equals(dataGuide.get__status())){
                PageLayoutBasic sourceLayout = new PageLayoutBasic();
                sourceLayout.setLayoutId(tag.getLayoutId());
                sourceLayout = layoutBasicService.selectByPrimaryKey(request, sourceLayout);

                PageLayoutBasic targetLayout = new PageLayoutBasic();
                targetLayout.setLayoutId(dataGuide.getTargetLayoutId());
                targetLayout = layoutBasicService.selectByPrimaryKey(request, targetLayout);

                PageTagBasic targetTag = new PageTagBasic();
                targetTag.setTagId(dataGuide.getTargetTagId());
                targetTag = tagBasicService.selectByPrimaryKey(request, targetTag);


                Boolean isSourceTargetSame = targetLayout == null ? false : sourceLayout.getLayoutId().equals(targetLayout.getLayoutId());

                //获取值变更时的数据向导
                if ("VALUE_CHANGE".equals(dataGuide.getTriggerType())) {
                    Boolean emptyFlag = true;
                    String jsContent = "function(_dataSet,_record,_name,_value,_oldValue){ \n";

                    //变更只读
                    if ((dataGuide.getReadonlyFlag() != null && !"NONE".equals(dataGuide.getReadonlyFlag())) && isSourceTargetSame) {
                        jsContent += jsTemplateService.getChangeReadonly(request, dataGuide)
                                + "\n\n";
                        emptyFlag = false;
                    }

                    //变更必输
                    if ((dataGuide.getRequiredFlag() != null && !"NONE".equals(dataGuide.getRequiredFlag())) && isSourceTargetSame) {
                        jsContent += jsTemplateService.getChangeRequired(request, dataGuide)
                                + "\n\n";
                        emptyFlag = false;
                    }


                    //变更清除
                    if ((dataGuide.getClearFlag() != null && !"NONE".equals(dataGuide.getRequiredFlag()))) {
                        jsContent += jsTemplateService.getChangeClear(request, dataGuide)
                                + "\n\n";
                        emptyFlag = false;
                    }


                    //变更Lov参数
                    if (dataGuide.getFilterName() != null && isSourceTargetSame) {
                        jsContent += jsTemplateService.getChangeLovParam(request, dataGuide)
                                + "\n\n";
                        emptyFlag = false;
                    }


                    //变更隐藏
                    if ((dataGuide.getHiddenFlag() != null && !"NONE".equals(dataGuide.getHiddenFlag())) && ("grid".equals(targetLayout.getLayoutType()) || "table".equals(targetLayout.getLayoutType())) && dataGuide.getTargetTagId() != null) {
                        //Grid上字段隐藏
                        jsContent += jsTemplateService.getChangeGridColHideShow(request, dataGuide)
                                + "\n\n";
                        emptyFlag = false;
                    } else if (dataGuide.getHiddenFlag() != null && !"NONE".equals(dataGuide.getHiddenFlag())) {
                        //Tag隐藏
                        jsContent += jsTemplateService.getChangeHidden(request, dataGuide)
                                + "\n\n";
                        emptyFlag = false;
                    }

                    jsContent += "}";

                    //保存值变更的事件
                    if (!emptyFlag) {
                        PageTagEvent event = new PageTagEvent();
                        event.setTagId(tag.getTagId());
                        event.setGuideId(dataGuide.getGuideId());
                        event.setEventHandler(jsContent);
                        event.setEventTarget("DATASET");
                        event.setEventType("update");

                        event = tagEventService.insert(request, event);

                        dataGuide.setUpdateEventId(event.getEventId());
                        updateByPrimaryKey(request, dataGuide);
                    }

                    //值加载事件生成
                    emptyFlag = true;
                    jsContent = "function(_dataSet){ \n";

                    //变更只读
                    if ((dataGuide.getReadonlyFlag() != null && !"NONE".equals(dataGuide.getReadonlyFlag())) && isSourceTargetSame) {
                        jsContent += jsTemplateService.getLoadReadonly(request, dataGuide)
                                + "\n\n";
                        emptyFlag = false;
                    }

                    //变更必输
                    if ((dataGuide.getRequiredFlag() != null && !"NONE".equals(dataGuide.getRequiredFlag())) && isSourceTargetSame) {
                        jsContent += jsTemplateService.getLoadRequired(request, dataGuide)
                                + "\n\n";
                        emptyFlag = false;
                    }


                    //变更清除
                    if ((dataGuide.getClearFlag() != null && !"NONE".equals(dataGuide.getRequiredFlag()))) {
                        jsContent += jsTemplateService.getLoadClear(request, dataGuide)
                                + "\n\n";
                        emptyFlag = false;
                    }


                    //变更Lov参数
                    if (dataGuide.getFilterName() != null && isSourceTargetSame) {
                        jsContent += jsTemplateService.getLoadLovParam(request, dataGuide)
                                + "\n\n";
                        emptyFlag = false;
                    }


                    //变更隐藏
                    if ((dataGuide.getHiddenFlag() != null && !"NONE".equals(dataGuide.getHiddenFlag())) && ("grid".equals(targetLayout.getLayoutType()) || "table".equals(targetLayout.getLayoutType())) && dataGuide.getTargetTagId() != null) {
                        //Grid上字段隐藏
                        jsContent += jsTemplateService.getLoadGridColHideShow(request, dataGuide)
                                + "\n\n";
                        emptyFlag = false;
                    } else if (dataGuide.getHiddenFlag() != null && !"NONE".equals(dataGuide.getHiddenFlag())) {
                        //Tag隐藏
                        jsContent += jsTemplateService.getLoadHidden(request, dataGuide)
                                + "\n\n";
                        emptyFlag = false;
                    }

                    jsContent += "}";

                    //保存值变更的事件
                    if (!emptyFlag) {
                        PageTagEvent event = new PageTagEvent();
                        event.setTagId(tag.getTagId());
                        event.setGuideId(dataGuide.getGuideId());
                        event.setEventHandler(jsContent);
                        event.setEventTarget("DATASET");
                        event.setEventType("load");

                        event = tagEventService.insert(request, event);

                        dataGuide.setLoadEventId(event.getEventId());
                        updateByPrimaryKey(request, dataGuide);
                    }
                }

                //Grid上的cellclick事件触发
                // 1、目标布局组件不为空
                // 2、目标布局组件为Table或Grid
                // 4、(目标布局组件与来源布局组件不一致 且 目标组件为Lov) 或  (目标布局组件与来源布局组件一致 且 目标组件为combobox)
                // 5、filterName不为空
                if ("VALUE_CHANGE".equals(dataGuide.getTriggerType())
                        && targetLayout != null
                        && ("table".equals(targetLayout.getLayoutType()) || "grid".equals(targetLayout.getLayoutType()))
                        && targetTag != null
                        && dataGuide.getFilterName() != null
                        &&
                        ((!isSourceTargetSame && ("lov".equals(targetTag.getTagType())))
                                ||
                                (isSourceTargetSame && "comboBox".equals(targetTag.getTagType())))) {
                    String jsContent = "function(_grid,_row,_name,_record){ \n\n";
                    jsContent += jsTemplateService.getCellClickLovPara(request, dataGuide) + "\n\n";
                    jsContent += "}";

                    PageLayoutEvent event = new PageLayoutEvent();
                    event.setLayoutId(tag.getLayoutId());
                    event.setGuideId(dataGuide.getGuideId());
                    event.setEventHandler(jsContent);
                    event.setEventTarget("LAYOUT");
                    event.setEventType("cellclick");

                    event = layoutEventService.insert(request, event);
                }

                generateDataGuideFormula(request,dataGuide, tag);
            }
        }
    }

    public void generateDataGuideFormula(IRequest request,PageTagDataGuide dataGuide, PageTagBasic resultTag) {

        //公式计算分为三种：
        //1.普通公式计算，MATH[#字段1#*#字段2#-#字段3#][小数精度]
        //2.算数平均值，AVG[#字段1+#字段2#+#字段3#][小数精度]
        //3.字符串拼接，#字段1#+ '--' + #字段2# + '--' +#字段3#
        if("VALUE_CHANGE".equals(dataGuide.getTriggerType()) && dataGuide.getFormula()!=null){
            String formula = dataGuide.getFormula();
            String formulaType = "";
            if(formula.indexOf("MATH")!=-1){
                formulaType = "MATH";
                formula = formula.replace("MATH","");
            }else if(formula.indexOf("AVG")!=-1){
                formulaType = "AVG";
                formula = formula.replace("AVG","");
            }
            String precision = "";
            String precisionReg = "\\[[0-9]{1}\\]";
            Pattern precisionPattern = Pattern.compile(precisionReg);
            Matcher precisionMatcher = precisionPattern.matcher(formula);
            if(precisionMatcher.find()){
                precision = precisionMatcher.group().replaceAll("\\[","").replaceAll("\\]","");
                formula = formula.replace(precisionMatcher.group(),"");
            }

            String  reg = "(#[^#]+#)";
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(formula);
            List<PageTagBasic> formulaList = new ArrayList<>();
            while(m.find()) {
                PageTagBasic pageTagBasic = new PageTagBasic();
                pageTagBasic.setLayoutId(resultTag.getLayoutId());
                pageTagBasic.setTagDesc(m.group(0).substring(1, m.group(0).length() - 1));
                List<PageTagBasic> tagList = tagBasicService.selectOptions(request, pageTagBasic, new Criteria(pageTagBasic));
                if (tagList.size() >0 && tagList.get(0) != null) {
                    pageTagBasic = tagList.get(0);
                    formula = formula.replaceAll(m.group(0), "_record.get('"+pageTagBasic.getName()+"')");
                    formulaList.add(pageTagBasic);
                }
            }
            formula = formula.replaceAll("\\[","").replaceAll("\\]","");
            if(!isContainChinese(formula)){
                dataGuide.setFormula(formula);
                for(PageTagBasic pageTagBasic : formulaList){
                    String jsFormula = "function(_dataSet,_record,_name,_value,_oldValue){ \n"
                            +jsTemplateService.getChangeFormula(request, dataGuide,pageTagBasic,resultTag,formulaType,precision)+"\n\n"
                            +" }";
                    PageTagEvent event = new PageTagEvent();
                    event.setGuideId(dataGuide.getGuideId());
                    event.setTagId(pageTagBasic.getTagId());
                    event.setEventTarget("DATASET");
                    event.setEventType("update");
                    event.setEventHandler(jsFormula);
                    event = tagEventService.insert(request, event);
                }
            }


        }
    }


    public boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
