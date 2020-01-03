package com.hand.hec.pur.dto;

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
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 供应商银行账户核算主体分配dto
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/21 14:25
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "pur_vender_account_ref_ae")
public class PurVenderAccountRefAe extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_ACCOUNT_ID = "accountId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_PRIMARY_FLAG = "primaryFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 供应商银行账户ID
     */
    @NotNull
    private Long accountId;

    /**
     * 核算主体ID
     */
    @NotNull
    private Long accEntityId;

    /**
     * 主帐号标志
     */
    @NotEmpty
    @Length(max = 1)
    private String primaryFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

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
     * 供应商ID
     */
    @Transient
    private Long venderId;

}
