package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-21-CSH1051-init-table-migration.groovy') {


    changeSet(author: "junkai.lu@hand-china.com", id: "2019-02-21-CSH_MO_TRX_CLS_ASGN_COM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_MO_TRX_CLS_ASGN_COM_S', startValue:"10001")
        }

        createTable(tableName: "CSH_MO_TRX_CLS_ASGN_COM", remarks: "管理组织级现金事物分类分配公司") {

            column(name: "ASSIGN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_MO_TRX_CLS_ASGN_COM_PK")} 
            column(name: "MO_CSH_TRX_CLASS_ID", type: "BIGINT", remarks: "管理组织级现金事物分类定义")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"COMPANY_ID,MO_CSH_TRX_CLASS_ID",tableName:"CSH_MO_TRX_CLS_ASGN_COM",constraintName: "CSH_MO_TRX_CLS_ASGN_COM_U1")
    }


}