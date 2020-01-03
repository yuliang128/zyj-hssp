package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 预算控制策略组
 * </p>
 * 
 * @author mouse 2019/01/07 16:23
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_control_strategy_group")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtControlStrategyGroup extends BaseDTO {

    public static final String FIELD_CONTROL_STRATEGY_GROUP_ID = "controlStrategyGroupId";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_CONTROL_STRATEGY_GROUP_CODE = "controlStrategyGroupCode";
    public static final String FIELD_CONTROL_STRATEGY_GROUP_DESC = "controlStrategyGroupDesc";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    @Where
    private Long controlStrategyGroupId;

    /**
     * 预算组织ID
     */
    @NotNull
    @Where
    private Long bgtOrgId;

    /**
     * 预算策略组代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where(comparison = Comparison.LIKE)
    private String controlStrategyGroupCode;

    /**
     * 预算策略组描述
     */
    @Length(max = 2000)
    @Where(comparison = Comparison.LIKE)
    private String controlStrategyGroupDesc;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;


}
