package com.hand.hec.gld.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

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
import com.hand.hec.bgt.dto.BgtEntity;

/**
 * <p>
 * GldAccEntityRefBe
 * </p>
 *
 * @author yang.duan 2019/01/10 13:44
 * update by luhui 2019/01/16 15:58
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "gld_acc_entity_ref_be")
public class GldAccEntityRefBe extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
    public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 核算主体ID
     */
    @NotNull
    @Where
    private Long accEntityId;

    /**
     * 预算实体ID
     */
    @JoinTable(name = "bgtEntityJoin", joinMultiLanguageTable = false, target = BgtEntity.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = BgtEntity.FIELD_ENTITY_ID)})
    @NotNull
    private Long bgtEntityId;

    /**
     * 预算实体代码
     */
    @JoinColumn(joinName = "bgtEntityJoin", field = BgtEntity.FIELD_ENTITY_CODE)
    @Transient
    private String bgtEntityCode;

    /**
     * 预算实体描述
     */
    @JoinColumn(joinName = "bgtEntityJoin", field = BgtEntity.FIELD_DESCRIPTION)
    @Transient
    private String bgtEntityName;

    /**
     * 默认标志
     */
    @Length(max = 1)
    private String defaultFlag;

    /**
     * 启用标志
     */
    @Length(max = 1)
    private String enabledFlag;

}
