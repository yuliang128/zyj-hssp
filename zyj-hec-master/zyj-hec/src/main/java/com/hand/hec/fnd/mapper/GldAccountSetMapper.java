package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.GldAccountSet;

/**
 * <p>
 * GldAccountSetMapper
 * </p>
 * 
 * @author guiyu 2019/01/08 15:36
 */
public interface GldAccountSetMapper extends Mapper<GldAccountSet> {
    /**
     *获取默认的科目表
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/27 17:01
     *@param
     *@return
     *@Version 1.0
     **/
    GldAccountSet getInitAccountSet();
}
