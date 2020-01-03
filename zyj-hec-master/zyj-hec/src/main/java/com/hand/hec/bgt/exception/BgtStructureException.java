package com.hand.hec.bgt.exception;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;

/**
 * description
 *
 * @author mouse 2019/01/09 21:17
 */
public class BgtStructureException extends BaseRuntimeExcepiton {

    public static String PERIOD_STRATEGY_ERROR = "bgt_budget_strc_period_strategy_error";

    public static String BUDGET_DIMENSION_DUPLICATE = "bgt_budget_strc_layouts_dimension_duplicate";

    private static final String CODE_EXCEPTION = "BGT2110";

    /**
     *
     * @param code 异常 code,通常与模块 CODE 对应
     * @param descriptionKey 异常消息代码,系统描述中定义
     * @param parameters
     */
    public BgtStructureException(String descriptionKey, Object[] parameters) {
        super(CODE_EXCEPTION, descriptionKey, parameters);
    }
}
