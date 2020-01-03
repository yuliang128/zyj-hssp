package com.hand.hec.exp.dto;

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
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * ExpMoRepTypeRefExpType
 * </p>
 *
 * @author yang.duan 2019/01/10 14:45
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_mo_rep_type_ref_exp_type")
public class ExpMoRepTypeRefExpType extends BaseDTO {

     public static final String FIELD_REF_ID = "refId";
     public static final String FIELD_MO_EXP_REPORT_TYPE_ID = "moExpReportTypeId";
     public static final String FIELD_MO_EXPENSE_TYPE_ID = "moExpenseTypeId";
     public static final String FIELD_DEFAULT_FLAG = "defaultFlag";


     @Id
     @GeneratedValue
     private Long refId;

    /**
     * 管理组织级报销单类型ID
     */
     @NotNull
     private Long moExpReportTypeId;

    /**
     * 管理组织级报销类型ID
     */
     @NotNull
     private Long moExpenseTypeId;

    /**
     * 默认标志
     */
     @NotEmpty
     @Length(max = 1)
     private String defaultFlag;

     }
