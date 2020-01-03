package com.hand.hec.exp.exception;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;

/**
 * @desc;
 * @author: zhongyu.wang@hand-china.com
 * @date: {DATE}
 */
public class ExpMoExpenseItemDescException  extends BaseRuntimeExcepiton {
    public static final String CODE_EXCEPTION = "EXP2160";

    public static String Data_Duplication = "expense_item_desc_duplication";
    public ExpMoExpenseItemDescException(String descriptionKey, Object[] parameters) {
        super(CODE_EXCEPTION, descriptionKey, parameters);
    }
}
