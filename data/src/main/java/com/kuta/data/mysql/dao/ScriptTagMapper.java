/**
 * copyright (c) 2020 Kuta Service Framework
 * @author: mybatis generator
 */
package com.kuta.data.mysql.dao;

import com.kuta.data.mysql.pojo.ScriptTagExample;
import com.kuta.data.mysql.pojo.ScriptTagKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScriptTagMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script_Tag
     * @date 2021-11-18 12:08:30
     */
    long countByExample(ScriptTagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script_Tag
     * @date 2021-11-18 12:08:30
     */
    int deleteByExample(ScriptTagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script_Tag
     * @date 2021-11-18 12:08:30
     */
    int deleteByPrimaryKey(ScriptTagKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script_Tag
     * @date 2021-11-18 12:08:30
     */
    int insert(ScriptTagKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script_Tag
     * @date 2021-11-18 12:08:30
     */
    int insertSelective(ScriptTagKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script_Tag
     * @date 2021-11-18 12:08:30
     */
    List<ScriptTagKey> selectByExample(ScriptTagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script_Tag
     * @date 2021-11-18 12:08:30
     */
    int updateByExampleSelective(@Param("record") ScriptTagKey record, @Param("example") ScriptTagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script_Tag
     * @date 2021-11-18 12:08:30
     */
    int updateByExample(@Param("record") ScriptTagKey record, @Param("example") ScriptTagExample example);

    /**
     * 这是Mybatis Generator拓展插件生成的方法(请勿删除).
     * This method corresponds to the database table BS_Script_Tag
     *
     * @mbg.generated
     * @author hewei
     */
    int batchInsert(@Param("list") List<ScriptTagKey> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script_Tag
     * @date 2021-11-18 12:08:30
     */
    int batchUpdate(@Param("recordList") List<ScriptTagKey> recordList);
}