package com.hand.hec.bgt.dto;

import lombok.*;

/**
 * <p>
 * 预算余额查询参数
 * </p>
 *
 * @author YHL 2019/03/22 13:47
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtBalanceQueryParam {

    /**
     * 预算参数
     */
    public static final String BGT_BUDGET_ORGANIZATION = "BGT_ORG";
    public static final String BGT_BUDGET_ENTITY = "BGT_ENTITY";
    public static final String BGT_BUDGET_CURRENCY = "BUDGET_CURRENCY";
    public static final String BGT_BUDGET_VERSION = "BUDGET_VERSION";
    public static final String BGT_BUDGET_SCENARIO = "BUDGET_SCENARIO";
    public static final String BGT_BUDGET_STRUCTURE = "BUDGET_STRUCTURE";
    public static final String BGT_PERIOD = "PERIOD";
    public static final String BGT_PERIOD_QUARTER = "PERIOD_QUARTER";
    public static final String BGT_PERIOD_YEAR = "PERIOD_YEAR";
    public static final String BGT_BUDGET_CENTER = "BGT_CENTER";
    public static final String BGT_BUDGET_ITEM = "BUDGET_ITEM";
    public static final String BGT_BUDGET_ITEM_TYPE = "BUDGET_ITEM_TYPE";

    /**
     * 组织相关参数
     */
    public static final String ORG_COMPANY = "COMPANY";
    public static final String ORG_EMPLOYEE = "EMPLOYEE";
    public static final String ORG_EMPLOYEE_GROUP = "EMPLOYEE_GROUP";
    public static final String ORG_EMPLOYEE_JOB = "EMPLOYEE_JOB";
    public static final String ORG_EMPLOYEE_LEVEL = "EMPLOYEE_LEVEL";
    public static final String ORG_ORG_POSITION = "ORG_POSITION";
    public static final String ORG_ORG_POSITION_GROUP = "ORG_POSITION_GROUP";
    public static final String ORG_ORG_UNIT = "ORG_UNIT";
    public static final String ORG_ORG_UNIT_GROUP = "ORG_UNIT_GROUP";

    /**
     * 维度相关参数
     */
    public static final String DIM_DIMENSION = "DIMENSION_";
    public static final String DIM_DIMENSION_1 = "DIMENSION_1";
    public static final String DIM_DIMENSION_2 = "DIMENSION_2";
    public static final String DIM_DIMENSION_3 = "DIMENSION_3";
    public static final String DIM_DIMENSION_4 = "DIMENSION_4";
    public static final String DIM_DIMENSION_5 = "DIMENSION_5";
    public static final String DIM_DIMENSION_6 = "DIMENSION_6";
    public static final String DIM_DIMENSION_7 = "DIMENSION_7";
    public static final String DIM_DIMENSION_8 = "DIMENSION_8";
    public static final String DIM_DIMENSION_9 = "DIMENSION_9";
    public static final String DIM_DIMENSION_10 = "DIMENSION_10";
    public static final String DIM_DIMENSION_11 = "DIMENSION_11";
    public static final String DIM_DIMENSION_12 = "DIMENSION_12";
    public static final String DIM_DIMENSION_13 = "DIMENSION_13";
    public static final String DIM_DIMENSION_14 = "DIMENSION_14";
    public static final String DIM_DIMENSION_15 = "DIMENSION_15";
    public static final String DIM_DIMENSION_16 = "DIMENSION_16";
    public static final String DIM_DIMENSION_17 = "DIMENSION_17";
    public static final String DIM_DIMENSION_18 = "DIMENSION_18";
    public static final String DIM_DIMENSION_19 = "DIMENSION_19";
    public static final String DIM_DIMENSION_20 = "DIMENSION_20";

    /**
     * 预算表编制期段
     */
    public static final String BGT_STRUCTURE_PERIOD_STRATEGY = "PERIOD_STRATEGY";

    /**
     * 参数类型
     */
    private String param;

    /**
     * 参数值
     */
    private BgtBalanceQueryParamValue paramValue;

}
