package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-06-05-acc-init-table-migration.groovy') {


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_BATCH") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_BATCH_S', startValue: "10001")
        }

        createTable(tableName: "ACC_BATCH", remarks: "核算引擎_核算批次") {

            column(name: "BATCH_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "批次ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_BATCH_PK")
            }
            column(name: "BATCH_CODE", type: "VARCHAR(30)", remarks: "批次代码") { constraints(nullable: "false") }
            column(name: "BATCH_NAME", type: "VARCHAR(200)", remarks: "批次名称") { constraints(nullable: "false") }
            column(name: "MODEL_ID", type: "BIGINT", remarks: "模型ID") { constraints(nullable: "false") }
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

        addUniqueConstraint(columnNames: "BATCH_CODE", tableName: "ACC_BATCH", constraintName: "ACC_BATCH_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_BATCH_BIZ_ASSIGN") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_BATCH_BIZ_ASSIGN_S', startValue: "10001")
        }

        createTable(tableName: "ACC_BATCH_BIZ_ASSIGN", remarks: "会计引擎_凭证批权限规则分配") {

            column(name: "ASSIGN_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "分配ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_BATCH_BIZ_ASSIGN_PK")
            }
            column(name: "BATCH_ID", type: "BIGINT", remarks: "凭证批ID") { constraints(nullable: "false") }
            column(name: "BUSINESS_RULE_ID", type: "BIGINT", remarks: "权限规则ID") { constraints(nullable: "false") }
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

        addUniqueConstraint(columnNames: "BATCH_ID,BUSINESS_RULE_ID", tableName: "ACC_BATCH_BIZ_ASSIGN", constraintName: "ACC_BATCH_BIZ_ASSIGN_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_JE_TYPE_FIELD") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_JE_TYPE_FIELD_S', startValue: "10001")
        }

        createTable(tableName: "ACC_JE_TYPE_FIELD", remarks: "核算引擎_分录类型字段") {

            column(name: "FIELD_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "字段ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_JE_TYPE_FIELD_PK")
            }
            column(name: "JE_TYPE_ID", type: "BIGINT", remarks: "分录类型ID") { constraints(nullable: "false") }
            column(name: "FIELD_CODE", type: "VARCHAR(30)", remarks: "字段代码") { constraints(nullable: "false") }
            column(name: "FIELD_NAME", type: "VARCHAR(200)", remarks: "字段名称") { constraints(nullable: "false") }
            column(name: "FIELD_SEQ", type: "BIGINT", remarks: "字段顺序") { constraints(nullable: "false") }
            column(name: "TABLE_FIELD_NAME", type: "VARCHAR(200)", remarks: "表字段名称") { constraints(nullable: "false") }
            column(name: "DTO_FIELD_NAME", type: "VARCHAR(200)", remarks: "dto字段名称") { constraints(nullable: "false") }
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

        addUniqueConstraint(columnNames: "JE_TYPE_ID,FIELD_CODE", tableName: "ACC_JE_TYPE_FIELD", constraintName: "ACC_JE_TYPE_FIELD_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_JOURNAL_ENTRY_TYPE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_JOURNAL_ENTRY_TYPE_S', startValue: "10001")
        }

        createTable(tableName: "ACC_JOURNAL_ENTRY_TYPE", remarks: "核算引擎_分录类型") {

            column(name: "JE_TYPE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "分录类型ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_JOURNAL_ENTRY_TYPE_PK")
            }
            column(name: "JE_TYPE_CODE", type: "VARCHAR(30)", remarks: "分录类型代码") { constraints(nullable: "false") }
            column(name: "JE_TYPE_NAME", type: "VARCHAR(200)", remarks: "分录类型名称") { constraints(nullable: "false") }
            column(name: "TABLE_NAME", type: "VARCHAR(200)", remarks: "表名称") { constraints(nullable: "false") }
            column(name: "DTO_NAME", type: "VARCHAR(200)", remarks: "DTO名称") { constraints(nullable: "false") }
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

        addUniqueConstraint(columnNames: "JE_TYPE_CODE", tableName: "ACC_JOURNAL_ENTRY_TYPE", constraintName: "ACC_JOURNAL_ENTRY_TYPE_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_MATCH_GROUP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_MATCH_GROUP_S', startValue: "10001")
        }

        createTable(tableName: "ACC_MATCH_GROUP", remarks: "核算引擎_匹配组") {

            column(name: "GROUP_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "匹配组ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_MATCH_GROUP_PK")
            }
            column(name: "GROUP_CODE", type: "VARCHAR(30)", remarks: "匹配组代码") { constraints(nullable: "false") }
            column(name: "GROUP_NAME", type: "VARCHAR(200)", remarks: "匹配组名称") { constraints(nullable: "false") }
            column(name: "JE_CODE", type: "VARCHAR(30)", remarks: "分录代码") { constraints(nullable: "false") }
            column(name: "GROUP_SEQ", type: "BIGINT", remarks: "匹配组顺序") { constraints(nullable: "false") }
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

        addUniqueConstraint(columnNames: "GROUP_CODE", tableName: "ACC_MATCH_GROUP", constraintName: "ACC_MATCH_GROUP_U1")
        addUniqueConstraint(columnNames: "GROUP_SEQ,JE_CODE", tableName: "ACC_MATCH_GROUP", constraintName: "ACC_MATCH_GROUP_U2")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_MATCH_GROUP_DETAIL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_MATCH_GROUP_DETAIL_S', startValue: "10001")
        }

        createTable(tableName: "ACC_MATCH_GROUP_DETAIL", remarks: "核算引擎_匹配组明细") {

            column(name: "DETAIL_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "明细ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_MATCH_GROUP_DETAIL_PK")
            }
            column(name: "GROUP_ID", type: "BIGINT", remarks: "匹配组ID") { constraints(nullable: "false") }
            column(name: "CONDITION_CODE", type: "VARCHAR(30)", remarks: "匹配项代码") { constraints(nullable: "false") }
            column(name: "CONDITION_SEQ", type: "BIGINT", remarks: "匹配项顺序") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "GROUP_ID,CONDITION_SEQ", tableName: "ACC_MATCH_GROUP_DETAIL", constraintName: "ACC_MATCH_GROUP_DETAIL_U1")
        addUniqueConstraint(columnNames: "CONDITION_CODE,GROUP_ID", tableName: "ACC_MATCH_GROUP_DETAIL", constraintName: "ACC_MATCH_GROUP_DETAIL_U2")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_MATCH_ITEM") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_MATCH_ITEM_S', startValue: "10001")
        }

        createTable(tableName: "ACC_MATCH_ITEM", remarks: "核算引擎_用途代码匹配项") {

            column(name: "ITEM_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "匹配项ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_MATCH_ITEM_PK")
            }
            column(name: "GROUP_ID", type: "BIGINT", remarks: "匹配组ID") { constraints(nullable: "false") }
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目ID") { constraints(nullable: "false") }
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
        createIndex(tableName: "ACC_MATCH_ITEM", indexName: "ACC_MATCH_ITEM_N1") {
            column(name: "GROUP_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_MATCH_ITEM_DETAIL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_MATCH_ITEM_DETAIL_S', startValue: "10001")
        }

        createTable(tableName: "ACC_MATCH_ITEM_DETAIL", remarks: "核算引擎_匹配项明细") {

            column(name: "DETAIL_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "明细ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_MATCH_ITEM_DETAIL_PK")
            }
            column(name: "ITEM_ID", type: "BIGINT", remarks: "匹配项ID") { constraints(nullable: "false") }
            column(name: "GROUP_DETAIL_ID", type: "BIGINT", remarks: "匹配组明细ID") { constraints(nullable: "false") }
            column(name: "CONDITION_VALUE_ID", type: "BIGINT", remarks: "条件值ID")
            column(name: "CONDITION_VALUE_CODE", type: "VARCHAR(200)", remarks: "条件值代码")
            column(name: "CONDITION_VALUE_NAME", type: "VARCHAR(2000)", remarks: "条件值名称")
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
        createIndex(tableName: "ACC_MATCH_ITEM_DETAIL", indexName: "ACC_MATCH_ITEM_DETAIL_N1") {
            column(name: "ITEM_ID")
        }
        createIndex(tableName: "ACC_MATCH_ITEM_DETAIL", indexName: "ACC_MATCH_ITEM_DETAIL_N2") {
            column(name: "GROUP_DETAIL_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_RULE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_RULE_S', startValue: "10001")
        }

        createTable(tableName: "ACC_RULE", remarks: "核算引擎_凭证规则") {

            column(name: "RULE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "规则ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_RULE_PK")
            }
            column(name: "BATCH_ID", type: "BIGINT", remarks: "凭证批ID") { constraints(nullable: "false") }
            column(name: "RULE_CODE", type: "VARCHAR(30)", remarks: "规则代码") { constraints(nullable: "false") }
            column(name: "RULE_NAME", type: "VARCHAR(200)", remarks: "规则名称") { constraints(nullable: "false") }
            column(name: "MODEL_ID", type: "BIGINT", remarks: "模型ID") { constraints(nullable: "false") }
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

        addUniqueConstraint(columnNames: "BATCH_ID,RULE_CODE", tableName: "ACC_RULE", constraintName: "ACC_RULE_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_RULE_BIZ_ASSIGN") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_RULE_BIZ_ASSIGN_S', startValue: "10001")
        }

        createTable(tableName: "ACC_RULE_BIZ_ASSIGN", remarks: "核算引擎_会计规则分配权限规则") {

            column(name: "ASSIGIN_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "分配ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_RULE_BIZ_ASSIGN_PK")
            }
            column(name: "RULE_ID", type: "BIGINT", remarks: "会计规则ID") { constraints(nullable: "false") }
            column(name: "BUSINESS_RULE_ID", type: "BIGINT", remarks: "权限规则ID") { constraints(nullable: "false") }
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

        addUniqueConstraint(columnNames: "RULE_ID,BUSINESS_RULE_ID", tableName: "ACC_RULE_BIZ_ASSIGN", constraintName: "ACC_RULE_BIZ_ASSIGN_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_RULE_ENTITY") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_RULE_ENTITY_S', startValue: "10001")
        }

        createTable(tableName: "ACC_RULE_ENTITY", remarks: "核算引擎_会计规则关联实体") {

            column(name: "ENTITY_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "实体ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_RULE_ENTITY_PK")
            }
            column(name: "RULE_ID", type: "BIGINT", remarks: "会计规则ID") { constraints(nullable: "false") }
            column(name: "ENTITY_CODE", type: "VARCHAR(30)", remarks: "实体代码") { constraints(nullable: "false") }
            column(name: "ENTITY_NAME", type: "VARCHAR(200)", remarks: "实体名称") { constraints(nullable: "false") }
            column(name: "ENTITY_TYPE", type: "VARCHAR(30)", remarks: "实体类型") { constraints(nullable: "false") }
            column(name: "MOD_ENTITY_ID", type: "BIGINT", remarks: "模型实体ID") { constraints(nullable: "false") }
            column(name: "LOGIC_SCOPE", type: "VARCHAR(30)", remarks: "逻辑范围")
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
        createIndex(tableName: "ACC_RULE_ENTITY", indexName: "ACC_RULE_ENTITY_N1") {
            column(name: "MOD_ENTITY_ID")
        }

        addUniqueConstraint(columnNames: "RULE_ID,ENTITY_CODE", tableName: "ACC_RULE_ENTITY", constraintName: "ACC_RULE_ENTITY_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_RULE_FIELD") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_RULE_FIELD_S', startValue: "10001")
        }

        createTable(tableName: "ACC_RULE_FIELD", remarks: "核算引擎_会计规则字段") {

            column(name: "FIELD_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "字段ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_RULE_FIELD_PK")
            }
            column(name: "ENTITY_ID", type: "BIGINT", remarks: "实体ID") { constraints(nullable: "false") }
            column(name: "MOD_FIELD_ID", type: "BIGINT", remarks: "模型字段ID")
            column(name: "FIELD_CODE", type: "VARCHAR(30)", remarks: "字段代码") { constraints(nullable: "false") }
            column(name: "FIELD_NAME", type: "VARCHAR(200)", remarks: "字段名称") { constraints(nullable: "false") }
            column(name: "FIELD_DATA_TYPE", type: "VARCHAR(30)", remarks: "字段数据类型") { constraints(nullable: "false") }
            column(name: "FIELD_TYPE", type: "VARCHAR(30)", remarks: "字段类型") { constraints(nullable: "false") }
            column(name: "REF_ENTITY_FIELD_ID", type: "BIGINT", remarks: "关联实体字段ID")
            column(name: "REF_JE_FIELD_ID", type: "BIGINT", remarks: "关联分录字段ID")
            column(name: "LOGICAL_TYPE", type: "VARCHAR(30)", remarks: "逻辑类型")
            column(name: "CONSTANT_VALUE", type: "VARCHAR(200)", remarks: "常量")
            column(name: "FAST_CODE", type: "VARCHAR(200)", remarks: "快码")
            column(name: "FORMULA", type: "VARCHAR(2000)", remarks: "函数")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "FIELD_CODE,ENTITY_ID", tableName: "ACC_RULE_FIELD", constraintName: "ACC_RULE_FIELD_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_RULE_JE_FIELD") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_RULE_JE_FIELD_S', startValue: "10001")
        }

        createTable(tableName: "ACC_RULE_JE_FIELD", remarks: "核算引擎_分录字段") {

            column(name: "FIELD_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "字段ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_RULE_JE_FIELD_PK")
            }
            column(name: "JE_ID", type: "BIGINT", remarks: "分录ID") { constraints(nullable: "false") }
            column(name: "FIELD_SEQ", type: "BIGINT", remarks: "字段顺序") { constraints(nullable: "false") }
            column(name: "FIELD_CODE", type: "VARCHAR(30)", remarks: "字段代码") { constraints(nullable: "false") }
            column(name: "FIELD_NAME", type: "VARCHAR(200)", remarks: "字段名称") { constraints(nullable: "false") }
            column(name: "FIELD_DATA_TYPE", type: "VARCHAR(30)", remarks: "字段数据类型") { constraints(nullable: "false") }
            column(name: "FIELD_TYPE", type: "VARCHAR(30)", remarks: "字段类型") { constraints(nullable: "false") }
            column(name: "REF_FIELD_ID", type: "BIGINT", remarks: "关联实体字段ID")
            column(name: "LOGIC_TYPE", type: "VARCHAR(30)", remarks: "逻辑类型")
            column(name: "CONSTANT_VALUE", type: "VARCHAR(200)", remarks: "常量")
            column(name: "FAST_CODE", type: "VARCHAR(200)", remarks: "快码")
            column(name: "FORMULA", type: "VARCHAR(2000)", remarks: "公式")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "JE_ID,FIELD_SEQ", tableName: "ACC_RULE_JE_FIELD", constraintName: "ACC_RULE_JE_FIELD_U1")
        addUniqueConstraint(columnNames: "JE_ID,FIELD_CODE", tableName: "ACC_RULE_JE_FIELD", constraintName: "ACC_RULE_JE_FIELD_U2")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_RULE_JOURNAL_ENTRY") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_RULE_JOURNAL_ENTRY_S', startValue: "10001")
        }

        createTable(tableName: "ACC_RULE_JOURNAL_ENTRY", remarks: "核算引擎_会计分录") {

            column(name: "JE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "分录ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_RULE_JOURNAL_ENTRY_PK")
            }
            column(name: "ENTITY_ID", type: "BIGINT", remarks: "实体ID") { constraints(nullable: "false") }
            column(name: "JE_CODE", type: "VARCHAR(30)", remarks: "分录代码") { constraints(nullable: "false") }
            column(name: "JE_NAME", type: "VARCHAR(200)", remarks: "分录名称") { constraints(nullable: "false") }
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
        createIndex(tableName: "ACC_RULE_JOURNAL_ENTRY", indexName: "ACC_RULE_JOURNAL_ENTRY_N1") {
            column(name: "ENTITY_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_RULE_REF_ENTITY") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_RULE_REF_ENTITY_S', startValue: "10001")
        }

        createTable(tableName: "ACC_RULE_REF_ENTITY", remarks: "核算引擎_会计规则关联实体") {

            column(name: "REF_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "关联ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_RULE_REF_ENTITY_PK")
            }
            column(name: "ENTITY_ID", type: "BIGINT", remarks: "实体ID") { constraints(nullable: "false") }
            column(name: "REF_ENTITY_ID", type: "BIGINT", remarks: "关联实体ID") { constraints(nullable: "false") }
            column(name: "REF_TYPE", type: "VARCHAR(30)", remarks: "关联类型") { constraints(nullable: "false") }
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
        createIndex(tableName: "ACC_RULE_REF_ENTITY", indexName: "ACC_RULE_REF_ENTITY_N1") {
            column(name: "ENTITY_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_RULE_REF_ENTITY_BIZ_ASGN") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_RULE_REF_ENTITY_BIZ_ASGN_S', startValue: "10001")
        }

        createTable(tableName: "ACC_RULE_REF_ENTITY_BIZ_ASGN", remarks: "核算引擎_会计规则关联实体_关联权限规则") {

            column(name: "ASSIGN_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "分配ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_RULE_REF_ENTITY_BIZ_ASGN_PK")
            }
            column(name: "REF_ID", type: "BIGINT", remarks: "关联ID") { constraints(nullable: "false") }
            column(name: "BUSINESS_RULE_ID", type: "BIGINT", remarks: "权限规则ID") { constraints(nullable: "false") }
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
        createIndex(tableName: "ACC_RULE_REF_ENTITY_BIZ_ASGN", indexName: "ACC_RULE_REF_ENTITY_BIZ_ASGN_N1") {
            column(name: "REF_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_RULE_REF_JE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_RULE_REF_JE_S', startValue: "10001")
        }

        createTable(tableName: "ACC_RULE_REF_JE", remarks: "核算引擎_会计规则关联分录") {

            column(name: "REF_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "关联ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_RULE_REF_JE_PK")
            }
            column(name: "ENTITY_ID", type: "BIGINT", remarks: "实体ID") { constraints(nullable: "false") }
            column(name: "JE_ID", type: "BIGINT", remarks: "分录ID") { constraints(nullable: "false") }
            column(name: "REF_TYPE", type: "VARCHAR(30)", remarks: "关联类型") { constraints(nullable: "false") }
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
        createIndex(tableName: "ACC_RULE_REF_JE", indexName: "ACC_RULE_REF_JE_N1") {
            column(name: "ENTITY_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-ACC_RULE_REF_JE_BIZ_ASGN") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'ACC_RULE_REF_JE_BIZ_ASGN_S', startValue: "10001")
        }

        createTable(tableName: "ACC_RULE_REF_JE_BIZ_ASGN", remarks: "核算引擎_会计规则关联分录_分配权限规则") {

            column(name: "ASSIGN_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "分配ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACC_RULE_REF_JE_BIZ_ASGN_PK")
            }
            column(name: "REF_ID", type: "BIGINT", remarks: "关联ID") { constraints(nullable: "false") }
            column(name: "BUSINESS_RULE_ID", type: "BIGINT", remarks: "权限规则ID") { constraints(nullable: "false") }
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
        createIndex(tableName: "ACC_RULE_REF_JE_BIZ_ASGN", indexName: "ACC_RULE_REF_JE_BIZ_ASGN_N1") {
            column(name: "REF_ID")
        }

    }


}