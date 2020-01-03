package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.validation.constraints.NotNull;
/**
 * <p>
 * 预算表授权
 * </p>
 * 
 * @author mouse 2019/01/07 16:21
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_budget_strc_authority")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtBudgetStrcAuthority extends BaseDTO {

    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_BUDGET_STRC_HEADER_ID = "budgetStrcHeaderId";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_STRC_ENTERABLE = "strcEnterable";
    public static final String FIELD_BUDGET_ENTERABLE = "budgetEnterable";


    /**
     * 公司ID
     */
    @NotNull
    private Long companyId;

    /**
     * 预算表头ID
     */
    @Id
    @GeneratedValue
    private Long budgetStrcHeaderId;

    /**
     * 用户ID
     */
    @NotNull
    private Long userId;

    /**
     * 预算表维护
     */
    @Length(max = 1)
    private String strcEnterable;

    /**
     * 预算编制
     */
    @Length(max = 1)
    private String budgetEnterable;


}
