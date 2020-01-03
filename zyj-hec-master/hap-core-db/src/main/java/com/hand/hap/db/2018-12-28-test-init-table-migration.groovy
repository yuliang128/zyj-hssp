package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2018-12-28-test-init-table-migration.groovy') {


    changeSet(author: "jialin.xing@hand-china.com", id: "2018-12-29-GLD_CURRENCY") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_CURRENCY_S', startValue:"10001")
        }

        createTable(tableName: "GLD_CURRENCY", remarks: "币种定义") {

            column(name: "CURRENCY_CODE", type: "VARCHAR(10)", remarks: "币种代码")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_CURRENCY_PK")}
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}
            column(name: "CURRENCY_NAME", type: "VARCHAR(2000)", remarks: "币种名称ID")
            column(name: "COUNTRY_CODE", type: "VARCHAR(30)", remarks: "国家/地区")  {constraints(nullable:"false")}
            column(name: "PRECISION", type: "BIGINT", remarks: "财务精度")  {constraints(nullable:"false")}
            column(name: "TRANSACTION_PRECISION", type: "BIGINT", remarks: "精度")  {constraints(nullable:"false")}
            column(name: "CURRENCY_SYMBOL", type: "VARCHAR(10)", remarks: "货币符号")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "GLD_CURRENCY", indexName: "GLD_CURRENCY_N1") {
            column(name: "COUNTRY_CODE")
        }

    }

    changeSet(author:"jialin.xing@hand-china.com", id: "2018-12-29-GLD_CURRENCY_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_CURRENCY_TL_S', startValue: "10001")
        }

        createTable(tableName: "GLD_CURRENCY_TL", remarks: "币种定义多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "CURRENCY_CODE", type: "VARCHAR(10)",  remarks: "币种代码")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "CURRENCY_NAME", type: "VARCHAR(2000)",  remarks: "币种名称ID")
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

    changeSet(author: "jialin.xing@hand-china.com", id: "2018-12-29-FND_MANAGING_ORGANIZATION") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_MANAGING_ORGANIZATION_S', startValue:"10001")
        }

        createTable(tableName: "FND_MANAGING_ORGANIZATION", remarks: "管理组织") {

            column(name: "MAG_ORG_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "管理组织ID，PK")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_MANAGING_ORGANIZATION_PK")}
            column(name: "MAG_ORG_CODE", type: "VARCHAR(30)", remarks: "管理组织代码")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "管理组织描述ID")  {constraints(nullable:"false")}
            column(name: "MANAGING_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "管理币种")
            column(name: "DEFAULT_BGT_ORG_ID", type: "BIGINT", remarks: "默认预算组织")
            column(name: "DEFAULT_SET_OF_BOOKS_ID", type: "BIGINT", remarks: "默认账套")
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

        addUniqueConstraint(columnNames:"MAG_ORG_CODE",tableName:"FND_MANAGING_ORGANIZATION",constraintName: "FND_MANAGING_ORGANIZATION_U1")
    }

    changeSet(author:"jialin.xing@hand-china.com", id: "2018-12-29-FND_MANAGING_ORGANIZATION_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_MANAGING_ORGANIZATION_TL_S', startValue: "10001")
        }

        createTable(tableName: "FND_MANAGING_ORGANIZATION_TL", remarks: "管理组织多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "MAG_ORG_ID", type: "BIGINT",  remarks: "管理组织ID，PK")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DESCRIPTION", type: "VARCHAR(2000)",  remarks: "管理组织描述ID")
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