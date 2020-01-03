package com.hand.hec.fnd.dto;

import com.hand.hap.system.dto.BaseDTO;
import lombok.*;

import javax.persistence.Table;

/**
 * description
 *
 * @author mouse 2019/02/22 18:51
 */
@Table(name = "fnd_doc_type_v")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FndDocTypeV {

    public static final String FIELD_DOC_CATEGORY = "docCategory";
    public static final String FIELD_DOC_TYPE_ID = "docTypeId";
    public static final String FIELD_DOC_TYPE_CODE = "docTypeCode";
    public static final String FIELD_DOC_TYPE_NAME = "docTypeName";

    private String docCategory;
    private Long docTypeId;
    private String docTypeCode;
    private String docTypeName;
}
