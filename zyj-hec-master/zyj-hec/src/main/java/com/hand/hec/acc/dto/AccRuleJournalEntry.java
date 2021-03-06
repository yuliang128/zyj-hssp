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
@Table(name = "acc_rule_journal_entry")
public class AccRuleJournalEntry extends BaseDTO {

     public static final String FIELD_JE_ID = "jeId";
     public static final String FIELD_ENTITY_ID = "entityId";
     public static final String FIELD_JE_CODE = "jeCode";
     public static final String FIELD_JE_NAME = "jeName";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long jeId;

    /**
     * 实体ID
     */
     @NotNull
     private Long entityId;

    /**
     * 分录代码
     */
     @NotEmpty
     @Length(max = 30)
     private String jeCode;

    /**
     * 分录名称
     */
     @NotEmpty
     @Length(max = 200)
     private String jeName;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
