package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.GldAePeriodStatus;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 核算主体级会计期间控制mapper
 * </p>
 *
 * @author $JK.Lu$ 2019/01/22 16:43
 */
public interface GldAePeriodStatusMapper extends Mapper<GldAePeriodStatus> {

    /**
     * 查询选择核算主体的下拉框数据
     *
     * @param null
     * @author LJK 2019-01-28 16:02
     * @return
     */
    List<GldAePeriodStatus> accEntityListQuery();

    /**
     * 查询未打开的会计期间
     *
     * @param periodSetId 会计期id
     * @param accEntityId 核算主体id
     * @author LJK 2019-01-28 16:02
     * @return
     */
    List<GldAePeriodStatus> unOpenedPeriodQuery(@Param("periodSetId") Long periodSetId,
                    @Param("accEntityId") Long accEntityId);

    /**
     * 查询已打开的会计期间
     *
     * @param periodSetId 会计期id
     * @param accEntityId 核算主体id
     * @author LJK 2019-01-28 16:02
     * @return
     */
    List<GldAePeriodStatus> OpenedPeriodQuery(@Param("periodSetId") Long periodSetId,
                    @Param("accEntityId") Long accEntityId);

    /**
     * 查询下一个期间是否打开
     *
     * @param periodSetCode 会计期Code
     * @param accEntityId 核算主体id
     * @param internalPeriodNum 期间序号
     * @author LJK 2019-01-28 16:02
     * @return
     */
    Integer checkNextPeriod(@Param("accEntityId") Long accEntityId, @Param("periodSetCode") String periodSetCode,
                    @Param("internalPeriodNum") Long internalPeriodNum);

    /**
     * 检查上一个期间状态
     *
     * @param periodSetCode 会计期Code
     * @param accEntityId 核算主体id
     * @param internalPeriodNum 期间序号
     * @param periodStatusCode 期间状态代码
     * @author LJK 2019-01-28 16:02
     * @return
     */
    Integer checkLastPeriod(@Param("accEntityId") Long accEntityId, @Param("periodSetCode") String periodSetCode,
                    @Param("internalPeriodNum") Long internalPeriodNum,
                    @Param("periodStatusCode") String periodStatusCode);

    /**
     * 查询是否第一个期间
     *
     * @param periodSetCode 会计期Code
     * @param accEntityId 核算主体id
     * @param internalPeriodNum 期间序号
     * @author LJK 2019-01-28 16:02
     * @return
     */
    Integer checkIsFirstPeriod(@Param("accEntityId") Long accEntityId, @Param("periodSetCode") String periodSetCode,
                    @Param("internalPeriodNum") Long internalPeriodNum);

    /**
     * 条件查询GldAePeriodStatus 用处：是否是第一次打开期间
     * 
     * @param gldAePeriodStatus 查询条件
     * @author rui.shi@hand-china.com 2019-03-08 15:17
     * @return 查询条数
     */
    List<GldAePeriodStatus> queryGldAePeriodStatus(GldAePeriodStatus gldAePeriodStatus);

    /**
     * 查询下一个期间序号
     *
     * @param gldAePeriodStatus 查询条件
     * @author rui.shi@hand-china.com 2019-03-08 15:17
     * @return 期间序号
     */
    Long queryNextInternalPeriodNum(GldAePeriodStatus gldAePeriodStatus);



    /**
     * 查询下一个期间序号
     *
     * @param periodSetId 会计期id
     * @param accEntityId 核算主体id
     * @param internalPeriodNum 期间序号
     * @author LJK 2019-01-28 16:02
     * @return
     */
    Long selectLastPeriod(@Param("accEntityId") Long accEntityId, @Param("periodSetId") Long periodSetId,
                    @Param("internalPeriodNum") Long internalPeriodNum);

    /**
     * 查询下一个期间序号
     *
     * @param periodSetCode 会计期Code
     * @param accEntityId 核算主体id
     * @param internalPeriodNum 期间序号
     * @author LJK 2019-01-28 16:02
     * @return
     */
    Long selectNextPeriod(@Param("accEntityId") Long accEntityId, @Param("periodSetCode") String periodSetCode,
                    @Param("internalPeriodNum") Long internalPeriodNum);



    /**
     * 判断期间是否打开
     *
     * @param periodName 期间名字
     * @param periodName 核算主体ID
     * @author ngls.luhui 2019-03-11 12:10
     * @return 打开返回 O ，未打开返回 C，从未打开过返回 U
     */
    String checkPeriodOpen(@Param("periodName") String periodName, @Param("accEntityId") Long accEntityId);

    /**
     * 获取打开期间的最后一天
     *
     * @param accEntityId 核算主体ID
     * @author guiyuting 2019-05-08 13:46
     * @return
     */
    Date getMaxDateOfPeriod(@Param("accEntityId") Long accEntityId);
}
