package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.GldAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * GldAccountMapper
 * </p>
 *
 * @author guiyu 2019/01/08 15:34
 */
public interface GldAccountMapper extends Mapper<GldAccount> {

    /**
     * 科目关联表数据查询
     *
     * @param accountId accountId
     * @param accountSetId accountSetId
     * @return Map<String,Object>
     */
    Map<String, Object> accountSetRefrenceQuery(@Param("accountId") Long accountId,
                    @Param("accountSetId") Long accountSetId);

    /**
     * 根据account_code查询科目
     *
     * @param Map<String,Object> accountMap
     * @return List<GldAccount>
     */
    List<GldAccount> selectAccountId(Map<String, Object> accountMap);

    /**
     * 根据accountSetId查询科目
     *
     * @param Map<String,Object> accountMap
     * @return List<GldAccount>
     */
    List<GldAccount> selectByAccountSetId(@Param("accountSetId") Long accountSetId);



    /**
     * 科目层次树
     *
     * @param request
     * @param accountSetId 科目ID
     * @author weikun.wang2019-02-28
     * @return ResponseData
     */
    List<GldAccount> hierarchyTreeQuery(@Param("accountSetId") Long accountSetId);
}
