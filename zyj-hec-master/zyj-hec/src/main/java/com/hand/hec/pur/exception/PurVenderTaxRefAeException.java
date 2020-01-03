package com.hand.hec.pur.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * description
 *
 * @author xingjialin 2019/03/02 22:17
 */
public class PurVenderTaxRefAeException extends BaseException {

    public static final String PUR_VENDER_TAX_REF_AE_CODE_DUPLICATE = "pur_vender_tax_ref_ae_code_duplicate";

    public PurVenderTaxRefAeException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }
}
