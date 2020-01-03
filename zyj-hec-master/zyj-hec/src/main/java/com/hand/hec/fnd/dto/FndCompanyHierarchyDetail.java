package com.hand.hec.fnd.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
/**
 * <p>
 *  FndCompanyHierarchyDetail
 * </p>
 *
 * @author yang.duan 2019/01/10 11:05
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "fnd_company_hierarchy_detail")
public class FndCompanyHierarchyDetail extends BaseDTO {

     public static final String FIELD_COMPANY_HIERARCHY_ID = "companyHierarchyId";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_PARENT_COMPANY_ID = "parentCompanyId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long companyHierarchyId;

    /**
     * 公司ID
     */
     @NotNull
     private Long companyId;

    /**
     * 上级公司ID
     */
     private Long parentCompanyId;

    /**
     * 启用标志
     */
     @Length(max = 1)
     private String enabledFlag;

     }
