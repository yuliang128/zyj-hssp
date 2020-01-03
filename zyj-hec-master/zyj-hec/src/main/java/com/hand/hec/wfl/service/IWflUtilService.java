package com.hand.hec.wfl.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.dto.WflVersionProcess;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/3/23 \* Time: 16:48 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */

public interface IWflUtilService extends ProxySelf<IWflUtilService> {

    String getInstanceDesc(WflInstance instance);

    WflVersionProcess getVersionProcess(WflInstance instance);
}
