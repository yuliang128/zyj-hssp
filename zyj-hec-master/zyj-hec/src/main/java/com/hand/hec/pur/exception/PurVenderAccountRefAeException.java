package com.hand.hec.pur.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * 供应商银行账户相关异常
 *
 * @author xingjialin 2019/02/22 16:24
 */
public class PurVenderAccountRefAeException extends BaseException {

    public static final String PRIMARY_FLAG_CHECK_ERROR = "primary_flag_check_error";

    public static final String PUR_VEN_ACCOUNT_REF_AE_CODE_DUPLICATE = "pur_ven_account_ref_ae_code_duplicate";

    public PurVenderAccountRefAeException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }
}
