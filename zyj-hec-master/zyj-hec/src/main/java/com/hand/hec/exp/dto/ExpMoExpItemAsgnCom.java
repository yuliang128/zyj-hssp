package com.hand.hec.exp.dto;

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
@Table(name = "exp_mo_exp_item_asgn_com")
public class ExpMoExpItemAsgnCom extends BaseDTO {


    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_MO_EXPENSE_ITEM_ID = "moExpenseItemId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_COMPANY_CODE = "companyCode";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_COMPANY_SHORT_NAME = "companyShortName";


    @Id
    @GeneratedValue
    private Long assignId;

    /**
     * 费用项目ID
     */
    @NotNull
    @Where
    private Long moExpenseItemId;

    /**
     * 公司ID
     */
    @NotNull
    @Where
    private Long companyId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 管理组织ID
     */
    @Transient
    private Long magOrgId;

    /**
     * 公司代码
     */
    @Transient
    private String companyCode;

    /**
     * 公司名称
     */
    @Transient
    private String companyShortName;


}
