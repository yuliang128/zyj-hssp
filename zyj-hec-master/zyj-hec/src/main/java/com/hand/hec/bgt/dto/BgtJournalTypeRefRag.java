package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * 预算日记账类型指定范围
 * </p>
 * 
 * @author mouse 2019/01/07 16:39
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_journal_type_ref_rag")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtJournalTypeRefRag extends BaseDTO {

    public static final String FIELD_RANGE_ID = "rangeId";
    public static final String FIELD_BGT_JOURNAL_TYPE_ID = "bgtJournalTypeId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_EMPLOYEE_CODE = "employeeCode";
    public static final String FIELD_EMPLOYEE_NAME = "employeeName";


    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    private Long rangeId;

    /**
     * 预算日记账类型ID
     */
    @Where
    @NotNull
    private Long bgtJournalTypeId;

    /**
     * 员工ID
     */

    @NotNull
    @JoinTable(name = "employeeJoin", joinMultiLanguageTable = false, target = ExpEmployee.class, type = JoinType.LEFT,
            on = {@JoinOn(joinField = ExpEmployee.FIELD_EMPLOYEE_ID)})
    private Long employeeId;

    @Transient
    @JoinColumn(joinName = "employeeJoin",field = ExpEmployee.FIELD_EMPLOYEE_CODE)
    private String employeeCode;

    @Transient
    @JoinColumn(joinName = "employeeJoin",field = ExpEmployee.FIELD_NAME)
    private String employeeName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;


}
