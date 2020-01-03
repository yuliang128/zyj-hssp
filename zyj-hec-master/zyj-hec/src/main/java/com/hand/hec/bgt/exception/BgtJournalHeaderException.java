package com.hand.hec.bgt.exception;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;

/**
 * description
 *
 * @author mouse 2019/03/14 15:53
 */
public class BgtJournalHeaderException extends BaseRuntimeExcepiton {
    public static String APPROVE_CANCEL_ERROR = "bgt_journal_cancel_approved_status_error";

    public static String WITHDRAWAL_ERROR = "bgt_journal_withdrawal_status_error";

    public static String REJECTED_ERROR = "bgt_journal_rejected_status_error";

    public static String APPROVED_ERROR = "bgt_journal_approved_status_error";

    public static String POSTED_ERROR = "bgt_journal_posted_status_error";

    public static String REVERSED_ERROR = "bgt_journal_reversed_status_error";

    public static String EXCHANGE_AMOUNT_NULL = "exchange_amount_null";

    public static String ADJ_AMOUNT_ERROR = "bgt_journal_adjust_amount_error";

    private static final String CODE_EXCEPTION = "BGT";

    /**
     *
     * @param code 异常 code,通常与模块 CODE 对应
     * @param descriptionKey 异常消息代码,系统描述中定义
     * @param parameters
     */
    public BgtJournalHeaderException(String descriptionKey, Object[] parameters) {
        super(CODE_EXCEPTION, descriptionKey, parameters);
    }
}

