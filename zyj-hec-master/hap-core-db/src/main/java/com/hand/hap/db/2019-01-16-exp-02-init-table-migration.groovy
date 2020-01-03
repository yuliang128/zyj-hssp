package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-16-exp-02-init-table-migration.groovy') {


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-16-EXP_MO_EMP_GROUP_ASGN_COM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EMP_GROUP_ASGN_COM_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_EMP_GROUP_ASGN_COM", remarks: "管理组织级员工组分配公司") {

            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "ASSIGN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_EMP_GROUP_ASGN_COM_PK")} 
            column(name: "MO_EMP_GROUP_ID", type: "BIGINT", remarks: "管理组织级员工组ID")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"MO_EMP_GROUP_ID,COMPANY_ID",tableName:"EXP_MO_EMP_GROUP_ASGN_COM",constraintName: "EXP_MO_EMP_GROUP_ASGN_COM_U1")
    }


}