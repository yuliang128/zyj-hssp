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
@Table(name = "acc_match_group")
public class AccMatchGroup extends BaseDTO {

     public static final String FIELD_GROUP_ID = "groupId";
     public static final String FIELD_GROUP_CODE = "groupCode";
     public static final String FIELD_GROUP_NAME = "groupName";
     public static final String FIELD_JE_CODE = "jeCode";
     public static final String FIELD_GROUP_SEQ = "groupSeq";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long groupId;

    /**
     * 匹配组代码
     */
     @NotEmpty
     @Length(max = 30)
     private String groupCode;

    /**
     * 匹配组名称
     */
     @NotEmpty
     @Length(max = 200)
     private String groupName;

    /**
     * 分录代码
     */
     @NotEmpty
     @Length(max = 30)
     private String jeCode;

    /**
     * 匹配组顺序
     */
     @NotNull
     private Long groupSeq;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
