package com.hand.hap.sys.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.sys.dto.SysParameter;
import com.hand.hap.sys.dto.SysParameterValue;
import com.hand.hap.system.service.IBaseService;

/**
 * <p>
 * 参数指定 Service 接口
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/01/22 13:54
 */
public interface ISysParameterValueService extends IBaseService<SysParameterValue>, ProxySelf<ISysParameterValueService> {
    List<SysParameter> batchSave(IRequest request, List<SysParameter> list);
}