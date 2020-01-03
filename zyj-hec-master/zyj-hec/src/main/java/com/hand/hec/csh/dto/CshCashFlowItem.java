package com.hand.hec.csh.dto;

import javax.persistence.*;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_cash_flow_item")
@MultiLanguage
public class CshCashFlowItem extends BaseDTO {

    public static final String FIELD_CASH_FLOW_ITEM_ID = "cashFlowItemId";
    public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
    public static final String FIELD_CASH_FLOW_LINE_NUMBER = "cashFlowLineNumber";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CASH_FLOW_ITEM_TYPE = "cashFlowItemType";
    public static final String FIELD_INDENT = "indent";
    public static final String FIELD_ORIENTATION = "orientation";
    public static final String FIELD_VISIBLE_FLAG = "visibleFlag";
    public static final String FIELD_CASH_FLOW_ITEM_TYPE_DESC = "cashFlowItemTypeDesc";
    public static final String FIELD_ORIENTATION_DESC = "orientationDesc";


    @Id
    @GeneratedValue
    @Where
    private Long cashFlowItemId;

    /**
     * 帐套ID
     */
    @NotNull
    @Where
    private Long setOfBooksId;

    /**
     * 流量项编号
     */
    @NotNull
    @Where
    private Long cashFlowLineNumber;

    /**
     * 描述ID
     */
    @NotEmpty
    @Length(max = 500)
    @MultiLanguageField
    @Where
    private String description;

    /**
     * 流量项类型（ACCOUNT科目，CHAR字符，FORMULA公式）
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String cashFlowItemType;

    /**
     * 描述缩进
     */
    @NotNull
    @Where
    private Long indent;

    /**
     * 流向（IN流入项，OUT流出项）
     */
    @Length(max = 30)
    @Where
    private String orientation;

    /**
     * 显示（Y显示，N不显示）
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String visibleFlag;

    /**
     * 行属性描述
     */
    @Transient
    @Where
    private String cashFlowItemTypeDesc;

    /**
     * 流量描述
     */
    @Transient
    @Where
    private String orientationDesc;

}
