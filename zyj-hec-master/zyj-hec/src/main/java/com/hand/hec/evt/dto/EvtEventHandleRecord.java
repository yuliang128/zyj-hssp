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
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "evt_event_handle_record")
public class EvtEventHandleRecord extends BaseDTO {

     public static final String FIELD_RECORD_ID = "recordId";
     public static final String FIELD_EVENT_RECORD_ID = "eventRecordId";
     public static final String FIELD_HANDLE_ID = "handleId";
     public static final String FIELD_RETURN_CODE = "returnCode";
     public static final String FIELD_SUCCESS_FLAG = "successFlag";


     @Id
     @GeneratedValue
     private BigDecimal recordId;

    /**
     * 事件记录id
     */
     private BigDecimal eventRecordId;

    /**
     * 过程id
     */
     private BigDecimal handleId;

    /**
     * 返回编码
     */
     @Length(max = 200)
     private String returnCode;

    /**
     * 成功标识
     */
     @Length(max = 1)
     private String successFlag;

     }
