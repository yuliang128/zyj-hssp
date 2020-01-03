package com.hand.hap.fnd.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 管理公司关联核算主体关联预算实体dto
 * </p>
 *
 * @author yang.duan 2019/01/10 11:06
 * @author xiuxian.wu 2019/01/25 14:52
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_company_ref_acc_be")
public class FndCompanyRefAccBe extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_ACC_REF_ID = "accRefId";
    public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
    public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    @Where
    private Long refId;

    /**
     * 管理公司关联核算主体ID
     */
    @NotNull
    @Where
    private Long accRefId;

    /**
     * 预算实体ID
     */
    @NotNull
    @Where
    private Long bgtEntityId;

    /**
     * 预算实体名称
     */
    @Transient
    private String bgtEntityName;

    /**
     * 预算实体代码
     */
    @Transient
    private String bgtEntityCode;

    /**
     * 默认标志
     */
    @Length(max = 1)
    @Where
    private String defaultFlag;

    /**
     * 启用标志
     */
    @Length(max = 1)
    @Where
    private String enabledFlag;

    /**
     * 管理组织ID
     */
    @Transient
    private Long magOrgId;

    /**
     * 四参构造方法
     *
     * @param accRefId
     * @param bgtEntityId
     * @param defaultFlag
     * @param enabledFlag
     * @author xiuxian.wu 2019/01/25 14:53
     */
    public FndCompanyRefAccBe(Long accRefId, Long bgtEntityId, String defaultFlag, String enabledFlag) {
        this.accRefId = accRefId;
        this.bgtEntityId = bgtEntityId;
        this.defaultFlag = defaultFlag;
        this.enabledFlag = enabledFlag;
    }
}
