package com.hand.hec.expm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.expm.dto.ExpReportObject;

import java.util.List;

/**
 * <p>
 * IExpReportObjectService
 * </p>
 *
 * @author yang.duan 2019/01/10 15:06
 */
public interface IExpReportObjectService extends IBaseService<ExpReportObject>, ProxySelf<IExpReportObjectService> {
    /**
     * <p>
     * 报销单费用对象更新
     * <p/>
     *
     * @param dto 需要更新的报销单费用对象
     * @return 返回null
     * @author yang.duan 2019/3/13 13:26
     */
    ExpReportObject updateExpReportObject(ExpReportObject dto);

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
    List<ExpReportObject> queryHdObjectByReport(IRequest request, Long expReportHeaderId);


    List<ExpReportObject> queryLnObjectByReport(IRequest request, Long expReportHeaderId, Long expReportLineId);

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
    void deleteExpObject(Long expReportHeaderId, Long expReportLineId);
}
