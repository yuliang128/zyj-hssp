package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hec.fnd.dto.GldAccount;
import com.hand.hec.gld.dto.GldSetOfBook;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 公司付款账户分配核算科目dto
 * </p>
 *
 * @author xiuxian.xu 2019-03-15 9:36
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_bank_account_asgn_acc")
public class CshBankAccountAsgnAcc extends BaseDTO {

    public static final String FIELD_ASSIGN_ACC_ID = "assignAccId";
    public static final String FIELD_BANK_ACCOUNT_ID = "bankAccountId";
    public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
    public static final String FIELD_CASH_ACCOUNT_ID = "cashAccountId";


    @Id
    @GeneratedValue
    @Where
    private Long assignAccId;

    /**
     * 银行账户ID
     */
    @NotNull
    @Where
    private Long bankAccountId;

    /**
     * 账套ID
     */
    @NotNull
    @Where
    @JoinTable(name = "setOfBooksJoin", type = JoinType.LEFT, target = GldSetOfBook.class, joinMultiLanguageTable = true, on = {@JoinOn(joinField = GldSetOfBook.FIELD_SET_OF_BOOKS_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long setOfBooksId;
    /**
     * 账套代码
     */
    @Transient
    @JoinColumn(joinName = "setOfBooksJoin", field = GldSetOfBook.FIELD_SET_OF_BOOKS_NAME)
    private String setOfBooksName;
    /**
     * 账套名称
     */
    @Transient
    @JoinColumn(joinName = "setOfBooksJoin", field = GldSetOfBook.FIELD_SET_OF_BOOKS_CODE)
    private String setOfBooksCode;

    /**
     * 银行科目ID
     */
    @NotNull
    @Where
    @JoinTable(name = "cashAccountJoin", type = JoinType.LEFT, target = GldAccount.class, joinMultiLanguageTable = true, on = {@JoinOn(joinField = GldAccount.FIELD_ACCOUNT_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long cashAccountId;
    /**
     * 银行科目代码
     */
    @Transient
    @JoinColumn(joinName = "cashAccountJoin", field = GldAccount.FIELD_ACCOUNT_CODE)
    private String cashAccountCode;
    /**
     * 银行科目名称
     */
    @Transient
    @JoinColumn(joinName = "cashAccountJoin", field = GldAccount.FIELD_DESCRIPTION)
    private String cashAccountName;

}
