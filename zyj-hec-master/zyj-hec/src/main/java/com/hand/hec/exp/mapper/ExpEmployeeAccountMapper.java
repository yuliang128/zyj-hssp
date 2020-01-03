package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpEmployeeAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 员工分配银行账户Mapper
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */
public interface ExpEmployeeAccountMapper extends Mapper<ExpEmployeeAccount> {
    List<ExpEmployeeAccount> queryGldPayeeV(@Param("payeeCategory") String payeeCategory,
                    @Param("payeeId") Long payeeId, @Param("accEntityId") Long accEntityId);

    /**
     * 根据用户、银行账户及开户行获取详细信息
     *
     * @author Tagin
     * @date 2019-04-11 22:21
     * @param employeeId 员工ID
     * @param accountName 银行户名
     * @param accountNumber 银行账号
     * @return List<ExpEmployeeAccount>
     * @version 1.0
     **/
    List<ExpEmployeeAccount> getEmployeeAccount(@Param("employeeId") Long employeeId,
                    @Param("accountName") String accountName, @Param("accountNumber") String accountNumber);
}
