package com.hand.hec.expm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.expm.dto.ExpAmortizationAccount;
import org.apache.ibatis.annotations.Param;

public interface ExpAmortizationAccountMapper extends Mapper<ExpAmortizationAccount>{
    /**
     *校验科目是否是需要摊销的科目
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/22 11:13
     *@param accountId 科目Id
     *@param setOfBooksId 账套Id
     *@return int
     *@Version 1.0
     **/
    int checkAccountExist(@Param("accountId")Long accountId,@Param("setOfBooksId")Long setOfBooksId);
}