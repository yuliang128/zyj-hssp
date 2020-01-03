package com.hand.hec.csh.dto;

import javax.persistence.*;

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
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_cash_account")
public class CshCashAccount extends BaseDTO {

    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_ACCOUNT_ID = "accountId";
    public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";

    public static final String FIELD_ACCOUNT_CODE = "accountCode";
    public static final String FIELD_ACCOUNT_NAME = "accountName";
    public static final String FIELD_SET_OF_BOOKS_CODE = "setOfBooksCode";
    public static final String FIELD_SET_OF_BOOKS_NAME = "setOfBooksName";

    @Id
    @GeneratedValue
    @Where
    private Long assignId;

    /**
     * 科目ID
     */
    @NotNull
    @Where
    private Long accountId;

    /**
     * 帐套ID
     */
    @NotNull
    @Where
    private Long setOfBooksId;

    /**
     * 科目代码
     */
    @Transient
    @Where
    private String accountCode;

    /**
     * 科目名称
     */
    @Transient
    @Where
    private String accountName;

    /**
     * 账套代码
     */
    @Transient
    @Where
    private String setOfBooksCode;

    /**
     * 账套名称
     */
    @Transient
    @Where
    private String setOfBooksName;




}
