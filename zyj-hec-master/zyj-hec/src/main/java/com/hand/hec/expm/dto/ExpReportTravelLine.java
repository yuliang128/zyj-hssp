package com.hand.hec.expm.dto;

import com.hand.hap.core.annotation.Children;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * ExpReportTravelLine
 * </p>
 *
 * @author yang.duan 2019/01/10 15:01
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_report_travel_line")
public class ExpReportTravelLine extends BaseDTO {

    public static final String FIELD_DEPARTURE_PLACE = "departurePlace";
    public static final String FIELD_DEPARTURE_PLACE_ID = "departurePlaceId";
    public static final String FIELD_DEPARTURE_DATE = "departureDate";
    public static final String FIELD_ARRIVAL_PLACE = "arrivalPlace";
    public static final String FIELD_ARRIVAL_PLACE_ID = "arrivalPlaceId";
    public static final String FIELD_ARRIVAL_DATE = "arrivalDate";
    public static final String FIELD_ACCOMMODATION_DATE_FROM = "accommodationDateFrom";
    public static final String FIELD_ACCOMMODATION_DATE_TO = "accommodationDateTo";
    public static final String FIELD_ACCOMMODATION_DAYS = "accommodationDays";
    public static final String FIELD_EXP_REPORT_LINE_ID = "expReportLineId";
    public static final String FIELD_TRAVEL_PLAN_LINE_ID = "travelPlanLineId";
    public static final String FIELD_TRAVEL_LINE_CATEGORY = "travelLineCategory";
    public static final String FIELD_TRANSPORTATION = "transportation";
    public static final String FIELD_SEAT_CLASS = "seatClass";

    /**
     * 行程_出发地
     */
    @Length(max = 200)
    private String departurePlace;

    /**
     * 行程_出发地ID
     */
    private Long departurePlaceId;

    /**
     * 行程_出发日期
     */
    private Date departureDate;

    /**
     * 行程_到达地
     */
    @Length(max = 200)
    private String arrivalPlace;

    /**
     * 行程_到达地ID
     */
    private Long arrivalPlaceId;

    /**
     * 行程_到达日期
     */
    private Date arrivalDate;

    /**
     * 住宿_入住日期
     */
    private Date accommodationDateFrom;

    /**
     * 住宿_离店日期
     */
    private Date accommodationDateTo;

    /**
     * 住宿_住宿天数
     */
    private BigDecimal accommodationDays;

    @Id
    private Long expReportLineId;

    /**
     * 差旅计划行ID，可为空
     */
    private Long travelPlanLineId;

    /**
     * 差旅计划行类别
     */
    @NotEmpty
    @Length(max = 30)
    private String travelLineCategory;

    /**
     * 行程_交通工具
     */
    @Length(max = 30)
    private String transportation;

    /**
     * 行程_舱位/座位等级
     */
    @Length(max = 30)
    private String seatClass;


    /*
     * 标准行
     * */
    @Children
    @Transient
    ExpReportLine standrdLine;

}
