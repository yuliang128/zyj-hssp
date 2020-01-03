package com.hand.hec.expm.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * ExpReportObject
 * </p>
 *
 * @author yang.duan 2019/01/10 15:00
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_report_object")
public class ExpReportObject extends BaseDTO {

    public static final String FIELD_EXP_REPORT_HEADER_ID = "expReportHeaderId";
    public static final String FIELD_EXP_REPORT_LINE_ID = "expReportLineId";
    public static final String FIELD_MO_EXP_OBJ_TYPE_ID = "moExpObjTypeId";
    public static final String FIELD_MO_EXPENSE_OBJECT_ID = "moExpenseObjectId";
    public static final String FIELD_MO_EXPENSE_OBJECT_NAME = "moExpenseObjectName";


    /**
     * 费用报销单头ID
     */
    @NotNull
    private Long expReportHeaderId;

    /**
     * 费用报销单行ID
     */
    private Long expReportLineId;

    /**
     * 费用对象类型ID
     */
    @NotNull
    private Long moExpObjTypeId;

    @Transient
    private String moExpObjTypeName;

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
