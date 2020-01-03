package com.hand.hec.acp.mapper;

/**
 * <p>
 * 付款申请单头信息mapper
 * </p>
 * 
 * @author guiyuting 2019/04/28 15:33
 */
import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.acp.dto.AcpRequisitionHd;

import java.util.List;

public interface AcpRequisitionHdMapper extends Mapper<AcpRequisitionHd> {

    /**
     * 根据当前登陆用户信息查询付款申请单头信息
     *
     * @param acpRequisitionHd
     * @author guiyuting 2019-04-28 15:31
     * @return
     */
    List<AcpRequisitionHd> queryMain(AcpRequisitionHd acpRequisitionHd);

    /**
     * 我的付款申请维护页面-根据headerId查询
     *
     * @param acpRequisitionHd
     * @author guiyuting 2019-04-29 16:06
     * @return
     */
    AcpRequisitionHd selectByHeaderId(AcpRequisitionHd acpRequisitionHd);

    /**
     * 付款申请单审核查询单据
     * @param dto
     * @return
     * @author zhongyu 2019-5-7
     */
    List<AcpRequisitionHd> queryAuditResult(AcpRequisitionHd dto);

    /**
     * 获取付款申请单头记录-根据付款申请单头 ID
     *
     * @author Tagin
     * @date 2019-05-07 21:12
     * @param requisitionHdsId 付款申请单头 ID
     * @return com.hand.hec.acp.dto.AcpRequisitionHd
     * @version 1.0
     **/
    AcpRequisitionHd getAcpRequisitionHeader(@Param("requisitionHdsId") Long requisitionHdsId);

    /**
     * 付款申请单创建凭证 - 获取 可审核的单据
     *
     * @param requisitionHdsId
     * @author guiyuting 2019-05-08 14:42
     * @return
     */
    AcpRequisitionHd queryForAudit(@Param("requisitionHdsId") Long requisitionHdsId);

    /**
     * 更新生成凭证状态
     *
     * @param dto
     * @author guiyuting 2019-05-23 19:19
     * @return 
     */
    void updateJeCreationStatus(AcpRequisitionHd dto);


    /**
     * <p>根据现金事务头ID获取单据编号(付款反冲)</p>
     *
     * @param transactionHeaderId 现金事务头ID
     * @return String
     * @author yang.duan 2019/5/23 14:54
    **/
    String getDocNumber(@Param("transactionHeaderId") Long transactionHeaderId);


    /**
     * 付款申请单财务查询
     * @param dto
     * @param allCompanyFlag
     * @authro zhongyu 2019-05-29
     * @return
     */
    List<AcpRequisitionHd>queryRequisition(@Param("dto")AcpRequisitionHd dto,@Param("allCompanyFlag") String allCompanyFlag);

    /**
     * 付款申请单财务查询 包含子公司
     * @param dto
     * @return
     */
    List<AcpRequisitionHd>queryComRequisition(@Param("dto")AcpRequisitionHd dto);

}
