package com.hand.hec.wfl.dto;

/**
 * Auto Generated By Hap Code Generator
 **/

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;
import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

@ExtensionAttribute(disable = true)
@Table(name = "wfl_instance")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WflInstance extends BaseDTO {

    public static final String FIELD_INSTANCE_ID = "instanceId";
    public static final String FIELD_VERSION = "version";
    public static final String FIELD_PROCESS_ID = "processId";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_DOC_CATEGORY = "docCategory";
    public static final String FIELD_DOC_TYPE_ID = "docTypeId";
    public static final String FIELD_DOC_ID = "docId";
    public static final String FIELD_START_DATE = "startDate";
    public static final String FIELD_END_DATE = "endDate";
    public static final String FIELD_INSTANCE_DESC = "instanceDesc";
    public static final String FIELD_DOC_AMOUNT = "docAmount";
    public static final String FIELD_DOC_NUMBER = "docNumber";


    public static final String STATUS_INITIAL = "INITIAL";
    public static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    // 完成状态 有一条流程走到结束事件的
    public static final String STATUS_FINISHED = "FINISHED";
    // 所有流程都中途终止 中断状态
    public static final String STATUS_INTERRUPTED = "INTERRUPTED";
    public static final String STATUS_SUSPEND = "SUSPEND";

    /**
     * 实例ID
     */
    @Id
    @GeneratedValue
    @Where
    private Long instanceId;

    /**
     * 流程ID
     */
    @Where
    private Long processId;

    private Long version;

    /**
     * 状态
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String status;

    /**
     * 单据类别
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String docCategory;

    /**
     * 单据类型ID
     */
    @Where
    private Long docTypeId;

    /**
     * 单据ID
     */
    @Where
    private Long docId;

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 工作流实例描述
     */
    @Length(max = 2000)
    @Where(comparison = Comparison.LIKE)
    private String instanceDesc;

    /**
     * 单据金额
     */
    @Length(max = 131089)
    private BigDecimal docAmount;

    /**
     * 单据编号
     */
    @Length(max = 30)
    @Where(comparison = Comparison.LIKE)
    private String docNumber;

}