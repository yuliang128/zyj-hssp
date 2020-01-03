package com.hand.hec.ssc.dto;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "ssc_document_workflow")
public class SscDocumentWorkflow extends BaseDTO {

    public static final String FIELD_DOC_WFL_ASSIGN_ID = "docWflAssignId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_DOC_CATEGORY = "docCategory";
    public static final String FIELD_DOC_TYPE_ID = "docTypeId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_WORKFLOW_ID = "workflowId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long docWflAssignId;

    /**
     * 管理组织ID
     */
    @NotNull
    private Long magOrgId;

    /**
     * 单据类别
     */
    @NotEmpty
    @Length(max = 30)
    private String docCategory;

    /**
     * 单据类型ID
     */
    private Long docTypeId;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 核算主体ID
     */
    private Long accEntityId;

    /**
     * 工作流程ID
     */
    @NotNull
    private Long workflowId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @Transient
    private String docCategoryName;

    @Transient
    private String docTypeName;

    @Transient
    private String docTypeCodeName;

    @Transient
    private String companyCode;

    @Transient
    private String companyName;

    @Transient
    private String companyCodeName;

    @Transient
    private String accEntityCode;

    @Transient
    private String accEntityName;

    @Transient
    private String accEntityCodeName;

    @Transient
    private String workflowCode;

    @Transient
    private String workflowName;

    @Transient
    private String workflowCodeName;

}
