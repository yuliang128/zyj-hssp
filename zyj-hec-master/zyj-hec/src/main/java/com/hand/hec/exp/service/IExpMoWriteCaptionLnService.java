package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoWriteCaptionLn;
import com.hand.hec.exp.exception.WriteCaptionMultiException;

import java.util.List;
/**
 * <p>
 * IExpMoWriteCaptionLnService
 * </p>
 *
 * @author yang.duan 2019/02/13 9:58
 */
public interface IExpMoWriteCaptionLnService extends IBaseService<ExpMoWriteCaptionLn>, ProxySelf<IExpMoWriteCaptionLnService>{
    /**
     * <p>单据填写说明定义行批量提交<p/>
     *
     * @param request
     * @param dto 需要提交的行list
     * @return 保存后的行list
     * @throws WriteCaptionMultiException
     * @author yang.duan 2019/3/15 19:33
     */
    List<ExpMoWriteCaptionLn> batchSubmit(IRequest request,List<ExpMoWriteCaptionLn> dto) throws WriteCaptionMultiException;
    /**
     * <p>获取单据类型对应的单据填写说明(对应原hec_util.exp_mo_write_caption)<p/>
     * @param request
     * @param docCategory 单据类别
     * @param docTypeId 单据类型ID
     * @return 单据填写说明行list
     * @author yang.duan 2019/3/15 19:30
     */
    List<ExpMoWriteCaptionLn> getWriteCaptionByDocType(IRequest request,String docCategory,Long docTypeId);
}