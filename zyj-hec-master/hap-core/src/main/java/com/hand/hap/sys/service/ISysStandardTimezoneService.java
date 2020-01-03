package com.hand.hap.sys.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.sys.dto.SysStandardTimezone;
import com.hand.hap.system.service.IBaseService;

public interface ISysStandardTimezoneService extends IBaseService<SysStandardTimezone>, ProxySelf<ISysStandardTimezoneService> {
    /**
     * 凭借时区代码获取新增管理公司默认时区
     *
     * @param request
     * @param code    时区代码
     * @return 默认时区
     * @author xiuxian.wu 2019/1/25 14:18
     */
    List<SysStandardTimezone> queryDefaultTimezoneByCode(String code, IRequest request);
}