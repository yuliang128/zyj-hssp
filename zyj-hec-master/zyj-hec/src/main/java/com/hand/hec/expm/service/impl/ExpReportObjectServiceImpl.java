package com.hand.hec.expm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.expm.dto.ExpReportObject;
import com.hand.hec.expm.mapper.ExpReportObjectMapper;
import com.hand.hec.expm.service.IExpReportObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * ExpReportObjectServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 15:04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpReportObjectServiceImpl extends BaseServiceImpl<ExpReportObject> implements IExpReportObjectService {
    @Autowired
    ExpReportObjectMapper expReportObjectMapper;

    /**
     * <p>
     * 报销单费用对象更新
     * <p/>
     *
     * @param dto 需要更新的报销单费用对象
     * @return 返回null
     * @author yang.duan 2019/3/13 13:26
     */
    @Override
    public ExpReportObject updateExpReportObject(ExpReportObject dto) {
        expReportObjectMapper.updateExpReportObject(dto);
        return dto;
    }

    /**
     * <p>
     * 查询费用报销单头对象
     * <p/>
     *
     * @param request
     * @param expReportHeaderId 费用报销单头ID
     * @return 费用头对象的list
     * @author yang.duan 2019/3/25 10:09
     */
    @Override
    public List<ExpReportObject> queryHdObjectByReport(IRequest request, Long expReportHeaderId) {
        return expReportObjectMapper.queryHdObjectByReport(expReportHeaderId);
    }


    @Override
    public List<ExpReportObject> queryLnObjectByReport(IRequest request, Long expReportHeaderId, Long expReportLineId) {
        return expReportObjectMapper.queryLnObjectByReport(expReportHeaderId, expReportLineId);
    }

    /**
     * <p>
     * 报销单费用对象删除
     * <p/>
     *
     * @param expReportHeaderId
     * @param expReportLineId
     * @return void
     * @author yang.duan 2019/3/29 15:57
     */
    @Override
    public void deleteExpObject(Long expReportHeaderId, Long expReportLineId) {
        expReportObjectMapper.deleteExpObject(expReportHeaderId, expReportLineId);
    }
}
