package com.hand.hec.csh.dto;

import javax.persistence.*;

import com.hand.hap.mybatis.common.query.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_cash_flow_formula_hd")
public class CshCashFlowFormulaHd extends BaseDTO {

    public static final String FIELD_CASH_FLOW_ITEM_ID = "cashFlowItemId";
    public static final String FIELD_FORMULA_ID = "formulaId";
    public static final String FIELD_CASH_FLOW_LINE_NUMBER = "cashFlowLineNumber";
    public static final String FIELD_CASH_FLOW_ITEM_DESC = "cashFlowItemDesc";

    /**
     * 现金流量项ID
     */
    @NotNull
    @Where
    private Long cashFlowItemId;

    @Id
    @GeneratedValue
    @Where
    private Long formulaId;

    /**
     * 现金流量项行号
     */
    @Transient
    @Where
    private Long cashFlowLineNumber;

    /**
     * 现金流量项描述
     */
    @Transient
    @Where
    private String cashFlowItemDesc;

}
