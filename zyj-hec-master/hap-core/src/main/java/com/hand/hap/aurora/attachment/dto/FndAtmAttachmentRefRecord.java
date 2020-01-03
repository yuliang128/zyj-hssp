package com.hand.hap.aurora.attachment.dto;

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
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "fnd_atm_attachment_ref_record")
public class FndAtmAttachmentRefRecord extends BaseDTO {

     public static final String FIELD_RECORD_ID = "recordId";
     public static final String FIELD_ATTACHMENT_ID = "attachmentId";
     public static final String FIELD_TABLE_NAME = "tableName";
     public static final String FIELD_TABLE_PK_VALUE = "tablePkValue";


     @Id
     @GeneratedValue
     private Long recordId;

     @NotNull
     private Long attachmentId;

    /**
     * 表名
     */
     @Length(max = 255)
     private String tableName;

     @NotNull
     private Long tablePkValue;

     }
