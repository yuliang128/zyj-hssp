package com.hand.hec.bpm.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bpm.dto.Tplt001Line;
/**
 * @author xxx@hand-china.com
 * @date
 * Description
 */
public interface ITplt001LineService extends IBaseService<Tplt001Line>, ProxySelf<ITplt001LineService>{
    Long queryMaxLineNum(Long headerId);
}