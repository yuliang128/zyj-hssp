package com.hand.hec.expm.dto;

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
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_document_authority")
public class ExpDocumentAuthority extends BaseDTO {

     public static final String FIELD_AUTHORITY_ID = "authorityId";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_UNIT_ID = "unitId";
     public static final String FIELD_POSITION_ID = "positionId";
     public static final String FIELD_EMPLOYEE_ID = "employeeId";
     public static final String FIELD_GRANTED_POSITION_ID = "grantedPositionId";
     public static final String FIELD_GRANTED_EMPLOYEE_ID = "grantedEmployeeId";
     public static final String FIELD_DOC_CATEGORY = "docCategory";
     public static final String FIELD_DOC_TYPE_ID = "docTypeId";
     public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
     public static final String FIELD_END_DATE_ACTIVE = "endDateActive";
     public static final String FIELD_INQUIRE_FLAG = "inquireFlag";
     public static final String FIELD_MAINTAIN_FLAG = "maintainFlag";


     @Id
     @GeneratedValue
     private Long authorityId;

    /**
     * 公司ID
     */
     @NotNull
     private Long companyId;

    /**
     * 委托部门ID
     */
     private Long unitId;

    /**
     * 委托岗位ID
     */
     private Long positionId;

    /**
     * 委托人ID
     */
     private Long employeeId;

    /**
     * 受托岗位ID
     */
     private Long grantedPositionId;

    /**
     * 受托人ID
     */
     private Long grantedEmployeeId;

    /**
     * 业务类型（SYSCODE：WFL_WORKFLOW_CATEGORY）
     */
     @NotEmpty
     @Length(max = 30)
     private String docCategory;

    /**
     * 单据类型ID
     */
     private Long docTypeId;

    /**
     * 有效期从
     */
     private Date startDateActive;

    /**
     * 有效期至
     */
     private Date endDateActive;

    /**
     * 查询标志
     */
     @NotEmpty
     @Length(max = 1)
     private String inquireFlag;

    /**
     * 维护标志
     */
     @NotEmpty
     @Length(max = 1)
     private String maintainFlag;

     }
