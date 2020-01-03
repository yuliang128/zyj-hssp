package com.hand.hec.evt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "evt_event")
public class EvtEvent extends BaseDTO {

    public static final String FIELD_EVENT_ID = "eventId";
    public static final String FIELD_EVENT_CODE = "eventCode";
    public static final String FIELD_EVENT_NAME = "eventName";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    @Where
    private Long eventId;

    /**
     * 事件名称
     */
    @NotEmpty
    @Length(max = 200)
    @Where(comparison = Comparison.LIKE)
    private String eventCode;

    /**
     * 事件名称
     */
    @Length(max = 2000)
    @Where(comparison = Comparison.LIKE)
    private String eventName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;

}
