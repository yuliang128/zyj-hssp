package com.hand.hec.fnd.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * GldAccountHierarchy
 * </p>
 *
 * @author guiyu 2019/01/08 15:32
 */
@ExtensionAttribute(disable = true)
@Table(name = "gld_account_hierarchy")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GldAccountHierarchy extends BaseDTO {

    public static final String FIELD_SUB_ACCOUNT_CODE = "subAccountCode";
    public static final String FIELD_FROM_ACCOUNT_CODE = "fromAccountCode";
    public static final String FIELD_TO_ACCOUNT_CODE = "toAccountCode";
    public static final String FIELD_ACCOUNT_HIERARCHY_ID = "accountHierarchyId";
    public static final String FIELD_ACCOUNT_SET_ID = "accountSetId";
    public static final String FIELD_PARENT_ACCOUNT_ID = "parentAccountId";


    /**
     * 子科目
     */
    @OrderBy("ASC")
    @Length(max = 100)
    private String subAccountCode;

    /**
     * 科目从
     */
    @Length(max = 100)
    private String fromAccountCode;

    /**
     * 科目到
     */
    @Length(max = 100)
    private String toAccountCode;

    /**
     * 代码层次ID
     */
    @Id
    @GeneratedValue
    private Long accountHierarchyId;

    /**
     * 科目表ID
     */
    @NotNull
    private Long accountSetId;

    /**
     * 父科目ID
     */
    @NotNull
    @Where
    private Long parentAccountId;


}
