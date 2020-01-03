package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: 'CSH1070-2019-02-18-init-table-migration.groovy') {


    changeSet(author: "dingwei.ma@hand-chiina.com", id: "2019-02-18-CSH_PAYMENT_METHOD") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_METHOD_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_METHOD", remarks: "付款方式表") {

            column(name: "PAYMENT_METHOD_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_METHOD_PK")} 
            column(name: "PAYMENT_METHOD_CODE", type: "VARCHAR(30)", remarks: "付款方式代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "付款方式名称ID")   
            column(name: "PAY_METHOD_CODE", type: "VARCHAR(30)", remarks: "支付方式（SYSCODE：CSH_PAY_METHOD）")  {constraints(nullable:"false")}  
            column(name: "PAY_CARRIER_CODE", type: "VARCHAR(30)", remarks: "支付载体（SYSCODE：CSH_PAY_CARRIER）")  {constraints(nullable:"false")}  
            column(name: "POSTING_FLAG", type: "VARCHAR(1)", remarks: "直接过账标志")  {constraints(nullable:"false")}  
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
            createIndex(tableName: "CSH_PAYMENT_METHOD", indexName: "CSH_PAYMENT_METHODS_N1") {
                    column(name: "DESCRIPTION")
                }
            
        addUniqueConstraint(columnNames:"PAYMENT_METHOD_CODE",tableName:"CSH_PAYMENT_METHOD",constraintName: "CSH_PAYMENT_METHODS_U1")
    }

    changeSet(author:"dingwei.ma@hand-chiina.com", id: "2019-02-18-CSH_PAYMENT_METHOD_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_METHOD_TL_S', startValue: "10001")
        }

        createTable(tableName: "CSH_PAYMENT_METHOD_TL", remarks: "付款方式表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "PAYMENT_METHOD_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "付款方式名称ID")   
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

    changeSet(author: "dingwei.ma@hand-chiina.com", id: "2019-02-18-CSH_PAYMENT_METHOD_ASGN_COM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_METHOD_ASGN_COM_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_METHOD_ASGN_COM", remarks: "付款方式分配管理公司表") {

            column(name: "ASSIGN_COM_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_METHOD_ASGN_COM_PK")} 
            column(name: "ASSIGN_MO_ID", type: "BIGINT", remarks: "付款方式分配管理组织ID")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"ASSIGN_MO_ID,COMPANY_ID",tableName:"CSH_PAYMENT_METHOD_ASGN_COM",constraintName: "CSH_PAYMENT_METHOD_ASGN_COM_U1")
    }


    changeSet(author: "dingwei.ma@hand-chiina.com", id: "2019-02-18-CSH_PAYMENT_METHOD_ASGN_MO") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_METHOD_ASGN_MO_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_METHOD_ASGN_MO", remarks: "付款方式分配管理组织表") {

            column(name: "ASSIGN_MO_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_METHOD_ASGN_MO_PK")} 
            column(name: "PAYMENT_METHOD_ID", type: "BIGINT", remarks: "付款方式ID")  {constraints(nullable:"false")}  
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"MAG_ORG_ID,PAYMENT_METHOD_ID",tableName:"CSH_PAYMENT_METHOD_ASGN_MO",constraintName: "CSH_PAYMENT_METHOD_ASGN_MO_U1")
    }


}