package com.hand.hec.acp.dto;

import javax.persistence.*;

import com.hand.hap.mybatis.common.query.Where;
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
@ExtensionAttribute(disable = true)
@Table(name = "acp_mo_pay_req_tp_ref_doc")
public class AcpMoPayReqTpRefDoc extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_MO_PAY_REQ_TYPE_ID = "moPayReqTypeId";
    public static final String FIELD_DOCUMENT_TYPE = "documentType";
    public static final String FIELD_DOCUMENT_TYPE_ID = "documentTypeId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级付款申请单类型ID
     */
    @NotNull
    @Where
    private Long moPayReqTypeId;

    /**
     * 单据类型（REPORT报销单/CONTRACT合同）
     */
    @NotEmpty
    @Length(max = 30)
    private String documentType;

    @Transient
    private String documentTypeCode;

    @Transient
    private String documentTypeName;

    /**
     * 单据类型ID（报销单类型ID/合同类型ID）
     */
    @NotNull
    private Long documentTypeId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
