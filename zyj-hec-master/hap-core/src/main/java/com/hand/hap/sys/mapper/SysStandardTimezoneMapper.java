package com.hand.hap.sys.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.sys.dto.SysStandardTimezone;

public interface SysStandardTimezoneMapper extends Mapper<SysStandardTimezone> {
    /**
     * 凭借时区代码获取新增管理公司默认时区
     *
     * @param code 时区代码
     * @return 默认时区
     * @author xiuxian.wu 2019/1/25 14:18
     */
    List<SysStandardTimezone> queryDefaultTimezoneByCode(String code);
}