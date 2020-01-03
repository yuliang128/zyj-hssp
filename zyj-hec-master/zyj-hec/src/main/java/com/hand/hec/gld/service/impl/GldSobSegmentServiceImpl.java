package com.hand.hec.gld.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.lock.components.DatabaseLockProvider;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.*;
import com.hand.hec.gld.mapper.*;
import com.hand.hec.gld.service.IGldSobSegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldSobSegmentServiceImpl extends BaseServiceImpl<GldSobSegment> implements IGldSobSegmentService {
    @Autowired
    private GldSobSegmentMapper gldSobSegmentMapper;

    @Autowired
    private GldSobRuleSegmentMapper gldSobRuleSegmentMapper;

    @Autowired
    private GldSegmentSourceMapper gldSegmentSourceMapper;

    @Autowired
    private GldSobRuleGroupMapper gldSobRuleGroupMapper;

    @Autowired
    private GldSobRuleMapper gldSobRuleMapper;

    @Autowired
    private DatabaseLockProvider databaseLockProvider;

    /**
     * 通过class类型获取获取对应类型的值
     *
     * @param typeClass class类型
     * @param value 值
     * @return Object
     */
    private static Object getClassTypeValue(Class<?> typeClass, Object value) {
        if (typeClass == int.class || value instanceof Integer) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == short.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == byte.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == double.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == long.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == String.class) {
            if (null == value) {
                return "";
            }
            return value;
        } else if (typeClass == boolean.class) {
            if (null == value) {
                return true;
            }
            return value;
        } else if (typeClass == BigDecimal.class) {
            if (null == value) {
                return new BigDecimal(0);
            }
            return new BigDecimal(value + "");
        } else {
            return typeClass.cast(value);
        }
    }

    /**
     * 根据属性，拿到set方法，并把值set到对象中
     *
     * @param obj 对象
     * @param clazz 对象的class
     * @param filedName 需要设置值得属性
     * @param typeClass
     * @param value
     */
    public static void setValue(Object obj, Class<?> clazz, String filedName, Class<?> typeClass, Object value) {
        String methodName = "set" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
        try {
            Method method = clazz.getDeclaredMethod(methodName, new Class[] {typeClass});
            method.invoke(obj, new Object[] {getClassTypeValue(typeClass, value)});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * TODO
     * 
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/20 17:10
     * @param ruleType 规则类型
     * @param jeLineId 凭证行Id
     * @Version 1.0
     **/
    public void lockTable(String ruleType, Long jeLineId) {
        String tableName = "";
        switch (ruleType) {
            case "EXP_REPORT":
                tableName = "gld_sob_segment gs,gld_segment_value gsv,gld_segment_source gss,gld_sob_rule_account gra,gld_sob_rule_segment grs,gld_sob_rule gir,gld_account ga,exp_report_account era";
                StringBuilder erWhereConditionSB = new StringBuilder();
                erWhereConditionSB.append("era.exp_report_je_line_id = ").append(jeLineId)
                                .append(" and ga.account_id = era.account_id ")
                                .append(" and gra.account_id = era.account_id ")
                                .append(" and gra.rule_id = gir.rule_id ").append(" and grs.rule_id = gra.rule_id ")
                                .append(" and gs.segment_id = grs.segment_id ")
                                .append(" and gsv.segment_id = gs.segment_id ")
                                .append(" and gss.segment_id = gs.segment_id");
                databaseLockProvider.lock(tableName, erWhereConditionSB.toString(), null);
                break;
            case "CSH_WRITE_OFF":
                tableName = "gld_sob_segment gs,gld_segment_value gsv,gld_segment_source gss,gld_sob_rule_account gra,gld_sob_rule_segment grs,gld_sob_rule gir,gld_account ga,csh_write_off_account cwo";
                StringBuilder cwoWhereConditionSB = new StringBuilder();
                cwoWhereConditionSB.append("cwo.write_off_je_line_id = ").append(jeLineId)
                                .append(" and ga.account_id = cwo.account_id ")
                                .append(" and gra.account_id = cwo.account_id ")
                                .append(" and gra.rule_id = gir.rule_id ").append(" and grs.rule_id = gra.rule_id ")
                                .append(" and gs.segment_id = grs.segment_id ")
                                .append(" and gsv.segment_id = gs.segment_id ")
                                .append(" and gss.segment_id = gs.segment_id");
                databaseLockProvider.lock(tableName, cwoWhereConditionSB.toString(), null);
                break;
            case "CSH_TRANSACTION":
                tableName = "gld_sob_segment gs,gld_segment_value gsv,gld_segment_source gss,gld_sob_rule_account gra,gld_sob_rule_segment grs,gld_sob_rule gir,gld_account ga,csh_transaction_account cta";
                StringBuilder ctwhereConditionSB = new StringBuilder();
                ctwhereConditionSB.append("cta.transaction_je_line_id = ").append(jeLineId)
                                .append(" and ga.account_id = cta.account_id ")
                                .append(" and gra.account_id = cta.account_id ")
                                .append(" and gra.rule_id = gir.rule_id ").append(" and grs.rule_id = gra.rule_id ")
                                .append(" and gs.segment_id = grs.segment_id ")
                                .append(" and gsv.segment_id = gs.segment_id ")
                                .append(" and gss.segment_id = gs.segment_id");
                databaseLockProvider.lock(tableName, ctwhereConditionSB.toString(), null);
                break;
            case "WORK_ORDER":
                tableName = "gld_sob_segment gs,gld_segment_value gsv,gld_segment_source gss,gld_sob_rule_account gra,gld_sob_rule_segment grs,gld_sob_rule gir,gld_account ga,gld_work_order_account era";
                StringBuilder wowhereConditionSB = new StringBuilder();
                wowhereConditionSB.append("era.work_order_je_line_id = ").append(jeLineId)
                                .append(" and ga.account_id = era.account_id ")
                                .append(" and gra.account_id = era.account_id ")
                                .append(" and gra.rule_id = gir.rule_id ").append(" and grs.rule_id = gra.rule_id ")
                                .append(" and gs.segment_id = grs.segment_id ")
                                .append(" and gsv.segment_id = gs.segment_id ")
                                .append(" and gss.segment_id = gs.segment_id");
                databaseLockProvider.lock(tableName, wowhereConditionSB.toString(), null);
                break;
            default:
                break;
        }
    }

    /**
     * 通过sql获取段值
     * 
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/21 10:58
     * @param sqlQuery 自定义sql
     * @param ruleType 规则类型
     * @param jeLineId 凭证行Id
     * @param docHeaderId 头表Id
     * @param docLineId 行表Id
     * @param docDistId 分配行表Id
     * @return String
     * @Version 1.0
     **/
    public String getSegmentSqlValue(String sqlQuery, String ruleType, Long jeLineId, Long docHeaderId, Long docLineId,
                    Long docDistId) {
        String value = "";
        if(sqlQuery != null) {
            sqlQuery.replace(":RULE_TYPE", ruleType).replace(":JE_LINE_ID", jeLineId.toString())
                    .replace(":DOC_HEADER_ID", docHeaderId.toString()).replace(":DOC_LINE_ID", docLineId.toString())
                    .replace(":DOC_DIST_ID", docDistId.toString());
            value = gldSobSegmentMapper.executeSql(sqlQuery);
        }
        return value;
    }

    /**
     * 通过取值来源获取段值
     * 
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/21 11:17
     * @param ruleType 规则类型
     * @param jeLineId 凭证行Id
     * @param docHeaderId 头表Id
     * @param docLineId 行表Id
     * @param docDistId 分配行表Id
     * @return String
     * @Version 1.0
     **/
    public String getSegmentSourceValue(String ruleType, Long segmentId, Long jeLineId, Long docHeaderId,
                    Long docLineId, Long docDistId, Long docPmtLineId) {
        String value = "";
        List<GldSegmentSource> gldSegmentSources = new ArrayList<>();
        gldSegmentSources = gldSegmentSourceMapper.getSegmentSource(segmentId);
        if(gldSegmentSources != null) {
            if(!gldSegmentSources.isEmpty()) {
                for(GldSegmentSource gldSegmentSource : gldSegmentSources) {
                    String value1 = "";
                    switch (gldSegmentSource.getValueTable()) {
                        case "EXP_REPORT_HEADER_CODE_V":
                            if(GlAccountEntry.RULE_TYPE_EXP_REPORT.equals(ruleType)) {
                                StringBuilder sqlExpHeaderSB = new StringBuilder();
                                sqlExpHeaderSB.append("select ").append(gldSegmentSource.getValueItem())
                                        .append(" from exp_report_header_code_v h where h.exp_report_header_id = ")
                                        .append(docHeaderId);
                                value1 = gldSobSegmentMapper.executeSql(sqlExpHeaderSB.toString());
                                if (value1 != null) {
                                    if (value1 != "") {
                                        value = value1;
                                    }
                                }
                            }
                            break;
                        case "EXP_REPORT_LINE_CODE_V":
                            if(GlAccountEntry.RULE_TYPE_EXP_REPORT.equals(ruleType)) {
                                StringBuilder sqlExpLineSB = new StringBuilder();
                                sqlExpLineSB.append("select ").append(gldSegmentSource.getValueItem())
                                        .append(" from exp_report_line_code_v l where l.exp_report_line_id = ")
                                        .append(docLineId);
                                value1 = gldSobSegmentMapper.executeSql(sqlExpLineSB.toString());
                                if (value1 != null) {
                                    if (value1 != "") {
                                        value = value1;
                                    }
                                }
                            }
                            break;
                        case "EXP_REPORT_DIST_CODE_V":
                            if(GlAccountEntry.RULE_TYPE_EXP_REPORT.equals(ruleType)) {
                                StringBuilder sqlExpDistSB = new StringBuilder();
                                sqlExpDistSB.append("select ").append(gldSegmentSource.getValueItem())
                                        .append(" from exp_report_dist_code_v d where d.exp_report_dist_id = ")
                                        .append(docDistId);
                                value1 = gldSobSegmentMapper.executeSql(sqlExpDistSB.toString());
                                if (value1 != null) {
                                    if (value1 != "") {
                                        value = value1;
                                    }
                                }
                            }
                            break;
                        case "EXP_REPORT_ACCOUNT_CODE_V":
                            if(GlAccountEntry.RULE_TYPE_EXP_REPORT.equals(ruleType)) {
                                StringBuilder sqlExpAccountSB = new StringBuilder();
                                sqlExpAccountSB.append("select ").append(gldSegmentSource.getValueItem())
                                        .append(" from exp_report_account_code_v l where l.exp_report_je_line_id = ")
                                        .append(jeLineId);
                                value1 = gldSobSegmentMapper.executeSql(sqlExpAccountSB.toString());
                                if (value1 != null) {
                                    if (value1 != "") {
                                        value = value1;
                                    }
                                }
                            }
                            break;
                        case "EXP_RPT_PMT_SCHEDULE_CODE_V":
                            if(GlAccountEntry.RULE_TYPE_EXP_REPORT.equals(ruleType)) {
                                StringBuilder sqlExpPmtSB = new StringBuilder();
                                sqlExpPmtSB.append("select ").append(gldSegmentSource.getValueItem())
                                        .append(" from exp_rpt_pmt_schedule_code_v v where v.payment_schedule_line_id = ")
                                        .append(docPmtLineId);
                                value1 = gldSobSegmentMapper.executeSql(sqlExpPmtSB.toString());
                                if (value1 != null) {
                                    if (value1 != "") {
                                        value = value1;
                                    }
                                }
                            }
                            break;
                        case "CSH_TRANSACTION_HEADER_CODE_V":
                            if(GlAccountEntry.RULE_TYPE_CSH_TRANSACTION.equals(ruleType)) {
                                StringBuilder sqlCshTransHeaderSB = new StringBuilder();
                                sqlCshTransHeaderSB.append("select ").append(gldSegmentSource.getValueItem())
                                        .append(" from csh_transaction_header_code_v h where h.transaction_header_id = ")
                                        .append(docHeaderId);
                                value1 = gldSobSegmentMapper.executeSql(sqlCshTransHeaderSB.toString());
                                if (value1 != null) {
                                    if (value1 != "") {
                                        value = value1;
                                    }
                                }
                            }
                            break;
                        case "CSH_TRANSACTION_LINE_CODE_V":
                            if(GlAccountEntry.RULE_TYPE_CSH_TRANSACTION.equals(ruleType)) {
                                StringBuilder sqlCshTransLineSB = new StringBuilder();
                                sqlCshTransLineSB.append("select ").append(gldSegmentSource.getValueItem())
                                        .append(" from csh_transaction_line_code_v l where l.transaction_line_id = ")
                                        .append(docLineId);
                                value1 = gldSobSegmentMapper.executeSql(sqlCshTransLineSB.toString());
                                if (value1 != null) {
                                    if (value1 != "") {
                                        value = value1;
                                    }
                                }
                            }
                            break;
                        case "CSH_TRANSACTION_DIST_CODE_V":
                            if(GlAccountEntry.RULE_TYPE_CSH_TRANSACTION.equals(ruleType)) {
                                StringBuilder sqlCshTransDistSB = new StringBuilder();
                                sqlCshTransDistSB.append("select ").append(gldSegmentSource.getValueItem())
                                        .append(" from csh_transaction_dist_code_v d where d.distribution_line_id = ")
                                        .append(docDistId);
                                value1 = gldSobSegmentMapper.executeSql(sqlCshTransDistSB.toString());
                                if (value1 != null) {
                                    if (value1 != "") {
                                        value = value1;
                                    }
                                }
                            }
                            break;
                        case "CSH_TRANSACTION_ACC_CODE_V":
                            if(GlAccountEntry.RULE_TYPE_CSH_TRANSACTION.equals(ruleType)) {
                                StringBuilder sqlCshTransAccSB = new StringBuilder();
                                sqlCshTransAccSB.append("select ").append(gldSegmentSource.getValueItem())
                                        .append(" from csh_transaction_acc_code_v l where l.transaction_je_line_id = ")
                                        .append(jeLineId);
                                value1 = gldSobSegmentMapper.executeSql(sqlCshTransAccSB.toString());
                                if (value1 != null) {
                                    if (value1 != "") {
                                        value = value1;
                                    }
                                }
                            }
                            break;
                        case "CSH_WRITE_OFF_ACCOUNT_CODE_V":
                            if(GlAccountEntry.RULE_TYPE_CSH_WRITE_OFF.equals(ruleType)) {
                                StringBuilder sqlCwoSB = new StringBuilder();
                                sqlCwoSB.append("select ").append(gldSegmentSource.getValueItem())
                                        .append(" from csh_write_off_account_code_v l where l.write_off_je_line_id = ")
                                        .append(jeLineId);
                                value1 = gldSobSegmentMapper.executeSql(sqlCwoSB.toString());
                                if (value1 != null) {
                                    if (value1 != "") {
                                        value = value1;
                                    }
                                }
                            }
                            break;
                        case "CSH_PMT_REQ_HEADERS_CODE_V":
                            if(GlAccountEntry.RULE_TYPE_CSH_PMT_REQ.equals(ruleType)) {
                                StringBuilder sqlCprHeaderSB = new StringBuilder();
                                sqlCprHeaderSB.append("select ").append(gldSegmentSource.getValueItem())
                                        .append(" from csh_pmt_req_header_code_v h where h.payment_requisition_header_id = ")
                                        .append(docHeaderId);
                                value1 = gldSobSegmentMapper.executeSql(sqlCprHeaderSB.toString());
                                if (value1 != null) {
                                    if (value1 != "") {
                                        value = value1;
                                    }
                                }
                            }
                            break;
                        case "CSH_PMT_REQ_LINES_CODE_V":
                            if(GlAccountEntry.RULE_TYPE_CSH_PMT_REQ.equals(ruleType)) {
                                StringBuilder sqlCprLineSB = new StringBuilder();
                                sqlCprLineSB.append("select ").append(gldSegmentSource.getValueItem())
                                        .append(" from csh_pmt_req_line_code_v l where l.payment_requisition_line_id = ")
                                        .append(docLineId);
                                value1 = gldSobSegmentMapper.executeSql(sqlCprLineSB.toString());
                                if (value1 != null) {
                                    if (value1 != "") {
                                        value = value1;
                                    }
                                }
                            }
                            break;
                        case "CSH_PAY_REQ_ACCOUNT_CODE_V":
                            if(GlAccountEntry.RULE_TYPE_CSH_PMT_REQ.equals(ruleType)) {
                                StringBuilder sqlCprAccSB = new StringBuilder();
                                sqlCprAccSB.append("select ").append(gldSegmentSource.getValueItem())
                                        .append(" from csh_pay_req_account_code_v l where l.payment_req_je_line_id = ")
                                        .append(jeLineId);
                                value1 = gldSobSegmentMapper.executeSql(sqlCprAccSB.toString());
                                if (value1 != null) {
                                    if (value1 != "") {
                                        value = value1;
                                    }
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
		
        return value;
		
    }

    /**
     * 获取段值
     * 
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/20 17:19
     * @param gldSegment 通用匹配项dto
     * @param ruleId 规则明细Id
     * @return String
     * @Version 1.0
     **/
    public String getSegmentValue(GldSobSegment gldSobSegment, Long ruleId, String ruleType, Long jeLineId,
                    Long docHeaderId, Long docLineId, Long docDistId, Long docPmtLineId) {
        String value = "";
        if(gldSobSegment != null) {
            GldSobRuleSegment gldSobRuleSegment = new GldSobRuleSegment();
            gldSobRuleSegment = gldSobRuleSegmentMapper.getRuleSegmentInfo(ruleId, gldSobSegment.getSegmentId());
            if (gldSobRuleSegment != null) {
                if (GldSobRuleSegment.DEFAULT_SOURCE_CODE.equals(gldSobRuleSegment.getSourceCode())) {
                    value = gldSobRuleSegment.getDefaultValue();
                } else {
                    switch (gldSobSegment.getSegmentType()) {
                        case "TEXT":
                            value = gldSobSegment.getDefaultText();
                            break;
                        case "VALUE_LIST":
                            value = gldSobSegment.getDefaultText();
                            break;
                        case "SQL_VALUE_LIST":
                            value = gldSobSegment.getDefaultText();
                            break;
                        case "SQL":
                            value = getSegmentSqlValue(gldSobSegment.getSqlQuery(), ruleType, jeLineId, docHeaderId, docLineId,
                                    docDistId);
                            if (value == null || "".equals(value)) {
                                value = gldSobSegment.getDefaultText();
                            }
                            break;
                        case "VALUE_SOURCE":
                            value = getSegmentSourceValue(ruleType, gldSobSegment.getSegmentId(), jeLineId, docHeaderId,
                                    docLineId, docDistId, docPmtLineId);
                            if (value == null || "".equals(value)) {
                                value = gldSobSegment.getDefaultText();
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return value;
    }

    /**
     * 设置匹配项段值
     * 
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/20 17:19
     * @param gldSegment 通用匹配项dto
     * @param ruleId 规则明细Id
     * @return GldSegment
     * @Version 1.0
     **/
    public GldSegment setSegmentValue(Long ruleId, Long setOfBooksId, String drCrFlag, String ruleType, Long jeLineId,
                    Long docHeaderId, Long docLineId, Long docDistId, Long docPmtLineId) {
        GldSegment gldSegment = new GldSegment();
        int j = 20;
        for (int i = 1; i <= j; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("segment").append(i);
            // 获取字段对应的record
            GldSobSegment gldSobSegment = gldSobSegmentMapper.selectSegInfo(sb.toString(), drCrFlag, setOfBooksId);
            String value = getSegmentValue(gldSobSegment, ruleId, ruleType, jeLineId, docHeaderId, docLineId, docDistId,
                            docPmtLineId);
            try {
                setValue(gldSegment, gldSegment.getClass(), sb.toString(),
                                GldSegment.class.getDeclaredField(sb.toString()).getType(), value);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("设置字段值失败，请检查是否存在对应的SET方法！");
            }
        }
        return gldSegment;
    }

    @Override
    public GldSegment mappingSegmentValue(Long accEntityId, Long setOfBooksId, Long accountId, String ruleType,
                    Long jeLineId, String drCrFlag, Long docHeaderId, Long docLineId, Long docDistId,
                    Long docPmtLineId) {
        // 获取会计规则组ID
        Long ruleGroupId = 0L;
        ruleGroupId = gldSobRuleGroupMapper.getRuleGroupId(setOfBooksId, accEntityId);
        // 获取会计规则明细ID
        Long ruleId = 0L;
        ruleId = gldSobRuleMapper.getRuleId(ruleGroupId, accountId);
        // 锁相关表
        lockTable(ruleType, jeLineId);
        GldSegment gldSegment =  new GldSegment();
        gldSegment = setSegmentValue(ruleId, setOfBooksId, drCrFlag, ruleType, jeLineId, docHeaderId, docLineId, docDistId,
                docPmtLineId);
        return gldSegment;
    }

    @Override
    public List<Map> querySegmentDesc(IRequest request) {
        return gldSobSegmentMapper.querySegmentDesc();
    }
}
