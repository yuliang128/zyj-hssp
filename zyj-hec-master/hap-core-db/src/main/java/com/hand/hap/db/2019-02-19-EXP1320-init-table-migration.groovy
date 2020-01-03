package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-19-EXP1320-init-table-migration.groovy') {


    changeSet(author: "zhongyu.wang@hand-china.com", id: "2019-02-19-EXP_POLICY_PLACE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_POLICY_PLACE_S', startValue:"10001")
        }

        createTable(tableName: "EXP_POLICY_PLACE", remarks: "费用地点表") {

            column(name: "PLACE_ID", type: "BIGINT", remarks: "地点id")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_POLICY_PLACE_PK")}
            column(name: "PLACE_CODE", type: "VARCHAR(30)", remarks: "地点编码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")  {constraints(nullable:"false")}
            column(name: "DISTRICT_ID", type: "BIGINT", remarks: "描述id")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "COUNTRY_CODE", type: "VARCHAR(100)", remarks: "国家代码")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"PLACE_CODE",tableName:"EXP_POLICY_PLACE",constraintName: "EXP_POLICY_PLACE_U1")
    }

    changeSet(author:"zhongyu.wang@hand-china.com", id: "2019-02-19-EXP_POLICY_PLACE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_POLICY_PLACE_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_POLICY_PLACE_TL", remarks: "费用地点表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "PLACE_ID", type: "BIGINT",  remarks: "地点id")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "描述")
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

    changeSet(author: "zhongyu.wang@hand-china", id: "2019-02-20-EXP_POLICY_DISTRICT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_POLICY_DISTRICT_S', startValue:"10001")
        }

        createTable(tableName: "EXP_POLICY_DISTRICT", remarks: "费用地点行政划分") {

            column(name: "DISTRICT_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "描述id")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_POLICY_DISTRICT_PK")}
            column(name: "DISTRICT_CODE", type: "VARCHAR(30)", remarks: "描述编码")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")  {constraints(nullable:"false")}
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"DISTRICT_CODE",tableName:"EXP_POLICY_DISTRICT",constraintName: "EXP_POLICY_DISTRICTS_U1")
    }

    changeSet(author:"zhongyu.wang@hand-china", id: "2019-02-20-EXP_POLICY_DISTRICT_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_POLICY_DISTRICT_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_POLICY_DISTRICT_TL", remarks: "费用地点行政划分多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DISTRICT_ID", type: "BIGINT",  remarks: "描述id")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "描述")
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