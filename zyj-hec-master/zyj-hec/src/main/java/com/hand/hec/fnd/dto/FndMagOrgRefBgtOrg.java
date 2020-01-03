package com.hand.hec.fnd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.bgt.dto.BgtOrganization;

/**
 * <p>
 * FndMagOrgRefBgtOrg
 * </p>
 *
 * @author yang.duan 2019/01/10 10:53
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_mag_org_ref_bgt_org")
public class FndMagOrgRefBgtOrg extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @Where
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织ID
     */
    @NotNull
    @Where
    private Long magOrgId;

    /**
     * 预算组织ID
     */
    @JoinTable(name = "bgtJoin", joinMultiLanguageTable = false, target = BgtOrganization.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = BgtOrganization.FIELD_BGT_ORG_ID)})
    @NotNull
    private Long bgtOrgId;

    /**
     * 預算组织描述
     */
    @Transient
    @JoinColumn(joinName = "bgtJoin", field = BgtOrganization.FIELD_DESCRIPTION)
    private String description;

    /**
     * 預算组织编码
     */
    @Transient
    @JoinColumn(joinName = "bgtJoin", field = BgtOrganization.FIELD_BGT_ORG_CODE)
    private String bgtOrgCode;

    /**
     * 默认标志
     */
    @NotEmpty
    @Length(max = 1)
    private String defaultFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
