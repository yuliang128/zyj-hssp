package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-4-23-FND-init-table-migration.groovy') {


    changeSet(author: "jialin.xing@hand-china.com", id: "2019-04-23-FND_MO_CLASS_SET") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'FND_MO_CLASS_SET_S', startValue: "10001")
        }

        createTable(tableName: "FND_MO_CLASS_SET", remarks: "管理组织级分类集") {

            column(name: "MO_CLASS_SET_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "FND_MO_CLASS_SET_PK")
            }
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID") { constraints(nullable: "false") }
            column(name: "CLASS_SET_CODE", type: "VARCHAR(30)", remarks: "分类集代码") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "分类集描述")
            column(name: "LEVEL_DEPTH", type: "VARCHAR(1)", remarks: "分类层级数 syscode(FND_MO_CLASS_SETS_LEVEL_DEPTH)") {
                constraints(nullable: "false")
            }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "CLASS_SET_CODE", tableName: "FND_MO_CLASS_SET", constraintName: "FND_MO_CLASS_SET_U1")
    }

    changeSet(author: "jialin.xing@hand-china.com", id: "2019-04-23-FND_MO_CLASS_SET_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'FND_MO_CLASS_SET_TL_S', startValue: "10001")
        }

        createTable(tableName: "FND_MO_CLASS_SET_TL", remarks: "管理组织级分类集多语言表") {

            column(name: "MO_CLASS_SET_ID", type: "BIGINT", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "分类集描述")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "jialin.xing@hand-china.com", id: "2019-04-23-FND_MO_CLASS") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'FND_MO_CLASS_S', startValue: "10001")
        }

        createTable(tableName: "FND_MO_CLASS", remarks: "管理组织级分类") {

            column(name: "CLASS_ID", type: "BIGINT",autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "FND_MO_CLASS_PK")
            }
            column(name: "MO_CLASS_SET_ID", type: "BIGINT", remarks: "分类集ID") { constraints(nullable: "false") }
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID") { constraints(nullable: "false") }
            column(name: "CLASS_CODE", type: "VARCHAR(30)", remarks: "分类代码") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "分类描述")
            column(name: "CLASS_LEVEL", type: "VARCHAR(1)", remarks: "分类层级") { constraints(nullable: "false") }
            column(name: "PARENT_CLASS_ID", type: "NUMBER", remarks: "上级分类ID")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "NUMBER", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "NUMBER", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "FND_MO_CLASS", indexName: "FND_MO_CLASS_N1") {
            column(name: "PARENT_CLASS_ID")
        }

        addUniqueConstraint(columnNames: "CLASS_CODE", tableName: "FND_MO_CLASS", constraintName: "FND_MO_CLASS_U1")
    }

    changeSet(author: "jialin.xing@hand-china.com", id: "2019-04-23-FND_MO_CLASS_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'FND_MO_CLASS_TL_S', startValue: "10001")
        }

        createTable(tableName: "FND_MO_CLASS_TL", remarks: "管理组织级分类多语言表") {

            column(name: "CLASS_ID", type: "NUMBER", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "分类描述")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

}