package com.hand.hap.core.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

/**
 * 字符串去空格
 *
 * @author peng.jiang@hand-china.com
 * @date 2018/1/16
 **/
public class CustomStringDeserializer extends JsonDeserializer<String> implements ContextualDeserializer{

    private boolean stringTrim = false;

    public boolean isStringTrim() {
        return stringTrim;
    }

    public void setStringTrim(boolean stringTrim) {
        this.stringTrim = stringTrim;
    }

    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String str = jp.getText();
        if (isStringTrim()){
            str = str.trim();
        }
        return str;
    }


    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
            throws JsonMappingException {
        CustomStringDeserializer deserializer = new CustomStringDeserializer();
        if(property != null){
            StringTrim stringTrim = property.getMember().getAnnotation(StringTrim.class);
            if (stringTrim != null) {
                deserializer.setStringTrim(true);
            }
        }
        return deserializer;
    }

}
