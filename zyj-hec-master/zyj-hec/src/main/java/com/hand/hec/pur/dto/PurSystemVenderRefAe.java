package com.hand.hec.pur.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;

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
 * 供应商分配核算主体dto
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/20 15:45
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "pur_system_vender_ref_ae")
public class PurSystemVenderRefAe extends BaseDTO {


    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_VENDER_ID = "venderId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 供应商ID
     */
    @NotNull
    private Long venderId;

    /**
     * 核算主体ID
     */
    @NotNull
    private Long accEntityId;

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

    @Transient
    private List<Long> vendersId;

}
