package com.hand.hec.expm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.expm.dto.ExpReportObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * ExpReportObjectMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 15:02
 */
public interface ExpReportObjectMapper extends Mapper<ExpReportObject> {
    /**
     * <p>
     * 报销单费用对象更新
     * <p/>
     *
     * @param dto 需要更新的报销单费用对象
     * @return 返回null
     * @author yang.duan 2019/3/13 13:26
     */
    void updateExpReportObject(ExpReportObject dto);

    /**
     * <p>
     * 查询费用报销单头对象
     * <p/>
     *
     *
     * @param expReportHeaderId 费用报销单头ID
     * @return 费用头对象的list
     * @author yang.duan 2019/3/25 10:09
     */
    List<ExpReportObject> queryHdObjectByReport(@Param("expReportHeaderId") Long expReportHeaderId);

    List<ExpReportObject> queryLnObjectByReport(@Param("expReportHeaderId") Long expReportHeaderId,
                    @Param("expReportLineId") Long expReportLineId);

    /**
     * <p>
     * 报销单费用对象删除
     * <p/>
     *
     * @param expReportHeaderId
     * @param expReportLineId
     * @return void
     * @author yang.duan 2019/3/29 15:57
     */
    void deleteExpObject(@Param("expReportHeaderId") Long expReportHeaderId,
                    @Param("expReportLineId") Long expReportLineId);
}
