/**
 * copyright (c) 2020 Kuta Service Framework
 * @author: mybatis generator
 */
package com.kuta.database.mysql.dao;

import com.kuta.database.mysql.pojo.RolePermRelExample;
import com.kuta.database.mysql.pojo.RolePermRelKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePermRelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_RolePermRel
     * @date 2021-03-24 18:00:39
     */
    long countByExample(RolePermRelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_RolePermRel
     * @date 2021-03-24 18:00:39
     */
    int deleteByExample(RolePermRelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_RolePermRel
     * @date 2021-03-24 18:00:39
     */
    int deleteByPrimaryKey(RolePermRelKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_RolePermRel
     * @date 2021-03-24 18:00:39
     */
    int insert(RolePermRelKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_RolePermRel
     * @date 2021-03-24 18:00:39
     */
    int insertSelective(RolePermRelKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_RolePermRel
     * @date 2021-03-24 18:00:39
     */
    List<RolePermRelKey> selectByExample(RolePermRelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_RolePermRel
     * @date 2021-03-24 18:00:39
     */
    int updateByExampleSelective(@Param("record") RolePermRelKey record, @Param("example") RolePermRelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_RolePermRel
     * @date 2021-03-24 18:00:39
     */
    int updateByExample(@Param("record") RolePermRelKey record, @Param("example") RolePermRelExample example);

    /**
     * 这是Mybatis Generator拓展插件生成的方法(请勿删除).
     * This method corresponds to the database table BS_RolePermRel
     *
     * @mbg.generated
     * @author hewei
     */
    int batchInsert(@Param("list") List<RolePermRelKey> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_RolePermRel
     * @date 2021-03-24 18:00:39
     */
    int batchUpdate(@Param("recordList") List<RolePermRelKey> recordList);
}