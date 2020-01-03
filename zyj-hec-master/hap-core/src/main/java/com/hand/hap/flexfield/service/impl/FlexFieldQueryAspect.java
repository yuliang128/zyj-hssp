package com.hand.hap.flexfield.service.impl;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.flexfield.service.IFlexRuleService;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hap.system.dto.ResponseData;

/**
 * @author jialong.zuo@hand-china.com on 2017/12/1.
 */
@Aspect
@Component
public class FlexFieldQueryAspect {

    @Autowired
    IFlexRuleService iFlexRuleService;

    @Pointcut("execution(* *..*Controller.*(..))")
    public void pointCut() {
    }


    /** 当
     * @param joinpoint
     * @param responseData
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IOException
     */
    @AfterReturning(value = "pointCut()", returning = "responseData")
    public void FlexLovFieldAspect(JoinPoint joinpoint, Object responseData) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {
        HttpServletRequest httpServletRequest = null;
        Object dto=null;
        for (int i = 0; i < joinpoint.getArgs().length; i++) {
            if (joinpoint.getArgs()[i] instanceof HttpServletRequest) {
                httpServletRequest = (HttpServletRequest) joinpoint.getArgs()[i];
            }else if(joinpoint.getArgs()[i] instanceof BaseDTO){
                dto=joinpoint.getArgs()[i];
            }
        }
        if (null == httpServletRequest || null == dto) {
            return;
        }
        if (httpServletRequest.getParameterMap().containsKey("_FLEX_RULE_SET_CODE")) {
            IRequest iRequest = RequestHelper.createServiceRequest(httpServletRequest);
            String flexRuleCode = httpServletRequest.getParameterMap().get("_FLEX_RULE_SET_CODE")[0];
            if (responseData instanceof ResponseData) {
                ResponseData responseData1 = (ResponseData) responseData;
                if(null !=responseData1.getRows() && responseData1.getRows().size()!=0) {
                    Iterator iterator = responseData1.getRows().iterator();
                    while (iterator.hasNext()) {
                        Object o = iterator.next();
                        iFlexRuleService.matchingLovField(flexRuleCode,BeanUtils.describe(dto).entrySet() , o, iRequest);

                    }
                }
            }else{
                iFlexRuleService.matchingLovField(flexRuleCode, BeanUtils.describe(dto).entrySet(), responseData,iRequest);
            }

        }
    }

}
