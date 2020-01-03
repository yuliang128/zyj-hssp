package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoExpItemAsgnCom;
import com.hand.hec.exp.dto.ExpMoExpenseItem;
import com.hand.hec.fnd.dto.FndUom;
import com.hand.hec.fnd.dto.FndUomAsgnCom;
import com.hand.hec.fnd.dto.FndUomAsgnMo;

import java.util.List;

public interface IFndUomAsgnComService extends IBaseService<FndUomAsgnCom>, ProxySelf<IFndUomAsgnComService>{

    /**
     * 根据管理组织ID查
     * @param request
     * @param magOrgId
     * @param dto
     * @param page
     * @param pageSize
     * @author zhongyu 2019-4-25
     * @return
     */
    List<FndUomAsgnCom> selectByMagOrgId(IRequest request, Long magOrgId, FndUomAsgnCom dto, int page, int pageSize);

    /**
     * 多个计量单位分配多个公司
     * @param request
     * @param fndUoms
     * @return
     * @author zhongyu 2019-4-25
     */
    List<FndUomAsgnCom> batchAssignCompanyManytoMany(IRequest request, List<FndUom> fndUoms);

    /**
     * 单个计量单位分配多个公司
     * @param request
     * @param fndCompanies
     * @author zhongyu
     * @return
     */
    List<FndUomAsgnCom> batchAssignCompanyOnetoMany(IRequest request,List<FndCompany>fndCompanies);

//    /**
//     * 分配公司批量提交
//     * @param request
//     * @param fndUomAsgnComs
//     * @return
//     * @throws RuntimeException
//     * @author zhongyu 2019-4-25
//     */
//    List<FndUomAsgnCom> batchSubmit(IRequest request, List<FndUomAsgnCom> fndUomAsgnComs);
}