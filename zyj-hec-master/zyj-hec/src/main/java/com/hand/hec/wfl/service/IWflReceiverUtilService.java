package com.hand.hec.wfl.service;

import com.hand.hec.fnd.dto.FndDocInfo;
import com.hand.hec.wfl.dto.WflInstance;

import java.util.HashMap;
import java.util.List;

/**
 * description
 *
 * @author mouse 2019/03/06 15:25
 */
public interface IWflReceiverUtilService {

    /**
     * 获取单据头信息
     *
     * @param instance
     * @author mouse 2019-03-06 15:29
     * @return com.hand.hec.fnd.dto.FndDocInfo
     */
    FndDocInfo getHeadDocInfo(WflInstance instance);



    /**
     * 获取单据汇总信息
     *
     * @param instance
     * @author mouse 2019-03-06 15:29
     * @return com.hand.hec.fnd.dto.FndDocInfo
     */
    FndDocInfo getSumDocInfo(WflInstance instance);


    /**
     * 获取单据行信息列表
     *
     * @param instance
     * @author mouse 2019-03-06 15:29
     * @return java.util.List<com.hand.hec.fnd.dto.FndDocInfo>
     */
    List<FndDocInfo> getLineDocInfo(WflInstance instance);

    /**
     * 获取某个单据行下的分配行信息
     *
     * @param instance
     * @param lineDocInfo
     * @author mouse 2019-03-06 15:31
     * @return java.util.List<com.hand.hec.fnd.dto.FndDocInfo>
     */
    List<FndDocInfo> getDistDocInfo(WflInstance instance, FndDocInfo lineDocInfo);

    /**
     * 获取单据头数据
     *
     * @param instance
     * @author mouse 2019-03-06 15:33
     * @return java.util.List<java.util.HashMap>
     */
    HashMap getHeadData(WflInstance instance);


    /**
     * 获取单据汇总数据
     *
     * @param instance
     * @author mouse 2019-03-06 15:33
     * @return java.util.List<java.util.HashMap>
     */
    List<HashMap> getSumData(WflInstance instance);


    /**
     * 获取单据数据
     *
     * @param instance
     * @param docInfo
     * @param docId 单据ID，如单据头ID\行ID\分配行ID
     * @author mouse 2019-03-06 15:36
     * @return java.util.List<java.util.HashMap>
     */
    List<HashMap> getData(WflInstance instance, FndDocInfo docInfo, String docId);
}
