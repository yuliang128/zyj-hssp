package com.hand.hec.mod.dto;

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
@Table(name = "mod_field_group_child")
public class ModFieldGroupChild extends BaseDTO {

     public static final String FIELD_FIELD_ID = "fieldId";
     public static final String FIELD_CHILD_ENTITY_ID = "childEntityId";
     public static final String FIELD_CHILD_FIELD_ID = "childFieldId";
     public static final String FIELD_GROUP_FUNCTION = "groupFunction";
     public static final String FIELD_FORMULA = "formula";


     @Id
     @GeneratedValue
     private Long fieldId;

    /**
     * 子实体ID
     */
     @NotNull
     private Long childEntityId;

    /**
     * 子字段ID
     */
     @NotNull
     private Long childFieldId;

    /**
     * 分组函数
     */
     @NotEmpty
     @Length(max = 30)
     private String groupFunction;

    /**
     * 表达式
     */
     @Length(max = 200)
     private String formula;

     }
