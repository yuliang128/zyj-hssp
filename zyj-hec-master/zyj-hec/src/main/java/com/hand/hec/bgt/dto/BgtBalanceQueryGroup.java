package com.hand.hec.bgt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mouse 2019/04/17 13:57
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BgtBalanceQueryGroup {
    /**
     * 公司字段
     */
    Long companyId;
    String companyCode;

    /**
     * 预算组织字段
     */
    Long bgtOrgId;
    String bgtOrgCode;

    /**
     * 预算表字段
     */
    Long structureId;
    String structureCode;

    /**
     * 预算版本字段
     */
    Long versionId;
    String versionCode;

    /**
     * 预算场景字段
     */
    Long scenarioId;
    String scenarioCode;

    /**
     * 预算实体字段
     */
    Long bgtEntityId;
    String bgtEntityCode;

    /**
     * 币种字段
     */
    String currencyCode;

    /**
     * 金额或者数量
     */
    String amountOrQuantity;

    /**
     * 期间从
     */
    String periodFrom;
    Long internalPeriodNumFrom;

    /**
     * 期间到
     */
    String periodTo;
    Long internalPeriodNumTo;

    /**
     * 期间汇总
     */
    String periodSummary;

    /**
     * 季度从
     */
    Long quarterFrom;

    /**
     * 季度到
     */
    Long quarterTo;

    /**
     * 年度从
     */
    Long yearFrom;

    /**
     * 年度到
     */
    Long yearTo;

    /**
     * 预算条件List
     */
    List<BgtBalanceQueryCondition> bgtConditions;

    /**
     * 组织条件List
     */
    List<BgtBalanceQueryCondition> orgConditions;

    /**
     * 组织条件List
     */
    List<BgtBalanceQueryCondition> dimConditions;
}
