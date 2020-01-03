package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshBank;

import java.util.List;

/**
 * <p>
 * CshBankMapper
 * </p>
 *
 * @author guiyu 2019/01/29 14:20
 */
public interface CshBankMapper extends Mapper<CshBank> {
    /**
     * 查询所有银行信息
     *
     * @param cshBank
     * @return 返回符合条件的银行信息
     * @author guiyu 2019-01-29 14:18
     */
    List<CshBank> queryAll(CshBank cshBank);
}