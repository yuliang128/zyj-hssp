package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoReqItemsAsgnCom;
import com.hand.hap.fnd.dto.FndCompany;

import java.util.List;

public interface ExpMoReqItemsAsgnComMapper extends Mapper<ExpMoReqItemsAsgnCom>{
    List<ExpMoReqItemsAsgnCom> queryExpMoReqItemCom(ExpMoReqItemsAsgnCom dto);
    List<FndCompany> queryCompanyByItem(ExpMoReqItemsAsgnCom dto);
}