package com.hand.hec.wfl.service;

import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflVersionProcess;

/**
 * description
 *
 * @author mouse 2019/03/01 11:23
 */
public interface IWflVersionProcessService extends IBaseService<WflVersionProcess> {

    /**
     * 查询当前工作流的当前版本
     *
     * @param processId
     * @author mouse 2019-03-01 11:28
     * @return java.lang.Long
     */
    Long getProcessCurrentVersion(Long processId);
}
