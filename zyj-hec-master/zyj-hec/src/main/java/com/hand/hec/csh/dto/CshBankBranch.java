package com.hand.hec.csh.dto;

import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
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
 * 公司付款账户定义Dto
 * </p>
 *
 * @author xiuxian.xu 2019-03-15 9:36
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_bank_branch")
public class CshBankBranch extends BaseDTO {

    public static final String FIELD_BANK_BRANCH_ID = "bankBranchId";
    public static final String FIELD_BANK_ID = "bankId";
    public static final String FIELD_CNAPS_ID = "cnapsId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_CONTACT_NAME = "contactName";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_E_MAIL_ADDRESS = "eMailAddress";
    public static final String FIELD_PHONE = "phone";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_BANK_LOCATION_CODE = "bankLocationCode";
    public static final String FIELD_BANK_LOCATION_NAME = "bankLocationName";


    @Id
    @GeneratedValue
    @Where
    private Long bankBranchId;

    /**
     * 银行ID
     */
    @NotNull
    @Where
    @JoinTable(name = "bankJoin", type = JoinType.LEFT, target = CshBank.class, joinMultiLanguageTable = true, on = {@JoinOn(joinField = CshBank.FIELD_BANK_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long bankId;

    @Transient
    @JoinColumn(joinName = "bankJoin", field = CshBank.FIELD_BANK_CODE)
    private String bankCode;
    /**
     * 银行名称
     */
    @Transient
    @JoinColumn(joinName = "bankJoin", field = CshBank.FIELD_BANK_NAME)
    private String bankName;

    /**
     * 分行ID
     */
    @NotNull
    @JoinTable(name = "cnapsJoin", type = JoinType.LEFT, target = CshCnaps.class, joinMultiLanguageTable = false, on = {@JoinOn(joinField = CshCnaps.FIELD_CNAPS_ID)})
    private Long cnapsId;


    /**
     * 分行代码
     */
    @Transient
    @Where
    @JoinColumn(joinName = "cnapsJoin", field = CshCnaps.FIELD_BANK_LOCATION_CODE)
    private String bankLocationCode;

    /**
     * 分行名称
     */
    @Transient
    @Where
    @JoinColumn(joinName = "cnapsJoin", field = CshCnaps.FIELD_BANK_LOCATION_NAME)
    private String bankLocationName;

    /**
     * 核算主体ID
     */
    @NotNull
    @Where
    private Long accEntityId;

    /**
     * 联系人
     */
    @Length(max = 30)
    private String contactName;

    /**
     * 地址
     */
    @Length(max = 2000)
    private String address;

    /**
     * E-Mail
     */
    @Length(max = 100)
    private String eMailAddress;

    /**
     * 电话
     */
    @Length(max = 30)
    private String phone;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;

}
