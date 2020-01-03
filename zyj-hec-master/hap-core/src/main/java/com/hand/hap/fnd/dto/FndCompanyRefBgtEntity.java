package com.hand.hap.fnd.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import groovy.transform.ToString;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 管理公司关联预算实体dto
 * </p>
 *
 * @author yang.duan 2019/01/10 11:06
 * @author xiuxian.wu 2019/01/25 14:53
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Builder
@Table(name = "fnd_company_ref_bgt_entity")
public class FndCompanyRefBgtEntity extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ENTITY_ID = "entityId";
    public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    @Where
    private Long refId;

    /**
     * 管理公司ID
     */
    @NotNull
    @Where
    private Long companyId;

    /**
     * 预算实体ID
     */
    @NotNull
    @Where
    private Long entityId;


    /**
     * 预算实体代码
     */
    @Transient
    private String bgtEntityCode;

    /**
     * 预算实体名称
     */
    @Transient
    private String bgtEntityName;

    /**
     * 默认标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String defaultFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;
    /**
     * 管理组织Id
     */
    @Transient
    private Long magOrgId;

    /**
     * 四参构造方法
     *
     * @param companyId
     * @param entityId
     * @param defaultFlag
     * @param enabledFlag
     * @author xiuxian.wu 2019/01/25 14:53
     */
    public FndCompanyRefBgtEntity(Long companyId, Long entityId, String defaultFlag, String enabledFlag) {
        this.companyId = companyId;
        this.entityId = entityId;
        this.defaultFlag = defaultFlag;
        this.enabledFlag = enabledFlag;
    }
}
