package com.hand.hec.fnd.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
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
 * GldAccountHierarchyTree
 * </p>
 *
 * @author guiyu 2019/01/08 15:29
 */
@ExtensionAttribute(disable = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GldAccountHierarchyTree extends BaseDTO {

    public static final String FIELD_ACCOUNT_CODE = "accountCode";
    public static final String FIELD_ACCOUNT_ID = "accountId";
    public static final String FIELD_PARENT_ACCOUNT_ID = "parentAccountId";



    @Length(max = 100)
    private String accountCode;

    private Long accountId;

    private Long parentAccountId;

}
