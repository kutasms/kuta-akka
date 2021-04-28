/**
 * copyright (c) 2020 Kuta Service Framework
 * @author: mybatis generator
 */
package com.kuta.database.mysql.dao;

import com.kuta.database.mysql.pojo.PluginOrganization;
import com.kuta.database.mysql.pojo.PluginOrganizationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PluginOrganizationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org
     * @date 2021-03-24 18:00:39
     */
    long countByExample(PluginOrganizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org
     * @date 2021-03-24 18:00:39
     */
    int deleteByExample(PluginOrganizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org
     * @date 2021-03-24 18:00:39
     */
    int deleteByPrimaryKey(Integer poid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org
     * @date 2021-03-24 18:00:39
     */
    int insert(PluginOrganization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org
     * @date 2021-03-24 18:00:39
     */
    int insertSelective(PluginOrganization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org
     * @date 2021-03-24 18:00:39
     */
    List<PluginOrganization> selectByExample(PluginOrganizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org
     * @date 2021-03-24 18:00:39
     */
    PluginOrganization selectByPrimaryKey(Integer poid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org
     * @date 2021-03-24 18:00:39
     */
    int updateByExampleSelective(@Param("record") PluginOrganization record, @Param("example") PluginOrganizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org
     * @date 2021-03-24 18:00:39
     */
    int updateByExample(@Param("record") PluginOrganization record, @Param("example") PluginOrganizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org
     * @date 2021-03-24 18:00:39
     */
    int updateByPrimaryKeySelective(PluginOrganization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org
     * @date 2021-03-24 18:00:39
     */
    int updateByPrimaryKey(PluginOrganization record);

    /**
     * 这是Mybatis Generator拓展插件生成的方法(请勿删除).
     * This method corresponds to the database table BS_Plugin_Org
     *
     * @mbg.generated
     * @author hewei
     */
    int batchInsert(@Param("list") List<PluginOrganization> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org
     * @date 2021-03-24 18:00:39
     */
    int batchUpdate(@Param("recordList") List<PluginOrganization> recordList);
}