package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoWriteCaptionHd;
import com.hand.hec.exp.exception.WriteCaptionMultiException;

import java.util.List;
/**
 * <p>
 * IExpMoWriteCaptionHdService
 * </p>
 *
 * @author yang.duan 2019/02/13 9:58
 */
public interface IExpMoWriteCaptionHdService extends IBaseService<ExpMoWriteCaptionHd>, ProxySelf<IExpMoWriteCaptionHdService>{
    List<ExpMoWriteCaptionHd> batchSubmit(IRequest request,List<ExpMoWriteCaptionHd> dto) throws WriteCaptionMultiException;
}