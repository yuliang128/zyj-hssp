package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

/**
 * <p>
 * 预算日记账类型关联费用对象
 * </p>
 * 
 * @author mouse 2019/01/07 16:39
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_journal_type_ref_obj")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtJournalTypeRefObj extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_BGT_JOURNAL_TYPE_ID = "bgtJournalTypeId";
    public static final String FIELD_EXPENSE_OBJECT_TYPE_ID = "expenseObjectTypeId";
    public static final String FIELD_LAYOUT_POSITION = "layoutPosition";
    public static final String FIELD_LAYOUT_PRIORITY = "layoutPriority";
    public static final String FIELD_DEFAULT_OBJECT_ID = "defaultObjectId";
    public static final String FIELD_REQUIRED_FLAG = "requiredFlag";
    public static final String FIELD_EXPENSE_OBJECT_TYPE_CODE = "expenseObjectTypeCode";
    public static final String FIELD_EXPENSE_OBJECT_TYPE_NAME = "expenseObjectTypeName";
    public static final String FIELD_DEFAULT_OBJECT_CODE = "defaultObjectCode";
    public static final String FIELD_DEFAULT_OBJECT_DESC = "defaultObjectDesc";
    public static final String FIELD_LAYOUT_POSITION_DISPLAY = "layoutPositionDisplay";

    public static final String POSITION_HEADER = "DOCUMENTS_HEAD";
    public static final String POSITION_LINE = "DOCUMENTS_LINE";


    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 预算日记账类型ID
     */
    @NotNull
    private Long bgtJournalTypeId;

    /**
     * 费用对象类型ID
     */
    @NotNull
    private Long expenseObjectTypeId;

    /**
     * 布局位置
     */
    @NotEmpty
    @Length(max = 30)
    private String layoutPosition;

    /**
     * 布局顺序
     */
    private BigDecimal layoutPriority;

    /**
     * 默认对象
     */
    private Long defaultObjectId;

    /**
     * 是否必输
     */
    @NotEmpty
    @Length(max = 1)
    private String requiredFlag;

    @Transient
    private String expenseObjectTypeCode;

    @Transient
    private String expenseObjectTypeName;

    @Transient
    private String defaultObjectCode;

    @Transient
    private String defaultObjectDesc;

    @Transient
    private String layoutPositionDisplay;

}
