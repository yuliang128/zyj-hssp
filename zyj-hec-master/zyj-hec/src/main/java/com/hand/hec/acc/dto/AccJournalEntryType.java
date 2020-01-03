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
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "acc_journal_entry_type")
public class AccJournalEntryType extends BaseDTO {

     public static final String FIELD_JE_TYPE_ID = "jeTypeId";
     public static final String FIELD_JE_TYPE_CODE = "jeTypeCode";
     public static final String FIELD_JE_TYPE_NAME = "jeTypeName";
     public static final String FIELD_TABLE_NAME = "tableName";
     public static final String FIELD_DTO_NAME = "dtoName";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long jeTypeId;

    /**
     * 分录类型代码
     */
     @NotEmpty
     @Length(max = 30)
     private String jeTypeCode;

    /**
     * 分录类型名称
     */
     @NotEmpty
     @Length(max = 200)
     private String jeTypeName;

    /**
     * 表名称
     */
     @NotEmpty
     @Length(max = 200)
     private String tableName;

    /**
     * DTO名称
     */
     @NotEmpty
     @Length(max = 200)
     private String dtoName;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
