package com.hand.hec.wfl.dto;

/** Auto Generated By Hap Code Generator **/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;

import com.hand.hap.system.dto.BaseDTO;

@ExtensionAttribute(disable = true)
@Table(name = "wfl_task_action")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WflTaskAction extends BaseDTO {

    public static final String FIELD_TASK_ACTION_ID = "taskActionId";
    public static final String FIELD_TASK_ID = "taskId";
    public static final String FIELD_ACTION_ID = "actionId";
    public static final String FIELD_SEQUENCE = "sequence";
    public static final String FIELD_PROCEDURE_ID = "procedureId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    /**
     * ID
     */
    @Id
    @GeneratedValue
    @Where
    private Long taskActionId;

    /**
     * 任务ID
     */
    @Where
    private Long taskId;


    /**
     * 动作ID
     */
    @JoinTable(name = "actionJoin", joinMultiLanguageTable = true, target = WflProcessAction.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = WflProcessAction.FIELD_ACTION_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long actionId;

    @Transient
    @JoinColumn(joinName = "actionJoin", field = WflProcessAction.FIELD_ACTION_TYPE)
    private String actionType;

    @Transient
    @JoinColumn(joinName = "actionJoin", field = WflProcessAction.FIELD_ACTION_NAME)
    private String actionName;

    /**
     * 顺序
     */
    private Long sequence;

    /**
     * 过程ID
     */
    @JoinTable(name = "procedureJoin", joinMultiLanguageTable = true, target = WflProcedure.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = WflProcedure.FIELD_PROCEDURE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long procedureId;

    @Transient
    @JoinColumn(joinName = "procedureJoin", field = WflProcedure.FIELD_PROCEDURE_NAME)
    private String procedureName;

    /**
     * 启用标志
     */
    @Length(max = 1)
    private String enabledFlag;

}