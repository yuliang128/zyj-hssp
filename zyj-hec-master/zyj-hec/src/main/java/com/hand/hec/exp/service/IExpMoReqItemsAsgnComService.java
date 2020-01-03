package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoReqItem;
import com.hand.hec.exp.dto.ExpMoReqItemsAsgnCom;
import com.hand.hap.fnd.dto.FndCompany;

import java.util.List;

public interface IExpMoReqItemsAsgnComService
                extends IBaseService<ExpMoReqItemsAsgnCom>, ProxySelf<IExpMoReqItemsAsgnComService> {

    /**
     * <p>申请项目-分配公司批量提交<p/>
     *
     * @param IRequest request
     * @param List<ExpMoReqItemsAsgnCom> moReqItemsAsgnComs
     * @return List<ExpMoReqItemsAsgnCom>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/3/4 16:11
     */
    List<ExpMoReqItemsAsgnCom> batchSubmit(IRequest request, List<ExpMoReqItemsAsgnCom> moReqItemsAsgnComs)
                    throws RuntimeException;

    /**
     * <p>
     * 申请项目-分配公司主查询
     * <p/>
     *
     * @param request
     * @param dto(查询条件)
     * @return List<ExpMoReqItemsAsgnCom>
     * @author yang.duan 2019/2/19 16:10
     */
    List<ExpMoReqItemsAsgnCom> queryExpMoReqItemCom(IRequest request, ExpMoReqItemsAsgnCom dto);



    /**
     * <p>
     * 申请项目-批量分配公司查询公司
     * <p/>
     *
     * @param request
     * @param dto
     * @return List<FndCompany>
     * @author yang.duan 2019/2/19 16:10
     */
    List<FndCompany> queryCompanyByItem(IRequest request, ExpMoReqItemsAsgnCom dto);


    /**
     * <p>
     * 申请项目-批量分配公司(一对多)
     * <p/>
     *
     * @param request
     * @param companies(json数组)
     * @return List<ExpMoReqItemsAsgnCom>
     * @author yang.duan 2019/2/19 16:10
     */
    List<ExpMoReqItemsAsgnCom> batchAsgnCompanyOneToMany(IRequest request, List<FndCompany> companies);


    /**
     * <p>
     * 申请项目-批量分配公司(多对多)
     * <p/>
     *
     * @param request
     * @param expMoReqItems(json数组)
     * @return List<ExpMoReqItemsAsgnCom>
     * @author yang.duan 2019/2/19 16:10
     */
    List<ExpMoReqItemsAsgnCom> batchAsgnCompanyManyToMany(IRequest request, List<ExpMoReqItem> expMoReqItems);

}
