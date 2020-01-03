package com.hand.hec.bgt.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * description
 *
 * @author mouse 2019/01/23 12:02
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtCheckBalanceResult {

    private BigDecimal totalPeriodAmount;
    private BigDecimal totalPeriodFunctionalAmount;
    private BigDecimal totalPeriodQuantity;

    private BigDecimal totalQuarterAmount;
    private BigDecimal totalQuarterFunctionalAmount;
    private BigDecimal totalQuarterQuantity;

    private BigDecimal totalYearAmount;
    private BigDecimal totalYearFunctionalAmount;
    private BigDecimal totalYearQuantity;
}
