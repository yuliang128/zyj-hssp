package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.GldExchangerateType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 汇率类型Mapper接口
 * </p>
 *
 * @author xingjialin 2019/01/04 18:52
 */
public interface GldExchangerateTypeMapper extends Mapper<GldExchangerateType> {
    /**
     * 报销单审核修改汇率获取汇率类型
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/11 13:45
     * @param
     * @return List<GldExchangerateType>
     * @Version 1.0
     **/
    List<GldExchangerateType> expAuditSelectType();

    /**
     * <p>
     * TODO
     * <p>
     * 
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/12 11:16
     * @param exchangeRateType 税率类型
     * @return List<GldExchangerateType>
     * @Version 1.0
     **/
    List<GldExchangerateType> queryGldExchangerateTypes(@Param("exchangeRateType") String exchangeRateType);

    /**
     * 获取预算组织的汇率类型
     *
     * @param bgtOrgId 预算组织ID
     * @author guiyuting 2019-03-29 10:39
     * @return 对应预算组织的汇率类型code
     */
    String getRateTypeByBgtOrg(@Param("bgtOrgId") Long bgtOrgId);
}
