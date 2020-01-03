package com.hand.hec.acp.dto;

import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hec.csh.dto.CshMoTransactionClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 付款申请单类型关联现金事务分类实体类
 * </p>
 * 
 * @author guiyuting 2019/04/25 16:51
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "acp_mo_pay_req_tp_ref_trx")
public class AcpMoPayReqTpRefTrx extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_MO_PAY_REQ_TYPE_ID = "moPayReqTypeId";
    public static final String FIELD_MO_CSH_TRX_CLASS_ID = "moCshTrxClassId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级付款申请单类型ID
     */
    @NotNull
    @Where
    private Long moPayReqTypeId;

    /**
     * 管理组织级现金事物分类ID
     */

    @NotNull
    @JoinTable(name = "trxJoin", joinMultiLanguageTable = true, target = CshMoTransactionClass.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = CshMoTransactionClass.FIELD_MO_CSH_TRX_CLASS_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "trxJoin2", joinMultiLanguageTable = false, target = CshMoTransactionClass.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = CshMoTransactionClass.FIELD_MO_CSH_TRX_CLASS_ID)})
    private Long moCshTrxClassId;

    @Transient
    @JoinColumn(joinName = "trxJoin2", field = CshMoTransactionClass.FIELD_MO_CSH_TRX_CLASS_CODE)
    private String moCshTrxClassCode;

    @Transient
    @JoinColumn(joinName = "trxJoin", field = CshMoTransactionClass.FIELD_DESCRIPTION)
    private String moCshTrxClassName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
