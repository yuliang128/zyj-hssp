package com.hand.hec.wfl.dto;

/** Auto Generated By Hap Code Generator **/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.account.dto.User;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;

import com.hand.hap.system.dto.BaseDTO;
import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;

@ExtensionAttribute(disable = true)
@Table(name = "wfl_deliver")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WflDeliver extends BaseDTO {

    public static final String FIELD_DELIVER_ID = "deliverId";
    public static final String FIELD_DOC_CATEGORY = "docCategory";
    public static final String FIELD_PROCESS_ID = "processId";
    public static final String FIELD_FROM_USER_ID = "fromUserId";
    public static final String FIELD_TO_USER_ID = "toUserId";
    public static final String FIELD_DATE_FROM = "dateFrom";
    public static final String FIELD_DATE_TO = "dateTo";
    public static final String FIELD_DESCRIPTION = "description";

    /**
     * 转交ID
     */
    @Id
    @GeneratedValue
    @Where
    private Long deliverId;

    /**
     * 单据类别
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String docCategory;

    /**
     * 流程ID
     */
    @Where
    private Long processId;

    /**
     * 原审批人
     */
    @JoinTable(name = "fromUserJoin", target = User.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = User.FIELD_USER_ID)})
    private Long fromUserId;

    @Transient
    @JoinColumn(joinName = "fromUserJoin", field = User.FIELD_USER_NAME)
    private String fromUserName;

    /**
     * 转交人
     */
    @JoinTable(name = "toUserJoin", target = User.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = User.FIELD_USER_ID)})
    private Long toUserId;

    @Transient
    @JoinColumn(joinName = "toUserJoin", field = User.FIELD_USER_NAME)
    private String toUserName;

    /**
     * 日期从
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dateFrom;

    /**
     * 日期到
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dateTo;

    /**
     * 描述
     */
    @NotEmpty
    @Length(max = 2000)
    private String description;
}
