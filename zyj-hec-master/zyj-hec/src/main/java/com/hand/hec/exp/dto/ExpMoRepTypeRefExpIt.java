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
 * ExpMoRepTypeRefExpIt
 * </p>
 *
 * @author yang.duan 2019/01/10 14:45
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_mo_rep_type_ref_exp_it")
public class ExpMoRepTypeRefExpIt extends BaseDTO {

     public static final String FIELD_REF_ID = "refId";
     public static final String FIELD_EXP_TYPE_REF_ID = "expTypeRefId";
     public static final String FIELD_MO_EXPENSE_ITEM_ID = "moExpenseItemId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long refId;

    /**
     * 管理组织级报销单类型与报销类型关联ID
     */
     @NotNull
     private Long expTypeRefId;

    /**
     * 费用项目ID
     */
     @NotNull
     private Long moExpenseItemId;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
