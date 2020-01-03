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
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "mod_field_constant")
public class ModFieldConstant extends BaseDTO {

     public static final String FIELD_FIELD_ID = "fieldId";
     public static final String FIELD_VALUE = "value";


     @Id
     @GeneratedValue
     private Long fieldId;

    /**
     * 值
     */
     @Length(max = 200)
     private String value;

     }
