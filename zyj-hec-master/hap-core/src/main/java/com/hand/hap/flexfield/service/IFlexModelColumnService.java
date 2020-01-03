package com.hand.hap.flexfield.service;

import java.util.List;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.flexfield.dto.FlexModelColumn;
import com.hand.hap.system.service.IBaseService;

public interface IFlexModelColumnService extends IBaseService<FlexModelColumn>, ProxySelf<IFlexModelColumnService> {

    /** 通过表名获取表中列
     * @param tableName 表名
     * @return 表所包含的列
     */
    List<String> getTableColumn(String tableName);

}