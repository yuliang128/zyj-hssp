package com.hand.hec.gld.service.impl;

import com.hand.hap.code.rule.service.ISysCodeRuleProcessService;
import com.hand.hap.core.IRequest;
import com.hand.hap.generator.service.impl.FileUtil;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldMappingCondGrpHd;
import com.hand.hec.gld.dto.GldMappingCondGrpLn;
import com.hand.hec.gld.dto.GldMappingConditionSql;
import com.hand.hec.gld.dto.GldUsageCodeCatalog;
import com.hand.hec.gld.mapper.GldMappingCondGrpHdMapper;
import com.hand.hec.gld.mapper.GldMappingCondGrpLnMapper;
import com.hand.hec.gld.mapper.GldMappingConditionSqlMapper;
import com.hand.hec.gld.mapper.GldUsageCodeCatalogMapper;
import com.hand.hec.gld.service.IGldMappingCondGrpHdService;
import com.hand.hec.gld.service.IGldMappingCondGrpLnService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
// @Transactional(rollbackFor = Exception.class)
public class GldMappingCondGrpHdServiceImpl extends BaseServiceImpl<GldMappingCondGrpHd>
                implements IGldMappingCondGrpHdService {
    @Autowired
    private GldMappingCondGrpHdMapper gldMappingCondGrpHdMapper;

    @Autowired
    private GldUsageCodeCatalogMapper gldUsageCodeCatalogMapper;

    @Autowired
    private GldMappingConditionSqlMapper gldMappingConditionSqlMapper;

    @Autowired
    private GldMappingCondGrpLnMapper gldMappingCondGrpLnMapper;

    @Autowired
    private ISysCodeRuleProcessService codeRuleProcessService;

    @Autowired
    private IGldMappingCondGrpLnService gldMappingCondGrpLnService;

    @Autowired
    private IGldMappingCondGrpHdService gldMappingCondGrpHdService;

    @Value("${db.type}")
    private String dbType;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @Description 校验优先级的合法性, 如果校验通过则返回对应的优先级的值
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/1/18 13:57
     * @Param gldMappingCondGrpHd 匹配组头表dto
     * @Version 1.0
     **/
    public Long checkPriority(GldMappingCondGrpHd gldMappingCondGrpHd) {
        Long maxPriority = 10L;
        Long upLimitPriority = 999L;
        if (gldMappingCondGrpHd.getPriority() == null || "".equals(gldMappingCondGrpHd.getPriority())) {
            maxPriority += gldMappingCondGrpHdMapper.getMaxPriority(gldMappingCondGrpHd.getUsageCode());
            if (maxPriority.longValue() >= upLimitPriority.longValue()) {
                maxPriority -= 9L;
                if (maxPriority.longValue() >= upLimitPriority.longValue()) {
                    throw new RuntimeException("自动产生优先级编号超出范围,请手工输入优先级!");
                }
            }
        } else {
            maxPriority = gldMappingCondGrpHd.getPriority();
            if (maxPriority.longValue() == upLimitPriority.longValue()) {
                throw new RuntimeException("优先级最大为998,优先级999已经为系统保留,为通用匹配组使用!");
            } else if (maxPriority.longValue() > upLimitPriority.longValue()) {
                throw new RuntimeException("优先级最大为998，请重新输入其他的优先级!");
            } else {
                if (gldMappingCondGrpHdMapper.checkUnique(gldMappingCondGrpHd.getUsageCode(), maxPriority) > 0) {
                    throw new RuntimeException("优先级冲突,请重新输入其他的优先级!");
                }
            }
        }
        return maxPriority;
    }

    /**
     * @Description 拼接创建用途代码对应匹配组对应的表， 同时将记录插入匹配组行表
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/1/18 15:30
     * @Param tableName 表名
     * @Param mappingConditionCodes 匹配项名称*号隔开
     * @Param usageCode 用途代码编码
     * @Version 1.0
     **/
    public String createTableSql(IRequest iRequest, String tableName, String mappingConditionCodes, String usageCode,
                    GldMappingCondGrpHd gldMappingCondGrpHd, String description,
                    List<GldMappingCondGrpLn> gldMappingCondGrpLns) {
        try {
            // 建表sql
            StringBuilder sqlSB = new StringBuilder();
            // 匹配组描述
            StringBuilder mappingGroupDescSB = new StringBuilder();
            // 建表对应的主键字段
            StringBuilder primaryKeySB = new StringBuilder();
            String[] mappingConditionCodeArray = mappingConditionCodes.split("\\*");
            // 建表语句
            switch (dbType) {
                case "mysql":
                    sqlSB.append("create table ").append(tableName).append("(`LINE_ID`  bigint(20) not null, ");
                    for (int i = 0; i < mappingConditionCodeArray.length; i++) {
                        // 获取用途代码目录
                        GldUsageCodeCatalog gldUsageCodeCatalog = gldUsageCodeCatalogMapper
                                        .selectUsageCodeCataLog(mappingConditionCodeArray[i], usageCode);
                        if (gldUsageCodeCatalog != null) {
                            mappingGroupDescSB.append(mappingConditionCodeArray[i]);
                            primaryKeySB.append("`").append(mappingConditionCodeArray[i]).append("`,");
                            // 匹配组行表dto
                            GldMappingCondGrpLn gldMappingCondGrpLn = new GldMappingCondGrpLn();
                            // 获取匹配项明细
                            GldMappingConditionSql gldMappingConditionSql = gldMappingConditionSqlMapper
                                            .selectMappingConditionDetail(mappingConditionCodeArray[i]);
                            if (gldMappingConditionSql.getRefIdField() != null
                                            && !"".equals(gldMappingConditionSql.getRefIdField())) {
                                // 获取字段的参数类型
                                String dataType = gldMappingConditionSqlMapper.getDataType(
                                                gldMappingConditionSql.getRefTable(),
                                                gldMappingConditionSql.getRefField(), dbType);
                                // 如果未获取到参数类型则报错
                                if (dataType == null || "".equals(dataType)) {
                                    throw new RuntimeException("未获取到关联字段的字段类型!");
                                } else {
                                    sqlSB.append("`").append(mappingConditionCodeArray[i])
                                                    .append("`  bigint(20) not null, `")
                                                    .append(mappingConditionCodeArray[i]).append("_C` ")
                                                    .append(dataType).append(" not null, ");
                                }
                            } else {
                                // 获取字段的参数类型
                                String dataType = gldMappingConditionSqlMapper.getDataType(
                                                gldMappingConditionSql.getRefTable(),
                                                gldMappingConditionSql.getRefField(), dbType);
                                // 如果未获取到参数类型则报错
                                if (dataType == null || "".equals(dataType)) {
                                    throw new RuntimeException("未获取到关联字段的字段类型!");
                                } else {
                                    sqlSB.append("`").append(mappingConditionCodeArray[i]).append("` ").append(dataType)
                                                    .append(" not null, ");
                                }
                            }
                            gldMappingCondGrpLn.setGroupName(gldMappingCondGrpHd.getGroupName());
                            gldMappingCondGrpLn.setLayoutPriority(gldUsageCodeCatalog.getLayoutPriority());
                            gldMappingCondGrpLn.setMappingConditionCode(mappingConditionCodeArray[i]);
                            gldMappingCondGrpLn.setCreatedBy(iRequest.getUserId());
                            gldMappingCondGrpLn.setCreationDate(new Date());
                            gldMappingCondGrpLn.setLastUpdatedBy(iRequest.getUserId());
                            gldMappingCondGrpLn.setLastUpdateDate(new Date());
                            gldMappingCondGrpLn.setRequestId(-1L);
                            gldMappingCondGrpLn.setProgramId(-1L);
                            gldMappingCondGrpLn.setLastUpdateLogin(-1L);
                            gldMappingCondGrpLns.add(gldMappingCondGrpLn);
                        }
                    }
                    // 拼接后续建表语句
                    sqlSB.append("`ACCOUNT_ID`  bigint(20) not null,`ACCOUNT_CODE`  varchar(100) not null,`MAG_ORG_ID`  bigint(20) not null,")
                                    .append("`SET_OF_BOOKS_ID` bigint(20) not null,`CREATION_DATE`  date not null,`CREATED_BY` bigint(20) not null,")
                                    .append("`LAST_UPDATE_DATE` date not null,`LAST_UPDATED_BY`  bigint(20) not null,")
                                    .append("PRIMARY KEY (`MAG_ORG_ID`,`SET_OF_BOOKS_ID`,")
                                    .append(primaryKeySB.substring(0, primaryKeySB.length() - 1))
                                    .append(")) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci");
                    if (description == null || "".equals(description)) {
                        gldMappingCondGrpHd.setDescription(mappingGroupDescSB.toString());
                    }
                    break;
                case "sqlserver":
                    sqlSB.append("create table ").append(tableName).append("(LINE_ID  int not null, ");
                    for (int i = 0; i < mappingConditionCodeArray.length; i++) {
                        // 获取用途代码目录
                        GldUsageCodeCatalog gldUsageCodeCatalog = gldUsageCodeCatalogMapper
                                        .selectUsageCodeCataLog(mappingConditionCodeArray[i], usageCode);
                        if (gldUsageCodeCatalog != null) {
                            mappingGroupDescSB.append(mappingConditionCodeArray[i]);
                            primaryKeySB.append(mappingConditionCodeArray[i]).append(",");
                            // 匹配组行表dto
                            GldMappingCondGrpLn gldMappingCondGrpLn = new GldMappingCondGrpLn();
                            // 获取匹配项明细
                            GldMappingConditionSql gldMappingConditionSql = gldMappingConditionSqlMapper
                                            .selectMappingConditionDetail(mappingConditionCodeArray[i]);
                            if (gldMappingConditionSql.getRefIdField() != null
                                            && !"".equals(gldMappingConditionSql.getRefIdField())) {
                                // 获取字段的参数类型
                                String dataType = gldMappingConditionSqlMapper.getDataType(
                                                gldMappingConditionSql.getRefTable(),
                                                gldMappingConditionSql.getRefField(), dbType);
                                // 如果未获取到参数类型则报错
                                if (dataType == null || "".equals(dataType)) {
                                    throw new RuntimeException("未获取到关联字段的字段类型!");
                                } else {
                                    sqlSB.append(mappingConditionCodeArray[i]).append("  int not null, ")
                                                    .append(mappingConditionCodeArray[i]).append("_C ").append(dataType)
                                                    .append(" not null, ");
                                }
                            } else {
                                // 获取字段的参数类型
                                String dataType = gldMappingConditionSqlMapper.getDataType(
                                                gldMappingConditionSql.getRefTable(),
                                                gldMappingConditionSql.getRefField(), dbType);
                                // 如果未获取到参数类型则报错
                                if (dataType == null || "".equals(dataType)) {
                                    throw new RuntimeException("未获取到关联字段的字段类型!");
                                } else {
                                    sqlSB.append(mappingConditionCodeArray[i]).append(" ").append(dataType)
                                                    .append(" not null, ");
                                }
                            }
                            gldMappingCondGrpLn.setGroupName(gldMappingCondGrpHd.getGroupName());
                            gldMappingCondGrpLn.setLayoutPriority(gldUsageCodeCatalog.getLayoutPriority());
                            gldMappingCondGrpLn.setMappingConditionCode(mappingConditionCodeArray[i]);
                            gldMappingCondGrpLn.setCreatedBy(iRequest.getUserId());
                            gldMappingCondGrpLn.setCreationDate(new Date());
                            gldMappingCondGrpLn.setLastUpdatedBy(iRequest.getUserId());
                            gldMappingCondGrpLn.setLastUpdateDate(new Date());
                            gldMappingCondGrpLn.setRequestId(-1L);
                            gldMappingCondGrpLn.setProgramId(-1L);
                            gldMappingCondGrpLn.setLastUpdateLogin(-1L);
                            gldMappingCondGrpLns.add(gldMappingCondGrpLn);
                        }
                    }
                    // 拼接后续建表语句
                    sqlSB.append("ACCOUNT_ID  int not null,ACCOUNT_CODE  varchar2(100) not null,MAG_ORG_ID  int not null,")
                                    .append("SET_OF_BOOKS_ID int not null,CREATION_DATE  date not null,CREATED_BY int not null,")
                                    .append("LAST_UPDATE_DATE date not null,LAST_UPDATED_BY int not null,")
                                    .append("PRIMARY KEY (MAG_ORG_ID,SET_OF_BOOKS_ID,")
                                    .append(primaryKeySB.substring(0, primaryKeySB.length() - 1)).append("))");
                    if (description == null || "".equals(description)) {
                        gldMappingCondGrpHd.setDescription(mappingGroupDescSB.toString());
                    }
                    break;
                case "oracle":
                    sqlSB.append("create table ").append(tableName).append("(LINE_ID  number not null, ");
                    for (int i = 0; i < mappingConditionCodeArray.length; i++) {
                        // 获取用途代码目录
                        GldUsageCodeCatalog gldUsageCodeCatalog = gldUsageCodeCatalogMapper
                                        .selectUsageCodeCataLog(mappingConditionCodeArray[i], usageCode);
                        if (gldUsageCodeCatalog != null) {
                            mappingGroupDescSB.append(mappingConditionCodeArray[i]);
                            primaryKeySB.append(mappingConditionCodeArray[i]).append(",");
                            // 匹配组行表dto
                            GldMappingCondGrpLn gldMappingCondGrpLn = new GldMappingCondGrpLn();
                            // 获取匹配项明细
                            GldMappingConditionSql gldMappingConditionSql = gldMappingConditionSqlMapper
                                            .selectMappingConditionDetail(mappingConditionCodeArray[i]);
                            if (gldMappingConditionSql.getRefIdField() != null
                                            && !"".equals(gldMappingConditionSql.getRefIdField())) {
                                // 获取字段的参数类型
                                String dataType = gldMappingConditionSqlMapper.getDataType(
                                                gldMappingConditionSql.getRefTable(),
                                                gldMappingConditionSql.getRefField(), dbType);
                                // 如果未获取到参数类型则报错
                                if (dataType == null || "".equals(dataType)) {
                                    throw new RuntimeException("未获取到关联字段的字段类型!");
                                } else {
                                    sqlSB.append(mappingConditionCodeArray[i]).append("  number not null, ")
                                                    .append(mappingConditionCodeArray[i]).append("_C ").append(dataType)
                                                    .append(" not null, ");
                                }
                            } else {
                                // 获取字段的参数类型
                                String dataType = gldMappingConditionSqlMapper.getDataType(
                                                gldMappingConditionSql.getRefTable(),
                                                gldMappingConditionSql.getRefField(), dbType);
                                // 如果未获取到参数类型则报错
                                if (dataType == null || "".equals(dataType)) {
                                    throw new RuntimeException("未获取到关联字段的字段类型!");
                                } else {
                                    sqlSB.append(mappingConditionCodeArray[i]).append(" ").append(dataType)
                                                    .append(" not null, ");
                                }
                            }
                            gldMappingCondGrpLn.setGroupName(gldMappingCondGrpHd.getGroupName());
                            gldMappingCondGrpLn.setLayoutPriority(gldUsageCodeCatalog.getLayoutPriority());
                            gldMappingCondGrpLn.setMappingConditionCode(mappingConditionCodeArray[i]);
                            gldMappingCondGrpLn.setCreatedBy(iRequest.getUserId());
                            gldMappingCondGrpLn.setCreationDate(new Date());
                            gldMappingCondGrpLn.setLastUpdatedBy(iRequest.getUserId());
                            gldMappingCondGrpLn.setLastUpdateDate(new Date());
                            gldMappingCondGrpLn.setRequestId(-1L);
                            gldMappingCondGrpLn.setProgramId(-1L);
                            gldMappingCondGrpLn.setLastUpdateLogin(-1L);
                            gldMappingCondGrpLns.add(gldMappingCondGrpLn);
                        }
                    }
                    // 拼接后续建表语句
                    sqlSB.append("ACCOUNT_ID  number not null,ACCOUNT_CODE  varchar2(100) not null,MAG_ORG_ID  number not null,")
                                    .append("SET_OF_BOOKS_ID number not null,CREATION_DATE  date not null,CREATED_BY number not null,")
                                    .append("LAST_UPDATE_DATE date not null,LAST_UPDATED_BY number not null,")
                                    .append("PRIMARY KEY (MAG_ORG_ID,SET_OF_BOOKS_ID,")
                                    .append(primaryKeySB.substring(0, primaryKeySB.length() - 1)).append("))");
                    if (description == null || "".equals(description)) {
                        gldMappingCondGrpHd.setDescription(mappingGroupDescSB.toString());
                    }
                    break;
                default:
                    break;
            }
            return sqlSB.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public void createMappingGroup(IRequest iRequest, List<GldMappingCondGrpHd> gldMappingCondGrpHds)
                    throws RuntimeException {
        // 获取前台传入的生成匹配中的信息
        GldMappingCondGrpHd gldMappingCondGrpHd = gldMappingCondGrpHds.get(0);
        // 插入表的dto对象
        GldMappingCondGrpHd gldMappingCondGrpHd1 = new GldMappingCondGrpHd();
        gldMappingCondGrpHd1.setGroupName(gldMappingCondGrpHd.getGroupName());
        gldMappingCondGrpHd1.setUsageCode(gldMappingCondGrpHd.getUsageCode());
        gldMappingCondGrpHd1.setDescription(gldMappingCondGrpHd.getDescription());
        gldMappingCondGrpHd1.setSysFlag("N");
        gldMappingCondGrpHd1.setCreatedBy(iRequest.getUserId());
        gldMappingCondGrpHd1.setCreationDate(new Date());
        gldMappingCondGrpHd1.setLastUpdatedBy(iRequest.getUserId());
        gldMappingCondGrpHd1.setLastUpdateDate(new Date());
        gldMappingCondGrpHd1.setRequestId(-1L);
        gldMappingCondGrpHd1.setProgramId(-1L);
        gldMappingCondGrpHd1.setLastUpdateLogin(-1L);
        // 自动创建表的sql语句对象
        StringBuilder createTableSB = new StringBuilder();
        // 表名
        StringBuilder tableNameSB = new StringBuilder();
        Long priority = checkPriority(gldMappingCondGrpHd);
        // 优先级参数校验通过之后set进插表的dto对象
        gldMappingCondGrpHd1.setPriority(priority);
        // 拼接表名
        tableNameSB.append("GLD_ACC_GEN_USER_");
        // 根据编码规则获取表名后缀。避免表名重复报错
        try {
            String ruleCode = codeRuleProcessService.getRuleCode("USAGE_CODE_TABLE_NAME_POSTFIX", null);
            tableNameSB.append(ruleCode.replaceFirst("^0*", ""));
        } catch (Exception e) {
            throw new RuntimeException("获取编码规则[USAGE_CODE_TABLE_NAME_POSTFIX]失败！");
        }
        // set表名字段
        gldMappingCondGrpHd1.setTableName(tableNameSB.toString());
        // 行保存的list
        List<GldMappingCondGrpLn> gldMappingCondGrpLns = new ArrayList<>();
        // 创建表sql
        // 获取匹配项数组
        String mappingConditionCode = gldMappingCondGrpHd.getInnerMap().get("mappingConditionCodes").toString();
        String sql = createTableSql(iRequest, tableNameSB.toString(), mappingConditionCode,
                        gldMappingCondGrpHd.getUsageCode(), gldMappingCondGrpHd1, gldMappingCondGrpHd.getDescription(),
                        gldMappingCondGrpLns);
        if (!"create".equals(sql.substring(0, 6))) {
            throw new RuntimeException(sql);
        }
        createTableSB.append(sql);
        // 插入行表
        try {
            for (GldMappingCondGrpLn gldMappingCondGrpLn : gldMappingCondGrpLns) {
                gldMappingCondGrpLnService.insertSelective(iRequest, gldMappingCondGrpLn);
            }
            // 插入头标
            gldMappingCondGrpHdService.insertSelective(iRequest, gldMappingCondGrpHd1);
        } catch (Exception e) {
            System.out.println(createTableSB.toString());
            e.printStackTrace();
            throw new RuntimeException("插入匹配组头行表失败!");
        }
        try {
            gldMappingCondGrpHdMapper.executeSql(createTableSB.toString());
            // 修改表主键
            switch (dbType) {
                case "mysql":
                    StringBuilder sb = new StringBuilder();
                    sb.append("alter table ").append(tableNameSB.toString()).append(" drop primary key");
                    // 先删除主键
                    gldMappingCondGrpHdMapper.executeSql(sb.toString());
                    StringBuilder sb1 = new StringBuilder();
                    sb1.append("alter table ").append(tableNameSB.toString()).append(" add primary key(`LINE_ID`)");
                    // 再创建表组建
                    gldMappingCondGrpHdMapper.executeSql(sb1.toString());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("alter table ").append(tableNameSB.toString())
                                    .append(" change `LINE_ID` `LINE_ID` bigint(20) not null AUTO_INCREMENT");
                    // 修改主键字段为自增长字段
                    gldMappingCondGrpHdMapper.executeSql(sb2.toString());
                    break;
                case "oracle":
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("alter table ").append(tableNameSB.toString()).append(" add constraint ")
                                    .append(tableNameSB.toString()).append("_pk primary key (LINE_ID)");
                    gldMappingCondGrpHdMapper.executeSql(sb3.toString());
                    break;
                case "sqlserver":
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("alter table ").append(tableNameSB.toString()).append(" drop primary key");
                    // 先删除主键
                    gldMappingCondGrpHdMapper.executeSql(sb4.toString());
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("alter table ").append(tableNameSB.toString()).append(" add primary key(LINE_ID)");
                    // 再创建表组建
                    gldMappingCondGrpHdMapper.executeSql(sb5.toString());
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append("alter table ").append(tableNameSB.toString()).append(" add LINE_ID int identity(1,1)");
                    // 修改主键字段为自增长字段
                    gldMappingCondGrpHdMapper.executeSql(sb6.toString());
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println(createTableSB.toString());
            e.printStackTrace();
            throw new RuntimeException("表格创建失败！");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public List<GldMappingCondGrpHd> deleteMappingGroups(IRequest iRequest,
                    List<GldMappingCondGrpHd> gldMappingCondGrpHds) throws RuntimeException {
        try {
            for (GldMappingCondGrpHd gldMappingCondGrpHd : gldMappingCondGrpHds) {
                StringBuilder sb = new StringBuilder();
                // 删除头表数据
                gldMappingCondGrpHdService.deleteByPrimaryKey(gldMappingCondGrpHd);
                // 查询行数据
                List<GldMappingCondGrpLn> gldMappingCondGrpLns =
                                gldMappingCondGrpLnMapper.selectGroupLn(gldMappingCondGrpHd.getGroupName());
                // 删除行表数据
                if (gldMappingCondGrpLns != null && gldMappingCondGrpLns.size() != 0) {
                    gldMappingCondGrpLnService.batchDelete(gldMappingCondGrpLns);
                }
                // 删除自动创建的表
                sb.append("drop table ").append(gldMappingCondGrpHd.getTableName());
                gldMappingCondGrpHdMapper.executeSql(sb.toString());
            }
            return gldMappingCondGrpHds;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<HashMap<String, Object>> queryUserDy(IRequest iRequest, GldMappingCondGrpHd gldMappingCondGrpHds,
                    HttpSession session) {
        List<HashMap<String, Object>> lists = gldMappingCondGrpHdMapper.queryResult(gldMappingCondGrpHds.getTableName(),
                        gldMappingCondGrpHds.getWhereClause(), gldMappingCondGrpHds.getSetOfBooksId(),
                        gldMappingCondGrpHds.getMagOrgId(), gldMappingCondGrpHds.getAccountCode());
        List<HashMap<String, Object>> lists1 = new ArrayList<>();
        for (HashMap<String, Object> list : lists) {
            HashMap<String, Object> map = new HashMap<>();
            for (String key : list.keySet()) {
                map.put(FileUtil.columnToCamel(key), list.get(key));
            }
            lists1.add(map);
        }
        return lists1;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public void queryUserDySubmit(IRequest iRequest, List<GldMappingCondGrpHd> gldMappingCondGrpHds)
                    throws RuntimeException {
        StringBuilder errorSql = new StringBuilder();
        try {
            for (GldMappingCondGrpHd gldMappingCondGrpHd : gldMappingCondGrpHds) {
                errorSql.setLength(0);
                String[] mappingConditionCodeArray = new String[100];
                String[] mappingConditionCodeValueArray = new String[100];
                if (gldMappingCondGrpHd.getMappingCondition() != null
                                && !"".equals(gldMappingCondGrpHd.getMappingCondition())) {
                    mappingConditionCodeArray = gldMappingCondGrpHd.getMappingCondition().split("\\*");
                }
                if (gldMappingCondGrpHd.getMappingCondition() != null
                                && !"".equals(gldMappingCondGrpHd.getMappingCondition())) {
                    mappingConditionCodeValueArray = gldMappingCondGrpHd.getMappingConditionValue().split("\\*");
                }
                if ("add".equals(gldMappingCondGrpHd.get__status())) {
                    if (gldMappingCondGrpHd.getLineId() == null || "".equals(gldMappingCondGrpHd.getLineId())) {
                        StringBuilder insertSB = new StringBuilder();
                        if ("oracle".equals(dbType)) {
                            insertSB.append("insert into ").append(gldMappingCondGrpHd.getTableName())
                                            .append("(line_id,");
                        } else {
                            insertSB.append("insert into ").append(gldMappingCondGrpHd.getTableName()).append("(");
                        }
                        for (int i = 0; i < mappingConditionCodeArray.length; i++) {
                            if (mappingConditionCodeArray[i] != null && !"".equals(mappingConditionCodeArray[i])) {
                                insertSB.append(mappingConditionCodeArray[i]).append(",");
                            }
                        }
                        insertSB.append("account_id,account_code,mag_org_id,set_of_books_id,creation_date,created_by,last_update_date,last_updated_by) values (");
                        if ("oracle".equals(dbType)) {
                            insertSB.append("(gld_acc_gen_record_s.nextval,");
                        }
                        for (int i = 0; i < mappingConditionCodeValueArray.length; i++) {
                            if (mappingConditionCodeValueArray[i] != null
                                            && !"".equals(mappingConditionCodeValueArray[i])) {
                                insertSB.append("'").append(mappingConditionCodeValueArray[i]).append("',");
                            }
                        }
                        if ("oracle".equals(dbType)) {
                            insertSB.append(gldMappingCondGrpHd.getAccountId()).append(",'")
                                    .append(gldMappingCondGrpHd.getAccountCode()).append("',")
                                    .append(gldMappingCondGrpHd.getMagOrgId()).append(",")
                                    .append(gldMappingCondGrpHd.getSetOfBooksId()).append(",sysdate,")
                                    .append(iRequest.getUserId()).append(",sysdate,")
                                    .append(iRequest.getUserId()).append(")");
                        } else if("mysql".equals(dbType)){
                            insertSB.append(gldMappingCondGrpHd.getAccountId()).append(",'")
                                    .append(gldMappingCondGrpHd.getAccountCode()).append("',")
                                    .append(gldMappingCondGrpHd.getMagOrgId()).append(",")
                                    .append(gldMappingCondGrpHd.getSetOfBooksId()).append(",current_time,")
                                    .append(iRequest.getUserId()).append(",current_time,")
                                    .append(iRequest.getUserId()).append(")");
                        } else {
                            insertSB.append(gldMappingCondGrpHd.getAccountId()).append(",'")
                                    .append(gldMappingCondGrpHd.getAccountCode()).append("',")
                                    .append(gldMappingCondGrpHd.getMagOrgId()).append(",")
                                    .append(gldMappingCondGrpHd.getSetOfBooksId()).append(",GETDATE(),")
                                    .append(iRequest.getUserId()).append(",GETDATE(),")
                                    .append(iRequest.getUserId()).append(")");
                        }
                        errorSql.append(insertSB.toString());
                        gldMappingCondGrpHdMapper.executeSql(insertSB.toString());
                    }
                } else if ("update".equals(gldMappingCondGrpHd.get__status())) {
                    StringBuilder updateSB = new StringBuilder();
                    updateSB.append("update ").append(gldMappingCondGrpHd.getTableName()).append(" set ");
                    for (int i = 0; i < mappingConditionCodeArray.length; i++) {
                        if (mappingConditionCodeArray[i] != null && !"".equals(mappingConditionCodeArray[i])
                                        && mappingConditionCodeValueArray[i] != null
                                        && !"".equals(mappingConditionCodeValueArray[i])) {
                            updateSB.append(mappingConditionCodeArray[i]).append("='")
                                            .append(mappingConditionCodeValueArray[i]).append("',");
                        }
                    }
                    if ("oracle".equals(dbType)) {
                        updateSB.append("account_id=").append(gldMappingCondGrpHd.getAccountId()).append(",")
                                .append("account_code='").append(gldMappingCondGrpHd.getAccountCode()).append("',")
                                .append("mag_org_id=").append(gldMappingCondGrpHd.getMagOrgId()).append(",")
                                .append("set_of_books_id=").append(gldMappingCondGrpHd.getSetOfBooksId())
                                .append(",").append("last_updated_by=").append(iRequest.getUserId()).append(",")
                                .append("last_update_date=sysdate where line_id=")
                                .append(gldMappingCondGrpHd.getLineId());
                    } else if("mysql".equals(dbType)){
                        updateSB.append("account_id=").append(gldMappingCondGrpHd.getAccountId()).append(",")
                                .append("account_code='").append(gldMappingCondGrpHd.getAccountCode()).append("',")
                                .append("mag_org_id=").append(gldMappingCondGrpHd.getMagOrgId()).append(",")
                                .append("set_of_books_id=").append(gldMappingCondGrpHd.getSetOfBooksId())
                                .append(",").append("last_updated_by=").append(iRequest.getUserId()).append(",")
                                .append("last_update_date=current_time where line_id=")
                                .append(gldMappingCondGrpHd.getLineId());
                    } else {
                        updateSB.append("account_id=").append(gldMappingCondGrpHd.getAccountId()).append(",")
                                .append("account_code='").append(gldMappingCondGrpHd.getAccountCode()).append("',")
                                .append("mag_org_id=").append(gldMappingCondGrpHd.getMagOrgId()).append(",")
                                .append("set_of_books_id=").append(gldMappingCondGrpHd.getSetOfBooksId())
                                .append(",").append("last_updated_by=").append(iRequest.getUserId()).append(",")
                                .append("last_update_date=GETDATE() where line_id=")
                                .append(gldMappingCondGrpHd.getLineId());
                    }

                    errorSql.append(updateSB.toString());
                    gldMappingCondGrpHdMapper.executeSql(updateSB.toString());
                } else if ("delete".equals(gldMappingCondGrpHd.get__status())) {
                    StringBuilder deleteSB = new StringBuilder();
                    deleteSB.append("delete from ").append(gldMappingCondGrpHd.getTableName())
                                    .append(" where line_id = ").append(gldMappingCondGrpHd.getLineId());
                    errorSql.append(deleteSB.toString());
                    gldMappingCondGrpHdMapper.executeSql(deleteSB.toString());
                }
            }
        } catch (Exception e) {
            logger.info(errorSql.toString());
            throw new RuntimeException(e.getMessage());
        }
    }
}
