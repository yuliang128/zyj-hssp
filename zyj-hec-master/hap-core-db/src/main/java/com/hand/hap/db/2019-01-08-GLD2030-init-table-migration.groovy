package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-08-GLD2030-init-table-migration.groovy') {


    changeSet(author: "rui.shi@hand-china.com", id: "2019-01-08-GLD_RESPONSIBILITY_CENTER") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_RESPONSIBILITY_CENTER_S', startValue:"10001")
        }

        createTable(tableName: "GLD_RESPONSIBILITY_CENTER", remarks: "核算主体级责任中心") {

            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_RESPONSIBILITY_CENTER_PK")}
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}
            column(name: "RESPONSIBILITY_CENTER_CODE", type: "VARCHAR(30)", remarks: "责任中心代码")  {constraints(nullable:"false")}
            column(name: "RESPONSIBILITY_CENTER_NAME", type: "VARCHAR(500)", remarks: "责任中心名称ID")
            column(name: "RESP_CENTER_TYPE_CODE", type: "VARCHAR(30)", remarks: "责任中心类型")
            column(name: "START_DATE_ACTIVE", type: "DATETIME", remarks: "有效日期从")  {constraints(nullable:"false")}
            column(name: "END_DATE_ACTIVE", type: "DATETIME", remarks: "有效日期至")
            column(name: "SUMMARY_FLAG", type: "VARCHAR(1)", remarks: "汇总标志")  {constraints(nullable:"false")}
            column(name: "PARENT_RESP_CENTER_ID", type: "BIGINT", remarks: "上级责任中心")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "GLD_RESPONSIBILITY_CENTER", indexName: "GLD_RESPONSIBILITY_CENTER_N1") {
                    column(name: "RESPONSIBILITY_CENTER_NAME")
                }

        addUniqueConstraint(columnNames:"ACC_ENTITY_ID,RESPONSIBILITY_CENTER_CODE",tableName:"GLD_RESPONSIBILITY_CENTER",constraintName: "GLD_RESPONSIBILITY_CENTER_U1")
    }

    changeSet(author:"rui.shi@hand-china.com", id: "2019-01-08-GLD_RESPONSIBILITY_CENTER_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_RESPONSIBILITY_CENTER_TL_S', startValue: "10001")
        }

        createTable(tableName: "GLD_RESPONSIBILITY_CENTER_TL", remarks: "核算主体级责任中心多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "RESPONSIBILITY_CENTER_NAME", type: "VARCHAR(500)",  remarks: "责任中心名称")
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


    changeSet(author: "rui.shi@hand-china.com", id: "2019-01-08-GLD_RESP_CENTER_HIERARCHY") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_RESP_CENTER_HIERARCHY_S', startValue:"10001")
        }

        createTable(tableName: "GLD_RESP_CENTER_HIERARCHY", remarks: "核算主体级责任中心层级") {

            column(name: "HIERARCHY_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_RESP_CENTER_HIERARCHY_PK")}
            column(name: "PARENT_RESP_CENTER_ID", type: "BIGINT", remarks: "父责任中心ID")  {constraints(nullable:"false")}
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "子责任中心ID")  {constraints(nullable:"false")}
            column(name: "START_PERIOD_NAME", type: "VARCHAR(30)", remarks: "启用期间")  {constraints(nullable:"false")}
            column(name: "END_PERIOD_NAME", type: "VARCHAR(30)", remarks: "关闭期间")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"RESPONSIBILITY_CENTER_ID,PARENT_RESP_CENTER_ID",tableName:"GLD_RESP_CENTER_HIERARCHY",constraintName: "GLD_RESP_CENTER_HIERARCHY_U1")
    }


    changeSet(author: "rui.shi@hand-china.com", id: "2019-01-08-GLD_RESP_CENTER_REF_BE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_RESP_CENTER_REF_BE_S', startValue:"10001")
        }

        createTable(tableName: "GLD_RESP_CENTER_REF_BE", remarks: "核算主体级成本中心关联预算中心表") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_RESP_CENTER_REF_BE_PK")}
            column(name: "RESP_CENTER_ID", type: "BIGINT", remarks: "成本中心ID")  {constraints(nullable:"false")}
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体ID")  {constraints(nullable:"false")}
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心ID")  {constraints(nullable:"false")}
            column(name: "DEFAULT_FLAG", type: "VARCHAR(1)", remarks: "默认标志")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"BGT_CENTER_ID,RESP_CENTER_ID,BGT_ENTITY_ID",tableName:"GLD_RESP_CENTER_REF_BE",constraintName: "GLD_RESP_CENTER_REF_BE_U1")
    }


}