package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-28-CSH1095-init-table-migration.groovy') {


    changeSet(author: "hui.lu@hand-china.com", id: "2019-02-28-CSH_PAYMENT_BATCH_RULE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_BATCH_RULE_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_BATCH_RULE", remarks: "付款批决定规则定义") {

            column(name: "RULE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_BATCH_RULE_PK")} 
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}  
            column(name: "PRIORITY", type: "BIGINT", remarks: "优先级")  {constraints(nullable:"false")}  
            column(name: "RULE_CODE", type: "VARCHAR(30)", remarks: "规则代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "规则名称ID")   
            column(name: "TYPE_ID", type: "BIGINT", remarks: "付款批类型ID")  {constraints(nullable:"false")}  
            column(name: "START_DATE_ACTIVE", type: "DATETIME", remarks: "有效期从")  {constraints(nullable:"false")}  
            column(name: "END_DATE_ACTIVE", type: "DATETIME", remarks: "有效期到")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_PAYMENT_BATCH_RULE", indexName: "CSH_PAYMENT_BATCH_RULES_N1") {
                    column(name: "DESCRIPTION")
                }
                createIndex(tableName: "CSH_PAYMENT_BATCH_RULE", indexName: "CSH_PAYMENT_BATCH_RULES_N2") {
                    column(name: "TYPE_ID")
                }
            
        addUniqueConstraint(columnNames:"MAG_ORG_ID,RULE_CODE",tableName:"CSH_PAYMENT_BATCH_RULE",constraintName: "CSH_PAYMENT_BATCH_RULES_U1")
    }

    changeSet(author:"hui.lu@hand-china.com", id: "2019-02-28-CSH_PAYMENT_BATCH_RULE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_BATCH_RULE_TL_S', startValue: "10001")
        }

        createTable(tableName: "CSH_PAYMENT_BATCH_RULE_TL", remarks: "付款批决定规则定义多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "RULE_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "规则名称ID")   
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
	
	changeSet(author: "hui.lu@hand-china.com", id: "2019-02-28-CSH_PAYMENT_BATCH_RULE_DTL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_BATCH_RULE_DTL_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_BATCH_RULE_DTL", remarks: "付款批决定规则明细") {

            column(name: "FILTRATE_METHOD", type: "VARCHAR(30)", remarks: "取值方式（SYSCODE：WFL_WORKFLOW_FILTRATE_METHOD）")  {constraints(nullable:"false")}  
            column(name: "UPPER_LIMIT", type: "VARCHAR(30)", remarks: "上限")   
            column(name: "LOWER_LIMIT", type: "VARCHAR(30)", remarks: "下限")   
            column(name: "INVALID_DATE", type: "DATETIME", remarks: "失效日期")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "RULE_DTL_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_BATCH_RULE_DTL_PK")} 
            column(name: "RULE_LNS_ID", type: "BIGINT", remarks: "付款批规则明细ID")  {constraints(nullable:"false")}  
            column(name: "RULE_PARAMETER", type: "VARCHAR(30)", remarks: "规则参数（SYSCODE：CSH_PAYMENT_TRX_RULE_PARAMETER）")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_PAYMENT_BATCH_RULE_DTL", indexName: "CSH_PAYMENT_BATCH_RULE_DTL_N1") {
                    column(name: "RULE_LNS_ID")
                    column(name: "RULE_PARAMETER")
                }
        
    }

    changeSet(author: "hui.lu@hand-china.com", id: "2019-02-28-CSH_PAYMENT_BATCH_RULE_LN") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_BATCH_RULE_LN_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_BATCH_RULE_LN", remarks: "付款批决定规则行") {

            column(name: "RULE_LNS_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_BATCH_RULE_LN_PK")}
            column(name: "RULE_ID", type: "BIGINT", remarks: "付款批规则定义ID")  {constraints(nullable:"false")}
            column(name: "RULE_CODE", type: "VARCHAR(30)", remarks: "明细代码")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "名称ID")
            column(name: "TYPE_CODE", type: "VARCHAR(30)", remarks: "类型代码（SYSCODE：BGT_STRATEGY_TYPE）")  {constraints(nullable:"false")}
            column(name: "CUSTOM_FUNCTION", type: "VARCHAR(2000)", remarks: "函数")
            column(name: "START_DATE_ACTIVE", type: "DATETIME", remarks: "有效期从")  {constraints(nullable:"false")}
            column(name: "END_DATE_ACTIVE", type: "DATETIME", remarks: "有效期到")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "CSH_PAYMENT_BATCH_RULE_LN", indexName: "CSH_PAYMENT_BATCH_RULE_LNS_N1") {
            column(name: "DESCRIPTION")
        }

        addUniqueConstraint(columnNames:"RULE_ID,RULE_CODE",tableName:"CSH_PAYMENT_BATCH_RULE_LN",constraintName: "CSH_PAYMENT_BATCH_RULE_LNS_U1")
    }

    changeSet(author:"hui.lu@hand-china.com", id: "2019-02-28-CSH_PAYMENT_BATCH_RULE_LN_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_BATCH_RULE_LN_TL_S', startValue: "10001")
        }

        createTable(tableName: "CSH_PAYMENT_BATCH_RULE_LN_TL", remarks: "付款批决定规则行多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "RULE_LNS_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "名称ID")
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
	
	changeSet(author: "hui.lu@hand-china.com", id: "2019-02-28-CSH_PAYMENT_BAT_RULE_ASGN_AE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_BAT_RULE_ASGN_AE_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_BAT_RULE_ASGN_AE", remarks: "付款批决定规则定义分配核算主体") {

            column(name: "ASSIGN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_BAT_RULE_ASGN_AE_PK")} 
            column(name: "RULE_ID", type: "BIGINT", remarks: "付款批规则定义ID")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"RULE_ID,ACC_ENTITY_ID",tableName:"CSH_PAYMENT_BAT_RULE_ASGN_AE",constraintName: "CSH_PAY_BAT_RULE_ASGN_AE_U1")
    }

}