package com.hand.hec.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.GldExchangerateTpAsgnAe;
/**
 * <p>
 * 汇率类型分配核算主体service接口
 * </p>
 * 
 * @author jialin.xing@hand-china.com 2019/01/08 16:12
 */
public interface IGldExchangerateTpAsgnAeService extends IBaseService<GldExchangerateTpAsgnAe>, ProxySelf<IGldExchangerateTpAsgnAeService>{

    /**
     * 查询指定汇率类型未分配的核算主体
     *
     * @param request
     * @param typeId
     * @author jialin.xing@hand-china.com 2019-03-25 17:06
     * @return java.util.List<com.hand.hec.fnd.dto.GldExchangerateTpAsgnAe>
     */
    List<GldExchangerateTpAsgnAe> queryUnallocatedAccEntity(IRequest request, Long typeId);

}