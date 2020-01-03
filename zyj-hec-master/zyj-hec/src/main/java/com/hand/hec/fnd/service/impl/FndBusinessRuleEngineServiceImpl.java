package com.hand.hec.fnd.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hec.fnd.dto.FndBusinessRuleParameter;
import com.hand.hec.fnd.mapper.FndBusinessRuleEngineMapper;
import com.hand.hec.fnd.dto.FndBusinessRule;
import com.hand.hec.fnd.dto.FndBusinessRuleDetail;
import com.hand.hec.fnd.dto.FndDocInfo;
import com.hand.hec.fnd.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/2/23 \* Time: 14:01 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FndBusinessRuleEngineServiceImpl implements IFndBusinessRuleEngineService {

    @Autowired
    private IFndBusinessRuleService fndBusinessRuleService;
    @Autowired
    private IFndBusinessRuleDetailService fndBusinessRuleDetailService;
    @Autowired
    private IFndBusinessRuleParameterService BusinessRuleParaService;
    @Autowired
    private IFndDocEngineService docEngineService;
    @Autowired
    private FndBusinessRuleEngineMapper ruleEngineMapper;

    private Logger logger = LoggerFactory.getLogger(FndBusinessRuleEngineServiceImpl.class);

    /**
     * 获取当前业务规则的所有SQL参数
     *
     * @param context 运行上下文
     * @param businessRule 业务规则
     * @param ruleDetailList 业务规则明细列表
     * @param docCategory 单据类别
     * @param docId 单据ID
     * @return 所有SQL参数列表
     */
    @Override
    public List<HashMap> getSqlParameter(IRequest context, FndBusinessRule businessRule,
                    List<FndBusinessRuleDetail> ruleDetailList, String docCategory, String docId) {

        List<HashMap> sqlParamList = new ArrayList<HashMap>();

        String distTableName = null;
        String lineTableName = null;
        String headTableName = null;
        String distIdFieldName = null;
        String lineIdFieldName = null;
        String headIdFieldName = null;
        FndDocInfo targetHeadDocInfo = null;
        FndDocInfo targetLineDocInfo = null;
        FndDocInfo targetDistDocInfo = null;

        // 获取所有单据详细信息
        List<FndDocInfo> docInfoList = docEngineService.getDocInfo(businessRule.getDocCategory());

        for (FndBusinessRuleDetail curRuleDetail : ruleDetailList) {
            FndBusinessRuleParameter ruleParameter = new FndBusinessRuleParameter();
            ruleParameter.setParameterId(curRuleDetail.getParameterId());
            ruleParameter = BusinessRuleParaService.selectByPrimaryKey(context, ruleParameter);
            // 循环所有的分配行级别参数，确定当前的查询级别
            // 循环查询出上级行和上级头
            for (FndDocInfo distDocInfo : docInfoList) {
                if (FndDocInfo.TABLE_LEVEL_DIST.equals(distDocInfo.getTableLevelCode())) {
                    String paramStr = "#{" + distDocInfo.getIdFieldName() + "}";
                    if (ruleParameter.getSqlContent().contains(paramStr)) {
                        // 当前单据SQL包含当前dist的ID字段，说明是当前分配行表级别
                        if (distTableName == null) {
                            distTableName = distDocInfo.getTableName();
                            distIdFieldName = distDocInfo.getIdFieldName();
                            targetDistDocInfo = distDocInfo;
                            // 生成对应的line信息
                            FndDocInfo lineDocInfo = new FndDocInfo();
                            lineDocInfo.setTableLevelCode(FndDocInfo.TABLE_LEVEL_LINE);
                            lineDocInfo.setDocCategory(distDocInfo.getDocCategory());
                            lineDocInfo.setDocInfoId(distDocInfo.getParentDocInfoId());
                            List<FndDocInfo> lineDocInfoList = docEngineService.selectOptions(context, lineDocInfo,
                                            new Criteria(lineDocInfo));
                            if (lineDocInfoList.size() != 1) {
                                throw new RuntimeException("当前权限规则对应的行级别单据信息不存在，请联系管理员!BusinessRuleId : "
                                                + businessRule.getBusinessRuleId());
                            } else {
                                lineDocInfo = lineDocInfoList.get(0);
                            }
                            lineTableName = lineDocInfo.getTableName();
                            lineIdFieldName = lineDocInfo.getIdFieldName();
                            targetLineDocInfo = lineDocInfo;
                            // 生成对应的head信息
                            FndDocInfo headDocInfo = new FndDocInfo();
                            headDocInfo.setTableLevelCode(FndDocInfo.TABLE_LEVEL_HEAD);
                            headDocInfo.setDocCategory(lineDocInfo.getDocCategory());
                            headDocInfo.setDocInfoId(lineDocInfo.getParentDocInfoId());
                            List<FndDocInfo> headDocInfoList = docEngineService.selectOptions(context, lineDocInfo,
                                            new Criteria(lineDocInfo));
                            if (headDocInfoList.size() != 1) {
                                throw new RuntimeException("当前权限规则对应的头级别单据信息不存在，请联系管理员!BusinessRuleId : "
                                                + businessRule.getBusinessRuleId());
                            } else {
                                headDocInfo = headDocInfoList.get(0);
                            }
                            headTableName = headDocInfo.getTableName();
                            headIdFieldName = headDocInfo.getIdFieldName();
                            targetHeadDocInfo = headDocInfo;
                        } else {
                            throw new RuntimeException("当前权限规则包含多种分配行参数，请联系管理员!BusinessRuleId : "
                                            + businessRule.getBusinessRuleId());
                        }
                    }
                }
            }
            // 循环所有的行级别参数，确定当前的查询级别
            for (FndDocInfo lineDocInfo : docInfoList) {
                if (FndDocInfo.TABLE_LEVEL_LINE.equals(lineDocInfo.getTableLevelCode())) {
                    String paramStr = "#{" + lineDocInfo.getIdFieldName() + "}";
                    if (ruleParameter.getSqlContent().contains(paramStr)) {
                        if (lineTableName == null) {
                            lineTableName = lineDocInfo.getTableName();
                            lineIdFieldName = lineDocInfo.getIdFieldName();
                            targetLineDocInfo = lineDocInfo;
                            // 生成对应的head信息
                            FndDocInfo headDocInfo = new FndDocInfo();
                            headDocInfo.setTableLevelCode(FndDocInfo.TABLE_LEVEL_HEAD);
                            headDocInfo.setDocCategory(lineDocInfo.getDocCategory());
                            headDocInfo.setDocInfoId(lineDocInfo.getParentDocInfoId());
                            List<FndDocInfo> headDocInfoList = docEngineService.selectOptions(context, lineDocInfo,
                                            new Criteria(lineDocInfo));
                            if (headDocInfoList.size() != 1) {
                                throw new RuntimeException("当前权限规则对应的头级别单据信息不存在，请联系管理员!BusinessRuleId : "
                                                + businessRule.getBusinessRuleId());
                            } else {
                                headDocInfo = headDocInfoList.get(0);
                            }
                            headTableName = headDocInfo.getTableName();
                            headIdFieldName = headDocInfo.getIdFieldName();
                            targetHeadDocInfo = headDocInfo;
                        } else if (lineTableName.equals(lineDocInfo.getTableName())) {
                            // 当前存在一致的表名，认为是分配行带出的，无需重新带出对应的头信息
                        } else {
                            throw new RuntimeException("当前权限规则包含多种行参数，请联系管理员!BusinessRuleId : "
                                            + businessRule.getBusinessRuleId());
                        }
                    }
                }
            }
            // 循环所有的头级别参数，确定当前的查询级别
            for (FndDocInfo headDocInfo : docInfoList) {
                if (FndDocInfo.TABLE_LEVEL_HEAD.equals(headDocInfo.getTableLevelCode())) {
                    String paramStr = "#{" + headDocInfo.getIdFieldName() + "}";
                    if (ruleParameter.getSqlContent().contains(paramStr)) {
                        headTableName = headDocInfo.getTableName();
                        headIdFieldName = headDocInfo.getIdFieldName();
                        targetHeadDocInfo = headDocInfo;
                    }
                }
            }
        }

        // 如果当前头信息为空，说明不需要任何单据参数，直接返回null
        // 根据业务表和传入的docId得出line_id和dist_id(具体看id_field_name定义的值)
        if (targetHeadDocInfo == null) {
            return null;
        } else {
            if (targetLineDocInfo != null) {
                // 如果行信息不为空，查询出所有行ID
                List<HashMap> lineDataList = docEngineService.getDocData(targetLineDocInfo, docId);
                for (HashMap lineData : lineDataList) {
                    // 如果分配行信息不为空，查询出所有分配行ID
                    if (targetDistDocInfo != null) {
                        List<HashMap> distDataList = docEngineService.getDocData(targetDistDocInfo,
                                        lineData.get(targetLineDocInfo.getIdFieldName()).toString());
                        for (HashMap distData : distDataList) {
                            HashMap<String, String> sqlParam = new HashMap<String, String>();
                            sqlParam.put(headIdFieldName, docId);
                            sqlParam.put(lineIdFieldName, lineData.get(targetLineDocInfo.getIdFieldName()).toString());
                            sqlParam.put(distIdFieldName, distData.get(targetDistDocInfo.getIdFieldName()).toString());
                            sqlParamList.add(sqlParam);
                        }
                    } else {
                        HashMap<String, String> sqlParam = new HashMap<String, String>();
                        sqlParam.put(headIdFieldName, docId);
                        sqlParam.put(lineIdFieldName, lineData.get(targetLineDocInfo.getIdFieldName()).toString());
                        sqlParamList.add(sqlParam);
                    }
                }
            } else {
                HashMap<String, String> sqlParam = new HashMap<String, String>();
                sqlParam.put(headIdFieldName, docId);
                sqlParamList.add(sqlParam);
            }
        }

        return sqlParamList;
    }

    /**
     * 获取业务规则明细列表
     *
     * @param context 运行上下文
     * @param businessRule 业务规则
     * @return 业务规则明细列表
     */
    @Override
    public List<FndBusinessRuleDetail> getBusinessRuleDetailList(IRequest context, FndBusinessRule businessRule) {
        // 获取权限规则信息
        FndBusinessRuleDetail ruleDetail = new FndBusinessRuleDetail();
        ruleDetail.setBusinessRuleId(businessRule.getBusinessRuleId());
        ruleDetail.setSortname("sequence");
        ruleDetail.setSortorder("asc");
        List<FndBusinessRuleDetail> ruleDetailList =
                        fndBusinessRuleDetailService.selectOptions(context, ruleDetail, new Criteria(ruleDetail));

        return ruleDetailList;
    }

    /**
     * 获取业务规则参数Map
     *
     * @param context 运行上下文
     * @param businessRuleDetailList 业务规则明细列表
     * @return 业务规则参数Map
     */
    @Override
    public HashMap<Long, FndBusinessRuleParameter> getBusinessRuleParameterMap(IRequest context,
                    List<FndBusinessRuleDetail> businessRuleDetailList) {
        HashMap<Long, FndBusinessRuleParameter> businessRuleParameterMap =
                        new HashMap<Long, FndBusinessRuleParameter>();
        for (FndBusinessRuleDetail businessRuleDetail : businessRuleDetailList) {
            if (businessRuleParameterMap.get(businessRuleDetail.getParameterId()) == null) {
                FndBusinessRuleParameter businessRuleParameter = new FndBusinessRuleParameter();
                businessRuleParameter.setParameterId(businessRuleDetail.getParameterId());
                businessRuleParameter = BusinessRuleParaService.selectByPrimaryKey(context, businessRuleParameter);
                businessRuleParameterMap.put(businessRuleDetail.getParameterId(), businessRuleParameter);
            }
        }

        return businessRuleParameterMap;
    }

    /**
     * 验证[等于]类型的单条权限规则明细
     *
     * @param businessRuleDetail 业务规则明细
     * @param conditionValueList 条件值列表
     * @param dataType 数据类型
     * @return 验证结果
     */
    private boolean validateEqual(FndBusinessRuleDetail businessRuleDetail, List<HashMap> conditionValueList,
                    String dataType) {
        if (businessRuleDetail.getConditionValue() == null) {
            throw new RuntimeException(
                            "权限规则参数值为空，请联系管理员!BusinessRuleDetailId:" + businessRuleDetail.getBusinessRuleId());
        }
        if (FndBusinessRuleParameter.DATA_TYPE_VARCHAR.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                if (!businessRuleDetail.getConditionValue().equals(conditionValue.get("condition_value").toString())) {
                    return false;
                }
            }
        } else if (FndBusinessRuleParameter.DATA_TYPE_NUMERIC.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                if (!new BigDecimal(businessRuleDetail.getConditionValue())
                                .equals(new BigDecimal(conditionValue.get("condition_value").toString()))) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 验证[不等于]类型的单条权限规则明细
     *
     * @param businessRuleDetail 业务规则明细
     * @param conditionValueList 条件值列表
     * @param dataType 数据类型
     * @return 验证结果
     */
    private boolean validateNotEqual(FndBusinessRuleDetail businessRuleDetail, List<HashMap> conditionValueList,
                    String dataType) {
        if (businessRuleDetail.getConditionValue() == null) {
            throw new RuntimeException(
                            "权限规则参数值为空，请联系管理员!BusinessRuleDetailId:" + businessRuleDetail.getBusinessRuleId());
        }
        if (FndBusinessRuleParameter.DATA_TYPE_VARCHAR.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                if (businessRuleDetail.getConditionValue().equals(conditionValue.get("condition_value").toString())) {
                    return false;
                }
            }
        } else if (FndBusinessRuleParameter.DATA_TYPE_NUMERIC.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                if (new BigDecimal(businessRuleDetail.getConditionValue())
                                .equals(new BigDecimal(conditionValue.get("condition_value").toString()))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证[大于]类型的单条权限规则明细
     *
     * @param businessRuleDetail 业务规则明细
     * @param conditionValueList 条件值列表
     * @param dataType 数据类型
     * @return 验证结果
     */
    private boolean validateGreatThan(FndBusinessRuleDetail businessRuleDetail, List<HashMap> conditionValueList,
                    String dataType) {
        if (businessRuleDetail.getConditionValue() == null) {
            throw new RuntimeException(
                            "权限规则参数值为空，请联系管理员!BusinessRuleDetailId:" + businessRuleDetail.getBusinessRuleId());
        }
        if (FndBusinessRuleParameter.DATA_TYPE_VARCHAR.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                int compareValue = businessRuleDetail.getConditionValue()
                                .compareTo(conditionValue.get("condition_value").toString());
                if (compareValue >= 0) {
                    return false;
                }
            }
        } else if (FndBusinessRuleParameter.DATA_TYPE_NUMERIC.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                int compareValue = new BigDecimal(businessRuleDetail.getConditionValue())
                                .compareTo(new BigDecimal(conditionValue.get("condition_value").toString()));
                if (compareValue >= 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证[大于等于]类型的单条权限规则明细
     *
     * @param businessRuleDetail 业务规则明细
     * @param conditionValueList 条件值列表
     * @param dataType 数据类型
     * @return 验证结果
     */
    private boolean validateGreatThanOrEqual(FndBusinessRuleDetail businessRuleDetail, List<HashMap> conditionValueList,
                    String dataType) {
        if (businessRuleDetail.getConditionValue() == null) {
            throw new RuntimeException(
                            "权限规则参数值为空，请联系管理员!BusinessRuleDetailId:" + businessRuleDetail.getBusinessRuleId());
        }
        if (FndBusinessRuleParameter.DATA_TYPE_VARCHAR.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                int compareValue = businessRuleDetail.getConditionValue()
                                .compareTo(conditionValue.get("condition_value").toString());
                if (compareValue > 0) {
                    return false;
                }
            }
        } else if (FndBusinessRuleParameter.DATA_TYPE_NUMERIC.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                int compareValue = new BigDecimal(businessRuleDetail.getConditionValue())
                                .compareTo(new BigDecimal(conditionValue.get("condition_value").toString()));
                if (compareValue > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证[小于]类型的单条权限规则明细
     *
     * @param businessRuleDetail 业务规则明细
     * @param conditionValueList 条件值列表
     * @param dataType 数据类型
     * @return 验证结果
     */
    private boolean validateLessThan(FndBusinessRuleDetail businessRuleDetail, List<HashMap> conditionValueList,
                    String dataType) {
        if (businessRuleDetail.getConditionValue() == null) {
            throw new RuntimeException(
                            "权限规则参数值为空，请联系管理员!BusinessRuleDetailId:" + businessRuleDetail.getBusinessRuleId());
        }
        if (FndBusinessRuleParameter.DATA_TYPE_VARCHAR.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                int compareValue = businessRuleDetail.getConditionValue()
                                .compareTo(conditionValue.get("condition_value").toString());
                if (compareValue <= 0) {
                    return false;
                }
            }
        } else if (FndBusinessRuleParameter.DATA_TYPE_NUMERIC.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                int compareValue = new BigDecimal(businessRuleDetail.getConditionValue())
                                .compareTo(new BigDecimal(conditionValue.get("condition_value").toString()));
                if (compareValue <= 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证[小于等于]类型的单条权限规则明细
     *
     * @param businessRuleDetail 业务规则明细
     * @param conditionValueList 条件值列表
     * @param dataType 数据类型
     * @return 验证结果
     */
    private boolean validateLessThanOrEqual(FndBusinessRuleDetail businessRuleDetail, List<HashMap> conditionValueList,
                    String dataType) {
        if (businessRuleDetail.getConditionValue() == null) {
            throw new RuntimeException(
                            "权限规则参数值为空，请联系管理员!BusinessRuleDetailId:" + businessRuleDetail.getBusinessRuleId());
        }
        if (FndBusinessRuleParameter.DATA_TYPE_VARCHAR.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                int compareValue = businessRuleDetail.getConditionValue()
                                .compareTo(conditionValue.get("condition_value").toString());
                if (compareValue < 0) {
                    return false;
                }
            }
        } else if (FndBusinessRuleParameter.DATA_TYPE_NUMERIC.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                int compareValue = new BigDecimal(businessRuleDetail.getConditionValue())
                                .compareTo(new BigDecimal(conditionValue.get("condition_value").toString()));
                if (compareValue < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证[like]类型的单条权限规则明细
     *
     * @param businessRuleDetail 业务规则明细
     * @param conditionValueList 条件值列表
     * @param dataType 数据类型
     * @return 验证结果
     */
    private boolean validateLike(FndBusinessRuleDetail businessRuleDetail, List<HashMap> conditionValueList,
                    String dataType) {
        if (businessRuleDetail.getConditionValue() == null) {
            throw new RuntimeException(
                            "权限规则参数值为空，请联系管理员!BusinessRuleDetailId:" + businessRuleDetail.getBusinessRuleId());
        }
        if (FndBusinessRuleParameter.DATA_TYPE_NUMERIC.equals(dataType)) {
            throw new RuntimeException(
                            "数字类型的参数无法配置Like条件，请联系管理员!BusinessRuleDetailId:" + businessRuleDetail.getBusinessRuleId());
        }
        if (FndBusinessRuleParameter.DATA_TYPE_VARCHAR.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                String regStr = businessRuleDetail.getConditionValue().replaceAll("%", ".*");
                Pattern pattern = Pattern.compile(regStr);
                Matcher matcher = pattern.matcher(conditionValue.get("condition_value").toString());
                if (!matcher.find()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证[存在等于]类型的单条权限规则明细
     *
     * @param businessRuleDetail 业务规则明细
     * @param conditionValueList 条件值列表
     * @param dataType 数据类型
     * @return 验证结果
     */
    private boolean validateExistEqual(FndBusinessRuleDetail businessRuleDetail, List<HashMap> conditionValueList,
                    String dataType) {
        int valueCount = 0;

        if (businessRuleDetail.getConditionValue() == null) {
            throw new RuntimeException(
                            "权限规则参数值为空，请联系管理员!BusinessRuleDetailId:" + businessRuleDetail.getBusinessRuleId());
        }
        if (FndBusinessRuleParameter.DATA_TYPE_VARCHAR.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                valueCount++;
                if (businessRuleDetail.getConditionValue().equals(conditionValue.get("condition_value").toString())) {
                    return true;
                }
            }
        } else if (FndBusinessRuleParameter.DATA_TYPE_NUMERIC.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                valueCount++;
                if (new BigDecimal(businessRuleDetail.getConditionValue())
                                .equals(new BigDecimal(conditionValue.get("condition_value").toString()))) {
                    return true;
                }
            }
        }

        if (valueCount > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证[存在不等于]类型的单条权限规则明细
     *
     * @param businessRuleDetail 业务规则明细
     * @param conditionValueList 条件值列表
     * @param dataType 数据类型
     * @return 验证结果
     */
    private boolean validateExistNotEqual(FndBusinessRuleDetail businessRuleDetail, List<HashMap> conditionValueList,
                    String dataType) {
        int valueCount = 0;

        if (businessRuleDetail.getConditionValue() == null) {
            throw new RuntimeException(
                            "权限规则参数值为空，请联系管理员!BusinessRuleDetailId:" + businessRuleDetail.getBusinessRuleId());
        }
        if (FndBusinessRuleParameter.DATA_TYPE_VARCHAR.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                valueCount++;
                if (!businessRuleDetail.getConditionValue().equals(conditionValue.get("condition_value").toString())) {
                    return true;
                }
            }
        } else if (FndBusinessRuleParameter.DATA_TYPE_NUMERIC.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                valueCount++;
                if (!new BigDecimal(businessRuleDetail.getConditionValue())
                                .equals(new BigDecimal(conditionValue.get("condition_value").toString()))) {
                    return true;
                }
            }
        }

        if (valueCount > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证[存在大于]类型的单条权限规则明细
     *
     * @param businessRuleDetail 业务规则明细
     * @param conditionValueList 条件值列表
     * @param dataType 数据类型
     * @return 验证结果
     */
    private boolean validateExistGreatThan(FndBusinessRuleDetail businessRuleDetail, List<HashMap> conditionValueList,
                    String dataType) {
        int valueCount = 0;

        if (businessRuleDetail.getConditionValue() == null) {
            throw new RuntimeException(
                            "权限规则参数值为空，请联系管理员!BusinessRuleDetailId:" + businessRuleDetail.getBusinessRuleId());
        }
        if (FndBusinessRuleParameter.DATA_TYPE_VARCHAR.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                valueCount++;
                int compareValue = businessRuleDetail.getConditionValue()
                                .compareTo(conditionValue.get("condition_value").toString());
                if (compareValue >= 0) {
                    return true;
                }
            }
        } else if (FndBusinessRuleParameter.DATA_TYPE_NUMERIC.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                valueCount++;
                int compareValue = new BigDecimal(businessRuleDetail.getConditionValue())
                                .compareTo(new BigDecimal(conditionValue.get("condition_value").toString()));
                if (compareValue >= 0) {
                    return true;
                }
            }
        }

        if (valueCount > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证[存在大于等于]类型的单条权限规则明细
     *
     * @param businessRuleDetail 业务规则明细
     * @param conditionValueList 条件值列表
     * @param dataType 数据类型
     * @return 验证结果
     */
    private boolean validateExistGreatThanOrEqual(FndBusinessRuleDetail businessRuleDetail,
                    List<HashMap> conditionValueList, String dataType) {
        int valueCount = 0;

        if (businessRuleDetail.getConditionValue() == null) {
            throw new RuntimeException(
                            "权限规则参数值为空，请联系管理员!BusinessRuleDetailId:" + businessRuleDetail.getBusinessRuleId());
        }
        if (FndBusinessRuleParameter.DATA_TYPE_VARCHAR.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                valueCount++;
                int compareValue = businessRuleDetail.getConditionValue()
                                .compareTo(conditionValue.get("condition_value").toString());
                if (compareValue > 0) {
                    return true;
                }
            }
        } else if (FndBusinessRuleParameter.DATA_TYPE_NUMERIC.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                valueCount++;
                int compareValue = new BigDecimal(businessRuleDetail.getConditionValue())
                                .compareTo(new BigDecimal(conditionValue.get("condition_value").toString()));
                if (compareValue > 0) {
                    return true;
                }
            }
        }

        if (valueCount > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证[存在小于]类型的单条权限规则明细
     *
     * @param businessRuleDetail 业务规则明细
     * @param conditionValueList 条件值列表
     * @param dataType 数据类型
     * @return 验证结果
     */
    private boolean validateExistLessThan(FndBusinessRuleDetail businessRuleDetail, List<HashMap> conditionValueList,
                    String dataType) {
        int valueCount = 0;

        if (businessRuleDetail.getConditionValue() == null) {
            throw new RuntimeException(
                            "权限规则参数值为空，请联系管理员!BusinessRuleDetailId:" + businessRuleDetail.getBusinessRuleId());
        }
        if (FndBusinessRuleParameter.DATA_TYPE_VARCHAR.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                valueCount++;
                int compareValue = businessRuleDetail.getConditionValue()
                                .compareTo(conditionValue.get("condition_value").toString());
                if (compareValue <= 0) {
                    return true;
                }
            }
        } else if (FndBusinessRuleParameter.DATA_TYPE_NUMERIC.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                valueCount++;
                int compareValue = new BigDecimal(businessRuleDetail.getConditionValue())
                                .compareTo(new BigDecimal(conditionValue.get("condition_value").toString()));
                if (compareValue <= 0) {
                    return true;
                }
            }
        }

        if (valueCount > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证[存在小于等于]类型的单条权限规则明细
     *
     * @param businessRuleDetail 业务规则明细
     * @param conditionValueList 条件值列表
     * @param dataType 数据类型
     * @return 验证结果
     */
    private boolean validateExistLessThanOrEqual(FndBusinessRuleDetail businessRuleDetail,
                    List<HashMap> conditionValueList, String dataType) {
        int valueCount = 0;

        if (businessRuleDetail.getConditionValue() == null) {
            throw new RuntimeException(
                            "权限规则参数值为空，请联系管理员!BusinessRuleDetailId:" + businessRuleDetail.getBusinessRuleId());
        }
        if (FndBusinessRuleParameter.DATA_TYPE_VARCHAR.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                valueCount++;
                int compareValue = businessRuleDetail.getConditionValue()
                                .compareTo(conditionValue.get("condition_value").toString());
                if (compareValue < 0) {
                    return true;
                }
            }
        } else if (FndBusinessRuleParameter.DATA_TYPE_NUMERIC.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                valueCount++;
                int compareValue = new BigDecimal(businessRuleDetail.getConditionValue())
                                .compareTo(new BigDecimal(conditionValue.get("condition_value").toString()));
                if (compareValue < 0) {
                    return true;
                }
            }
        }

        if (valueCount > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证[存在like]类型的单条权限规则明细
     *
     * @param businessRuleDetail 业务规则明细
     * @param conditionValueList 条件值列表
     * @param dataType 数据类型
     * @return 验证结果
     */
    private boolean validateExistLike(FndBusinessRuleDetail businessRuleDetail, List<HashMap> conditionValueList,
                    String dataType) {
        int valueCount = 0;

        if (businessRuleDetail.getConditionValue() == null) {
            throw new RuntimeException(
                            "权限规则参数值为空，请联系管理员!BusinessRuleDetailId:" + businessRuleDetail.getBusinessRuleId());
        }
        if (FndBusinessRuleParameter.DATA_TYPE_NUMERIC.equals(dataType)) {
            throw new RuntimeException("数字类型的参数无法配置Exist Like条件，请联系管理员!BusinessRuleDetailId:"
                            + businessRuleDetail.getBusinessRuleId());
        }
        if (FndBusinessRuleParameter.DATA_TYPE_VARCHAR.equals(dataType)) {
            for (HashMap conditionValue : conditionValueList) {
                valueCount++;
                String regStr = businessRuleDetail.getConditionValue().replaceAll("%", ".*");
                Pattern pattern = Pattern.compile(regStr);
                Matcher matcher = pattern.matcher(conditionValue.get("condition_value").toString());
                if (matcher.find()) {
                    return true;
                }
            }
        }

        if (valueCount > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证单条业务规则明细
     *
     * @param context 运行上下文
     * @param businessRuleDetail 业务规则明细
     * @param conditionValueList 条件值列表
     * @param dataType 数据类型
     * @return 验证结果
     */
    @Override
    public boolean validateSingleDetail(IRequest context, FndBusinessRuleDetail businessRuleDetail,
                    List<HashMap> conditionValueList, String dataType) {
        if (FndBusinessRuleDetail.CONDITION_TYPE_EQ.equals(businessRuleDetail.getConditionType())) {
            return validateEqual(businessRuleDetail, conditionValueList, dataType);
        } else if (FndBusinessRuleDetail.CONDITION_TYPE_NEQ.equals(businessRuleDetail.getConditionType())) {
            return validateNotEqual(businessRuleDetail, conditionValueList, dataType);
        } else if (FndBusinessRuleDetail.CONDITION_TYPE_GT.equals(businessRuleDetail.getConditionType())) {
            return validateGreatThan(businessRuleDetail, conditionValueList, dataType);
        } else if (FndBusinessRuleDetail.CONDITION_TYPE_GTEQUAL.equals(businessRuleDetail.getConditionType())) {
            return validateGreatThanOrEqual(businessRuleDetail, conditionValueList, dataType);
        } else if (FndBusinessRuleDetail.CONDITION_TYPE_LT.equals(businessRuleDetail.getConditionType())) {
            return validateLessThan(businessRuleDetail, conditionValueList, dataType);
        } else if (FndBusinessRuleDetail.CONDITION_TYPE_LTEQUAL.equals(businessRuleDetail.getConditionType())) {
            return validateLessThanOrEqual(businessRuleDetail, conditionValueList, dataType);
        } else if (FndBusinessRuleDetail.CONDITION_TYPE_LIKE.equals(businessRuleDetail.getConditionType())) {
            return validateLike(businessRuleDetail, conditionValueList, dataType);
        } else if (FndBusinessRuleDetail.CONDITION_TYPE_EXIST_EQ.equals(businessRuleDetail.getConditionType())) {
            return validateExistEqual(businessRuleDetail, conditionValueList, dataType);
        } else if (FndBusinessRuleDetail.CONDITION_TYPE_EXIST_NEQ.equals(businessRuleDetail.getConditionType())) {
            return validateExistNotEqual(businessRuleDetail, conditionValueList, dataType);
        } else if (FndBusinessRuleDetail.CONDITION_TYPE_EXIST_GT.equals(businessRuleDetail.getConditionType())) {
            return validateExistGreatThan(businessRuleDetail, conditionValueList, dataType);
        } else if (FndBusinessRuleDetail.CONDITION_TYPE_EXIST_GTEQUAL.equals(businessRuleDetail.getConditionType())) {
            return validateExistGreatThanOrEqual(businessRuleDetail, conditionValueList, dataType);
        } else if (FndBusinessRuleDetail.CONDITION_TYPE_EXIST_LT.equals(businessRuleDetail.getConditionType())) {
            return validateExistLessThan(businessRuleDetail, conditionValueList, dataType);
        } else if (FndBusinessRuleDetail.CONDITION_TYPE_EXIST_LTEQUAL.equals(businessRuleDetail.getConditionType())) {
            return validateExistLessThanOrEqual(businessRuleDetail, conditionValueList, dataType);
        } else if (FndBusinessRuleDetail.CONDITION_TYPE_EXIST_LIKE.equals(businessRuleDetail.getConditionType())) {
            return validateExistLike(businessRuleDetail, conditionValueList, dataType);
        }
        return true;
    }

    /**
     * 验证权限规则
     *
     * @param context 运行上下文
     * @param businessRule 业务规则
     * @param docCategory 单据类别
     * @param docId 单据ID
     * @return 验证结果
     */
    @Override
    public boolean validateBusinessRule(IRequest context, FndBusinessRule businessRule, String docCategory,
                    String docId) {
        // 获取权限规则参数
        List<FndBusinessRuleDetail> businessRuleDetailList = self().getBusinessRuleDetailList(context, businessRule);

        //
        // 如果当前权限规则没有对应的规则明细，则返回false
        // ------------------------------------------------------------------------------
        if (businessRuleDetailList == null || businessRuleDetailList.size() == 0) {
            return false;
        }

        // 获取权限规则明细使用到的所有参数<parameterID,FndBusinessRuleParameter>
        HashMap<Long, FndBusinessRuleParameter> businessRuleParameterMap =
                        self().getBusinessRuleParameterMap(context, businessRuleDetailList);
        // 将行和分配行的id_field_name字段实例化(根据业务表的数据量决定)
        List<HashMap> sqlParamList =
                        self().getSqlParameter(context, businessRule, businessRuleDetailList, docCategory, docId);
        if (sqlParamList != null) {
            for (HashMap sqlParam : sqlParamList) {
                StringBuffer resultScript = new StringBuffer("true");
                Boolean singleResult = null;
                Set<String> tableKeySet = sqlParam.keySet();
                for (FndBusinessRuleDetail businessRuleDetail : businessRuleDetailList) {
                    singleResult = null;
                    if (businessRuleDetail.getParameterId() != null) {
                        FndBusinessRuleParameter businessRuleParameter =
                                        businessRuleParameterMap.get(businessRuleDetail.getParameterId());
                        String sqlContent = businessRuleParameter.getSqlContent();
                        for (String tableKey : tableKeySet) {
                            sqlContent = sqlContent.replaceAll("#\\{" + tableKey + "\\}",
                                            sqlParam.get(tableKey).toString());
                        }
                        logger.debug(new StringBuffer("BusinessRuleId:" + businessRuleDetail.getBusinessRuleId()
                                        + ",BusinessRuleDetailId:" + businessRuleDetail.getDetailId() + ",ParameterId:"
                                        + businessRuleParameter.getParameterId() + "DocCategory:" + docCategory
                                        + ",DocId:" + docId + ",SqlContent:").append(sqlContent).toString());
                        List<HashMap> conditionValueList = ruleEngineMapper.getConditionValue(sqlContent);
                        singleResult = validateSingleDetail(context, businessRuleDetail, conditionValueList,
                                        businessRuleParameter.getDataType());
                        logger.debug(new StringBuffer("Validate result:").append(singleResult).toString());
                    }

                    // and_or信息添加
                    if (FndBusinessRuleDetail.LOGICAL_AND.equals(businessRuleDetail.getAndOr())) {
                        resultScript.append(" &&");
                    } else if (FndBusinessRuleDetail.LOGICAL_OR.equals(businessRuleDetail.getAndOr())) {
                        resultScript.append(" ||");
                    }

                    // 左括号添加
                    if (FndBusinessRuleDetail.PARENTHESIS_LEFT.equals(businessRuleDetail.getLeftParenthesis())) {
                        resultScript.append(" (");
                    }

                    // 判断结果添加
                    if (singleResult != null) {
                        resultScript.append(" " + singleResult.booleanValue());
                    }

                    // 右括号添加
                    if (FndBusinessRuleDetail.PARENTHESIS_RIGHT.equals(businessRuleDetail.getRightParenthesis())) {
                        resultScript.append(" )");
                    }

                }

                ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
                try {
                    logger.debug(new StringBuffer("ResultScript:").append(resultScript).toString());
                    Object resultObj = engine.eval(resultScript.toString());
                    if (resultObj != null) {
                        if (!((Boolean) resultObj).booleanValue()) {
                            return false;
                        }
                    }
                } catch (ScriptException se) {
                    throw new RuntimeException(se);
                }
            }
        }
        return true;
    }

    @Override
    public boolean validateBusinessRule(IRequest context, Long businessRuleId, String docCategory, String docId) {
        FndBusinessRule businessRule = new FndBusinessRule();
        businessRule.setBusinessRuleId(businessRuleId);
        businessRule = fndBusinessRuleService.selectByPrimaryKey(context, businessRule);
        return self().validateBusinessRule(context, businessRule, docCategory, docId);
    }
}


