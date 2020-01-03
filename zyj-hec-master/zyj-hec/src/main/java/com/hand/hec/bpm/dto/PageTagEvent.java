package com.hand.hec.bpm.dto;

/**
 * Auto Generated By Code Generator
 * Description:
 */

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Component
@ExtensionAttribute(disable = true)
@Table(name = "bpm_page_tag_event")
public class PageTagEvent extends BaseDTO {

    public static final String FIELD_EVENT_ID = "eventId";
    public static final String FIELD_TAG_ID = "tagId";
    public static final String FIELD_EVENT_TARGET = "eventTarget";
    public static final String FIELD_EVENT_TYPE = "eventType";
    public static final String FIELD_EVENT_HANDLER = "eventHandler";
    public static final String FIELD_GUIDE_ID = "guideId";


    @Id
    @GeneratedValue
    @Where
    private Long eventId;//事件ID

    @Where
    private Long tagId;//标签ID

    @NotEmpty
    @Length(max = 23)
    private String eventTarget;//事件目标，规定当前事件属于数据源还是属于标签

    @NotEmpty
    @Length(max = 23)
    private String eventType;//事件类型

    @Length(max = 23)
    private String eventHandler;//事件触发代码

    @Where
    private Long guideId;//数据关系向导


    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setEventTarget(String eventTarget) {
        this.eventTarget = eventTarget;
    }

    public String getEventTarget() {
        return eventTarget;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventHandler(String eventHandler) {
        this.eventHandler = eventHandler;
    }

    public String getEventHandler() {
        return eventHandler;
    }

    public void setGuideId(Long guideId) {
        this.guideId = guideId;
    }

    public Long getGuideId() {
        return guideId;
    }

}
