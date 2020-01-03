package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-28-CSH1240pgp-init-table-migration.groovy') {


    changeSet(author: "bo.zhang05@hand-china.com", id: "2019-02-28-CSH_PAYMENT_GPS_PRIVILEGE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_GPS_PRIVILEGE_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_GPS_PRIVILEGE", remarks: "付款工作组分配支付主体权限分配") {

            column(name: "PRIVILEGE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_GPS_PRIVILEGE_PK")} 
            column(name: "ASSIGN_AE_ID", type: "BIGINT", remarks: "付款工作组分配支付主体ID")  {constraints(nullable:"false")}  
            column(name: "RULE_ID", type: "BIGINT", remarks: "支付权限规则定义ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"ASSIGN_AE_ID,RULE_ID",tableName:"CSH_PAYMENT_GPS_PRIVILEGE",constraintName: "CSH_PAYMENT_GPS_PRIVILEGE_U1")
    }


}