package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-26-EXP2130-init-table-migration.groovy') {


    changeSet(author: "zhongyu.wang@hand-china.com", id: "2019-02-26-EXP_MO_EXP_ITEM_ASGN_COM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXP_ITEM_ASGN_COM_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_EXP_ITEM_ASGN_COM", remarks: "费用项目分配公司") {

            column(name: "ASSIGN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_EXP_ITEM_ASGN_COM_PK")} 
            column(name: "MO_EXPENSE_ITEM_ID", type: "BIGINT", remarks: "费用项目ID")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"MO_EXPENSE_ITEM_ID,COMPANY_ID",tableName:"EXP_MO_EXP_ITEM_ASGN_COM",constraintName: "EXP_MO_EXP_ITEM_ASGN_COM_U1")
    }


}