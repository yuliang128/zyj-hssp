package com.hand.hec.evt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "evt_event_handle")
public class EvtEventHandle extends BaseDTO {

     public static final String FIELD_HANDLE_RECORD_ID = "handleRecordId";
     public static final String FIELD_EVENT_ID = "eventId";
     public static final String FIELD_ORDER_NUM = "orderNum";
     public static final String FIELD_HANDLE_TITLE = "handleTitle";
     public static final String FIELD_HANDLE_DESC = "handleDesc";
     public static final String FIELD_BEAN_NAME = "beanName";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long handleRecordId;

     @NotNull
     private Long eventId;

    /**
     * 处理顺序
     */
     private BigDecimal orderNum;

    /**
     * 处理名称
     */
     @Length(max = 200)
     private String handleTitle;

    /**
     * 说明
     */
     @Length(max = 4000)
     private String handleDesc;

    /**
     * JavaBean名称
     */
     @Length(max = 2000)
     private String beanName;

    /**
     * 启用标志
     */
     @Length(max = 1)
     private String enabledFlag;

     }
