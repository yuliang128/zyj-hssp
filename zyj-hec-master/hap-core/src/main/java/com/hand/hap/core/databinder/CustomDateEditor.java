package com.hand.hap.core.databinder;

import com.hand.hap.core.util.DateUtils;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用于SpringMVC中日期格式的转换
 *
 * @author mouse 2019/03/14 14:05
 */
public class CustomDateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Date date = DateUtils.str2Date(text, null);
        setValue(date);
    }
}
