package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoReportType;
import com.hand.hec.exp.dto.ExpMoReportTypeAsgnCom;
import com.hand.hap.fnd.dto.FndCompany;

import java.util.List;

/**
 * <p>
 * IExpMoReportTypeAsgnComService
 * </p>
 *
 * @author yang.duan 2019/01/10 14:52
 */
public interface IExpMoReportTypeAsgnComService
                extends IBaseService<ExpMoReportTypeAsgnCom>, ProxySelf<IExpMoReportTypeAsgnComService> {

    /**
     * <p>报销单类型-分配公司批量提交<p/>
     *
     * @param IRequest request
     * @param List<ExpMoReportTypeAsgnCom> reportTypeAsgnComs
     * @return List<ExpMoReportTypeAsgnCom>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/3/4 11:21
     */
    List<ExpMoReportTypeAsgnCom> batchSubmit(IRequest request, List<ExpMoReportTypeAsgnCom> reportTypeAsgnComs)
                    throws RuntimeException;

    /**
     * <p>
     * 报销单类型定义-批量分配公司查询
     * <p/>
     *
     * @param ExpMoReportType dto(查询条件)
     * @return List<FndCompany>
     * @author yang.duan 2019/2/21 14:59
     */
    List<FndCompany> queryCompanyInfo(ExpMoReportType dto);

    /**
     * <p>
     * 批量分配公司(一对多)
     * <p/>
     * 
     * @param IRequest request
     * @param List<FndCompany> companies(公司数据)
     * @return List<ExpMoReportTypeAsgnCom>
     * @author yang.duan 2019/2/21 15:00
     */
    List<ExpMoReportTypeAsgnCom> batchAsgnCompanyOneToMany(IRequest request, List<FndCompany> companies);


    /**
     * <p>
     * 批量分配公司(多对多)
     * <p/>
     * 
     * @param IRequest request
     * @param List<ExpMoReportType> reportTypes(报销单类型数据)
     * @return List<ExpMoReportTypeAsgnCom>
     * @author yang.duan 2019/2/21 15:00
     */
    List<ExpMoReportTypeAsgnCom> batchAsgnCompanyManyToMany(IRequest request, List<ExpMoReportType> reportTypes);
}
