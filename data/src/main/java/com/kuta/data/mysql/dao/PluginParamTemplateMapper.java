/**
 * copyright (c) 2020 Kuta Service Framework
 * @author: mybatis generator
 */
package com.kuta.data.mysql.dao;

import com.kuta.data.mysql.pojo.PluginParamTemplate;
import com.kuta.data.mysql.pojo.PluginParamTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PluginParamTemplateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-11-18 12:08:30
     */
    long countByExample(PluginParamTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-11-18 12:08:30
     */
    int deleteByExample(PluginParamTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-11-18 12:08:30
     */
    int deleteByPrimaryKey(Integer pptid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-11-18 12:08:30
     */
    int insert(PluginParamTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-11-18 12:08:30
     */
    int insertSelective(PluginParamTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-11-18 12:08:30
     */
    List<PluginParamTemplate> selectByExample(PluginParamTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-11-18 12:08:30
     */
    PluginParamTemplate selectByPrimaryKey(Integer pptid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-11-18 12:08:30
     */
    int updateByExampleSelective(@Param("record") PluginParamTemplate record, @Param("example") PluginParamTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-11-18 12:08:30
     */
    int updateByExample(@Param("record") PluginParamTemplate record, @Param("example") PluginParamTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-11-18 12:08:30
     */
    int updateByPrimaryKeySelective(PluginParamTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Param_Template
     * @date 2021-11-18 12:08:30
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
     * @date 2021-11-18 12:08:30
     */
    int batchUpdate(@Param("recordList") List<PluginParamTemplate> recordList);
}