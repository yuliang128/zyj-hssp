package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-14-FND2505-init-table-migration.groovy') {


    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-02-14-ORD_CUSTOMER_TYPE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ORD_CUSTOMER_TYPE_S', startValue:"10001")
        }

        createTable(tableName: "ORD_CUSTOMER_TYPE", remarks: "客户类型表") {

            column(name: "CUSTOMER_TYPE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ORD_CUSTOMER_TYPE_PK")}
            column(name: "CUSTOMER_TYPE_CODE", type: "VARCHAR(30)", remarks: "客户代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "客户名称")   
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
        
        addUniqueConstraint(columnNames:"CUSTOMER_TYPE_CODE",tableName:"ORD_CUSTOMER_TYPE",constraintName: "ORD_CUSTOMER_TYPES_U1")
    }

    changeSet(author:"xiuxian.wu@hand-china.com", id: "2019-02-14-ORD_CUSTOMER_TYPE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ORD_CUSTOMER_TYPE_TL_S', startValue: "10001")
        }

        createTable(tableName: "ORD_CUSTOMER_TYPE_TL", remarks: "客户类型表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "CUSTOMER_TYPE_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "客户名称ID")   
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

    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-02-14-ORD_CUSTOMER_TYPE_REF_AE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ORD_CUSTOMER_TYPE_REF_AE_S', startValue:"10001")
        }

        createTable(tableName: "ORD_CUSTOMER_TYPE_REF_AE", remarks: "客户类型分配核算主体") {

            column(name: "REF_ID", type: "BIGINT" ,autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ORD_CUSTOMER_TYPE_REF_AE_PK")}
            column(name: "CUSTOMER_TYPE_ID", type: "BIGINT", remarks: "客户类型ID")  {constraints(nullable:"false")}
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
        
        addUniqueConstraint(columnNames:"ACC_ENTITY_ID,CUSTOMER_TYPE_ID",tableName:"ORD_CUSTOMER_TYPE_REF_AE",constraintName: "ORD_CUSTOMER_TYPE_REF_AE_U1")
    }


}