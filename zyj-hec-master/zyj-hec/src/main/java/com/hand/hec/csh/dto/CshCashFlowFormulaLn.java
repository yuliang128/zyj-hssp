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
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_cash_flow_formula_ln")
public class CshCashFlowFormulaLn extends BaseDTO {

    public static final String FIELD_FORMULA_ID = "formulaId";
    public static final String FIELD_FORMULA_LINE_ID = "formulaLineId";
    public static final String FIELD_OPERATION = "operation";
    public static final String FIELD_CASH_FLOW_ITEM_ID_FROM = "cashFlowItemIdFrom";
    public static final String FIELD_CASH_FLOW_ITEM_ID_TO = "cashFlowItemIdTo";
    public static final String FIELD_OPERATION_DESC = "operationDesc";
    public static final String FIELD_CASH_FLOW_ITEM_ID_FROM_DESC = "cashFlowItemIdFromDesc";
    public static final String FIELD_CASH_FLOW_ITEM_ID_TO_DESC = "cashFlowItemIdToDesc";


    /**
     * 公式ID
     */
    @NotNull
    @Where
    private Long formulaId;

    @Id
    @GeneratedValue
    @Where
    private Long formulaLineId;

    /**
     * 运算符
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String operation;

    /**
     * 现金流入项ID
     */
    @NotNull
    @Where
    private Long cashFlowItemIdFrom;

    /**
     * 现金流出项ID
     */
    @NotNull
    @Where
    private Long cashFlowItemIdTo;

    /**
     * 运算符描述
     */
    @Transient
    @Where
    private String operationDesc;

    /**
     * 现金流入项描述
     */
    @Transient
    @Where
    private String cashFlowItemIdFromDesc;

    /**
     * 现金流出项描述
     */
    @Transient
    @Where
    private String cashFlowItemIdToDesc;

}
