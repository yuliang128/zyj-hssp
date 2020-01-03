package com.hand.hec.csh.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.gld.dto.GldAccountingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_offer_format_asgn_ae")
public class CshOfferFormatAsgnAe extends BaseDTO {

     public static final String FIELD_ASSIGN_ID = "assignId";
     public static final String FIELD_FORMAT_HDS_ID = "formatHdsId";
     public static final String FIELD_ACC_ENTITY_ID = "accEntityId";


     @Id
     @GeneratedValue
     private Long assignId;

    /**
     * 报盘文件导出格式定义头ID
     */
     @NotNull
     private Long formatHdsId;

    /**
     * 核算主体ID
     */
     @NotNull
     @JoinTable(name = "AccountingEntityJoin", joinMultiLanguageTable = true, target = GldAccountingEntity.class,
             type = JoinType.LEFT, on = {@JoinOn(joinField = GldAccountingEntity.FIELD_ACC_ENTITY_ID),
             @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
     private Long accEntityId;

     @Transient
     private String accEntityCode;

     @Transient
     private String accEntityName;

     }
