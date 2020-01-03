package com.hand.hec.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.FndTaxTypeCode;
/**
 * <p>
 * 税率定义service接口
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/28 14:46
 */
public interface IFndTaxTypeCodeService extends IBaseService<FndTaxTypeCode>, ProxySelf<IFndTaxTypeCodeService>{

    /**
     * 根据核算主体ID查找税率信息
     * @author jialin.xing@hand-china.com 2019-02-28 14:44
     * @return java.util.List<com.hand.hec.fnd.dto.FndTaxTypeCode>
     */
    List<FndTaxTypeCode> queryByAccEntityId(IRequest request,Long companyId);

    /**
     * <p>获取税率信息下拉框</p>
     *
     * @return List<FndTaxTypeCode>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/4/18 9:59
     **/
    List<FndTaxTypeCode> queryTaxTypeWithholding(IRequest request);

}