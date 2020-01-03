package com.hand.hec.fnd.dto;

import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "fnd_uom_asgn_mo")
public class FndUomAsgnMo extends BaseDTO {

     public static final String FIELD_ASSIGN_MO_ID = "assignMoId";
     public static final String FIELD_UOM_ID = "uomId";
     public static final String FIELD_MAG_ORG_ID = "magOrgId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";
     public static final String FIELD_MAG_ORG_NAME ="magOrgName";
     public static final String FILED_MAG_ORG_CODE ="magOrgCode";


     @Id
     @GeneratedValue
     private Long assignMoId;

    /**
     * 管理组织级计量单位ID
     */
     @NotNull
     @Where
     private Long uomId;

    /**
     * 管理组织ID
     */
     @NotNull
     @Where
     private Long magOrgId;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

    /**
     * 管理组织代码
     */
     @Transient
     private String magOrgCode;

    /**
     * 管理组织描述
     */
    @Transient
    private String magOrgName;

}
