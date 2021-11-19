/**
 * copyright (c) 2020 Kuta Service Framework
 * @author: mybatis generator
 */
package com.kuta.data.mysql.dao;

import com.kuta.data.mysql.pojo.Tag;
import com.kuta.data.mysql.pojo.TagExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TagMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Tag
     * @date 2021-11-18 12:08:30
     */
    long countByExample(TagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Tag
     * @date 2021-11-18 12:08:30
     */
    int deleteByExample(TagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Tag
     * @date 2021-11-18 12:08:30
     */
    int deleteByPrimaryKey(Long tid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Tag
     * @date 2021-11-18 12:08:30
     */
    int insert(Tag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Tag
     * @date 2021-11-18 12:08:30
     */
    int insertSelective(Tag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Tag
     * @date 2021-11-18 12:08:30
     */
    List<Tag> selectByExample(TagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Tag
     * @date 2021-11-18 12:08:30
     */
    Tag selectByPrimaryKey(Long tid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Tag
     * @date 2021-11-18 12:08:30
     */
    int updateByExampleSelective(@Param("record") Tag record, @Param("example") TagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Tag
     * @date 2021-11-18 12:08:30
     */
    int updateByExample(@Param("record") Tag record, @Param("example") TagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Tag
     * @date 2021-11-18 12:08:30
     */
    int updateByPrimaryKeySelective(Tag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Tag
     * @date 2021-11-18 12:08:30
     */
    int updateByPrimaryKey(Tag record);

    /**
     * 这是Mybatis Generator拓展插件生成的方法(请勿删除).
     * This method corresponds to the database table BS_Tag
     *
     * @mbg.generated
     * @author hewei
     */
    int batchInsert(@Param("list") List<Tag> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Tag
     * @date 2021-11-18 12:08:30
     */
    int batchUpdate(@Param("recordList") List<Tag> recordList);
}