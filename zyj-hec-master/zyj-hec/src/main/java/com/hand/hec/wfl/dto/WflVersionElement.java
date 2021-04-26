package com.hand.hec.wfl.dto;

/**
 * Auto Generated By Code Generator Description:
 */

import java.util.List;

import javax.persistence.Transient;

import com.hand.hap.mybatis.common.query.Where;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import lombok.*;

@Component
@ExtensionAttribute(disable = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WflVersionElement extends BaseDTO {

    public static final String FIELD_ELEMENT_ID = "elementId";
    public static final String FIELD_PROCESS_ID = "processId";
    public static final String FIELD_ELEMENT_CODE = "elementCode";
    public static final String FIELD_ELEMENT_NAME = "elementName";
    public static final String FIELD_ELEMENT_TYPE = "elementType";
    public static final String FIELD_LANG = "lang";

    @Where
    private Long elementId;

    @Where
    private Long version;

    @Where
    private Long processId;

    @Length(max = 23)
    private String elementCode;

    @Length(max = 23)
    private String elementName;

    @Length(max = 23)
    private String elementType;

    @Length(max = 23)
    private String lang;

    @Transient
    private List<WflEvent> event;

    @Transient
    private List<WflTask> task;

    @Transient
    private List<WflGateway> gateway;

    @Transient
    private List<WflFlow> flow;

}