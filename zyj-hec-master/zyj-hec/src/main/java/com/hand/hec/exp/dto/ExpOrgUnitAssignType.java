package com.hand.hec.exp.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
/**
 * <p>
 *  ExpOrgUnitAssignType
 * </p>
 *
 * @author yang.duan 2019/01/10 11:16
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_org_unit_assign_type")
public class ExpOrgUnitAssignType extends BaseDTO {

     public static final String FIELD_ASSIGN_TYPES_ID = "assignTypesId";
     public static final String FIELD_UNIT_TYPE_ID = "unitTypeId";
     public static final String FIELD_UNIT_TYPE_CODE = "unitTypeCode";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";
     public static final String FIELD_DESCRIPTION = "description";


     @Id
     @GeneratedValue
     private Long assignTypesId;

    /**
     * 部门级别id
     */
     @NotNull
     private Long unitTypeId;

    /**
     * 部门级别编码
     */
     @Length(max = 30)
     private String unitTypeCode;

    /**
     * 公司ID
     */
     @NotNull
     private Long companyId;

    /**
     * 启用标志
     */
     @Length(max = 1)
     private String enabledFlag;

    /**
     * 描述
     */
     @Length(max = 500)
     private String description;

     }
