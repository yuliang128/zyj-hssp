package com.hand.hec.fnd.exception;


import com.hand.hap.core.exception.BaseException;

/**
 * <p>
 * 创建会计期间异常
 * </p>
 *
 * @author $JK.Lu$ 2019/01/22 18:36
 */
public class GldPeriodException extends BaseException {

    /**
     * 调整期间定义不合理，请输入合适的调整期间定义数据！
     */
    public static final String GLD_CREATE_ADJUSTMENT_PERIOD_ERROR = "gld_create_adjustment_period_error";

    /**
     * 期间定义不合理，请输入合适的期间定义数据！
     */
    public static final String GLD_CREATE_PERIOD_ERROR = "gld_create_period_error";

    /**
     * 生成期间年度从-年度到输入不合理,请重新输入!
     */
    public static final String FND2120_CREATE_YEAR_ERROR = "fnd2120_create_year_error";

    private static final String CODE_EXCEPTION = "FND2120";

    /**
     * 日期格式转换错误
     */
    public static final String DATE_FORMAT_ERROR = "error.date_format_error";

    public GldPeriodException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }

    public GldPeriodException(String descriptionKey, Object[] parameters) {
        super(CODE_EXCEPTION, descriptionKey, parameters);
    }
}
