package com.hand.hec.wfl.dto;

/**
 * Auto Generated By Hap Code Generator
 **/

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;

import com.hand.hap.core.annotation.Children;
import com.hand.hap.mybatis.common.query.Where;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.system.dto.BaseDTO;

import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@MultiLanguage
@ExtensionAttribute(disable = true)
@Table(name = "wfl_version_process")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WflVersionProcess extends BaseDTO {

    public static final String FIELD_VERSION_PROCESS_ID = "versionProcessId";
    public static final String FIELD_PROCESS_ID = "processId";
    public static final String FIELD_VERSION = "version";
    public static final String FIELD_PROCESS_CODE = "processCode";
    public static final String FIELD_PROCESS_NAME = "processName";
    public static final String FIELD_DOC_CATEGORY = "docCategory";
    public static final String FIELD_SUB_PROCESS_FLAG = "subProcessFlag";
    public static final String FIELD_PARENT_PROCESS_ID = "parentProcessId";
    public static final String FIELD_PROCESS_PAGE_ID = "processPageId";
    public static final String FIELD_VIEW_PAGE_ID = "viewPageId";
    public static final String FIELD_START_PROCEDURE_ID = "startProcedureId";
    public static final String FIELD_END_PROCEDURE_ID = "endProcedureId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_CONFIG_TYPE = "configType";

    /**
     * 工作流ID
     */
    @Id
    @GeneratedValue
    @Where
    private Long versionProcessId;

    @Where
    private Long processId;

    /**
     * 工作流版本
     */
    @Where
    private Long version;

    /**
     * 工作流代码
     */
    @NotEmpty
    @Length(max = 30)
    private String processCode;

    /**
     * 工作流名称
     */
    @Length(max = 240)
    @MultiLanguageField
    private String processName;

    /**
     * 单据类别
     */
    @NotEmpty
    @Length(max = 30)
    private String docCategory;

    /**
     * 子流程标志
     */
    @NotEmpty
    @Length(max = 1)
    private String subProcessFlag;

    /**
     * 父流程ID
     */
    @JoinTable(name = "parentProcessJoin", joinMultiLanguageTable = true, target = WflVersionProcess.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = WflVersionProcess.FIELD_PROCESS_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long parentProcessId;

    @Transient
    @JoinColumn(joinName = "parentProcessJoin", field = WflVersionProcess.FIELD_PROCESS_NAME)
    private String parentProcessName;

    /**
     * 操作页面ID
     */
    @JoinTable(name = "processPageJoin", joinMultiLanguageTable = true, target = WflPage.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = WflPage.FIELD_PAGE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long processPageId;

    @Transient
    @JoinColumn(joinName = "processPageJoin", field = WflPage.FIELD_PAGE_NAME)
    private String processPageName;

    /**
     * 查看页面ID
     */
    @JoinTable(name = "viewPageJoin", joinMultiLanguageTable = true, target = WflPage.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = WflPage.FIELD_PAGE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long viewPageId;

    @Transient
    @JoinColumn(joinName = "viewPageJoin", field = WflPage.FIELD_PAGE_NAME)
    private String viewPageName;

    /**
     * 启动触发过程ID
     */
    @JoinTable(name = "startProcedureJoin", joinMultiLanguageTable = true, target = WflProcedure.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = WflProcedure.FIELD_PROCEDURE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long startProcedureId;

    @Transient
    @JoinColumn(joinName = "startProcedureJoin", field = WflProcedure.FIELD_PROCEDURE_NAME)
    private String startProcedureName;

    /**
     * 结束触发过程ID
     */
    @JoinTable(name = "endProcedureJoin", joinMultiLanguageTable = true, target = WflProcedure.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = WflProcedure.FIELD_PROCEDURE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long endProcedureId;

    @Transient
    @JoinColumn(joinName = "ENDProcedureJoin", field = WflProcedure.FIELD_PROCEDURE_NAME)
    private String endProcedureName;

    /**
     * 启用标志
     */
    @Length(max = 1)
    private String enabledFlag;

    private String configType;

    @Transient
    @Children
    private Map<String, WflVersionEvent> eventMap;

    @Transient
    @Children
    private Map<String, WflVersionFlow> flowMap;

    @Transient
    @Children
    private Map<String, WflVersionGateway> gatewayMap;

    @Transient
    @Children
    private Map<String, WflVersionTask> taskMap;

    @Transient
    @Children
    private Map<String, WflVersionElement> elementMap;

    @Transient
    @Children
    private Map<String, WflVerGtwBizRuleGroup> gtwBizRuleGroupMap;

    @Transient
    @Children
    private Map<String, WflVerGtwBizRuleDetail> gtwBizRuleDetailMap;

    @Transient
    @Children
    private Map<String, WflVersionUserTask> userTaskMap;

    @Transient
    @Children
    private Map<String, WflVerSubProcessTask> subProcessTaskMap;

    @Transient
    @Children
    private Map<String, WflVerTaskReceiver> taskReceiverMap;

    @Transient
    @Children
    private Map<String, WflVerTaskRcvBizDetail> taskRcvBizDetailMap;

    public WflVersionEvent getEvent(String key) {
        return eventMap == null ? null : eventMap.get(key);
    }

    public WflVersionFlow getFlow(String key) {
        return flowMap == null ? null : flowMap.get(key);
    }

    public WflVersionGateway getGateway(String key) {
        return gatewayMap == null ? null : gatewayMap.get(key);
    }

    public WflVersionTask getTask(String key) {
        return taskMap == null ? null : taskMap.get(key);
    }

    public WflVersionElement getElement(String key) {
        return elementMap == null ? null : elementMap.get(key);
    }

    public WflVerGtwBizRuleGroup getGtwBizRuleGroup(String key) {
        return gtwBizRuleGroupMap == null ? null : gtwBizRuleGroupMap.get(key);
    }

    public WflVerGtwBizRuleDetail getGtwBizRuleDetail(String key) {
        return gtwBizRuleDetailMap == null ? null : gtwBizRuleDetailMap.get(key);
    }

    public WflVersionUserTask getUserTask(String key) {
        return userTaskMap == null ? null : userTaskMap.get(key);
    }

    public WflVerSubProcessTask getSubProcessTask(String key) {
        return subProcessTaskMap == null ? null : subProcessTaskMap.get(key);
    }

    public WflVerTaskReceiver getTaskReceiver(String key) {
        return taskReceiverMap == null ? null : taskReceiverMap.get(key);
    }

    public WflVerTaskRcvBizDetail getTaskRcvBizDetail(String key) {
        return taskRcvBizDetailMap == null ? null : taskRcvBizDetailMap.get(key);
    }

    public void addEvent(String key, WflVersionEvent event) {
        if (eventMap == null) {
            eventMap = new HashMap<String, WflVersionEvent>();
        }
        eventMap.put(key, event);
    }

    public void addFlow(String key, WflVersionFlow flow) {
        if (flowMap == null) {
            flowMap = new HashMap<String, WflVersionFlow>();
        }
        flowMap.put(key, flow);
    }

    public void addGateway(String key, WflVersionGateway gateway) {
        if (gatewayMap == null) {
            gatewayMap = new HashMap<String, WflVersionGateway>();
        }
        gatewayMap.put(key, gateway);
    }

    public void addTask(String key, WflVersionTask task) {
        if (taskMap == null) {
            taskMap = new HashMap<String, WflVersionTask>();
        }
        taskMap.put(key, task);
    }

    public void addElement(String key, WflVersionElement element) {
        if (elementMap == null) {
            elementMap = new HashMap<String, WflVersionElement>();
        }
        elementMap.put(key, element);;
    }

    public void addGtwBizRuleGroup(String key, WflVerGtwBizRuleGroup group) {
        if (gtwBizRuleGroupMap == null) {
            gtwBizRuleGroupMap = new HashMap<String, WflVerGtwBizRuleGroup>();
        }
        gtwBizRuleGroupMap.put(key, group);
    }

    public void addGtwBizRuleDetail(String key, WflVerGtwBizRuleDetail detail) {
        if (gtwBizRuleDetailMap == null) {
            gtwBizRuleDetailMap = new HashMap<String, WflVerGtwBizRuleDetail>();
        }
        gtwBizRuleDetailMap.put(key, detail);
    }

    public void addUserTask(String key, WflVersionUserTask task) {
        if (userTaskMap == null) {
            userTaskMap = new HashMap<String, WflVersionUserTask>();
        }
        userTaskMap.put(key, task);
    }

    public void addSubProcessTask(String key, WflVerSubProcessTask task) {
        if (subProcessTaskMap == null) {
            subProcessTaskMap = new HashMap<String, WflVerSubProcessTask>();
        }
        subProcessTaskMap.put(key, task);
    }

    public void addTaskReceiver(String key, WflVerTaskReceiver receiver) {
        if (taskReceiverMap == null) {
            taskReceiverMap = new HashMap<String, WflVerTaskReceiver>();
        }
        taskReceiverMap.put(key, receiver);
    }

    public void addTaskRcvBizDetail(String key, WflVerTaskRcvBizDetail detail) {
        if (taskRcvBizDetailMap == null) {
            taskRcvBizDetailMap = new HashMap<String, WflVerTaskRcvBizDetail>();
        }
        taskRcvBizDetailMap.put(key, detail);
    }

    public WflVersionProcess(WflProcess process) {
        this.processId = process.getProcessId();
        this.processCode = process.getProcessCode();
        this.processName = process.getProcessName();
        this.docCategory = process.getDocCategory();
        this.subProcessFlag = process.getSubProcessFlag();
        this.parentProcessId = process.getParentProcessId();
        this.processPageId = process.getProcessPageId();
        this.viewPageId = process.getViewPageId();
        this.startProcedureId = process.getStartProcedureId();
        this.endProcedureId = process.getEndProcedureId();
        this.enabledFlag = process.getEnabledFlag();
        this.configType = process.getConfigType();
    }
}
