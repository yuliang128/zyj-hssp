package com.hand.hec.fnd.dto;

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
 * 供应商类型分配核算主体dto
 * </p>
 *
 * @author YHL 2019/01/29 12:45
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "pur_vender_type_ref_ae")
public class PurVenderTypeRefAe extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_VENDER_TYPE_ID = "venderTypeId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_ACC_ENTITY_CODE = "accEntityCode";
    public static final String FIELD_ACC_ENTITY_NAME = "accEntityName";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    /**
     * 供应商类型分配核算主体ID
     */
    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 供应商类型ID
     */
    @NotNull
    private Long venderTypeId;

    /**
     * 核算主体ID
     */
    @NotNull
    private Long accEntityId;

    /**
     * 核算主体代码
     */
    @Transient
    @Length(max = 30)
    private String accEntityCode;

    /**
     * 核算主体名称
     */
    @Transient
    @Length(max = 500)
    private String accEntityName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
