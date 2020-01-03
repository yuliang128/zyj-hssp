package com.hand.hap.system.dto;

/**Auto Generated By Hap Code Generator**/

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;


@MultiLanguage
@ExtensionAttribute(disable=true)
@Table(name = "sys_hotkey_b")
public class Hotkey extends BaseDTO {

     public static final String FIELD_HOTKEY_ID = "hotkeyId";
     public static final String FIELD_CODE = "code";
     public static final String FIELD_HOTKEY_LEVEL = "hotkeyLevel";
     public static final String FIELD_HOTKEY = "hotkey";
     public static final String FIELD_DESCRIPTION = "description";


     @Id
     @GeneratedValue
     private Long hotkeyId;

     @NotEmpty
     @Length(max = 50)
     @Where
     @OrderBy("asc")
     private String code; //热键编码

     @Where
     private String hotkeyLevel; //热键级别

     @Where
     private Long  hotkeyLevelId; //热键级别 ID

     @Length(max = 50)
     private String hotkey; //热键

     @Length(max = 250)
     @MultiLanguageField
     private String description; //热键描述

     public void setHotkeyLevel(String hotkeyLevel) {
          this.hotkeyLevel = hotkeyLevel;
     }

     public String getHotkeyLevel(){
          return hotkeyLevel;
     }

     public Long getHotkeyLevelId() {
          return hotkeyLevelId;
     }

     public void setHotkeyLevelId(Long hotkeyLevelId) {
          this.hotkeyLevelId = hotkeyLevelId;
     }

     public void setHotkeyId(Long hotkeyId){
         this.hotkeyId = hotkeyId;
     }

     public Long getHotkeyId(){
         return hotkeyId;
     }

     public void setCode(String code){
         this.code = code;
     }

     public String getCode(){
         return code;
     }

     public void setHotkey(String hotkey){
         this.hotkey = hotkey;
     }

     public String getHotkey(){
         return hotkey;
     }

     public void setDescription(String description){
         this.description = description;
     }

     public String getDescription(){
         return description;
     }

     }
