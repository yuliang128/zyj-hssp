package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoExpItemAsgnCom;
import com.hand.hec.exp.dto.ExpMoExpenseItem;

import java.util.List;

public interface IExpMoExpItemAsgnComService extends IBaseService<ExpMoExpItemAsgnCom>, ProxySelf<IExpMoExpItemAsgnComService>{


    /**
     *根据费用项目和管理组织查询分配公司信息
     * @param request
     * @param dto
     * @param page
     * @param pageSize
     * @return 分配的公司信息
     * @author zhongyu 2019-2-26 14:55
     */
    List<ExpMoExpItemAsgnCom> queryCompany(IRequest request,ExpMoExpItemAsgnCom dto,int page,int pageSize);

    /**
     *
     * 根据费用项目和管理组织
     *  查询不在当前项目已经分配了的公司的公司信息
     * @param request
     * @param dto
     * @param page
     * @param pageSize
     * @return
     * @author zhongyu 2019-2-26 20:07
     */
    List<ExpMoExpItemAsgnCom> queryCompanyByItem(IRequest request,ExpMoExpItemAsgnCom dto,int page,int pageSize);

    /**
     * 当前组织下-公司lov-不限制已分配的公司
     * @param request
     * @param dto
     * @param page
     * @param pageSize
     * @return
     * @author zhongyu 2019-2-26 16:36
     */
    List<FndCompany> queryBatchCompany (IRequest request, FndCompany dto, int page, int pageSize);

    /**
     * 多个费用项目分配多个公司
     * @param request
     * @param expMoExpenseItems
     * @return
     * @author zhongyu 2019-2-26 19:04
     */
    List<ExpMoExpItemAsgnCom> batchAssignCompanyManytoMany(IRequest request, List<ExpMoExpenseItem> expMoExpenseItems);

    /**
     * 单个费用项目分配多个公司
     * @param request
     * @param companies
     * @return
     * @author zhongyu 2019-2-26 19:44
     */
    List<ExpMoExpItemAsgnCom> batchAssignCompanyOnetoMany(IRequest request,List<FndCompany> companies);

    /**
     * 分配公司批量提交
     * @param request
     * @param moExpItemsAsgnComs
     * @return
     * @throws RuntimeException
     * @author zhongyu 2019-3-6 18:48
     */
     List<ExpMoExpItemAsgnCom> batchSubmit(IRequest request, List<ExpMoExpItemAsgnCom> moExpItemsAsgnComs);

}