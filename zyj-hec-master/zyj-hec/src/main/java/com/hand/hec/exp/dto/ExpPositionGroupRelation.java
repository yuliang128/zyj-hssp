package com.hand.hec.exp.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 岗位分配岗位组dto
 * </p>
 *
 * @author YHL 2019/01/18 12:14
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_position_group_relation")
public class ExpPositionGroupRelation extends BaseDTO {

    public static final String FIELD_RELATION_ID = "relationId";
    public static final String FIELD_POSITION_GROUP_ID = "positionGroupId";
    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_POSITION_CODE = "positionCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_COMPANY_SHORT_NAME = "companyShortName";

    /**
     * 岗位分配岗位组关联ID
     */
    @Id
    @GeneratedValue
    private Long relationId;

    /**
     * 岗位组ID
     */
    @OrderBy
    @NotNull
    private Long positionGroupId;

    /**
     * 岗位ID
     */
    @NotNull
    private Long positionId;

    /**
     * 岗位代码
     */
    @OrderBy
    @Transient
    @Length(max = 30)
    private String positionCode;

    /**
     * 岗位名称
     */
    @Transient
    @Length(max = 500)
    private String description;

    /**
     * 公司ID
     */
    @Transient
    private Long companyId;

    /**
     * 公司简称
     */
    @Transient
    @Length(max = 500)
    private String companyShortName;

}
