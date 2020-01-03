package com.hand.hec.bgt.dto;

import javax.persistence.*;

import com.hand.hap.mybatis.common.query.JoinCode;
import com.hand.hap.mybatis.common.query.Where;
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
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 预算控制策略决定机制表
 * </p>
 * 
 * @author guiyuting 2019/03/11 15:45
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "bgt_ctrl_strategy_mp_cond")
public class BgtCtrlStrategyMpCond extends BaseDTO {

    public static final String MANNER_FIXED_AMOUNT = "FIXED_AMOUNT";
    public static final String MANNER_PERCENTAGE = "PERCENTAGE";

    public static final String FIELD_CTRL_STRATEGY_MP_COND_ID = "ctrlStrategyMpCondId";
    public static final String FIELD_CONTROL_STRATEGY_ID = "controlStrategyId";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_RANGE = "range";
    public static final String FIELD_OBJECT = "object";
    public static final String FIELD_PERIOD_STRATEGY = "periodStrategy";
    public static final String FIELD_MANNER = "manner";
    public static final String FIELD_OPERATOR = "operator";
    public static final String FIELD_VALUE = "value";
    public static final String FIELD_AND_OR = "andOr";
    public static final String FIELD_USER_EXIT_PROCEDURE = "userExitProcedure";

    /**
     * 期间控制
     */
    public static final String STRATEGY_PERIOD_MONTH = "MONTH";
    /**
     * 季度至今
     */
    public static final String STRATEGY_PERIOD_QTD = "QTD";
    /**
     * 季度
     */
    public static final String STRATEGY_PERIOD_QUARTER = "QUARTER";
    /**
     * 季度滚动
     */
    public static final String STRATEGY_PERIOD_RQB = "RQB";
    /**
     * 年度
     */
    public static final String STRATEGY_PERIOD_YEAR = "YEAR";
    /**
     * 年度至今
     */
    public static final String STRATEGY_PERIOD_YTD = "YTD";
    /**
     * 累计季度
     */
    public static final String STRATEGY_PERIOD_YTQ = "YTQ";
    /**
     * 无固定期段
     */
    public static final String STRATEGY_PERIOD_NO_FIXED = "NO_FIXED";

    /**
     * 加减乘除
     */
    public static final String OPERATOR_PLUS = "PLUS";
    public static final String OPERATOR_MINUS = "MINUS";
    public static final String OPERATOR_MULTIPLY = "MULTIPLY";
    public static final String OPERATOR_DIVIDE = "DIVIDE";

    /**
     * 小于、小于等于、大于、大于等于、等于、不等于
     *
     */
    public static final String RANGE_LESS_THEN = "10";
    public static final String RANGE_LESS_THEN_EQUAL = "20";
    public static final String RANGE_GREAT_THEN = "30";
    public static final String RANGE_GREAT_THEN_EQUAL = "40";
    public static final String RANGE_EQUAL = "50";
    public static final String RANGE_NOT_EQUAL = "60";

    /**
     * 控制对象：AMOUNT\QUANTITY
     *
     */
    public static final String OBJECT_AMOUNT = "AMOUNT";
    public static final String OBJECT_QUANTITY = "QUANTITY";

    /**
     * 决定机制ID
     */
    @Id
    @GeneratedValue
    @Where
    private Long ctrlStrategyMpCondId;

    /**
     * 策略明细ID
     */
    @NotNull
    @Where
    private Long controlStrategyId;

    /**
     * 10:参数,20:函数
     */
    @NotEmpty
    @Length(max = 30)
    private String type;

    @Transient
    @JoinCode(code = "BGT_STRATEGY_TYPE", joinKey = BgtCtrlStrategyMpCond.FIELD_TYPE)
    private String typeDisplay;

    /**
     * 范围
     */
    @Column(name = "`RANGE`")
    @Length(max = 30)
    private String range;

    @Transient
    @JoinCode(code = "BGT_STRATEGY_RANGE", joinKey = BgtCtrlStrategyMpCond.FIELD_RANGE)
    private String rangeDisplay;

    /**
     * 对象
     */
    @Length(max = 30)
    private String object;

    @Transient
    @JoinCode(code = "BGT_STRATEGY_OBJECT", joinKey = BgtCtrlStrategyMpCond.FIELD_OBJECT)
    private String objectDisplay;

    /**
     * 控制期段
     */
    @Length(max = 30)
    private String periodStrategy;

    @Transient
    @JoinCode(code = "BGT_STRATEGY_PERIOD", joinKey = BgtCtrlStrategyMpCond.FIELD_PERIOD_STRATEGY)
    private String periodStrategyDisplay;

    /**
     * 方式
     */
    @Length(max = 30)
    private String manner;

    @Transient
    @JoinCode(code = "BGT_STRATEGY_MANNER", joinKey = BgtCtrlStrategyMpCond.FIELD_MANNER)
    private String mannerDisplay;

    /**
     * 运算符
     */
    @Length(max = 30)
    private String operator;

    @Transient
    @JoinCode(code = "BGT_STRATEGY_OPERATOR", joinKey = BgtCtrlStrategyMpCond.FIELD_OPERATOR)
    private String operatorDisplay;

    /**
     * 值
     */
    @Column(name = "`VALUE`")
    private BigDecimal value;

    /**
     * And Or
     */
    @Length(max = 30)
    private String andOr;

    /**
     * 用户出口程序
     */
    @Length(max = 100)
    private String userExitProcedure;

}
