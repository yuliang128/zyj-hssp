package com.hand.hec.bpm.dto;

/**
 * Auto Generated By Code Generator
 * Description:
 */

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.GeneratedValue;

import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.Where;
import org.springframework.stereotype.Component;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

@Component
@ExtensionAttribute(disable = true)
@Table(name = "bpm_tplt_layout_grid")
public class TpltLayoutGrid extends BaseDTO {

    public static final String FIELD_LAYOUT_ID = "layoutId";
    public static final String FIELD_NAVBAR = "navbar";


    @Id
    @Where
    private Long layoutId;//布局组件ID

    @Length(max = 200)
    private String navbar;//是否启用导航


    public void setLayoutId(Long layoutId) {
        this.layoutId = layoutId;
    }

    public Long getLayoutId() {
        return layoutId;
    }

    public void setNavbar(String navbar) {
        this.navbar = navbar;
    }

    public String getNavbar() {
        return navbar;
    }

}
