package com.hand.hec.bgt.mapper;

/**
 * <p>
 * 预算保留临时表 mapper
 * </p>
 * 
 * @author guiyuting 2019/05/22 15:38
 */
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtBalanceDetailInit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BgtBalanceDetailInitMapper extends Mapper<BgtBalanceDetailInit> {

    /**
     * 根据sessionId删除预算保留临时表记录
     *
     * @param reserveFlag R:申请单冻结,U:报销单占用
     * @author guiyuting 2019-05-22 15:40
     * @return
     */
    void deleteBgtBalanceDetailInit(@Param("reserveFlag") String reserveFlag);

    /**
     * 新增预算保留临时表记录
     *
     * @param bgtBalanceDetailInit
     * @author guiyuting 2019-05-22 15:52
     * @return
     */
    void insertBgtBalanceDetailInit(BgtBalanceDetailInit bgtBalanceDetailInit);

    /**
     * 查询预算余额明细
     *
     * @param bgtBalanceDetailInit
     * @author guiyuting 2019-05-23 10:41
     * @return 
     */
    List<BgtBalanceDetailInit> queryBgtBalanceInit(BgtBalanceDetailInit bgtBalanceDetailInit);


    List<BgtBalanceDetailInit> queryFromBalanceReserve(BgtBalanceDetailInit bgtBalanceDetailInit);

}
