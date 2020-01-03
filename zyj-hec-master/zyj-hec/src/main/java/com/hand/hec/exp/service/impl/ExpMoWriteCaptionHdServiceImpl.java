package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoWriteCaptionHd;
import com.hand.hec.exp.dto.ExpMoWriteCaptionLn;
import com.hand.hec.exp.exception.WriteCaptionMultiException;
import com.hand.hec.exp.mapper.ExpMoWriteCaptionHdMapper;
import com.hand.hec.exp.service.IExpMoWriteCaptionHdService;
import com.hand.hec.exp.service.IExpMoWriteCaptionLnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * ExpMoWriteCaptionHdServiceImpl
 * </p>
 *
 * @author yang.duan 2019/02/13 9:57
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoWriteCaptionHdServiceImpl extends BaseServiceImpl<ExpMoWriteCaptionHd>
                implements IExpMoWriteCaptionHdService {
    @Autowired
    ExpMoWriteCaptionHdMapper mapper;

    @Autowired
    IExpMoWriteCaptionLnService lnService;

    @Override
    public List<ExpMoWriteCaptionHd> batchSubmit(IRequest request, List<ExpMoWriteCaptionHd> dto)
                    throws WriteCaptionMultiException {
        Set<String> codeSet = new HashSet<String>();
        for (ExpMoWriteCaptionHd hd : dto) {
            if (hd.get__status().equals(DTOStatus.DELETE)) {
                // 级联删除单据填写说明行信息
                ExpMoWriteCaptionLn writeCaptionLn = new ExpMoWriteCaptionLn();
                writeCaptionLn.setCaptionHdsId(hd.getCaptionHdsId());
                Criteria criteria = new Criteria(writeCaptionLn);
                criteria.where(new WhereField(ExpMoWriteCaptionLn.FIELD_CAPTION_HDS_ID, Comparison.EQUAL));
                lnService.batchDelete(lnService.selectOptions(request, writeCaptionLn, criteria));
            }
            codeSet.add(hd.getCaptionCode());
        }
        if ((codeSet.size() != dto.size()) || (checkUnique(dto))) {
            throw new WriteCaptionMultiException("EXP",
                    WriteCaptionMultiException.ERROR_WRITE_CAPTION_HDS_UNIQUE_MULTI);
        }

        return this.batchUpdate(request, dto);
    }

    /**
     * <p>
     * 校验唯一性
     * </p>
     * 
     * @author yang.duan 2019/02/13 9:52
     */
    private boolean checkUnique(List<ExpMoWriteCaptionHd> dto) {
        int count = 0;
        boolean flag = false;
        ExpMoWriteCaptionHd expMoWriteCaptionHd = new ExpMoWriteCaptionHd();
        for (ExpMoWriteCaptionHd hd : dto) {
            if ((hd.get__status().equals(DTOStatus.DELETE)) || (hd.get__status().equals(DTOStatus.UPDATE))) {
                break;
            } else {
                expMoWriteCaptionHd.setMagOrgId(hd.getMagOrgId());
                expMoWriteCaptionHd.setCaptionCode(hd.getCaptionCode());
                count = mapper.selectCount(expMoWriteCaptionHd);
                if (count > 0) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
}
