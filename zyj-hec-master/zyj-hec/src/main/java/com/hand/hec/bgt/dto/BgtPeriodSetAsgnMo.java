package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * 预算期间集分配管理组织
 * </p>
 * 
 * @author mouse 2019/01/07 16:46
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_period_set_asgn_mo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtPeriodSetAsgnMo extends BaseDTO {

    public static final String FIELD_ASSIGN_MO_ID = "assignMoId";
    public static final String FIELD_PERIOD_SET_ID = "periodSetId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long assignMoId;

    /**
     * 预算期ID
     */
    @NotNull
    private Long periodSetId;

    /**
     * 管理组织ID
     */
    @NotNull
    private Long magOrgId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
