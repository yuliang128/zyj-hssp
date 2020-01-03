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
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.wfl.dto.WflPage;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "ssc_node_asgn_work_center")
public class SscNodeAsgnWorkCenter extends BaseDTO {

    public static final String FIELD_RECORD_ID = "recordId";
    public static final String FIELD_NODE_ID = "nodeId";
    public static final String FIELD_WORK_CENTER_ID = "workCenterId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long recordId;

    /**
     * 工作流程节点ID
     */
    @NotNull
    @Where
    private Long nodeId;

    /**
     * 工作中心ID
     */
    @NotNull
    @JoinTable(name = "centerJoin", joinMultiLanguageTable = true, target = SscWorkCenter.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = SscWorkCenter.FIELD_WORK_CENTER_ID)})
    private Long workCenterId;

    /**
     * 工作中心Code
     */
    @JoinColumn(joinName = "centerJoin", field = SscWorkCenter.FIELD_WORK_CENTER_CODE)
    @Transient
    private String workCenterCode;

    /**
     * 工作中心名称
     */
    @JoinColumn(joinName = "centerJoin", field = SscWorkCenter.FIELD_DESCRIPTION)
    @Transient
    private String workCenterName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
