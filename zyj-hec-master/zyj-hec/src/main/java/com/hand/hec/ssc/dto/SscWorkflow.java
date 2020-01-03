package com.hand.hec.ssc.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.wfl.dto.WflPage;
import com.hand.hec.wfl.dto.WflProcedure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@MultiLanguage
@Table(name = "ssc_workflow")
public class SscWorkflow extends BaseDTO {

    public static final String FIELD_WORKFLOW_ID = "workflowId";
    public static final String FIELD_WORKFLOW_CODE = "workflowCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_DOC_CATEGORY = "docCategory";
    public static final String FIELD_PAGE_ID = "pageId";
    public static final String FIELD_FINISH_PROCEDURE_ID = "finishProcedureId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long workflowId;

    /**
     * 工作流程代码
     */
    @Length(max = 30)
    @Where
    private String workflowCode;

    /**
     * 描述
     */
    @Length(max = 500)
    @MultiLanguageField
    @Where
    private String description;

    /**
     * 单据类别
     */
    @Where
    @Length(max = 30)
    private String docCategory;

    /**
     * 共享作业操作页面ID
     */
    @JoinTable(name = "wflPageJoin", joinMultiLanguageTable = true, target = WflPage.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = WflPage.FIELD_PAGE_ID)})
    private Long pageId;

    /**
     * 工作流页面名称
     */
    @JoinColumn(joinName = "wflPageJoin", field = WflPage.FIELD_PAGE_NAME)
    @Transient
    private String pageName;

    /**
     * 工作流页面代码
     */
    @JoinColumn(joinName = "wflPageJoin", field = WflPage.FIELD_PAGE_CODE)
    @Transient
    private String pageCode;

    /**
     * 结束时过程ID
     */
    @JoinTable(name = "wflProJoin", joinMultiLanguageTable = true, target = WflProcedure.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = WflProcedure.FIELD_PROCEDURE_ID)})
    private Long finishProcedureId;

    /**
     * 工作流页面代码
     */
    @JoinColumn(joinName = "wflProJoin", field = WflProcedure.FIELD_PROCEDURE_NAME)
    @Transient
    private String finishProcedure;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 流程节点
     */
    @Transient
    private List<SscWorkflowNode> nodes;

    /**
     * 流程动作
     */
    @Transient
    private List<SscWorkflowAction> actions;

    /**
     * 流程过程
     */
    @Transient
    private List<SscWorkflowProc> procs;

    @Transient
    private String docCategoryName;

    @Transient
    private String page;

    @Transient
    private String pageTitle;

}
