package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-25-FND2100-FND_TAX_TYPE_CODE_REF_AE-init-table-migration.groovy') {


    changeSet(author: "weikun.wang@hand-china.com", id: "2019-02-25-FND_TAX_TYPE_CODE_REF_AE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_TAX_TYPE_CODE_REF_AE_S', startValue:"10001")
        }

        createTable(tableName: "FND_TAX_TYPE_CODE_REF_AE", remarks: "税率分配核算主体表") {

            column(name: "REF_ID", type: "BIGINT", remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_TAX_TYPE_CODE_REF_AE_PK")}
            column(name: "TAX_TYPE_ID", type: "BIGINT", remarks: "税率定义ID")  {constraints(nullable:"false")}
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
        
        addUniqueConstraint(columnNames:"ACC_ENTITY_ID,TAX_TYPE_ID",tableName:"FND_TAX_TYPE_CODE_REF_AE",constraintName: "FND_TAX_TYPE_CODE_REF_AE_U1")
    }


}