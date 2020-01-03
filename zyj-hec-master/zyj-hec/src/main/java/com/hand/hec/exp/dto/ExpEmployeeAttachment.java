package com.hand.hec.exp.dto;

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
@Table(name = "exp_employee_attachment")
public class ExpEmployeeAttachment extends BaseDTO {

     public static final String FIELD_ATTACHMENT_ID = "attachmentId";
     public static final String FIELD_EMPLOYEE_ID = "employeeId";
     public static final String FIELD_FND_ATTACHMENT_ID = "fndAttachmentId";
     public static final String FIELD_FILE_NAME = "fileName";
     public static final String FIELD_USED_FLAG = "usedFlag";
     public static final String FIELD_DOC_CATEGORY = "docCategory";
     public static final String FIELD_DOC_ID = "docId";
     public static final String FIELD_IMAGE_WIDTH = "imageWidth";
     public static final String FIELD_IMAGE_HEIGHT = "imageHeight";


     @Id
     @GeneratedValue
     private Long attachmentId;

    /**
     * 员工ID
     */
     @NotNull
     private Long employeeId;

    /**
     * 系统附件ID
     */
     private Long fndAttachmentId;

    /**
     * 文件名
     */
     @Length(max = 30)
     private String fileName;

    /**
     * 使用标志
     */
     @Length(max = 1)
     private String usedFlag;

    /**
     * 关联单据类别
     */
     @Length(max = 30)
     private String docCategory;

    /**
     * 关联单据ID
     */
     private Long docId;

    /**
     * 图片宽度
     */
     private Long imageWidth;

    /**
     * 图片高度
     */
     private Long imageHeight;

     }
