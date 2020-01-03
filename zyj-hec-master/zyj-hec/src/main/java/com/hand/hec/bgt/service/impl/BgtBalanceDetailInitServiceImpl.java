package com.hand.hec.bgt.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBudgetItem;
import com.hand.hec.bgt.dto.BgtPeriod;
import com.hand.hec.bgt.mapper.BgtBalanceDetailInitMapper;
import com.hand.hec.bgt.service.IBgtBudgetItemService;
import com.hand.hec.bgt.service.IBgtPeriodService;
import com.hand.hec.fnd.dto.FndDimValueHierarchy;
import com.hand.hec.fnd.service.IFndDimValueHierarchyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtBalanceDetailInit;
import com.hand.hec.bgt.service.IBgtBalanceDetailInitService;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBalanceDetailInitServiceImpl extends BaseServiceImpl<BgtBalanceDetailInit>
                implements IBgtBalanceDetailInitService {

    @Autowired
    private BgtBalanceDetailInitMapper balanceDetailInitMapper;

    @Autowired
    private IBgtBudgetItemService bgtBudgetItemService;

    @Autowired
    private IBgtPeriodService periodService;

    @Autowired
    private IFndDimValueHierarchyService dimValueHierarchyService;

    @Override
    public void deleteBgtBalanceDetailInit(IRequest request, String reserveFlag) {
        balanceDetailInitMapper.deleteBgtBalanceDetailInit(reserveFlag);
    }

    @Override
    public void insertBgtBalanceDetailInit(IRequest request, BgtBalanceDetailInit bgtBalanceDetailInit) {
        // 维度条件过滤
        if (bgtBalanceDetailInit.getDimension1Id() != null) {
            bgtBalanceDetailInit.setDimension1Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension1Id()));
        }

        if (bgtBalanceDetailInit.getDimension2Id() != null) {
            bgtBalanceDetailInit.setDimension2Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension2Id()));
        }

        if (bgtBalanceDetailInit.getDimension3Id() != null) {
            bgtBalanceDetailInit.setDimension3Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension3Id()));
        }

        if (bgtBalanceDetailInit.getDimension4Id() != null) {
            bgtBalanceDetailInit.setDimension4Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension4Id()));
        }

        if (bgtBalanceDetailInit.getDimension5Id() != null) {
            bgtBalanceDetailInit.setDimension5Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension5Id()));
        }

        if (bgtBalanceDetailInit.getDimension6Id() != null) {
            bgtBalanceDetailInit.setDimension6Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension6Id()));
        }

        if (bgtBalanceDetailInit.getDimension7Id() != null) {
            bgtBalanceDetailInit.setDimension7Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension7Id()));
        }

        if (bgtBalanceDetailInit.getDimension8Id() != null) {
            bgtBalanceDetailInit.setDimension8Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension8Id()));
        }

        if (bgtBalanceDetailInit.getDimension9Id() != null) {
            bgtBalanceDetailInit.setDimension9Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension9Id()));
        }

        if (bgtBalanceDetailInit.getDimension10Id() != null) {
            bgtBalanceDetailInit
                            .setDimension10Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension10Id()));
        }

        if (bgtBalanceDetailInit.getDimension11Id() != null) {
            bgtBalanceDetailInit
                            .setDimension11Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension11Id()));
        }

        if (bgtBalanceDetailInit.getDimension12Id() != null) {
            bgtBalanceDetailInit
                            .setDimension12Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension12Id()));
        }

        if (bgtBalanceDetailInit.getDimension13Id() != null) {
            bgtBalanceDetailInit
                            .setDimension13Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension13Id()));
        }

        if (bgtBalanceDetailInit.getDimension14Id() != null) {
            bgtBalanceDetailInit
                            .setDimension14Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension14Id()));
        }

        if (bgtBalanceDetailInit.getDimension15Id() != null) {
            bgtBalanceDetailInit
                            .setDimension15Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension15Id()));
        }

        if (bgtBalanceDetailInit.getDimension16Id() != null) {
            bgtBalanceDetailInit
                            .setDimension16Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension16Id()));
        }

        if (bgtBalanceDetailInit.getDimension17Id() != null) {
            bgtBalanceDetailInit
                            .setDimension17Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension17Id()));
        }

        if (bgtBalanceDetailInit.getDimension18Id() != null) {
            bgtBalanceDetailInit
                            .setDimension18Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension18Id()));
        }

        if (bgtBalanceDetailInit.getDimension19Id() != null) {
            bgtBalanceDetailInit
                            .setDimension19Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension19Id()));
        }

        if (bgtBalanceDetailInit.getDimension20Id() != null) {
            bgtBalanceDetailInit
                            .setDimension20Ids(this.getDimValueIds(request, bgtBalanceDetailInit.getDimension20Id()));
        }

        // 查询需插入的数据
        List<BgtBalanceDetailInit> bgtBalanceDetailInitList =
                        balanceDetailInitMapper.queryFromBalanceReserve(bgtBalanceDetailInit);
        // 期间的条件过滤
        List<String> periodNames = this.periodDeal(request, bgtBalanceDetailInit);
        bgtBalanceDetailInitList = bgtBalanceDetailInitList.parallelStream().filter(bgtBalanceDetailInit1 -> {
            return periodNames.contains(bgtBalanceDetailInit1.getPeriodName());
        }).collect(Collectors.toList());

        // 预算项目条件过滤
        if (bgtBalanceDetailInit.getBudgetItemId() != null) {
            List<BgtBudgetItem> bgtBudgetItemList =
                            bgtBudgetItemService.getTreeChildItems(request, bgtBalanceDetailInit.getBudgetItemId());
            List<Long> bgtBudgetItemIds = bgtBudgetItemList.parallelStream().map(BgtBudgetItem::getBudgetItemId)
                            .collect(Collectors.toList());

            bgtBalanceDetailInitList = bgtBalanceDetailInitList.parallelStream().filter(bgtBalanceDetailInit1 -> {
                return bgtBudgetItemIds.contains(bgtBalanceDetailInit1.getBudgetItemId());
            }).collect(Collectors.toList());
        }

        bgtBalanceDetailInitList.forEach(detailInit -> {
            detailInit.setSessionId(request.getSessionId());
            detailInit.setBudgetReserveLineId(detailInit.getBudgetReserveLineId());
            balanceDetailInitMapper.insertBgtBalanceDetailInit(detailInit);
        });
    }

    private List<Long> getDimValueIds(IRequest request, Long dimensionId) {
        List<FndDimValueHierarchy> dimValueHierarchyList =
                        dimValueHierarchyService.getTreeChildDims(request, dimensionId);
        List<Long> dimValueIds = dimValueHierarchyList.parallelStream().map(FndDimValueHierarchy::getDimensionValueId)
                        .collect(Collectors.toList());
        return dimValueIds;
    }

    @Override
    public boolean downDeal(IRequest request, List<BgtBalanceDetailInit> list) {
        for (BgtBalanceDetailInit bgtBalanceDetailInit : list) {
            balanceDetailInitMapper.deleteBgtBalanceDetailInit(bgtBalanceDetailInit.getReserveFlag());
            self().insertBgtBalanceDetailInit(request, bgtBalanceDetailInit);
        }
        return true;
    }

    /**
     * 期间处理
     *
     * @param bgtBalanceDetailInit
     * @author guiyuting 2019-05-28 10:49
     * @return
     */
    private List<String> periodDeal(IRequest request, BgtBalanceDetailInit bgtBalanceDetailInit) {
        List<String> periodNames = new ArrayList<>();
        String periodName = bgtBalanceDetailInit.getPeriodName();
        Long periodQuarter = bgtBalanceDetailInit.getPeriodQuarter();
        Long periodYear = bgtBalanceDetailInit.getPeriodYear();
        if (periodYear == null && periodQuarter == null && periodName != null) {
            // 期间从到
            String periodNameFrom = periodName.indexOf("~") != -1 ? periodName.substring(1, 7) : periodName;
            String periodNameTo = periodName.indexOf("~") != -1 ? periodName.substring(10, 17) : periodName;
            periodNames = periodService.getPeriodNamesForSummary(request,
                            BgtPeriod.builder().periodNameFrom(periodNameFrom).periodNameTo(periodNameTo).build());
        } else if (periodYear != null && periodQuarter == null && periodName == null) {
            periodNames = periodService.getPeriodNamesForSummary(request,
                            BgtPeriod.builder().periodYear(periodYear).build());
        } else if (periodYear == null && periodQuarter != null && periodName != null) {
            periodNames = periodService.getPeriodNamesForSummary(request,
                            BgtPeriod.builder().periodYear(periodYear).quarterNum(periodQuarter).build());
        } else if (periodYear != null && periodQuarter != null && periodName != null) {
            String periodNameFrom = periodName.indexOf("~") != -1 ? periodName.substring(1, 7) : periodName;
            String periodNameTo = periodName.indexOf("~") != -1 ? periodName.substring(10, 17) : periodName;
            periodNames = periodService.getPeriodNamesForSummary(request,
                            BgtPeriod.builder().periodNameFrom(periodNameFrom).periodNameTo(periodNameTo)
                                            .periodYear(periodYear).quarterNum(periodQuarter).build());
        }
        return periodNames;
    }

    /**
     * 维度处理
     *
     * @param bgtBalanceDetailInit
     * @author guiyuting 2019-05-28 14:26
     * @return
     */
    private List<Long> dimensionDeal(IRequest request, BgtBalanceDetailInit bgtBalanceDetailInit) {
        List<Long> dimensionIds = new ArrayList<>();
        return dimensionIds;
    }

    @Override
    public List<BgtBalanceDetailInit> queryBgtBalanceInit(IRequest request, BgtBalanceDetailInit bgtBalanceDetailInit,
                    int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return balanceDetailInitMapper.queryBgtBalanceInit(bgtBalanceDetailInit);
    }
}
