package com.hand.hec.gld.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.GldAccount;
import com.hand.hec.gld.dto.GldAccEntityRefAccount;
/**
 * <p>
 * GldAccEntityRefAccountMapper
 * </p>
 * 
 * @author yang.duan 2019/01/10 13:47
 */
public interface GldAccEntityRefAccountMapper extends Mapper<GldAccEntityRefAccount>{

    /**
     * 查询出当前核算主体未分配的科目
     *
     * @param accEntityId 核算主体ID
     * @param setOfBooksId 账套ID
     * @author ngls.luhui 2019-02-25 16:48
     * @return
     */
    List<GldAccEntityRefAccount> queryActNotAsgnAccE(@Param("accEntityId") Long accEntityId,@Param("setOfBooksId") Long setOfBooksId);

    /**
     * 查询当前账套下的所有科目表中的科目
     *
     * @param setOfBooksId 账套id
     * @author ngls.luhui 2019-02-25 19:17
     * @return 
     */
    List<GldAccEntityRefAccount> queryAccountBySob(@Param("setOfBooksId") Long setOfBooksId);
}