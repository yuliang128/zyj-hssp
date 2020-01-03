package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-05-06-core-init-table-migration.groovy') {


    changeSet(author: "rui.shi@hand-china.com", id: "2019-05-06-FND_ATM_ATTACHMENT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_ATM_ATTACHMENT_S', startValue:"10001")
        }

        createTable(tableName: "FND_ATM_ATTACHMENT", remarks: "附件表") {
            if (mhi.getDbType().isSuppportAutoIncrement()) {
                column(name:"ATTACHMENT_ID",type:"bigint",autoIncrement: "true", startWith:"10001",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "FND_ATM_ATTACHMENT_PK")
                }
            } else {
                column(name:"ATTACHMENT_ID",type:"bigint",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "FND_ATM_ATTACHMENT_PK")
                }
            }

            column(name: "FILE_NAME", type: "VARCHAR(500)", remarks: "文件名")
            column(name: "FILE_PATH", type: "VARCHAR(500)", remarks: "文件路径")
            column(name: "FILE_SIZE", type: "decimal(20,0)", remarks: "文件大小")
            column(name: "FILE_TYPE", type: "VARCHAR(500)", remarks: "文件类型")
            column(name: "MINE_TYPE", type: "VARCHAR(500)", remarks: "mine类型")
            column(name: "SAVE_TYPE", type: "VARCHAR(500)", remarks: "存储类型：file/oss")

            column(name:"OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "bigint", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "bigint", defaultValue : "-1")
            column(name: "CREATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "bigint", defaultValue : "-1")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(240)")
            column(name:"ATTRIBUTE2",type:"varchar(240)")
            column(name:"ATTRIBUTE3",type:"varchar(240)")
            column(name:"ATTRIBUTE4",type:"varchar(240)")
            column(name:"ATTRIBUTE5",type:"varchar(240)")
            column(name:"ATTRIBUTE6",type:"varchar(240)")
            column(name:"ATTRIBUTE7",type:"varchar(240)")
            column(name:"ATTRIBUTE8",type:"varchar(240)")
            column(name:"ATTRIBUTE9",type:"varchar(240)")
            column(name:"ATTRIBUTE10",type:"varchar(240)")
            column(name:"ATTRIBUTE11",type:"varchar(240)")
            column(name:"ATTRIBUTE12",type:"varchar(240)")
            column(name:"ATTRIBUTE13",type:"varchar(240)")
            column(name:"ATTRIBUTE14",type:"varchar(240)")
            column(name:"ATTRIBUTE15",type:"varchar(240)")

        }
            createIndex(tableName: "FND_ATM_ATTACHMENT", indexName: "FND_ATM_ATTACHMENT_N1") {
                    column(name: "ATTACHMENT_ID")
                }

    }



    changeSet(author: "rui.shi@hand-china.com", id: "2019-05-06-FND_ATM_ATTACHMENT_REF_RECORD") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_ATM_ATTACHMENT_REF_RECORD_S', startValue:"10001")
        }

        createTable(tableName: "FND_ATM_ATTACHMENT_REF_RECORD", remarks: "附件与记录关联表") {
            if (mhi.getDbType().isSuppportAutoIncrement()) {
                column(name:"RECORD_ID",type:"bigint",autoIncrement: "true", startWith:"10001",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "FND_ATM_ATTACHMENT_REF_RECORD_PK")
                }
            } else {
                column(name:"RECORD_ID",type:"bigint",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "FND_ATM_ATTACHMENT_REF_RECORD_PK")
                }
            }

            column(name: "ATTACHMENT_ID",type:"bigint",remarks: "附件ID")
            column(name: "TABLE_NAME", type: "VARCHAR(500)", remarks: "业务类型名，一般为表名")
            column(name: "TABLE_PK_VALUE", type: "bigint", remarks: "业务主键，一般为表主键")


            column(name:"OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "bigint", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "bigint", defaultValue : "-1")
            column(name: "CREATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "bigint", defaultValue : "-1")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(240)")
            column(name:"ATTRIBUTE2",type:"varchar(240)")
            column(name:"ATTRIBUTE3",type:"varchar(240)")
            column(name:"ATTRIBUTE4",type:"varchar(240)")
            column(name:"ATTRIBUTE5",type:"varchar(240)")
            column(name:"ATTRIBUTE6",type:"varchar(240)")
            column(name:"ATTRIBUTE7",type:"varchar(240)")
            column(name:"ATTRIBUTE8",type:"varchar(240)")
            column(name:"ATTRIBUTE9",type:"varchar(240)")
            column(name:"ATTRIBUTE10",type:"varchar(240)")
            column(name:"ATTRIBUTE11",type:"varchar(240)")
            column(name:"ATTRIBUTE12",type:"varchar(240)")
            column(name:"ATTRIBUTE13",type:"varchar(240)")
            column(name:"ATTRIBUTE14",type:"varchar(240)")
            column(name:"ATTRIBUTE15",type:"varchar(240)")

        }
        createIndex(tableName: "FND_ATM_ATTACHMENT_REF_RECORD", indexName: "FND_ATM_ATTACHMENT_REF_RECORD_N1") {
            column(name: "RECORD_ID")
        }

    }



}