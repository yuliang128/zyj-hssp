package com.hand.hec.exp.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
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
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_document_history")
public class ExpDocumentHistory extends BaseDTO {
    /**
     * 新建
     */
    public static final String STATUS_GENERATE = "GENERATE";
    /**
     * 提交
     */
    public static final String STATUS_SUBMIT = "SUBMIT";
    /**
     * 关闭
     */
    public static final String STATUS_CLOSE = "CLOSE";
    /**
     * 审批
     */
    public static final String STATUS_APPROVE = "APPROVE";
    /**
     * 审批拒绝
     */
    public static final String STATUS_APPROVAL_REJECT = "APPROVAL_REJECT";
    /**
     * 审批取消
     */
    public static final String STATUS_APPROVAL_CANCEL = "APPROVAL_CANCEL";
    /**
     * 收回
     */
    public static final String STATUS_TAKEN_BACK = "TAKEN_BACK";
    /**
     * 审核
     */
    public static final String STATUS_AUDIT = "AUDIT";
    /**
     * 审核拒绝
     */
    public static final String STATUS_AUDIT_REJECT = "AUDIT_REJECT";
    /**
     * 反冲
     */
    public static final String STATUS_REVERSE = "REVERSE";
    /**
     * 转交
     */
    public static final String STATUS_DELIVER = "DELIVER";
    /**
     * 付款
     */
    public static final String STATUS_PAY = "PAY";
    /**
     * 过账
     */
    public static final String STATUS_POST = "POST";
    /**
     * 下达
     */
    public static final String STATUS_RELEASE = "RELEASE";
    /**
     * 退款
     */
    public static final String STATUS_REFUND = "REFUND";
    /**
     * 打开
     */
    public static final String STATUS_OPEN = "OPEN";
    /**
     * 创建凭证
     */
    public static final String STATUS_CREATE_JE = "CREATE_JE";
    /**
     * 删除凭证
     */
    public static final String STATUS_DELETE_JE = "DELETE_JE";
    /**
     * 确认
     */
    public static final String STATUS_CONFIRM = "CONFIRM";
    /**
     * 确认退回
     */
    public static final String STATUS_CONFIRM_RETURN = "CONFIRM_RETURN";
    /**
     * 复核
     */
    public static final String STATUS_REAUDIT = "REAUDIT";
    /**
     * 出纳确认
     */
    public static final String STATUS_CASHIER_CONFIRM = "CASHIER_CONFIRM";
    /**
     * 出纳拒绝
     */
    public static final String STATUS_CASHIER_REJECTED = "CASHIER_REJECTED";
    /**
     * 会计确认
     */
    public static final String STATUS_ACCOUNTING_CONFIRM = "ACCOUNTING_CONFIRM";
    /**
     * 会计拒绝
     */
    public static final String STATUS_ACCOUNTING_REJECTED = "ACCOUNTING_REJECTED";
    /**
     * 完全确认
     */
    public static final String STATUS_COMPLETELY_CONFIRM = "COMPLETELY_CONFIRM";
    /**
     * 管理更新
     */
    public static final String STATUS_MANAGE_UPDATE = "MANAGE_UPDATE";
    /**
     * 共享作业同意
     */
    public static final String STATUS_SSC_APPROVE = "SSC_APPROVE";
    /**
     * 共享作业拒绝
     */
    public static final String STATUS_SSC_REJECT = "SSC_REJECT";
    /**
     * 共享作业申请退回
     */
    public static final String STATUS_SSC_APPLY_RETURN = "SSC_APPLY_RETURN";
    /**
     * 共享作业同意退回
     */
    public static final String STATUS_SSC_AGREE_RETURN = "SSC_AGREE_RETURN";
    /**
     * 共享作业拒绝退回
     */
    public static final String STATUS_SSC_REJECT_RETURN = "SSC_REJECT_RETURN";
    /**
     * 共享作业强制退回
     */
    public static final String STATUS_SSC_FORCE_RETURN = "SSC_FORCE_RETURN";
    /**
     * 共享作业退回提单人
     */
    public static final String STATUS_SSC_RETURN_BACK = "SSC_RETURN_BACK";
    /**
     * 重新提回共享作业
     */
    public static final String STATUS_SSC_CANCEL_RETURN_BACK = "SSC_CANCEL_RETURN_BACK";
    /**
     * 共享作业暂挂
     */
    public static final String STATUS_SSC_HOLD = "SSC_HOLD";
    /**
     * 共享作业取消暂挂
     */
    public static final String STATUS_SSC_CANCEL_HOLD = "SSC_CANCEL_HOLD";
    /**
     * 核销
     */
    public static final String STATUS_WRITE_OFF = "WRITE_OFF";
    /**
     * 取消核销
     */
    public static final String STATUS_CANCEL_WRITE_OFF = "CANCEL_WRITE_OFF";
    /**
     * 确认支付失败
     */
    public static final String STATUS_CONFIRM_PAY_FAILED = "CONFIRM_PAY_FAILED";

    /**
     * 支付退回
     */
    public static final String STATUS_PAY_BACK = "PAY_BACK";

    public static final String FIELD_HISTORY_ID = "historyId";
    public static final String FIELD_DOCUMENT_TYPE = "documentType";
    public static final String FIELD_DOCUMENT_ID = "documentId";
    public static final String FIELD_OPERATION_CODE = "operationCode";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_OPERATION_TIME = "operationTime";
    public static final String FIELD_OPERATION_TIME_TZ = "operationTimeTz";
    public static final String FIELD_OPERATION_TIME_LTZ = "operationTimeLtz";
    public static final String FIELD_DESCRIPTION = "description";

    /**
     * 单据类型
     */
    public static final String DOCUMENT_TYPE_EXP_REPORT = "EXP_REPORT";
    public static final String DOCUMENT_TYPE_EXP_REQUISITION = "EXP_REQUISITION";
    public static final String DOCUMENT_TYPE_PAYMENT_REQUISITION = "PAYMENT_REQUISITION";
    public static final String DOCUMENT_TYPE_ACP_REQUISITION = "ACP_REQUISITION";
    public static final String DOCUMENT_TYPE_BUDGET_JOURNAL = "BUDGET_JOURNAL";
    public static final String DOCUMENT_TYPE_CSH_REPAYMENT_REGISTER = "CSH_REPAYMENT_REGISTER";

    @Id
    @GeneratedValue
    private Long historyId;

    /**
     * 单据类型（EXP_REPORT费用报销单,EXP_REQUISITION费用申请单,PAYMENT_REQUISITION借款申请单,ACP_REQUISITION付款申请单,BUDGET_JOURNAL预算日记账,CSH_REPAYMENT_REGISTER还款申请单）
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String documentType;

    /**
     * 单据ID
     */
    @NotNull
    @Where
    private Long documentId;

    /**
     * 操作动作代码
     */
    @NotEmpty
    @Length(max = 30)
    private String operationCode;

    /**
     * 用户ID
     */
    @NotNull
    private Long userId;

    /**
     * 员工ID
     */
    private Long employeeId;

    /**
     * 操作时间
     */
    private Date operationTime;

    /**
     * 操作时间_业务时区
     */
    private Date operationTimeTz;

    /**
     * 操作时间_查询时区
     */
    private Date operationTimeLtz;

    /**
     * 描述说明
     */
    @Length(max = 2000)
    private String description;


    @Transient
    private String operater;

    @Transient
    private String operation;

}
