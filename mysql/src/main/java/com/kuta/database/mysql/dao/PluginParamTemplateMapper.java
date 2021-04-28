/**
 * copyright (c) 2020 Kuta Service Framework
 * @author: mybatis generator
 */
package com.kuta.database.mysql.dao;

import com.kuta.database.mysql.pojo.PluginParamTemplate;
import com.kuta.database.mysql.pojo.PluginParamTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PluginParamTemplateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-03-24 18:00:39
     */
    long countByExample(PluginParamTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-03-24 18:00:39
     */
    int deleteByExample(PluginParamTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-03-24 18:00:39
     */
    int deleteByPrimaryKey(Integer pptid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-03-24 18:00:39
     */
    int insert(PluginParamTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-03-24 18:00:39
     */
    int insertSelective(PluginParamTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-03-24 18:00:39
     */
    List<PluginParamTemplate> selectByExample(PluginParamTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-03-24 18:00:39
     */
    PluginParamTemplate selectByPrimaryKey(Integer pptid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-03-24 18:00:39
     */
    int updateByExampleSelective(@Param("record") PluginParamTemplate record, @Param("example") PluginParamTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-03-24 18:00:39
     */
    int updateByExample(@Param("record") PluginParamTemplate record, @Param("example") PluginParamTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-03-24 18:00:39
     */
    int updateByPrimaryKeySelective(PluginParamTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-03-24 18:00:39
     */
    int updateByPrimaryKey(PluginParamTemplate record);

    /**
     * 这是Mybatis Generator拓展插件生成的方法(请勿删除).
     * This method corresponds to the database table BS_Plugin_Param_Template
     *
     * @mbg.generated
     * @author hewei
     */
    int batchInsert(@Param("list") List<PluginParamTemplate> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-03-24 18:00:39
     */
    int batchUpdate(@Param("recordList") List<PluginParamTemplate> recordList);
}