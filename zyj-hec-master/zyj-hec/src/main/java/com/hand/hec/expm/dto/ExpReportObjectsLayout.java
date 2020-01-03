package com.hand.hec.expm.dto;

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
/**
 * <p>
 * ExpReportObjectsLayout
 * </p>
 *
 * @author yang.duan 2019/01/10 15:00
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_report_objects_layout")
public class ExpReportObjectsLayout extends BaseDTO {

     public static final String FIELD_EXP_REPORT_HEADER_ID = "expReportHeaderId";
     public static final String FIELD_LAYOUT_POSITION = "layoutPosition";
     public static final String FIELD_LAYOUT_PRIORITY = "layoutPriority";
     public static final String FIELD_EXPENSE_OBJECT_TYPE_ID = "expenseObjectTypeId";
     public static final String FIELD_DEFAULT_OBJECT_ID = "defaultObjectId";


    /**
     * 费用报销单头ID
     */
     @Id
     @GeneratedValue
     private Long expReportHeaderId;

    /**
     * 布局位置
     */
     @NotEmpty
     @Length(max = 30)
     private String layoutPosition;

    /**
     * 布局顺序
     */
     @NotNull
     private Long layoutPriority;

    /**
     * 费用对象类型ID
     */
     @NotNull
     private Long expenseObjectTypeId;

    /**
     * 缺省费用对象ID
     */
     private Long defaultObjectId;

     }
