package com.hand.hec.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.FndMoClassSet;

/**
 * <p>
 *
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/04/23 19:28
 */
public interface IFndMoClassSetService extends IBaseService<FndMoClassSet>, ProxySelf<IFndMoClassSetService>{

    List<FndMoClassSet> baseSelect(IRequest request,FndMoClassSet dto,int page,int pageSize);

}