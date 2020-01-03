package com.hand.hec.csh.dto;

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
@Table(name = "con_document_flow")
public class CshDocumentFlow extends BaseDTO {

     public static final String FIELD_DOCUMENT_TYPE = "documentType";
     public static final String FIELD_DOCUMENT_ID = "documentId";
     public static final String FIELD_DOCUMENT_LINE_ID = "documentLineId";
     public static final String FIELD_SOURCE_DOCUMENT_TYPE = "sourceDocumentType";
     public static final String FIELD_SOURCE_DOCUMENT_ID = "sourceDocumentId";
     public static final String FIELD_SOURCE_DOCUMENT_LINE_ID = "sourceDocumentLineId";


    /**
     * 单据类型
     */
     @NotEmpty
     @Length(max = 30)
     private String documentType;

    /**
     * 单据头ID
     */
     @NotNull
     private Long documentId;

    /**
     * 单据行ID
     */
     private Long documentLineId;

    /**
     * 源单据类型
     */
     @NotEmpty
     @Length(max = 30)
     private String sourceDocumentType;

    /**
     * 源单据头ID
     */
     @NotNull
     private Long sourceDocumentId;

    /**
     * 源单据行ID
     */
     private Long sourceDocumentLineId;

     }
