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
public class BgtCheckReserveResult {

    private BigDecimal totalAmount;
    private BigDecimal totalFunctionalAmount;
    private BigDecimal totalQuantity;
}
