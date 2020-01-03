package com.hand.hec.fnd.exception;


import com.hand.hap.core.exception.BaseException;

/**
 * <p>
 * 客户主文件定义异常
 * </p>
 *
 * @author guiyu 2019/02/20 19:36
 */
public class OrdSystemCustomerException extends BaseException {

    /**
     * 客户编码【#CUSTOMER_CODE】 已存在其他核算主体下, 请重新录入!
     */
    public static final String EXISTS_CUSTOMER_ERROR = "ord_ae_customer_code_exists";

    /**
     * 客户代码不能重复!
     */
    public static final String CUSTOMER_CODE_ERROR = "ord_customer_code_duplicate";

    /**
     *
     * 当前客户已分配该核算主体,请检查!
     */
    public static final String ACC_ENTITY_ERROR = "ord_customer_ref_ae_code_duplicate";

    /**
     *
     * 仅能存在一个主帐号, 请重新维护!
     */
    public static final String PRIMARY_FLAG_ERROR = "pur_primary_flag_check_error";

    /**
     *
     * 当前银行账号已分配该核算主体,请检查!
     */
    public static final String BANK_ACC_ENTITY_ERROR = "ord_cus_account_ref_ae_code_duplicate";

    /**
     *
     * 当前纳税人类型已分配该核算主体,请检查!
     */
    public static final String TAX_ACC_ENTITY_ERROR = "ord_customer_tax_ref_ae_code_duplicate";


    private static final String CODE_EXCEPTION = "FND2520";

    public OrdSystemCustomerException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }

    public OrdSystemCustomerException(String descriptionKey, Object[] parameters) {
        super(CODE_EXCEPTION, descriptionKey, parameters);
    }
}
