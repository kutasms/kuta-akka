package com.kuta.data.mysql.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface CommonMapper {
    /**
               * 使用information_schema检查表是否存在
     * @param tableSchema
     * @param tableName
     * @return
     */
    Integer checkTableExistsWithSchema(@Param("tableSchema")String tableSchema, @Param("tableName")String tableName);
    
    /**
                  * 使用show tables检查表是否存在
     * @param tableName
     * @return
     */
    Map<String, String> checkTableExistsWithShow(@Param("tableName")String tableName);
}
