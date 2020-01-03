package com.hand.hec.csh.mapper;

import java.util.List;
import java.util.Map;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshCashAccount;
import org.apache.ibatis.annotations.Param;

public interface CshCashAccountMapper extends Mapper<CshCashAccount>{
    /**
     *查询账套信息
     *
     * @param setOfBooksId 账套Id
     * @author dingwei.ma@hand-china.com 2019-02-22 11:14
     * @return List<Map>
     */
    List<Map> queryByBooksId(@Param("setOfBooksId") Long setOfBooksId);

    /**
     *查询现金流量表分配科目
     *
     * @param record 现金流量表分配科目实体类
     * @author dingwei.ma@hand-china.com 2019-02-22 11:14
     * @return List<CshCashAccount>
     */
    List<CshCashAccount> queryCashAccount(CshCashAccount record);

}