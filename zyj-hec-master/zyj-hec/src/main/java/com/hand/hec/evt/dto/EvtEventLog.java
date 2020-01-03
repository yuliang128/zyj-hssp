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
import java.util.Date;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "evt_event_log")
public class EvtEventLog extends BaseDTO {

     public static final String FIELD_HANDLE_RECORD_ID = "handleRecordId";
     public static final String FIELD_LOG_ID = "logId";
     public static final String FIELD_LOG_DATE = "logDate";
     public static final String FIELD_LOG_TEXT = "logText";
     public static final String FIELD_EVENT_ID = "eventId";
     public static final String FIELD_EVENT_SOURCE = "eventSource";
     public static final String FIELD_EVENT_PARAM = "eventParam";


    /**
     * 处理程序ID
     */
     private Long handleRecordId;

     @Id
     @GeneratedValue
     private Long logId;

    /**
     * 日志日期
     */
     private Date logDate;

    /**
     * 日志内容
     */
     @Length(max = 4000)
     private String logText;

    /**
     * 事件ID
     */
     @NotNull
     private Long eventId;

    /**
     * 事件来源
     */
     @Length(max = 200)
     private String eventSource;

    /**
     * 事件参数
     */
     @NotNull
     private Long eventParam;

     }
