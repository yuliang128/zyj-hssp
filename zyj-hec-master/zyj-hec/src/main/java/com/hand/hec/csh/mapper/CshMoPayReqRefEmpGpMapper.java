package com.hand.hec.csh.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshMoPayReqRefEmpGp;

public interface CshMoPayReqRefEmpGpMapper extends Mapper<CshMoPayReqRefEmpGp>{
    List<CshMoPayReqRefEmpGp> queryByTypeId(CshMoPayReqRefEmpGp record);
}