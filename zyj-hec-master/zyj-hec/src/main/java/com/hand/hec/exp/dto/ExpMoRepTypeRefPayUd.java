package com.hand.hec.exp.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.csh.dto.CshMoPaymentUsed;
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
import javax.validation.constraints.NotNull;

/**
 * <p>
 * ExpMoRepTypeRefPayUd
 * </p>
 *
 * @author yang.duan 2019/01/10 14:46
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_rep_type_ref_pay_ud")
public class ExpMoRepTypeRefPayUd extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_MO_EXP_REPORT_TYPE_ID = "moExpReportTypeId";
    public static final String FIELD_USEDES_ID = "usedesId";
    public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    public static final String FIELD_USEDES_CODE = "usedesCode";
    public static final String FIELD_USEDES_NAME = "usedesName";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级报销单类型ID
     */
    @NotNull
    @Where
    private Long moExpReportTypeId;

    /**
     * 付款用途ID
     */
    @NotNull
    @JoinTable(name = "usedesJoin", target = CshMoPaymentUsed.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true, on = {@JoinOn(joinField = CshMoPaymentUsed.FIELD_PAYMENT_USEDE_ID)})
    private Long usedesId;

    /*
     * 付款用途Code
     */
    @Transient
    @Where
    @JoinColumn(joinName = "usedesJoin", field = CshMoPaymentUsed.FIELD_PAYMENT_USEDE_CODE)
    private String usedesCode;


    /*
     * 付款用途Name
     */
    @Transient
    @Where
    @JoinColumn(joinName = "usedesJoin", field = CshMoPaymentUsed.FIELD_DESCRIPTION)
    private String usedesName;

    /**
     * 默认标志
     */
    @NotEmpty
    @Length(max = 1)
    private String defaultFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
