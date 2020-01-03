package com.hand.hap.fnd.dto;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 管理公司关联核算主体dto
 * </p>
 *
 * @author yang.duan 2019/01/10 11:06
 * @suthor xiuxian.wu 2019/01/25 14:54
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_company_ref_acc_entity")
public class FndCompanyRefAccEntity extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
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
     * 默认预算主体ID
     */
    @Transient
    private Long bgtEntityId;

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
     * 核算主体ID
     */
    @NotNull
    @Where
    private Long accEntityId;

    /**
     * 核算主体代码
     */
    @Transient
    private String accEntityCode;

    /**
     * 核算主体名称
     */
    @Transient
    private String accEntityName;

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
     * @param accEntityId
     * @param defaultFlag
     * @param enabledFlag
     * @author xiuxian.wu 2019/01/25 14:53
     */
    public FndCompanyRefAccEntity(Long companyId, Long accEntityId, String defaultFlag, String enabledFlag) {
        this.companyId = companyId;
        this.accEntityId = accEntityId;
        this.defaultFlag = defaultFlag;
        this.enabledFlag = enabledFlag;
    }
}
