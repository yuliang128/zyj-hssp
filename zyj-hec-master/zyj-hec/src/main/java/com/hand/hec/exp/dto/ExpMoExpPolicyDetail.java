package com.hand.hec.exp.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.gld.dto.GldCurrency;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
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

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;


/**
 * <p>
 * 政策标准明细定义dto
 * </p>
 *
 * @author xiuxian.wu 2019/01/19 25:25
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@MultiLanguage
@Table(name = "exp_mo_exp_policy_detail")
public class ExpMoExpPolicyDetail extends BaseDTO {

    public static final String FIELD_DETAIL_ID = "detailId";
    public static final String FIELD_EXPENSE_POLICY_ID = "expensePolicyId";
    public static final String FIELD_PRIORITY = "priority";
    public static final String FIELD_DETAIL_CODE = "detailCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
    public static final String FIELD_CHANGEABLE_FLAG = "changeableFlag";
    public static final String FIELD_EXPENSE_STANDARD = "expenseStandard";
    public static final String FIELD_UPPER_LIMIT = "upperLimit";
    public static final String FIELD_LOWER_LIMIT = "lowerLimit";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_COMMIT_FLAG = "commitFlag";
    public static final String FIELD_TRANSPORTATION = "transportation";
    public static final String FIELD_SEAT_CLASS = "seatClass";
    public static final String FIELD_ROOM_TYPE = "roomType";
    public static final String FIELD_EVENT_NAME = "eventName";
    public static final String FIELD_UPPER_STD_EVENT_NAME = "upperStdEventName";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";


    @Id
    @GeneratedValue
    @Where
    private Long detailId;

    /**
     * 政策标准ID
     */
    @NotNull
    @Where
    private Long expensePolicyId;

    /**
     * 优先级
     */
    @NotNull
    private Long priority;

    /**
     * 明细代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String detailCode;

    /**
     * 明细描述ID
     */
    @NotEmpty
    @Length(max = 500)
    @Where
    @MultiLanguageField
    private String description;

    /**
     * 缺省标志
     */
    @NotEmpty
    @Length(max = 1)
    private String defaultFlag;

    /**
     * 可修改标志
     */
    @NotEmpty
    @Length(max = 1)
    private String changeableFlag;

    /**
     * 费用标准
     */
    @Where
    private BigDecimal expenseStandard;

    /**
     * 上限
     */
    @Where
    private BigDecimal upperLimit;

    /**
     * 下限
     */
    @Where
    private BigDecimal lowerLimit;

    /**
     * 币种
     */
    @Length(max = 30)
    @Where
    @JoinTable(name = "currencyJoin", target = GldCurrency.class
            , type = JoinType.LEFT, joinMultiLanguageTable = true
            , on = {@JoinOn(joinField = GldCurrency.FIELD_CURRENCY_CODE)
            , @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private String currencyCode;
    /**
     * 币种名称
     */
    @Transient
    @JoinColumn(joinName = "currencyJoin", field = GldCurrency.FIELD_CURRENCY_NAME)
    private String currencyName;

    /**
     * 超上下限后是否可以提交
     */
    @NotEmpty
    @Length(max = 1)
    private String commitFlag;

    /**
     * 交通工具（SYSCODE：TRANSPORTATION）
     */
    @Length(max = 30)
    private String transportation;

    /**
     * 舱位/席别
     */
    @Length(max = 30)
    private String seatClass;

    /**
     * 房型
     */
    @Length(max = 30)
    private String roomType;

    /**
     * 上下限事件
     */
    @Length(max = 30)
    private String eventName;

    /**
     * 超出标准事件
     */
    @Length(max = 30)
    private String upperStdEventName;

    /**
     * 有效日期从
     */
    private Date startDateActive;

    /**
     * 有效日期到
     */
    private Date endDateActive;


    /*
    * 交通工具描述
    * */
    @Transient
    private String transportationName;

    /**
     * 舱位/席别描述
     */
    @Transient
    private String seatClassName;

    /**
     * 房型描述
     */
    @Transient
    private String roomTypeName;
}
