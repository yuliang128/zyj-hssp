package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoReportType;

import java.util.List;

/**
 * <p>
 * IExpMoReportTypeService
 * </p>
 *
 * @author yang.duan 2019/01/10 14:52
 */
public interface IExpMoReportTypeService extends IBaseService<ExpMoReportType>, ProxySelf<IExpMoReportTypeService>{
    /**
     * <p>报销单类型定义提交<p/>
     * @param  request
     * @param  reportTypes
     * @return List<ExpMoReportType>
     * @throws RuntimeException exception 运行异常
     * @author yang.duan 2019/2/20 18:56
     */
    List<ExpMoReportType> batchSubmit(IRequest request,List<ExpMoReportType> reportTypes) throws RuntimeException;

    /**
     * <p>根据员工和公司获取报销单类型(对应原exp_mo_report_type_list.bm)<p/>
     * @param request
     * @param employeeId 员工ID
     * @param companyId 公司ID
     * @return 单据类型list
     * @author yang.duan 2019/3/18 18:17
     */
    List<ExpMoReportType> queryExpMoReportTypeList(IRequest request,Long employeeId,Long companyId,int page,int pageSize);

    /**
     * <p>根据公司获取报销单类型<p/>
     *
     * @param request
     * @param companyId
     * @return 单据类型list
     * @author yang.duan 2019/3/19 19:32
     */
    List<ExpMoReportType> queryExpReportTypeByCom(IRequest request,Long companyId);
}