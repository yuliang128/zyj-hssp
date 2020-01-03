package com.hand.hec.csh.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshCashFlowFormulaHd;
import com.hand.hec.csh.mapper.CshCashFlowItemMapper;
import com.hand.hec.csh.service.ICshCashFlowFormulaHdService;
import com.hand.hec.gld.dto.GldSetOfBook;
import com.hand.hap.sys.service.ISysParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshCashFlowItem;
import com.hand.hec.csh.service.ICshCashFlowItemService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshCashFlowItemServiceImpl extends BaseServiceImpl<CshCashFlowItem> implements ICshCashFlowItemService {
    @Autowired
    ICshCashFlowFormulaHdService iCshCashFlowFormulaHdService;
    @Autowired
    CshCashFlowItemMapper mapper;
    @Autowired
    ISysParameterService iSysParameterService;

    @Override
    public List<CshCashFlowItem> batchUpdate(IRequest request, List<CshCashFlowItem> list) {
        for (CshCashFlowItem t : list) {
            switch (t.get__status()) {
                case DTOStatus.ADD:
                    self().insertSelective(request, t);
                    // 保存头信息
                    CshCashFlowFormulaHd cashFlowFormulaHd = new CshCashFlowFormulaHd();
                    cashFlowFormulaHd.setCashFlowItemId(t.getCashFlowItemId());
                    iCshCashFlowFormulaHdService.insertSelective(request, cashFlowFormulaHd);
                    break;
                case DTOStatus.UPDATE:
                    if (useSelectiveUpdate()) {
                        self().updateByPrimaryKeySelective(request, t);
                    } else {
                        self().updateByPrimaryKey(request, t);
                    }
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(t);
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    @Override
    public GldSetOfBook defaultSetOfBook(IRequest request) {
        List<GldSetOfBook> list = mapper.defaultSetOfBook(request);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<GldSetOfBook> queryForSob(IRequest request, CshCashFlowItem condition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        String allOrganizationalFlag = iSysParameterService.queryParamValueByCode("FND_ALL_ORGANIZATIONAL",
                        request.getUserId(), request.getRoleId(), null, null, null, null, null);
        return mapper.queryForSob(allOrganizationalFlag);
    }

    @Override
    public List<CshCashFlowItem> queryCashItem(IRequest request, CshCashFlowItem condition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return mapper.queryCashItem(condition);
    }

    /**
     * @Description 根据核算主体（账套）获取现金流量项
     *
     * @Author Tagin
     * @Date 2019-03-07 16:39
     * @param iRequest 请求
     * @param setOfBooksId 账套ID
     * @param accEntityId 核算主体ID
     * @Return java.util.List<com.hand.hec.csh.dto.CshCashFlowItem>
     * @Version 1.0
     **/
    @Override
    public List<CshCashFlowItem> getCashFlowItme(IRequest iRequest, Long setOfBooksId, Long accEntityId) {
        return mapper.getCashFlowItem(setOfBooksId, accEntityId);
    }

}
