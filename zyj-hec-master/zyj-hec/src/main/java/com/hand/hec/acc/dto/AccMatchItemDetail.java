package com.hand.hec.acc.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "acc_match_item_detail")
public class AccMatchItemDetail extends BaseDTO {

     public static final String FIELD_DETAIL_ID = "detailId";
     public static final String FIELD_ITEM_ID = "itemId";
     public static final String FIELD_GROUP_DETAIL_ID = "groupDetailId";
     public static final String FIELD_CONDITION_VALUE_ID = "conditionValueId";
     public static final String FIELD_CONDITION_VALUE_CODE = "conditionValueCode";
     public static final String FIELD_CONDITION_VALUE_NAME = "conditionValueName";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long detailId;

    /**
     * 匹配项ID
     */
     @NotNull
     private Long itemId;

    /**
     * 匹配组明细ID
     */
     @NotNull
     private Long groupDetailId;

    /**
     * 条件值ID
     */
     private Long conditionValueId;

    /**
     * 条件值代码
     */
     @Length(max = 200)
     private String conditionValueCode;

    /**
     * 条件值名称
     */
     @Length(max = 2000)
     private String conditionValueName;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
