package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * 预算项目分配公司
 * </p>
 * 
 * @author mouse 2019/01/07 16:11
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_budget_item_asgn_com")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtBudgetItemAsgnCom extends BaseDTO {

    public static final String FIELD_ASSIGN_MO_ID = "assignMoId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_ASSIGN_COM_ID = "assignComId";
    public static final String FIELD_COMPANY_CODE = "companyCode";
    public static final String FIELD_COMPANY_NAME = "companyName";


    /**
     * 预算项目分配管理组织ID
     */
    private Long assignMoId;

    /**
     * 管理公司ID
     */
    private Long companyId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @Id
    @GeneratedValue
    private Long assignComId;

    @Transient
    private String companyCode;

    @Transient
    private String companyName;


}
