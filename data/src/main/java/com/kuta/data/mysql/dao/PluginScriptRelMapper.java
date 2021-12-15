/**
 * copyright (c) 2020 Kuta Service Framework
 * @author: mybatis generator
 */
package com.kuta.data.mysql.dao;

import com.kuta.data.mysql.pojo.PluginScriptRel;
import com.kuta.data.mysql.pojo.PluginScriptRelExample;
import com.kuta.data.mysql.pojo.PluginScriptRelKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PluginScriptRelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Script_Rel
     * @date 2021-12-13 00:27:34
     */
    long countByExample(PluginScriptRelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Script_Rel
     * @date 2021-12-13 00:27:34
     */
    int deleteByExample(PluginScriptRelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Script_Rel
     * @date 2021-12-13 00:27:34
     */
    int deleteByPrimaryKey(PluginScriptRelKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Script_Rel
     * @date 2021-12-13 00:27:34
     */
    int insert(PluginScriptRel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Script_Rel
     * @date 2021-12-13 00:27:34
     */
    int insertSelective(PluginScriptRel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Script_Rel
     * @date 2021-12-13 00:27:34
     */
    List<PluginScriptRel> selectByExample(PluginScriptRelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Script_Rel
     * @date 2021-12-13 00:27:34
     */
    PluginScriptRel selectByPrimaryKey(PluginScriptRelKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Script_Rel
     * @date 2021-12-13 00:27:34
     */
    int updateByExampleSelective(@Param("record") PluginScriptRel record, @Param("example") PluginScriptRelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Script_Rel
     * @date 2021-12-13 00:27:34
     */
    int updateByExample(@Param("record") PluginScriptRel record, @Param("example") PluginScriptRelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Script_Rel
     * @date 2021-12-13 00:27:34
     */
    int updateByPrimaryKeySelective(PluginScriptRel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Script_Rel
     * @date 2021-12-13 00:27:34
     */
    int updateByPrimaryKey(PluginScriptRel record);

    /**
     * get the max value of 索引
     */
    Short maxIndex(@Param("pid") Integer pid);

    /**
     * 这是Mybatis Generator拓展插件生成的方法(请勿删除).
     * This method corresponds to the database table BS_Plugin_Script_Rel
     *
     * @mbg.generated
     * @author hewei
     */
    int batchInsert(@Param("list") List<PluginScriptRel> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Script_Rel
     * @date 2021-12-13 00:27:34
     */
    int updateWithOptimisticLock(PluginScriptRel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Script_Rel
     * @date 2021-12-13 00:27:34
     */
    int batchUpdate(@Param("recordList") List<PluginScriptRel> recordList);
}