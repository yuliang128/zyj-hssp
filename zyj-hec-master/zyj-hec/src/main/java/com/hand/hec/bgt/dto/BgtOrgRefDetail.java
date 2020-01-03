package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * 预算组织来源明细
 * </p>
 * 
 * @author mouse 2019/01/07 16:40
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_org_ref_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtOrgRefDetail extends BaseDTO {

    public static final String FIELD_DETAIL_ID = "detailId";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_SOURCE_ID = "sourceId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    private Long detailId;

    /**
     * 预算组织ID
     */
    @NotNull
    @Where
    private Long bgtOrgId;

    /**
     * 来源ID
     */
    @NotNull
    private Long sourceId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 来源明细代码
     */
    @Transient
    private String sourceCode;

    /**
     * 来源明细描述
     */
    @Transient
    private String sourceName;

}
