package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoWriteCaptionLn;
import com.hand.hec.exp.exception.WriteCaptionMultiException;
import com.hand.hec.exp.mapper.ExpMoWriteCaptionLnMapper;
import com.hand.hec.exp.service.IExpMoWriteCaptionLnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * ExpMoWriteCaptionLnServiceImpl
 * </p>
 *
 * @author yang.duan 2019/02/13 9:58
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoWriteCaptionLnServiceImpl extends BaseServiceImpl<ExpMoWriteCaptionLn>
                implements IExpMoWriteCaptionLnService {
    @Autowired
    ExpMoWriteCaptionLnMapper expMoWriteCaptionLnMapper;

    @Override
    public List<ExpMoWriteCaptionLn> batchSubmit(IRequest request, List<ExpMoWriteCaptionLn> dto)
                    throws WriteCaptionMultiException {
        Set<Long> numberSet = new HashSet<Long>();
        for (ExpMoWriteCaptionLn ln : dto) {
            numberSet.add(ln.getLineStepNumber());
        }
        if (numberSet.size() != dto.size() || checkUnique(dto)) {
            throw new WriteCaptionMultiException("EXP",
                            WriteCaptionMultiException.ERROR_WRITE_CAPTION_LNS_UNIQUE_MULTI);
        }
        return this.batchUpdate(request, dto);
    }

    /**
     * <p>
     * 校验唯一性
     * </p>
     *
     * @author yang.duan 2019/02/13 9:51
     */
    private boolean checkUnique(List<ExpMoWriteCaptionLn> dto) {
        boolean flag = false;
        int count = 0;
        ExpMoWriteCaptionLn expMoWriteCaptionLn = new ExpMoWriteCaptionLn();
        for (ExpMoWriteCaptionLn ln : dto) {
            if (ln.get__status().equals(DTOStatus.DELETE)) {
                break;
            } else {
                expMoWriteCaptionLn.setCaptionHdsId(ln.getCaptionHdsId());
                expMoWriteCaptionLn.setLineStepNumber(ln.getLineStepNumber());
                count = mapper.selectCount(expMoWriteCaptionLn);
                if (count > 0) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * <p>
     * 获取单据类型对应的单据填写说明(对应原hec_util.exp_mo_write_caption)
     * <p/>
     * @param request
     * @param docCategory 单据类别
     * @param docTypeId 单据类型ID
     * @return 单据填写说明行list
     * @author yang.duan 2019/3/15 19:30
     */
    public List<ExpMoWriteCaptionLn> getWriteCaptionByDocType(IRequest request,String docCategory, Long docTypeId) {
        return expMoWriteCaptionLnMapper.getWriteCaptionByDocType(docCategory, docTypeId);
    }
}
