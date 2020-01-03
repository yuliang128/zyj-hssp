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
 *  ExpOrgUnitTypeRelation
 * </p>
 *
 * @author yang.duan 2019/01/10 11:17
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_org_unit_type_relation")
public class ExpOrgUnitTypeRelation extends BaseDTO {

     public static final String FIELD_UNIT_TYPE_ID = "unitTypeId";
     public static final String FIELD_UNIT_ID = "unitId";


    /**
     * 部门类型id
     */
     @Id
     @GeneratedValue
     private Long unitTypeId;

    /**
     * 部门id
     */
     @NotNull
     private Long unitId;

     }
