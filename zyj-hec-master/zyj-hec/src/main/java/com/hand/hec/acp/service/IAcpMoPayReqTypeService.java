package com.hand.hec.acp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.acp.dto.AcpMoPayReqType;

import java.util.List;

/**
 * <p>
 * 付款申请单类型service
 * </p>
 * 
 * @author guiyuting 2019/04/28 10:57
 */
public interface IAcpMoPayReqTypeService extends IBaseService<AcpMoPayReqType>, ProxySelf<IAcpMoPayReqTypeService> {

    /**
     * 根据公司Id查询申请单类型信息
     *
     * @param companyId 公司ID
     * @author guiyuting 2019-04-28 10:38
     * @return
     */
    List<AcpMoPayReqType> queryByCompany(IRequest request, Long companyId);

    /**
     * 我的付款申请-查询关联的付款申请单类型
     *
     * @param employeeId 员工ID
     * @param companyId 公司ID
     * @author guiyuting 2019-04-28 18:27
     * @return
     */
    List<AcpMoPayReqType> getAcpMoPayReqTypeList(IRequest request, Long employeeId, Long companyId);

}
