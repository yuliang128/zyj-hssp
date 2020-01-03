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
@Table(name = "acc_match_group_detail")
public class AccMatchGroupDetail extends BaseDTO {

     public static final String FIELD_DETAIL_ID = "detailId";
     public static final String FIELD_GROUP_ID = "groupId";
     public static final String FIELD_CONDITION_CODE = "conditionCode";
     public static final String FIELD_CONDITION_SEQ = "conditionSeq";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long detailId;

    /**
     * 匹配组ID
     */
     @NotNull
     private Long groupId;

    /**
     * 匹配项代码
     */
     @NotEmpty
     @Length(max = 30)
     private String conditionCode;

    /**
     * 匹配项顺序
     */
     @NotNull
     private Long conditionSeq;

    /**
     * 启用
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
