package com.hand.hec.exp.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
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
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_requisition_travel_line")
public class ExpRequisitionTravelLine extends BaseDTO {

    public static final String FIELD_TRAVEL_LINE_ID = "travelLineId";
    public static final String FIELD_EXP_REQUISITION_LINE_ID = "expRequisitionLineId";
    public static final String FIELD_TRAVEL_LINE_CATEGORY = "travelLineCategory";
    public static final String FIELD_TRANSPORTATION = "transportation";
    public static final String FIELD_SEAT_CLASS = "seatClass";
    public static final String FIELD_DEPARTURE_PLACE = "departurePlace";
    public static final String FIELD_DEPARTURE_PLACE_ID = "departurePlaceId";
    public static final String FIELD_DEPARTURE_DATE = "departureDate";
    public static final String FIELD_ARRIVAL_PLACE = "arrivalPlace";
    public static final String FIELD_ARRIVAL_PLACE_ID = "arrivalPlaceId";
    public static final String FIELD_ARRIVAL_DATE = "arrivalDate";
    public static final String FIELD_ACCOMMODATION_DATE_FROM = "accommodationDateFrom";
    public static final String FIELD_ACCOMMODATION_DATE_TO = "accommodationDateTo";
    public static final String FIELD_ACCOMMODATION_DAYS = "accommodationDays";
    public static final String FIELD_PEER_PEOPLE = "peerPeople";
    public static final String FIELD_MONOPOLIZE_FLAG = "monopolizeFlag";
    public static final String FIELD_MONOPOLIZE_PLATFORM = "monopolizePlatform";


    @Id
    @GeneratedValue
    private Long travelLineId;

    /**
     * 费用申请单行ID
     */
    @NotNull
    @Where
    private Long expRequisitionLineId;

    /**
     * 差旅行类别
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

    /**
     * 行程_出发地
     */
    @Length(max = 255)
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
    @Length(max = 255)
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
    private Long accommodationDays;

    /**
     * 同行人
     */
    @Length(max = 255)
    private String peerPeople;

    /**
     * 统购标志
     */
    @NotEmpty
    @Length(max = 1)
    private String monopolizeFlag;

    /**
     * 统购平台
     */
    @Length(max = 30)
    private String monopolizePlatform;

}
