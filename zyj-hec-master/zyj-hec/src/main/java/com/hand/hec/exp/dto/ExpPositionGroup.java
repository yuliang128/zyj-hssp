package com.hand.hec.exp.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 岗位组dto
 * </p>
 *
 * @author YHL 2019/01/18 12:13
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_position_group")
@MultiLanguage
public class ExpPositionGroup extends BaseDTO {

    public static final String FIELD_POSITION_GROUP_ID = "positionGroupId";
    public static final String FIELD_POSITION_GROUP_CODE = "positionGroupCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_POSITION_ID = "positionId";

    /**
     * 岗位组ID
     */
    @Id
    @GeneratedValue
    private Long positionGroupId;

    /**
     * 岗位组
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String positionGroupCode;

    /**
     * 描述
     */
    @MultiLanguageField
    @Where
    @NotEmpty
    @Length(max = 500)
    private String description;

    /**
     * 公司ID
     */
    @Where
    private Long companyId;

    /**
     * 启用标志
     */
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 岗位ID
     */
    @Transient
    private Long positionId;

}
