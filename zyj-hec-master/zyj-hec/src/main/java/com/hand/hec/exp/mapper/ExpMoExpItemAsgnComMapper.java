package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoExpItemAsgnCom;

import java.util.List;

public interface ExpMoExpItemAsgnComMapper extends Mapper<ExpMoExpItemAsgnCom>{


    /**
     * 根据管理组织，费用项目查询分配公司信息
     * @param dto
     * @return 公司信息
     * @author zhongyu 2019-2-26
     */
    List<ExpMoExpItemAsgnCom> queryCompany(ExpMoExpItemAsgnCom dto);


    List<ExpMoExpItemAsgnCom> queryCompanyByItem(ExpMoExpItemAsgnCom dto);

}