package com.hand.hap.fnd.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * description
 *
 * @author xingjialin 2019/03/06 16:16
 */
public class FndCompanyException extends BaseException {

    /**
     * 员工公司不存在.
     */
    public static final String MSG_INVALID_EMPLOYEE_COMPANY = "error.employee_company_invalid";


    public FndCompanyException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }
}
