package com.hand.hec.exp.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * <p>
 * 申请项目定义异常
 * </p>
 *
 * @author yang.duan 2019-2-12 11:23:08
 */
public class ReqItemMultiException extends BaseException {

    private static final long serialVersionUID = -3250576758107608016L;
    /*申请项目代码已经存在,请重新定义!*/
    public static String ERROR_REQ_ITEM_UNIQUE_MULTI = "exp_requisition_item_duplicate";




    public ReqItemMultiException(String code, String message) {
        super(code, message, null);
    }


}
