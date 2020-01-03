package com.hand.hec.exp.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * <p>
 * 单据填写说明异常
 * </p>
 *
 * @author yang.duan 2019-2-12 11:23:08
 */
public class WriteCaptionMultiException extends BaseException {

    private static final long serialVersionUID = -3250576758107608016L;
    /*填写说明代码重复,请重新定义!*/
    public static String ERROR_WRITE_CAPTION_HDS_UNIQUE_MULTI = "write_caption_hds_unique_error";

    /*步骤序号重复,请重新定义!*/
    public static String ERROR_WRITE_CAPTION_LNS_UNIQUE_MULTI = "write_caption_lns_unique_error";


    public WriteCaptionMultiException(String code, String message) {
        super(code, message, null);
    }


}
