package com.hand.hec.expm.mapper;

import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.expm.dto.ExpDocumentAuthority;

import java.util.List;

public interface ExpDocumentAuthorityMapper extends Mapper<ExpDocumentAuthority> {

    /**
     * 查询授权员工
     *
     * @param dto
     * @author mouse 2019-03-12 16:57
     * @return java.util.List<com.hand.hap.exp.dto.ExpEmployee>
     */
    List<ExpEmployee> queryDocAuthEmployee(ExpDocumentAuthority dto);


    /**
     * 查询授权员工
     *
     * @param dto
     * @return java.util.List<com.hand.hap.exp.dto.ExpEmployee>
     * @author yang.duan 2019/4/25 11:25
    **/
    List<ExpEmployee> getEmpCurCompAuth(ExpDocumentAuthority dto);
 }
