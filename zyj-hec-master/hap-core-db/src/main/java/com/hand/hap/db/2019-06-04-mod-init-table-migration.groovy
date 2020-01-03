package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-06-05-mod-init-table-migration.groovy') {


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-06-MOD_ENTITY") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'MOD_ENTITY_S', startValue: "10001")
        }

        createTable(tableName: "MOD_ENTITY", remarks: "模型_模型实体") {

            column(name: "ENTITY_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "实体ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MOD_ENTITY_PK")
            }
            column(name: "MODEL_ID", type: "BIGINT", remarks: "模型ID") { constraints(nullable: "false") }
            column(name: "ENTITY_CODE", type: "VARCHAR(30)", remarks: "实体代码") { constraints(nullable: "false") }
            column(name: "ENTITY_NAME", type: "VARCHAR(200)", remarks: "实体名称")
            column(name: "ENTITY_LEVEL", type: "BIGINT", remarks: "实体层级")
            column(name: "ENTITY_SEQ", type: "BIGINT", remarks: "实体顺序")
            column(name: "PARENT_ENTITY_ID", type: "BIGINT", remarks: "父模型ID")
            column(name: "RELATION_TYPE", type: "VARCHAR(30)", remarks: "关联类型")
            column(name: "REF_TABLE", type: "VARCHAR(200)", remarks: "关联表")
            column(name: "REF_DTO", type: "VARCHAR(500)", remarks: "关联DTO")
            column(name: "MULTI_LANGUAGE_FLAG", type: "VARCHAR(1)", remarks: "多语言标志")
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
        createIndex(tableName: "MOD_ENTITY", indexName: "MOD_ENTITY_N1") {
            column(name: "MODEL_ID")
        }

        addUniqueConstraint(columnNames: "ENTITY_CODE,MODEL_ID", tableName: "MOD_ENTITY", constraintName: "MOD_ENTITY_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-MOD_ENTITY_REFERENCE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'MOD_ENTITY_REFERENCE_S', startValue: "10001")
        }

        createTable(tableName: "MOD_ENTITY_REFERENCE", remarks: "模型_跨模型实体间关联") {

            column(name: "REFERENCE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "关联ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MOD_ENTITY_REFERENCE_PK")
            }
            column(name: "ENTITY_ID", type: "BIGINT", remarks: "实体ID") { constraints(nullable: "false") }
            column(name: "REF_MODEL_ID", type: "BIGINT", remarks: "关联模型ID") { constraints(nullable: "false") }
            column(name: "REF_ENTITY_ID", type: "BIGINT", remarks: "关联实体ID") { constraints(nullable: "false") }
            column(name: "REF_TYPE", type: "VARCHAR(30)", remarks: "关联类型") { constraints(nullable: "false") }
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
        createIndex(tableName: "MOD_ENTITY_REFERENCE", indexName: "MOD_ENTITY_REFERENCE_N1") {
            column(name: "ENTITY_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-MOD_ENTITY_REF_DETAIL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'MOD_ENTITY_REF_DETAIL_S', startValue: "10001")
        }

        createTable(tableName: "MOD_ENTITY_REF_DETAIL", remarks: "模型_实体关联明细") {

            column(name: "DETAIL_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "明细ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MOD_ENTITY_REF_DETAIL_PK")
            }
            column(name: "REFERENCE_ID", type: "BIGINT", remarks: "关联ID") { constraints(nullable: "false") }
            column(name: "SOURCE_FIELD_ID", type: "BIGINT", remarks: "来源字段ID") { constraints(nullable: "false") }
            column(name: "TARGET_FIELD_ID", type: "BIGINT", remarks: "目标字段ID") { constraints(nullable: "false") }
            column(name: "CONDITION_TYPE", type: "VARCHAR(30)", remarks: "条件类型") { constraints(nullable: "false") }
            column(name: "CONDITION_VALUE", type: "VARCHAR(200)", remarks: "条件值")
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
        createIndex(tableName: "MOD_ENTITY_REF_DETAIL", indexName: "MOD_ENTITY_REF_DETAIL_N1") {
            column(name: "REFERENCE_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-06-MOD_ENTITY_RELATION") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'MOD_ENTITY_RELATION_S', startValue: "10001")
        }

        createTable(tableName: "MOD_ENTITY_RELATION", remarks: "模型_实体关系") {

            column(name: "RELATION_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "关联ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MOD_ENTITY_RELATION_PK")
            }
            column(name: "ENTITY_ID", type: "BIGINT", remarks: "实体ID") { constraints(nullable: "false") }
            column(name: "TARGET_ENTITY_ID", type: "BIGINT", remarks: "实体ID") { constraints(nullable: "false") }
            column(name: "SOURCE_FIELD_ID", type: "BIGINT", remarks: "来源字段ID") { constraints(nullable: "false") }
            column(name: "TARGET_FIELD_ID", type: "BIGINT", remarks: "目标字段ID") { constraints(nullable: "false") }
            column(name: "CONDITION_TYPE", type: "VARCHAR(30)", remarks: "条件类型") { constraints(nullable: "false") }
            column(name: "CONDITION_VALUE", type: "VARCHAR(200)", remarks: "条件值")
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

        createIndex(tableName: "MOD_ENTITY_RELATION", indexName: "MOD_ENTITY_RELATION_N1") {
            column(name: "ENTITY_ID")
        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-MOD_FIELD") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'MOD_FIELD_S', startValue: "10001")
        }

        createTable(tableName: "MOD_FIELD", remarks: "模型_业务实体字段") {

            column(name: "FIELD_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "字段ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MOD_FIELD_PK")
            }
            column(name: "ENTITY_ID", type: "BIGINT", remarks: "实体ID") { constraints(nullable: "false") }
            column(name: "FIELD_NAME", type: "VARCHAR(200)", remarks: "字段名称") { constraints(nullable: "false") }
            column(name: "FIELD_SEQ", type: "BIGINT", remarks: "字段顺序")
            column(name: "TABLE_FIELD_NAME", type: "VARCHAR(200)", remarks: "表字段名称")
            column(name: "DTO_FIELD_NAME", type: "VARCHAR(200)", remarks: "dto字段名称")
            column(name: "MULTI_LANGUAGE_FLAG", type: "VARCHAR(1)", remarks: "多语言标志") { constraints(nullable: "false") }
            column(name: "FIELD_DATA_TYPE", type: "VARCHAR(30)", remarks: "字段数据类型") { constraints(nullable: "false") }
            column(name: "FIELD_TYPE", type: "VARCHAR(30)", remarks: "字段类型（PHYSICAL/LOGICAL)") {
                constraints(nullable: "false")
            }
            column(name: "LOGICAL_TYPE", type: "VARCHAR(30)", remarks: "逻辑类型（Lov/Code/Prompt/GroupChild/Constant/MasterData/Program）")
            column(name: "LOGIC_FIELD_ID", type: "BIGINT", remarks: "逻辑字段ID")
            column(name: "LAZY_LOAD", type: "VARCHAR(1)", remarks: "延迟加载")
            column(name: "PK_FLAG", type: "VARCHAR(1)", remarks: "主键字段")
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
        createIndex(tableName: "MOD_FIELD", indexName: "MOD_FIELD_N1") {
            column(name: "ENTITY_ID")
        }

        addUniqueConstraint(columnNames: "FIELD_NAME,ENTITY_ID", tableName: "MOD_FIELD", constraintName: "MOD_FIELD_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-MOD_FIELD_CODE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'MOD_FIELD_CODE_S', startValue: "10001")
        }

        createTable(tableName: "MOD_FIELD_CODE", remarks: "模型_模型字段_CODE") {

            column(name: "FIELD_ID", type: "BIGINT", remarks: "字段ID") { constraints(nullable: "false") }
            column(name: "CODE", type: "VARCHAR(200)", remarks: "代码") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-MOD_FIELD_CONSTANT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'MOD_FIELD_CONSTANT_S', startValue: "10001")
        }

        createTable(tableName: "MOD_FIELD_CONSTANT", remarks: "模型_模型字段_常量") {

            column(name: "FIELD_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "字段ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MOD_FIELD_CONSTANT_PK")
            }
            column(name: "VALUE", type: "VARCHAR(200)", remarks: "值")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-MOD_FIELD_GROUP_CHILD") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'MOD_FIELD_GROUP_CHILD_S', startValue: "10001")
        }

        createTable(tableName: "MOD_FIELD_GROUP_CHILD", remarks: "模型_模型字段_汇总子实体字段") {

            column(name: "FIELD_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "字段ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MOD_FIELD_GROUP_CHILD_PK")
            }
            column(name: "CHILD_ENTITY_ID", type: "BIGINT", remarks: "子实体ID") { constraints(nullable: "false") }
            column(name: "CHILD_FIELD_ID", type: "BIGINT", remarks: "子字段ID") { constraints(nullable: "false") }
            column(name: "GROUP_FUNCTION", type: "VARCHAR(30)", remarks: "分组函数") { constraints(nullable: "false") }
            column(name: "FORMULA", type: "VARCHAR(200)", remarks: "表达式")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-MOD_FIELD_LOV") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'MOD_FIELD_LOV_S', startValue: "10001")
        }

        createTable(tableName: "MOD_FIELD_LOV", remarks: "模型_模型字段_LOV") {

            column(name: "FIELD_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "字段ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MOD_FIELD_LOV_PK")
            }
            column(name: "LOV_CODE", type: "VARCHAR(200)", remarks: "LOV代码") { constraints(nullable: "false") }
            column(name: "LOV_FIELD_NAME", type: "VARCHAR(200)", remarks: "LOV字段名") { constraints(nullable: "false") }
            column(name: "LOV_PARAM_NAME", type: "VARCHAR(200)", remarks: "LOV参数名") { constraints(nullable: "false") }
            column(name: "LOV_PARAM_DATA_TYPE", type: "VARCHAR(30)", remarks: "LOV参数数据类型") {
                constraints(nullable: "false")
            }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-MOD_FIELD_MASTER_DATA") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'MOD_FIELD_MASTER_DATA_S', startValue: "10001")
        }

        createTable(tableName: "MOD_FIELD_MASTER_DATA", remarks: "模型_模型字段_主数据") {

            column(name: "FIELD_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "字段ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MOD_FIELD_MASTER_DATA_PK")
            }
            column(name: "TARGET_ENTITY_ID", type: "BIGINT", remarks: "目标实体ID") { constraints(nullable: "false") }
            column(name: "TARGET_FIELD_ID", type: "BIGINT", remarks: "目标字段ID")
            column(name: "SOURCE_FIELD_2_ID", type: "BIGINT", remarks: "来源字段2ID")
            column(name: "TARGET_FIELD_2_ID", type: "BIGINT", remarks: "目标字段2ID")
            column(name: "SOURCE_FIELD_3_ID", type: "BIGINT", remarks: "来源字段3ID")
            column(name: "TARGET_FIELD_3_ID", type: "BIGINT", remarks: "目标字段3ID")
            column(name: "TARGET_VALUE_FIELD_ID", type: "BIGINT", remarks: "目标值字段ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-MOD_FIELD_PROGRAM") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'MOD_FIELD_PROGRAM_S', startValue: "10001")
        }

        createTable(tableName: "MOD_FIELD_PROGRAM", remarks: "模型_模型字段_程序") {

            column(name: "FIELD_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "字段ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MOD_FIELD_PROGRAM_PK")
            }
            column(name: "FORMULA", type: "VARCHAR(2000)", remarks: "公式") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-06-05-MOD_MODEL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'MOD_MODEL_S', startValue: "10001")
        }

        createTable(tableName: "MOD_MODEL", remarks: "模型_业务建模") {

            column(name: "MODEL_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "模型ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MOD_MODEL_PK")
            }
            column(name: "MODEL_CODE", type: "VARCHAR(30)", remarks: "模型代码") { constraints(nullable: "false") }
            column(name: "MODEL_NAME", type: "VARCHAR(200)", remarks: "模型名称") { constraints(nullable: "false") }
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

        addUniqueConstraint(columnNames: "MODEL_CODE", tableName: "MOD_MODEL", constraintName: "MOD_MODEL_U1")
    }


}