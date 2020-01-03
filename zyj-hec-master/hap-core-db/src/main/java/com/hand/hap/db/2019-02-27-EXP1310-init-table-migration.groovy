package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-27-EXP1310-init-table-migration.groovy') {


    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-02-27-EXP_POLICY_PLC_TP_ASGN_COM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_POLICY_PLC_TP_ASGN_COM_S', startValue:"10001")
        }

        createTable(tableName: "EXP_POLICY_PLC_TP_ASGN_COM", remarks: "费用政策地点类型分配公司") {

            column(name: "ASSIGN_ID", type: "BIGINT" ,autoIncrement: true, startWith: "10001", remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_POLICY_PLC_TP_ASGN_COM_PK")}
            column(name: "PLACE_TYPE_ID", type: "BIGINT", remarks: "地点类型ID")  {constraints(nullable:"false")}
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
        
        addUniqueConstraint(columnNames:"COMPANY_ID,PLACE_TYPE_ID",tableName:"EXP_POLICY_PLC_TP_ASGN_COM",constraintName: "EXP_POLICY_PLC_TP_ASGN_COM_U1")
    }


    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-02-27-EXP_POLICY_PLC_TP_REF_PLC") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_POLICY_PLC_TP_REF_PLC_S', startValue:"10001")
        }

        createTable(tableName: "EXP_POLICY_PLC_TP_REF_PLC", remarks: "费用政策地点类型分配地点") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001", remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_POLICY_PLC_TP_REF_PLC_PK")}
            column(name: "ASSIGN_ID", type: "BIGINT", remarks: "费用政策地点类型分配公司ID")  {constraints(nullable:"false")}
            column(name: "PLACE_ID", type: "BIGINT", remarks: "地点ID")  {constraints(nullable:"false")}
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
        
        addUniqueConstraint(columnNames:"ASSIGN_ID,PLACE_ID",tableName:"EXP_POLICY_PLC_TP_REF_PLC",constraintName: "EXP_POLICY_PLC_TP_REF_PLCS_U1")
    }


}