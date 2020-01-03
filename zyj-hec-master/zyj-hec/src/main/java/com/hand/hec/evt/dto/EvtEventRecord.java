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
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "evt_event_record")
public class EvtEventRecord extends BaseDTO {

     public static final String FIELD_RECORD_ID = "recordId";
     public static final String FIELD_EVENT_ID = "eventId";
     public static final String FIELD_EVENT_TYPE = "eventType";
     public static final String FIELD_EVENT_PARAM = "eventParam";
     public static final String FIELD_EVENT_SOURCE = "eventSource";
     public static final String FIELD_EVENT_DATA = "eventData";


     @Id
     @GeneratedValue
     private Long recordId;

    /**
     * 事件名称
     */
     private Long eventId;

    /**
     * 事件类型
     */
     @Length(max = 200)
     private String eventType;

    /**
     * 事件参数
     */
     private Long eventParam;

    /**
     * 事件来源
     */
     @Length(max = 200)
     private String eventSource;

    /**
     * 事件DATA
     */
     @Length(max = 2147483647)
     private String eventData;

     }
