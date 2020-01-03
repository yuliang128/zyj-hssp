package com.hand.hec.bpm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.bpm.dto.DynamicDataLine;
import com.hand.hec.bpm.dto.Page;
import com.hand.hec.bpm.dto.PageGroupField;
import com.hand.hec.bpm.dto.PageLayoutBasic;
import com.hand.hec.bpm.service.ICoreEngine;
import com.hand.hec.bpm.service.IPageGroupFieldService;
import com.hand.hec.bpm.service.IPageLayoutBasicService;
import com.hand.hec.bpm.service.IPageService;
import org.joor.Reflect;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(rollbackFor = Exception.class)
public class CoreEngine implements ICoreEngine {

    Pattern paramPattern = Pattern.compile("#.+#");
    Pattern beanPattern = Pattern.compile("\\w+\\.");
    Pattern methodPattern = Pattern.compile("\\.\\w+\\(");

    @Autowired
    IPageLayoutBasicService layoutBasicService;

    @Autowired
    IPageService pageService;

    @Autowired
    IPageGroupFieldService fieldService;

    @Autowired
    BeanFactory beanFactory;

    @Override
    public void fireLayoutWriteBack(IRequest request, Long pageId, String layoutCode, DynamicDataLine dataLine) {
        //回调函数demo
        //xxxBean.xxxMethod(#金额#,#单价#,#数量#)
        PageLayoutBasic basic = new PageLayoutBasic();
        basic.setPageId(pageId);
        basic.setLayoutCode(layoutCode);
        Criteria criteria = new Criteria();
        criteria.where(new WhereField(PageLayoutBasic.FIELD_PAGE_ID), new WhereField(PageLayoutBasic.FIELD_LAYOUT_CODE, Comparison.EQUAL));
        List<PageLayoutBasic> layoutBasicList = layoutBasicService.selectOptions(request, basic, criteria, 0, 0);
        String writeBackProgram = layoutBasicList.get(0).getWriteBackProgram();

        if (writeBackProgram == null) {
            return;
        }

        Matcher beanMatcher = beanPattern.matcher(writeBackProgram);
        beanMatcher.find();
        String beanName = beanMatcher.group().replace(".", "");


        Matcher methodMatcher = methodPattern.matcher(writeBackProgram);
        methodMatcher.find();
        String methodName = methodMatcher.group().replace(".", "").replace("(", "");

        //找到匹配的参数配置
        Matcher paramMatcher = paramPattern.matcher(writeBackProgram);
        List<String> paramList = new ArrayList<String>();
        List<Object> actParamList = new ArrayList<Object>();

        while (paramMatcher.find()) {
            paramList.add(paramMatcher.group().replaceAll("#", ""));
        }

        Page page = new Page();
        page.setPageId(pageId);
        page = pageService.selectByPrimaryKey(request, page);

        //将参数转换成实际调用参数


//        PageGroupField queryField = new PageGroupField();
//        queryField.setGroupId(page.getGroupId());
//        Criteria fieldCriteria = new Criteria(queryField);
//        fieldCriteria.where(new WhereField(PageGroupField.FIELD_GROUP_ID));
//        List<PageGroupField> fieldList = fieldService.selectOptions(request, queryField, fieldCriteria, 0, 0);
//
//        for (String paramName : paramList) {
//            for (PageGroupField field : fieldList) {
//                if (paramName.equals(field.getFieldDesc())) {
//                    Object actParam = Reflect.on(dataLine).call("get" + field.getFieldName().substring(0, 1).toUpperCase() + field.getFieldName().substring(1)).get();
//                    actParamList.add(actParam);
//                }
//            }
//        }
//
//        callWriteBack(beanName, methodName, actParamList);
    }

    private void callWriteBack(String beanName, String methodName, List<Object> actParamList) {
        Object bean = beanFactory.getBean(beanName);
        Object[] actParams = actParamList.toArray();
        if (actParams.length == 0) {
            Reflect.on(bean).call(methodName);
        } else if (actParams.length == 1) {
            Reflect.on(bean).call(methodName, actParams[0]);
        } else if (actParams.length == 2) {

            Reflect.on(bean).call(methodName, actParams[0], actParams[1]);
        } else if (actParams.length == 3) {
            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2]);

        } else if (actParams.length == 4) {
            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2], actParams[3]);

        } else if (actParams.length == 5) {
            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2], actParams[3],
                    actParams[4]);

        } else if (actParams.length == 6) {
            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2], actParams[3],
                    actParams[4], actParams[5]);

        } else if (actParams.length == 7) {
            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2], actParams[3],
                    actParams[4], actParams[5], actParams[6]);

        } else if (actParams.length == 8) {
            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2], actParams[3],
                    actParams[4], actParams[5], actParams[6], actParams[7]);

        } else if (actParams.length == 9) {
            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2], actParams[3],
                    actParams[4], actParams[5], actParams[6], actParams[7], actParams[8]);

        } else if (actParams.length == 10) {
            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2], actParams[3],
                    actParams[4], actParams[5], actParams[6], actParams[7], actParams[8], actParams[9]);

        } else if (actParams.length == 11) {
            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2], actParams[3],
                    actParams[4], actParams[5], actParams[6], actParams[7], actParams[8], actParams[9],
                    actParams[10]);

        } else if (actParams.length == 12) {
            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2], actParams[3],
                    actParams[4], actParams[5], actParams[6], actParams[7], actParams[8], actParams[9],
                    actParams[10], actParams[11]);

        } else if (actParams.length == 13) {
            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2], actParams[3],
                    actParams[4], actParams[5], actParams[6], actParams[7], actParams[8], actParams[9],
                    actParams[10], actParams[11], actParams[12]);

        } else if (actParams.length == 14) {
            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2], actParams[3],
                    actParams[4], actParams[5], actParams[6], actParams[7], actParams[8], actParams[9],
                    actParams[10], actParams[11], actParams[12], actParams[13]);

        } else if (actParams.length == 15) {
            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2], actParams[3],
                    actParams[4], actParams[5], actParams[6], actParams[7], actParams[8], actParams[9],
                    actParams[10], actParams[11], actParams[12], actParams[13], actParams[14]);

        } else if (actParams.length == 16) {
            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2], actParams[3],
                    actParams[4], actParams[5], actParams[6], actParams[7], actParams[8], actParams[9],
                    actParams[10], actParams[11], actParams[12], actParams[13], actParams[14], actParams[15]);

        } else if (actParams.length == 17) {
            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2], actParams[3],
                    actParams[4], actParams[5], actParams[6], actParams[7], actParams[8], actParams[9],
                    actParams[10], actParams[11], actParams[12], actParams[13], actParams[14], actParams[15],
                    actParams[16]);

        } else if (actParams.length == 18) {
            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2], actParams[3],
                    actParams[4], actParams[5], actParams[6], actParams[7], actParams[8], actParams[9],
                    actParams[10], actParams[11], actParams[12], actParams[13], actParams[14], actParams[15],
                    actParams[16], actParams[17]);

        } else if (actParams.length == 19) {

            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2], actParams[3],
                    actParams[4], actParams[5], actParams[6], actParams[7], actParams[8], actParams[9],
                    actParams[10], actParams[11], actParams[12], actParams[13], actParams[14], actParams[15],
                    actParams[16], actParams[17], actParams[18]);
        } else if (actParams.length == 20) {

            Reflect.on(bean).call(methodName, actParams[0], actParams[1], actParams[2], actParams[3],
                    actParams[4], actParams[5], actParams[6], actParams[7], actParams[8], actParams[9],
                    actParams[10], actParams[11], actParams[12], actParams[13], actParams[14], actParams[15],
                    actParams[16], actParams[17], actParams[18], actParams[19]);
        }

    }

    public void testWriteBack(String arg1) {
        System.out.println(arg1);
    }

}
