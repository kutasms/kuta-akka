/**
 * copyright (c) 2020 Kuta Service Framework
 * @author: mybatis generator
 */
package com.kuta.data.mysql.pojo;

import com.kuta.base.annotation.PrimaryKey;
import com.kuta.base.database.KutaDBEntity;
import java.io.Serializable;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table BS_Script_Category
 * @date 2021-07-15 12:55:28
 */
public class ScriptCategory extends KutaDBEntity implements Serializable {
    /**
     * @desc 分类编号
     * @jdbcType INTEGER
     * @author mybatis generator
     * @date 2021-07-15 12:55:28
     */
    @PrimaryKey
    private Integer cid;

    /**
     * @desc 名称
     * @jdbcType VARCHAR
     * @author mybatis generator
     * @date 2021-07-15 12:55:28
     */
    private String name;

    /**
     * @desc 上层分类编号
     * @jdbcType INTEGER
     * @author mybatis generator
     * @date 2021-07-15 12:55:28
     */
    private Integer parentCid;

    /**
     * @desc 层级
     * @jdbcType VARCHAR
     * @author mybatis generator
     * @date 2021-07-15 12:55:28
     */
    private String level;

    /**
     * @desc 深度
     * @jdbcType SMALLINT
     * @author mybatis generator
     * @date 2021-07-15 12:55:28
     */
    private Short deep;

    /**
     * @desc 公司编号
     * @jdbcType INTEGER
     * @author mybatis generator
     * @date 2021-07-15 12:55:28
     */
    private Integer oid;

    /**
     * @date 2021-07-15 12:55:28
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script_Category
     * @date 2021-07-15 12:55:28
     */
    public ScriptCategory(Integer cid, String name, Integer parentCid, String level, Short deep, Integer oid) {
        this.cid = cid;
        this.name = name;
        this.parentCid = parentCid;
        this.level = level;
        this.deep = deep;
        this.oid = oid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script_Category
     * @date 2021-07-15 12:55:28
     */
    public ScriptCategory() {
        super();
    }

    /**
     * @return cid : 分类编号
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * @param cid : 分类编号
     */
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    /**
     * @return name : 名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name : 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return parentCid : 上层分类编号
     */
    public Integer getParentCid() {
        return parentCid;
    }

    /**
     * @param parentCid : 上层分类编号
     */
    public void setParentCid(Integer parentCid) {
        this.parentCid = parentCid;
    }

    /**
     * @return level : 层级
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level : 层级
     */
    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    /**
     * @return deep : 深度
     */
    public Short getDeep() {
        return deep;
    }

    /**
     * @param deep : 深度
     */
    public void setDeep(Short deep) {
        this.deep = deep;
    }

    /**
     * @return oid : 公司编号
     */
    public Integer getOid() {
        return oid;
    }

    /**
     * @param oid : 公司编号
     */
    public void setOid(Integer oid) {
        this.oid = oid;
    }
}