package com.hand.hec.bgt.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.hand.hap.mybatis.common.query.JoinCode;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 预算控制策略
 * </p>
 * 
 * @author mouse 2019/01/07 16:19
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_control_strategy")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtControlStrategy extends BaseDTO {

    public static final String FIELD_CONTROL_STRATEGY_GROUP_ID = "controlStrategyGroupId";
    public static final String FIELD_CONTROL_STRATEGY_ID = "controlStrategyId";
    public static final String FIELD_CONTROL_STRATEGY_SEQUENCE = "controlStrategySequence";
    public static final String FIELD_CONTROL_STRATEGY_CODE = "controlStrategyCode";
    public static final String FIELD_CONTROL_STRATEGY_DESC = "controlStrategyDesc";
    public static final String FIELD_EXP_WF_EVENT = "expWfEvent";
    public static final String FIELD_CONTROL_METHOD = "controlMethod";
    public static final String FIELD_MESSAGE_CODE = "messageCode";

    public static final String CONTROL_METHOD_ALLOWED = "ALLOWED";
    public static final String CONTROL_METHOD_BLOCK = "BLOCK";

    /**
     * 策略明细ID
     */
    @Id
    @GeneratedValue
    @Where
    private Long controlStrategyId;

    /**
     * 控制策略ID
     */
    @NotNull
    @Where
    private Long controlStrategyGroupId;

    /**
     * 序号
     */
    @OrderBy("ASC")
    private Long controlStrategySequence;

    /**
     * 策略代码
     */
    @NotEmpty
    @Length(max = 30)
    private String controlStrategyCode;

    /**
     * 策略描述
     */
    @Length(max = 2000)
    private String controlStrategyDesc;

    /**
     * 事件
     */
    @Length(max = 50)
    private String expWfEvent;

    /**
     * 控制方法
     */
    @Length(max = 30)
    private String controlMethod;

    @Transient
    @JoinCode(code = "BGT_STRATEGY_METHOD", joinKey = BgtControlStrategy.FIELD_CONTROL_METHOD)
    private String controlMethodDesc;

    /**
     * 消息代码
     */
    @Length(max = 200)
    private String messageCode;

    @Transient
    @JoinCode(code = "BGT_CONTROL_ERROR", joinKey = BgtControlStrategy.FIELD_MESSAGE_CODE)
    private String messageCodeDisplay;


}
