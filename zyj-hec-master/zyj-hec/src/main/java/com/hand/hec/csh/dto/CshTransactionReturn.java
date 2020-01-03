package com.hand.hec.csh.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 付款退款信息
 *
 * @author mouse 2019/05/05 11:22
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
public class CshTransactionReturn {

    private String sourceType;
    private Long sourceId;
    private BigDecimal returnAmount;
}
