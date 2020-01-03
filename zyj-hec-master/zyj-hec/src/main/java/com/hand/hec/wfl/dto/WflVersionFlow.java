package com.hand.hec.wfl.dto;

/**
 * Auto Generated By Hap Code Generator
 **/

import java.util.Map;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.mybatis.common.query.Where;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinCode;
import com.hand.hap.system.dto.BaseDTO;

import lombok.*;


@MultiLanguage
@ExtensionAttribute(disable = true)
@Table(name = "wfl_version_flow")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WflVersionFlow extends BaseDTO {

    public static final String FIELD_VERSION_FLOW_ID = "versionFlowId";
    public static final String FIELD_FLOW_ID = "flowId";
    public static final String FIELD_VERSION = "version";
    public static final String FIELD_PROCESS_ID = "processId";
    public static final String FIELD_FLOW_CODE = "flowCode";
    public static final String FIELD_FLOW_NAME = "flowName";
    public static final String FIELD_FROM_ELEMENT_TYPE = "fromElementType";
    public static final String FIELD_FROM_ELEMENT_ID = "fromElementId";
    public static final String FIELD_TO_ELEMENT_TYPE = "toElementType";
    public static final String FIELD_TO_ELEMENT_ID = "toElementId";
    public static final String FIELD_FLOW_VALUE = "flowValue";
    public static final String FIELD_SEQUENCE = "sequence";

    public static final String ELEMENT_FLOW = "FLOW";

    /**
     * 流转ID
     */
    @Id
    @GeneratedValue
    @Where
    private Long versionFlowId;

    @Where
    private Long flowId;

    /**
     * 工作流版本
     */
    @Where
    private Long version;

    /**
     * 工作流ID
     */
    @Where
    private Long processId;

    /**
     * 流转代码
     */
    @NotEmpty
    @Length(max = 30)
    private String flowCode;

    /**
     * 流转名称
     */
    @Length(max = 240)
    @MultiLanguageField
    private String flowName;

    /**
     * 输入元素类型
     */
    @NotEmpty
    @Length(max = 30)
    private String fromElementType;

    /**
     * 输入元素ID
     */
    private Long fromElementId;

    /**
     * 输出元素类型
     */
    @NotEmpty
    @Length(max = 30)
    private String toElementType;

    /**
     * 输出元素ID
     */
    private Long toElementId;

    /**
     * 流转判定值
     */
    @Length(max = 2000)
    private String flowValue;

    /**
     * 流转顺序
     */
    private Long sequence;

    @Transient
    private String fromElementName;

    @Transient
    @JoinCode(code = "WFL.ELEMENT_TYPE", joinKey = FIELD_FROM_ELEMENT_TYPE)
    private String fromElementTypeName;

    @Transient
    private String toElementName;

    @Transient
    @JoinCode(code = "WFL.ELEMENT_TYPE", joinKey = FIELD_TO_ELEMENT_TYPE)
    private String toElementTypeName;

    @Transient
    private String fromElementCode;

    @Transient
    private String toElementCode;

    public WflVersionFlow(Map map) {
        this.setFlowId(Long.parseLong(map.get("element_id").toString()));
        this.setFlowCode(map.get("element_code").toString());
        this.setFlowName(map.get("element_name").toString());
        this.setFromElementId(Long.parseLong(map.get("from_element_id").toString()));
        this.setFromElementType(map.get("from_element_type").toString());
        this.setToElementId(Long.parseLong(map.get("to_element_id").toString()));
        this.setToElementType(map.get("to_element_type").toString());
        this.setSequence(Long.parseLong(map.get("sequence").toString()));
    }

    public WflVersionFlow(WflFlow flow) {
        this.flowId = flow.getFlowId();
        this.processId = flow.getProcessId();
        this.flowCode = flow.getFlowCode();
        this.flowName = flow.getFlowName();
        this.fromElementCode = flow.getFromElementCode();
        this.fromElementId = flow.getFromElementId();
        this.fromElementType = flow.getFromElementType();
        this.fromElementName = flow.getFromElementName();
        this.toElementCode = flow.getToElementCode();
        this.toElementId = flow.getToElementId();
        this.toElementType = flow.getToElementType();
        this.toElementName = flow.getToElementName();
        this.flowValue = flow.getFlowValue();
        this.sequence = flow.getSequence();
    }
}
