package com.hand.hec.fnd.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.hand.hap.system.dto.BaseDTO;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * GldAccountHierarchyDetail
 * </p>
 * 
 * @author guiyu 2019/01/08 15:32
 */
@ExtensionAttribute(disable = true)
@Table(name = "gld_account_hierarchy_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GldAccountHierarchyDetail extends BaseDTO {

    public static final String FIELD_HIERARCHY_DETAIL_ID = "hierarchyDetailId";
    public static final String FIELD_ACCOUNT_SET_ID = "accountSetId";
    public static final String FIELD_PARENT_ACCOUNT_ID = "parentAccountId";
    public static final String FIELD_ACCOUNT_ID = "accountId";


    /**
     * 科目表ID
     */
    @Id
    @GeneratedValue
    private Long hierarchyDetailId;

    /**
     * 科目表ID
     */
    @Where
    @NotNull
    private Long accountSetId;

    /**
     * 父科目ID
     */
    private Long parentAccountId;

    /**
     * 科目ID
     */
    @NotNull
    private Long accountId;



}
