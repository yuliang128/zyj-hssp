package com.hand.hec.expm.dto;

import javax.persistence.*;

import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hec.fnd.dto.FndTaxTypeCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Table;
import java.math.BigDecimal;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * ExpReportPmtSchTaxLine
 * </p>
 *
 * @author yang.duan 2019/01/10 15:01
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_report_pmt_sch_tax_line")
public class ExpReportPmtSchTaxLine extends BaseDTO {

    public static final String FIELD_TAX_LINE_ID = "taxLineId";
    public static final String FIELD_PAYMENT_SCHEDULE_LINE_ID = "paymentScheduleLineId";
    public static final String FIELD_TAX_TYPE_ID = "taxTypeId";
    public static final String FIELD_TAX_RATE = "taxRate";
    public static final String FIELD_TAX_AMOUNT = "taxAmount";


    @Id
    @GeneratedValue
    private Long taxLineId;

    /**
     * 报销单计划付款行ID
     */
    @NotNull
    @Where
    private Long paymentScheduleLineId;

    /**
     * 税率类型ID
     */
    @JoinTable(name = "taxTypeJoin", type = JoinType.LEFT, target = FndTaxTypeCode.class, joinMultiLanguageTable = true, on = {@JoinOn(joinField = FndTaxTypeCode.FIELD_TAX_TYPE_ID)})
    private Long taxTypeId;


    @Transient
    @JoinColumn(joinName = "taxTypeJoin", field = FndTaxTypeCode.FIELD_DESCRIPTION)
    private String taxTypeName;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 税额
     */
    private BigDecimal taxAmount;

}
