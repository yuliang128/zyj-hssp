package com.hand.hec.expm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.expm.dto.ExpPolicyPlcTpAsgnCom;

import java.util.List;

/**
 * <p>
 * 费用政策地点类型分配公司Service
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 15:50
 */
public interface IExpPolicyPlcTpAsgnComService extends IBaseService<ExpPolicyPlcTpAsgnCom>, ProxySelf<IExpPolicyPlcTpAsgnComService> {
    /**
     * 查询剩余可以分配给费用政策类型的公司
     *
     * @param request
     * @param dto
     * @param page
     * @param pageSize
     * @return 公司数据
     * @author xiuxian.wu 2019-02-27
     */
    List<ExpPolicyPlcTpAsgnCom> queryRemainExpPolicyPlcTpAsgnCom(IRequest request, ExpPolicyPlcTpAsgnCom dto, Integer page, Integer pageSize);
}