/**
 * copyright (c) 2020 Kuta Service Framework
 * @author: mybatis generator
 */
package com.kuta.database.mysql.dao;

import com.kuta.database.mysql.pojo.Script;
import com.kuta.database.mysql.pojo.ScriptExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScriptMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script
     * @date 2021-03-24 18:00:39
     */
    long countByExample(ScriptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script
     * @date 2021-03-24 18:00:39
     */
    int deleteByExample(ScriptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script
     * @date 2021-03-24 18:00:39
     */
    int deleteByPrimaryKey(Long sid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script
     * @date 2021-03-24 18:00:39
     */
    int insert(Script record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script
     * @date 2021-03-24 18:00:39
     */
    int insertSelective(Script record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script
     * @date 2021-03-24 18:00:39
     */
    List<Script> selectByExample(ScriptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script
     * @date 2021-03-24 18:00:39
     */
    Script selectByPrimaryKey(Long sid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script
     * @date 2021-03-24 18:00:39
     */
    int updateByExampleSelective(@Param("record") Script record, @Param("example") ScriptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script
     * @date 2021-03-24 18:00:39
     */
    int updateByExample(@Param("record") Script record, @Param("example") ScriptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script
     * @date 2021-03-24 18:00:39
     */
    int updateByPrimaryKeySelective(Script record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script
     * @date 2021-03-24 18:00:39
     */
    int updateByPrimaryKey(Script record);

    /**
     * 这是Mybatis Generator拓展插件生成的方法(请勿删除).
     * This method corresponds to the database table BS_Script
     *
     * @mbg.generated
     * @author hewei
     */
    int batchInsert(@Param("list") List<Script> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script
     * @date 2021-03-24 18:00:39
     */
    int batchUpdate(@Param("recordList") List<Script> recordList);
}