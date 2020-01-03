package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoExpItemAsgnCom;
import com.hand.hec.fnd.dto.FndUom;
import com.hand.hec.fnd.dto.FndUomAsgnCom;
import com.hand.hec.fnd.dto.FndUomAsgnMo;

import java.util.List;

public interface IFndUomAsgnMoService extends IBaseService<FndUomAsgnMo>, ProxySelf<IFndUomAsgnMoService>{

    /**
     * 多个计量单位分配多个管理组织
     * @param request
     * @param fndUoms
     * @return
     * @author zhongyu 2019-4-26
     */
    List<FndUomAsgnMo> batchAssignMagOrgManytoMany(IRequest request, List<FndUom> fndUoms);

//    /**
//     * 分配公司批量提交
//     * @param request
//     * @param fndUomAsgnMos
//     * @return
//     * @throws RuntimeException
//     * @author zhongyu 2019-4-26
//     */
//    List<FndUomAsgnMo> batchSubmit(IRequest request, List<FndUomAsgnMo> fndUomAsgnMos);

    /**
     * 单个计量单位分配多个管理组织
     * @param request
     * @param fndManagingOrganizations
     * @return
     */
    List<FndUomAsgnMo> batchAssignMoOrgOnetoMany(IRequest request, List<FndManagingOrganization> fndManagingOrganizations);
}