package com.hand.hec.csh.mapper;

import java.util.List;
import java.util.Map;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentReqAccount;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 借款申请单凭证表Mapper
 * </p>
 *
 * @author Tagin 2019/02/21 22:35
 */
public interface CshPaymentReqAccountMapper extends Mapper<CshPaymentReqAccount> {
    /**
     * 借款单Id查询凭证
     *
     * @param paymentRequisitionHeaderId 头Id
     * @author dingwei.ma@hand-china.com 2019-02-26 13:59
     * @return List<Map>
     */

    List<Map> queryByHeaderId(@Param("paymentRequisitionHeaderId") Long paymentRequisitionHeaderId);


    /**
     * 查询当前时间
     *
     *
     * @author dingwei.ma@hand-china.com 2019-02-26 13:59
     * @return List<Map>
     */

    List<Map> queryCurrentTime();

    /**
     * 按行Id和核算主体汇总数据
     *
     * @param paymentRequisitionHeaderId 头Id
     * @author dingwei.ma@hand-china.com 2019-02-26 13:59
     * @return List<Map>
     */

    List<Map> selectGroupByLine(@Param("paymentRequisitionHeaderId") Long paymentRequisitionHeaderId);

    /**
     * 查询借贷方金额差值
     *
     * @param paymentRequisitionLineId 行Id
     * @param accEntityId 核算主体Id
     * @author dingwei.ma@hand-china.com 2019-02-26 13:59
     * @return List<Map>
     */

    List<Map> selectForEnterAmount(@Param("paymentRequisitionLineId") Long paymentRequisitionLineId,
                    @Param("accEntityId") Long accEntityId);

    /**
     * 查询借贷方金额差值
     *
     * @param paymentRequisitionHeaderId 借款申请单头Id
     * @param interfaceFlag 接口状态
     * @author dingwei.ma@hand-china.com 2019-02-26 13:59
     * @return void
     */

    void updateByLineId(@Param("paymentRequisitionHeaderId") Long paymentRequisitionHeaderId,
                    @Param("interfaceFlag") String interfaceFlag);

    /**
     * 查询借款单关联的申请单是否有核销记录
     *
     * @param expRequisitionHeaderId 申请单头Id
     * @author dingwei.ma@hand-china.com 2019-02-26 13:59
     * @return String
     */

    String selectExpReqExist(@Param("expRequisitionHeaderId") Long expRequisitionHeaderId);

    /**
     * 查询借款单关联的申请单是否关闭
     *
     * @param expRequisitionHeaderId 申请单头Id
     * @author dingwei.ma@hand-china.com 2019-02-26 13:59
     * @return String
     */

    String selectExpReqCloseExist(@Param("expRequisitionHeaderId") Long expRequisitionHeaderId);

    /**
     * 根据借款申请单头Id删除凭证
     *
     * @param paymentRequisitionHeaderId 借款申请单头Id
     * @author dingwei.ma@hand-china.com 2019-02-26 13:59
     * @return void
     */

    void deleteByPayReqLineId(@Param("paymentRequisitionHeaderId") Long paymentRequisitionHeaderId);

    /**
     * 获取借款审核凭证
     *
     * @author Tagin
     * @date 2019-04-01 14:51
     * @param transactionLineId 现金事务行ID
     * @param usageCode 用途代码
     * @param paymentRequisitionLineId 借款申请单行ID
     * @param drFlag 借方标识
     * @param crFlag 贷方标识
     * @return java.util.List<com.hand.hec.csh.dto.CshPaymentReqAccount>
     * @version 1.0
     **/
    List<CshPaymentReqAccount> queryAccount(@Param("transactionLineId") Long transactionLineId,
                    @Param("usageCode") String usageCode,
                    @Param("paymentRequisitionLineId") Long paymentRequisitionLineId, @Param("drFlag") String drFlag,
                    @Param("crFlag") String crFlag);

}
