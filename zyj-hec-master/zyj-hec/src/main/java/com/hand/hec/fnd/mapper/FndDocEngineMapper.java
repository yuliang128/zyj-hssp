package com.hand.hec.fnd.mapper;

import com.hand.hec.fnd.dto.FndDocInfo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: MouseZhou
 * \* Date: 2018/3/20
 * \* Time: 20:11
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */

public interface FndDocEngineMapper {

    public List<FndDocInfo> getDocInfo(FndDocInfo docInfo);

    public List<HashMap> getDocData(@Param("docInfo") FndDocInfo docInfo, @Param("docId") long docId);

}
