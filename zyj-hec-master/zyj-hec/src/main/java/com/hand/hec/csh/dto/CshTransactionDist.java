package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_transaction_dist")

/**
 * <p>
 * 现金事务分配行DTO
 * </p>
 * 
 * @author Tagin 2019/01/22 20:02
 */
public class CshTransactionDist extends BaseDTO {

    public static final String FIELD_TRANSACTION_HEADER_ID = "transactionHeaderId";
    public static final String FIELD_DISTRIBUTION_LINE_ID = "distributionLineId";
    public static final String FIELD_SOURCE_DISTRIBUTION_LINE_ID = "sourceDistributionLineId";
    public static final String FIELD_DEBIT_FLAG = "debitFlag";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_RESPONSIBILITY_CENTER_ID = "responsibilityCenterId";
    public static final String FIELD_ACCOUNT_ID = "accountId";
    public static final String FIELD_DESCRIPTION = "description";


    /**
     * 交易事务头ID
     */
    @NotNull
    private Long transactionHeaderId;

    @Id
    @GeneratedValue
    private Long distributionLineId;

    /**
     * 来源单据分配行ID
     */
    private Long sourceDistributionLineId;

    /**
     * 借方
     */
    @Length(max = 2)
    private String debitFlag;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 成本中心ID
     */
    @NotNull
    private Long responsibilityCenterId;

    /**
     * 科目ID
     */
    @NotNull
    private Long accountId;

    /**
     * 描述
     */
    @Length(max = 2000)
    private String description;

}
