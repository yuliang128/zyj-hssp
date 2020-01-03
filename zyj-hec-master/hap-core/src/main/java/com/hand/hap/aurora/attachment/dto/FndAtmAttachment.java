package com.hand.hap.aurora.attachment.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_atm_attachment")
@Builder
public class FndAtmAttachment extends BaseDTO {

    public static final String FIELD_ATTACHMENT_ID = "attachmentId";
    public static final String FIELD_FILE_NAME = "fileName";
    public static final String FIELD_FILE_PATH = "filePath";
    public static final String FIELD_FILE_SIZE = "fileSize";
    public static final String FIELD_FILE_TYPE = "fileType";
    public static final String FIELD_MINE_TYPE = "mineType";
    public static final String FIELD_SAVE_TYPE = "saveType";

    public static final String FIELD_TABLE_NAME = "tableName";
    public static final String FIELD_TABLE_PK_VALUE = "tablePkValue";

    @Id
    @GeneratedValue
    private Long attachmentId;

    /**
     * 文件名
     */
    @Length(max = 255)
    private String fileName;

    /**
     * 文件虚拟路径
     */
    @Length(max = 255)
    private String filePath;

    /**
     * 文件大小
     */
    private BigDecimal fileSize;

    /**
     * 文件类型
     */
    @Length(max = 240)
    private String fileType;

    @Length(max = 240)
    private String mineType;

    /**
     * 存储类型：db/oss
     */
    @Length(max = 255)
    private String saveType;

    @Transient
    @Length(max = 255)
    private String tableName;

    @Transient
    @NotNull
    private Long tablePkValue;

    @Transient
    private String divId;

    @Transient
    private Boolean showDelete;


}
