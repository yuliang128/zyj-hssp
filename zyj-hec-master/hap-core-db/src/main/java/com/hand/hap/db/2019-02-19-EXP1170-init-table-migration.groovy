package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-19-EXP1170-init-table-migration.groovy') {


    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-02-19-EXP_MO_EXPENSE_POLICY") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXPENSE_POLICY_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_EXPENSE_POLICY", remarks: "管理组织级政策标准定义") {

            column(name: "EXPENSE_POLICY_ID", type: "BIGINT",autoIncrement: true, startWith: "10001"  , remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_EXPENSE_POLICY_PK")} 
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}  
            column(name: "EXPENSE_POLICY_CODE", type: "VARCHAR(30)", remarks: "政策标准代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "政策标准描述ID")   
            column(name: "START_DATE_ACTIVE", type: "DATETIME", remarks: "有效日期从")  {constraints(nullable:"false")}  
            column(name: "END_DATE_ACTIVE", type: "DATETIME", remarks: "有效日期至")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"EXPENSE_POLICY_CODE",tableName:"EXP_MO_EXPENSE_POLICY",constraintName: "EXP_MO_EXPENSE_POLICY_U1")
    }

    changeSet(author:"xiuxian.wu@hand-china.com", id: "2019-02-19-EXP_MO_EXPENSE_POLICY_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXPENSE_POLICY_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_EXPENSE_POLICY_TL", remarks: "管理组织级政策标准定义多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "EXPENSE_POLICY_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "政策标准描述ID")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
    }

    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-02-19-EXP_MO_EXP_POLICY_ASGN_COM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXP_POLICY_ASGN_COM_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_EXP_POLICY_ASGN_COM", remarks: "管理组织级政策标准定义分配公司") {

            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "ASSIGN_ID", type: "BIGINT" ,autoIncrement: true, startWith: "10001", remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_EXP_POLICY_ASGN_COM_PK")} 
            column(name: "EXPENSE_POLICY_ID", type: "BIGINT", remarks: "政策标准ID")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"COMPANY_ID,EXPENSE_POLICY_ID",tableName:"EXP_MO_EXP_POLICY_ASGN_COM",constraintName: "EXP_MO_EXP_POLICY_ASGN_COM_U1")
    }


    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-02-19-EXP_MO_EXP_POLICY_CONDITION") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXP_POLICY_CONDITION_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_EXP_POLICY_CONDITION", remarks: "管理组织级政策标准定义明细条件") {

            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CONDITION_ID", type: "BIGINT" ,autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_EXP_POLICY_CONDITION_PK")} 
            column(name: "DETAIL_ID", type: "BIGINT", remarks: "标准明细ID")  {constraints(nullable:"false")}  
            column(name: "CONDITION_CODE", type: "VARCHAR(30)", remarks: "条件代码")  {constraints(nullable:"false")}  
            column(name: "CONDITION_NAME", type: "VARCHAR(500)", remarks: "条件描述ID")   
            column(name: "START_DATE_ACTIVE", type: "DATETIME", remarks: "有效日期从")  {constraints(nullable:"false")}  
            column(name: "END_DATE_ACTIVE", type: "DATETIME", remarks: "有效日期至")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"DETAIL_ID,CONDITION_CODE",tableName:"EXP_MO_EXP_POLICY_CONDITION",constraintName: "EXP_MO_EXP_POLICY_CONDITION_U1")
    }

    changeSet(author:"xiuxian.wu@hand-china.com", id: "2019-02-19-EXP_MO_EXP_POLICY_CONDITION_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXP_POLICY_CONDITION_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_EXP_POLICY_CONDITION_TL", remarks: "管理组织级政策标准定义明细条件多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "CONDITION_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "CONDITION_NAME", type: "VARCHAR(500)",  remarks: "条件描述ID")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
    }

    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-02-19-EXP_MO_EXP_POLICY_COND_HDS") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXP_POLICY_COND_HDS_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_EXP_POLICY_COND_HDS", remarks: "管理组织级政策标准定义条件分配头") {

            column(name: "CONDITION_HDS_ID", type: "BIGINT" ,autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_EXP_POLICY_COND_HDS_PK")} 
            column(name: "CONDITION_ID", type: "BIGINT", remarks: "管理组织级政策标准定义条件分配")  {constraints(nullable:"false")}  
            column(name: "MATCH_ITEM_CODE", type: "VARCHAR(30)", remarks: "匹配项代码")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"CONDITION_ID,MATCH_ITEM_CODE",tableName:"EXP_MO_EXP_POLICY_COND_HDS",constraintName: "EXP_MO_EXP_POLICY_COND_HDS_U1")
    }


    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-02-19-EXP_MO_EXP_POLICY_COND_LNS") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXP_POLICY_COND_LNS_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_EXP_POLICY_COND_LNS", remarks: "管理组织级政策标准定义条件分配行") {

            column(name: "CONDITION_LNS_ID", type: "BIGINT" ,autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_EXP_POLICY_COND_LNS_PK")} 
            column(name: "CONDITION_HDS_ID", type: "BIGINT", remarks: "管理组织级政策标准定义条件分配头")  {constraints(nullable:"false")}  
            column(name: "CONDITION_VALUE_CODE", type: "VARCHAR(30)", remarks: "值代码（管理公司、核算主体等）")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"CONDITION_VALUE_CODE,CONDITION_HDS_ID",tableName:"EXP_MO_EXP_POLICY_COND_LNS",constraintName: "EXP_MO_EXP_POLICY_COND_LNS_U1")
    }


    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-02-19-EXP_MO_EXP_POLICY_DETAIL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXP_POLICY_DETAIL_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_EXP_POLICY_DETAIL", remarks: "管理组织级政策标准定义标准明细") {

            column(name: "DETAIL_ID", type: "BIGINT" ,autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_EXP_POLICY_DETAIL_PK")} 
            column(name: "EXPENSE_POLICY_ID", type: "BIGINT", remarks: "政策标准ID")  {constraints(nullable:"false")}  
            column(name: "PRIORITY", type: "BIGINT", remarks: "优先级")  {constraints(nullable:"false")}  
            column(name: "DETAIL_CODE", type: "VARCHAR(30)", remarks: "明细代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "明细描述ID")  {constraints(nullable:"false")}  
            column(name: "DEFAULT_FLAG", type: "VARCHAR(1)", remarks: "缺省标志")  {constraints(nullable:"false")}  
            column(name: "CHANGEABLE_FLAG", type: "VARCHAR(1)", remarks: "可修改标志")  {constraints(nullable:"false")}  
            column(name: "EXPENSE_STANDARD", type: "BIGINT", remarks: "费用标准")   
            column(name: "UPPER_LIMIT", type: "BIGINT", remarks: "上限")   
            column(name: "LOWER_LIMIT", type: "BIGINT", remarks: "下限")   
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")   
            column(name: "COMMIT_FLAG", type: "VARCHAR(1)", remarks: "超上下限后是否可以提交")  {constraints(nullable:"false")}  
            column(name: "TRANSPORTATION", type: "VARCHAR(30)", remarks: "交通工具（SYSCODE：TRANSPORTATION）")   
            column(name: "SEAT_CLASS", type: "VARCHAR(30)", remarks: "舱位/席别")   
            column(name: "ROOM_TYPE", type: "VARCHAR(30)", remarks: "房型")   
            column(name: "EVENT_NAME", type: "VARCHAR(30)", remarks: "上下限事件")   
            column(name: "UPPER_STD_EVENT_NAME", type: "VARCHAR(30)", remarks: "超出标准事件")   
            column(name: "START_DATE_ACTIVE", type: "DATETIME", remarks: "有效日期从")  {constraints(nullable:"false")}  
            column(name: "END_DATE_ACTIVE", type: "DATETIME", remarks: "有效日期到")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "EXP_MO_EXP_POLICY_DETAIL", indexName: "EXP_MO_EXP_POLICY_DETAIL_N1") {
                    column(name: "PRIORITY")
                    column(name: "DETAIL_CODE")
                }
            
        addUniqueConstraint(columnNames:"DETAIL_CODE",tableName:"EXP_MO_EXP_POLICY_DETAIL",constraintName: "EXP_MO_EXP_POLICY_DETAIL_U1")
    }

    changeSet(author:"xiuxian.wu@hand-china.com", id: "2019-02-19-EXP_MO_EXP_POLICY_DETAIL_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXP_POLICY_DETAIL_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_EXP_POLICY_DETAIL_TL", remarks: "管理组织级政策标准定义标准明细多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DETAIL_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "明细描述ID")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
    }

}