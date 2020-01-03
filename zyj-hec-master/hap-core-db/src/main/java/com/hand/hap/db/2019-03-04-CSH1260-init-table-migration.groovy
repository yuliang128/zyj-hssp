package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-04-CSH1260-init-table-migration.groovy') {


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-03-04-CSH_PAYMENT_BATCH_TP_ASGN_AE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_BATCH_TP_ASGN_AE_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_BATCH_TP_ASGN_AE", remarks: "付款批类型定义分配核算主体") {

            column(name: "ASSIGN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_BATCH_TP_ASGN_AE_PK")} 
            column(name: "TYPE_ID", type: "BIGINT", remarks: "付款批类型")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"TYPE_ID,ACC_ENTITY_ID",tableName:"CSH_PAYMENT_BATCH_TP_ASGN_AE",constraintName: "CSH_PAY_BATCH_TP_ASGN_AE_U1")
    }


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-03-04-CSH_PAYMENT_BATCH_TP_DTL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_BATCH_TP_DTL_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_BATCH_TP_DTL", remarks: "付款批类型定义组批规则") {

            column(name: "TYPE_DTL_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_BATCH_TP_DTL_PK")} 
            column(name: "TYPE_ID", type: "BIGINT", remarks: "付款批类型定义")  {constraints(nullable:"false")}  
            column(name: "GROUP_CONDITION", type: "VARCHAR(30)", remarks: "分组条件（SYSCODE：CSH_PAYMENT_TRX_TP_GROUP_CONDITION）")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"GROUP_CONDITION,TYPE_ID",tableName:"CSH_PAYMENT_BATCH_TP_DTL",constraintName: "CSH_PAYMENT_BATCH_TP_DTL_U1")
    }


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-03-04-CSH_PAYMENT_BATCH_TP_RULE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_BATCH_TP_RULE_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_BATCH_TP_RULE", remarks: "付款批类型定义合并规则") {

            column(name: "TYPE_RULE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_BATCH_TP_RULE_PK")} 
            column(name: "TYPE_ID", type: "BIGINT", remarks: "付款批类型定义")  {constraints(nullable:"false")}  
            column(name: "DR_CR_CODE", type: "VARCHAR(30)", remarks: "借/贷方（SYSCODE：DR_CR_FLAG）")  {constraints(nullable:"false")}  
            column(name: "PARAMETER_TYPE", type: "VARCHAR(30)", remarks: "合并参数类型（SYSCODE：CSH_PAYMENT_PARAMETER_TYPE）")  {constraints(nullable:"false")}  
            column(name: "PARAMETER_VALUE", type: "VARCHAR(30)", remarks: "参数值")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"PARAMETER_VALUE,PARAMETER_TYPE,TYPE_ID,DR_CR_CODE",tableName:"CSH_PAYMENT_BATCH_TP_RULE",constraintName: "CSH_PAYMENT_BATCH_TP_RULE_U1")
    }


}