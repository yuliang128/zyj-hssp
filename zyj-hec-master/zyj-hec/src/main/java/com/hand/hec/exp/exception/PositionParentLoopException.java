package com.hand.hec.exp.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * <p>
 * PositionParentLoopException
 * </p>
 *
 * @author gyt 2019/01/16 11:16
 */
public class PositionParentLoopException extends BaseException {

    private static final long serialVersionUID = -3250576758107608016L;

    /**
     * 上级岗位不能存在于本岗位的下级
     */
    public static final String ERROR_PARENT_LOOP = "exp_org_position_parent_position_error";

    private static final String CODE_EXCEPTION = "EXP1020";

    public PositionParentLoopException(String message, Object[] parameters) {
        super(CODE_EXCEPTION, message, parameters);
    }


}
