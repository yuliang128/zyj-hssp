package com.hand.hec.exp.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_requisition_object")
public class ExpRequisitionObject extends BaseDTO {

    public static final String FIELD_EXP_REQUISITION_HEADER_ID = "expRequisitionHeaderId";
    public static final String FIELD_EXP_REQUISITION_LINE_ID = "expRequisitionLineId";
    public static final String FIELD_MO_EXP_OBJ_TYPE_ID = "moExpObjTypeId";
    public static final String FIELD_MO_EXPENSE_OBJECT_ID = "moExpenseObjectId";
    public static final String FIELD_MO_EXPENSE_OBJECT_NAME = "moExpenseObjectName";


    /**
     * 费用申请单头ID
     */
    @NotNull
    @Where
    private Long expRequisitionHeaderId;

    /**
     * 费用申请单行ID
     */
    @Where
    private Long expRequisitionLineId;

    /**
     * 费用对象类型ID
     */
    @NotNull
    @Where
    private Long moExpObjTypeId;

    /**
     * 费用对象ID
     */
    private Long moExpenseObjectId;

    /**
     * 费用对象描述
     */
    @Length(max = 2000)
    private String moExpenseObjectName;
    @Transient
    private String returnField;

    @Transient
    private String displayField;

    @Transient
    private String requiredFlag;

    @Transient
    private String expenseObjectMethod;

    @Transient
    private String sqlQuery;
}
