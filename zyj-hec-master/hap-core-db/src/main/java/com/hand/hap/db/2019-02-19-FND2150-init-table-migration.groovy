package com.hand.hec.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-19-FND2150-init-table-migration.groovy') {


    changeSet(author: "yuting.gui@hand-china.com", id: "2019-02-19-ORD_CUSTOMER_ACCOUNT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ORD_CUSTOMER_ACCOUNT_S', startValue:"10001")
        }

        createTable(tableName: "ORD_CUSTOMER_ACCOUNT", remarks: "客户银行账户表") {

            column(name: "ACCOUNT_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ORD_CUSTOMER_ACCOUNT_PK")}
            column(name: "CUSTOMER_ID", type: "BIGINT", remarks: "客户ID")  {constraints(nullable:"false")}
            column(name: "BANK_ID", type: "BIGINT", remarks: "银行ID")  {constraints(nullable:"false")}
            column(name: "CNAPS_CODE", type: "VARCHAR(50)", remarks: "联行号/SWIFT")  {constraints(nullable:"false")}
            column(name: "PROVINCE_CODE", type: "VARCHAR(30)", remarks: "省代码")
            column(name: "PROVINCE_NAME", type: "VARCHAR(200)", remarks: "省名称")
            column(name: "CITY_CODE", type: "VARCHAR(30)", remarks: "市代码")
            column(name: "CITY_NAME", type: "VARCHAR(200)", remarks: "市名称")
            column(name: "ACCOUNT_NAME", type: "VARCHAR(200)", remarks: "银行户名")
            column(name: "ACCOUNT_NUMBER", type: "VARCHAR(200)", remarks: "银行帐号")
            column(name: "NOTES", type: "VARCHAR(4000)", remarks: "备注")
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
        createIndex(tableName: "ORD_CUSTOMER_ACCOUNT", indexName: "ORD_CUSTOMER_ACCOUNT_N1") {
            column(name: "CUSTOMER_ID")
        }

    }


    changeSet(author: "yuting.gui@hand-china.com", id: "2019-02-19-ORD_CUSTOMER_ACCOUNT_REF_AE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ORD_CUSTOMER_ACCOUNT_REF_AE_S', startValue:"10001")
        }

        createTable(tableName: "ORD_CUSTOMER_ACCOUNT_REF_AE", remarks: "客户银行账户分配核算主体表") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ORD_CUSTOMER_ACCOUNT_REF_AE_PK")}
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "客户银行账户ID")  {constraints(nullable:"false")}
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}
            column(name: "PRIMARY_FLAG", type: "VARCHAR(1)", remarks: "主账号标志")  {constraints(nullable:"false")}
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

        addUniqueConstraint(columnNames:"ACC_ENTITY_ID,ACCOUNT_ID",tableName:"ORD_CUSTOMER_ACCOUNT_REF_AE",constraintName: "ORD_CUSTOMER_ACCOUNT_REF_AE_U1")
    }


    changeSet(author: "yuting.gui@hand-china.com", id: "2019-02-19-ORD_CUSTOMER_TAX") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ORD_CUSTOMER_TAX_S', startValue:"10001")
        }

        createTable(tableName: "ORD_CUSTOMER_TAX", remarks: "客户税务信息表") {

            column(name: "TAX_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ORD_CUSTOMER_TAX_PK")}
            column(name: "CUSTOMER_ID", type: "BIGINT", remarks: "客户ID")  {constraints(nullable:"false")}
            column(name: "TAXPAYER_TYPE", type: "VARCHAR(200)", remarks: "纳税人类型（SYSCODE：TYPE_OF_TAXPAYER）")  {constraints(nullable:"false")}
            column(name: "TAXPAYER_NUMBER", type: "VARCHAR(200)", remarks: "纳税人识别号")
            column(name: "ADDRESS", type: "VARCHAR(2000)", remarks: "地址电话")
            column(name: "TAXPAYER_BANK", type: "VARCHAR(200)", remarks: "开户行及帐号")
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

        addUniqueConstraint(columnNames:"CUSTOMER_ID,TAXPAYER_TYPE",tableName:"ORD_CUSTOMER_TAX",constraintName: "ORD_CUSTOMER_TAX_U1")
    }


    changeSet(author: "yuting.gui@hand-china.com", id: "2019-02-19-ORD_CUSTOMER_TAX_REF_AE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ORD_CUSTOMER_TAX_REF_AE_S', startValue:"10001")
        }

        createTable(tableName: "ORD_CUSTOMER_TAX_REF_AE", remarks: "客户税务信息分配核算主体表") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ORD_CUSTOMER_TAX_REF_AE_PK")}
            column(name: "TAX_ID", type: "BIGINT", remarks: "客户税务信息ID")  {constraints(nullable:"false")}
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

        addUniqueConstraint(columnNames:"ACC_ENTITY_ID,TAX_ID",tableName:"ORD_CUSTOMER_TAX_REF_AE",constraintName: "ORD_CUSTOMER_TAX_REF_AE_U1")
    }


    changeSet(author: "yuting.gui@hand-china.com", id: "2019-02-19-ORD_SYSTEM_CUSTOMER") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ORD_SYSTEM_CUSTOMER_S', startValue:"10001")
        }

        createTable(tableName: "ORD_SYSTEM_CUSTOMER", remarks: "系统级客户主数据表") {

            column(name: "CUSTOMER_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ORD_SYSTEM_CUSTOMER_PK")}
            column(name: "CUSTOMER_CODE", type: "VARCHAR(30)", remarks: "客户编码")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "客户描述")  {constraints(nullable:"false")}
            column(name: "CUSTOMER_TYPE_ID", type: "BIGINT", remarks: "客户类型ID")
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
        createIndex(tableName: "ORD_SYSTEM_CUSTOMER", indexName: "ORD_SYSTEM_CUSTOMER_N1") {
            column(name: "DESCRIPTION")
        }

    }

    changeSet(author:"yuting.gui@hand-china.com", id: "2019-02-19-ORD_SYSTEM_CUSTOMER_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ORD_SYSTEM_CUSTOMERS_TL_S', startValue: "10001")
        }

        createTable(tableName: "ORD_SYSTEM_CUSTOMER_TL", remarks: "系统级客户主数据表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "CUSTOMER_ID", type: "BIGINT",  remarks: "主键")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "客户描述ID")
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

    changeSet(author: "yuting.gui@hand-china.com", id: "2019-02-19-ORD_SYSTEM_CUSTOMER_REF_AE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ORD_SYSTEM_CUSTOMER_REF_AE_S', startValue:"10001")
        }

        createTable(tableName: "ORD_SYSTEM_CUSTOMER_REF_AE", remarks: "客户分配核算主体表") {

            column(name: "REF_ID", type: "BIGINT", autoIncrement: true, startWith: "10001" , remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ORD_SYSTEM_CUSTOMER_REF_AE_PK")}
            column(name: "CUSTOMER_ID", type: "BIGINT", remarks: "客户ID")  {constraints(nullable:"false")}
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

        addUniqueConstraint(columnNames:"ACC_ENTITY_ID,CUSTOMER_ID",tableName:"ORD_SYSTEM_CUSTOMER_REF_AE",constraintName: "ORD_SYSTEM_CUSTOMER_REF_AE_U1")
    }


}