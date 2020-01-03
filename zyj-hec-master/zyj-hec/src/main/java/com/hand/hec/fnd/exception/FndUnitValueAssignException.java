package com.hand.hec.fnd.exception;


import com.hand.hap.core.exception.BaseException;

/**
 * <p>
 * 创建会计期间异常
 * </p>
 *
 * @author $JK.Lu$ 2019/01/22 18:36
 */
public class FndUnitValueAssignException extends BaseException {

    /**
     * 调整期间定义不合理，请输入合适的调整期间定义数据！
     */
    public static final String INSERT_FND_UNIT_DIM_VALUE_ASSIGN_ERROR = "insert_fnd_unit_dim_value_assign_error";

    /**
     * 不能删除默认维值！
     */
    public static final String DELETE_FND_UNIT_DIM_VALUE_ASSIGN_ERROR = "delete_fnd_unit_dim_value_assign_error";

    private static final String CODE_EXCEPTION = "FND1010";

    public FndUnitValueAssignException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }

    public FndUnitValueAssignException(String descriptionKey, Object[] parameters) {
        super(CODE_EXCEPTION, descriptionKey, parameters);
    }
}
