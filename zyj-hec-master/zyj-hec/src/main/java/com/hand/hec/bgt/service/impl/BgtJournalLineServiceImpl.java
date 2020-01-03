package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtJournalHeader;
import com.hand.hec.bgt.dto.BgtJournalObject;
import com.hand.hec.bgt.exception.BgtPeriodException;
import com.hand.hec.bgt.mapper.BgtJournalLineMapper;
import com.hand.hec.bgt.service.IBgtJournalObjectService;
import com.hand.hec.bgt.service.IBgtPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtJournalLine;
import com.hand.hec.bgt.service.IBgtJournalLineService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 预算日记账行ServiceImpl
 * </p>
 *
 * @author mouse 2019/01/07 16:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtJournalLineServiceImpl extends BaseServiceImpl<BgtJournalLine> implements IBgtJournalLineService {

    @Autowired
    private IBgtJournalObjectService objectService;

    @Autowired
    private IBgtPeriodService periodService;

    @Autowired
    private BgtJournalLineMapper journalLineMapper;

    @Override
    public int deleteByPrimaryKey(BgtJournalLine line) {
        //
        // 删除预算日记账行之前先删除费用对象
        // ------------------------------------------------------------------------------
        BgtJournalObject obj = new BgtJournalObject();
        obj.setJournalHeaderId(line.getJournalHeaderId());
        obj.setJournalLineId(line.getJournalLineId());
        List<BgtJournalObject> objList = objectService.select(getRequest(), obj, 0, 0);
        objectService.batchDelete(objList);

        return super.deleteByPrimaryKey(line);
    }

    @Override
    public void saveJournalLine(IRequest requestCtx, BgtJournalLine line) {

        //
        // 如果行上的公司ID为空，则设置为当前登录的公司
        // ------------------------------------------------------------------------------
        if (line.getCompanyId() == null) {
            line.setCompanyId(requestCtx.getCompanyId());
        }

        if (periodService.checkPeriodOpen(requestCtx, line.getBgtOrgId(), line.getBgtEntityId(),
                        line.getLinePeriodName())) {
            if (DTOStatus.ADD.equals(line.get__status())) {
                line = self().insertSelective(requestCtx, line);
            } else if (DTOStatus.UPDATE.equals(line.get__status())) {
                line = self().updateByPrimaryKey(requestCtx, line);
            }
        } else {
            throw new BgtPeriodException("BGT", "bgt_period.line_period_not_open", null);
        }

        Map lineMap = line.getInnerMap();
        //
        // 更新费用对象数据
        // ------------------------------------------------------------------------------
        for (Object keyObj : lineMap.keySet()) {
            String key = keyObj.toString();
            if (key.startsWith("^#")) {
                Long objTypeId = Long.parseLong(key.replace("^#", ""));
                String objDesc = lineMap.get("^#" + objTypeId).toString();
                Boolean updateFlag = false;
                Long objId = null;
                if (lineMap.get("#" + objTypeId) != null) {
                    objId = Long.parseLong(lineMap.get("#" + objTypeId).toString());
                }

                BgtJournalObject obj = new BgtJournalObject();
                obj.setJournalHeaderId(line.getJournalHeaderId());
                obj.setJournalLineId(line.getJournalLineId());
                obj.setExpenseObjectTypeId(objTypeId);

                // 如果本次预算日记账为修改，则需要判断本次费用对象是新增还是修改
                // 根据预算日记账行ID和费用对象类型查询费用对象数据，判断本次为新增还是修改
                // ------------------------------------------------------------------------------
                if (obj.getJournalLineId() != null) {
                    List<BgtJournalObject> objList = objectService.select(requestCtx, obj, 0, 0);
                    for (BgtJournalObject resObj : objList) {
                        updateFlag = true;
                        obj.setJournalObjectId(resObj.getJournalObjectId());
                    }
                }

                obj.setExpenseObjectDesc(objDesc);
                obj.setExpenseObjectId(objId);

                if (updateFlag) {
                    objectService.updateByPrimaryKey(requestCtx, obj);
                } else {
                    objectService.insert(requestCtx, obj);
                }
            }
        }
    }

    @Override
    public List<BgtJournalLine> queryInfoByHeaderId(IRequest request, Long journalHeaderId) {
        return journalLineMapper.queryInfoByHeaderId(journalHeaderId);
    }

    @Override
    public int queryNeedCheckCount(Long journalHeaderId) {
        return journalLineMapper.queryNeedCheckCount(journalHeaderId);
    }

    @Override
    public List<BgtJournalLine> queryDimByHeaderId(Long journalHeaderId) {
        return journalLineMapper.queryDimByHeaderId(journalHeaderId);
    }

    @Override
    public Map<String, BigDecimal> querySumAmount(BgtJournalLine bgtJournalLine) {
        return journalLineMapper.querySumAmount(bgtJournalLine);
    }

    @Override
    public Map<String, BigDecimal> queryBalanceSumAmount(BgtJournalLine bgtJournalLine) {
        return journalLineMapper.queryBalanceSumAmount(bgtJournalLine);
    }

    @Override
    public BigDecimal queryTotalFunctionAmount(Long journalHeaderId) {
        return journalLineMapper.queryTotalFunctionAmount(journalHeaderId);
    }

    @Override
    public List<BgtJournalLine> batchUpdate(IRequest request, List<BgtJournalLine> list) {
        list.forEach(bgtJournalLine -> {
            switch (bgtJournalLine.get__status()) {
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(bgtJournalLine);
                    break;
                default:
                    self().saveJournalLine(request, bgtJournalLine);
                    break;
            }
        });
        return list;
    }

}
