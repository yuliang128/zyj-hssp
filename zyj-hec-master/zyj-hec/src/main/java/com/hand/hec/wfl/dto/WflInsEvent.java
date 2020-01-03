package com.hand.hec.wfl.dto;

/** Auto Generated By Hap Code Generator **/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.hand.hap.system.dto.BaseDTO;
import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;

@ExtensionAttribute(disable = true)
@Table(name = "wfl_ins_event")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WflInsEvent extends BaseDTO {

    public static final String FIELD_INS_EVENT_ID = "insEventId";
    public static final String FIELD_INSTANCE_ID = "instanceId";
    public static final String FIELD_EVENT_ID = "eventId";
    public static final String FIELD_RESULT = "result";
    public static final String FIELD_ARRIVAL_STATUS = "arrivalStatus";
    public static final String FIELD_ARRIVAL_DATE = "arrivalDate";
    public static final String FIELD_DEPARTURE_DATE = "departureDate";


    public static final String ARRIVAL_STATUS_NOT_ARRIVED = "NOT_ARRIVED";
    public static final String ARRIVAL_STATUS_ARRIVED = "ARRIVED";
    public static final String ARRIVAL_STATUS_PASSED = "PASSED";


    /**
     * ID
     */
    @Id
    @GeneratedValue
    @Where
    private Long insEventId;

    /**
     * 实例ID
     */
    @Where
    private Long instanceId;

    /**
     * 事件ID
     */
    @Where
    private Long eventId;

    /**
     * 执行结果
     */
    @Length(max = 200)
    private String result;

    /**
     * 抵达状态
     */
    @NotEmpty
    @Length(max = 30)
    private String arrivalStatus;

    /**
     * 抵达日期
     */
    private Date arrivalDate;

    /**
     * 离开日期
     */
    private Date departureDate;

}
