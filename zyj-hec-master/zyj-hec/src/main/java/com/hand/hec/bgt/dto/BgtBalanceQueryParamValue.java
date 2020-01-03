package com.hand.hec.bgt.dto;

import lombok.*;

/**
 * <p>
 * 预算余额查询参数值
 * </p>
 *
 * @author YHL 2019/03/22 14:02
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtBalanceQueryParamValue {

    /**
     * 取值范围：全部、明细、汇总
     */
    public static final String CONTROL_RULE_RANGE_ALL = "ALL";
    public static final String CONTROL_RULE_RANGE_DETAIL = "DETAIL";
    public static final String CONTROL_RULE_RANGE_SUMMARY = "SUMMARY";

    /**
     * 取值范围
     */
    private String controlRuleRange;

    /**
     * 参数代码（值）
     */
    private String parameterCode;

    /**
     * 参数代码始值
     */
    private String parameterLowerLimit;

    /**
     * 参数代码止值
     */
    private String parameterUpperLimit;

    private String str1;
    private String str2;
    private String str3;
    private String str4;
    private String str5;
    private Long long1;
    private Long long2;

    public BgtBalanceQueryParamValue(String controlRuleRange, String parameterCode, String parameterLowerLimit,
            String parameterUpperLimit) {
        this.controlRuleRange = controlRuleRange;
        this.parameterCode = parameterCode;
        this.parameterLowerLimit = parameterLowerLimit;
        this.parameterUpperLimit = parameterUpperLimit;
    }
}
