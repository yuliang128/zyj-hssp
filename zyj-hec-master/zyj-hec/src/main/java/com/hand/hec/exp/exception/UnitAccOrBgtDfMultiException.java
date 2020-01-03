package com.hand.hec.exp.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * <p>
 * UnitAccOrBgtDfMultiException
 * </p>
 *
 * @author gyt 2019/01/16 11:16
 */
public class UnitAccOrBgtDfMultiException extends BaseException {

    private static final long serialVersionUID = -3250576758107608016L;

    /**
     * 同一核算主体下，部门至多关联一个默认成本中心
     */
    public static final String ERROR_UNIT_ACC_DEFAULT_MULTI = "exp_org_unit_ref_acc_error";

    /**
     * 只能有一个启用的默认组织
     *
     */
    public static final String ERROR_UNIT_BGT_DEFAULT_MULTI = "exp_org_unit_ref_bgt_error";

    public static final String ERROR_PARENT_LOOP = "exp_org_unit_parent_unit_error";


    public UnitAccOrBgtDfMultiException(String code, String message) {
        super(code, message, null);
    }


}
