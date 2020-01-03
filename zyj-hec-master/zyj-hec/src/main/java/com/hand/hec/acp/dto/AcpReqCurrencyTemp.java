package com.hand.hec.acp.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.*;
import org.jasypt.encryption.BigDecimalEncryptor;

/**
 * 创建凭证时需要的临时表
 *
 * @author guiyuting 2019/05/08 10:35
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcpReqCurrencyTemp {

    private Long requisitionHdsId;

    private String currencyCode;

    private String exchangeRateType;

    private BigDecimal exchangeRate;

    private Date creationDate;

}
