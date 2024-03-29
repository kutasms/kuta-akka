/**
 * copyright (c) 2020 Kuta Service Framework
 * @author: mybatis generator
 */
package com.kuta.data.mysql.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.kuta.base.annotation.PrimaryKey;
import com.kuta.base.database.KutaDBEntity;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table BS_Organization
 * @date 2021-12-28 00:51:29
 */
public class Organization extends KutaDBEntity implements Serializable {
    /**
     * @desc 组织机构编号
     * @jdbcType INTEGER
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    @PrimaryKey
    private Integer oid;

    /**
     * @desc 组织名称
     * @jdbcType VARCHAR
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    private String name;

    /**
     * @desc 父级编号
     * @jdbcType INTEGER
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    private Integer parentOid;

    /**
     * @desc 状态
     * @jdbcType VARCHAR
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    private String status;

    /**
     * @desc 创建时间
     * @jdbcType TIMESTAMP
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss SSS")
    private Date created;

    /**
     * @date 2021-12-28 00:51:29
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Organization
     * @date 2021-12-28 00:51:29
     */
    public Organization(Integer oid, String name, Integer parentOid, String status, Date created) {
        this.oid = oid;
        this.name = name;
        this.parentOid = parentOid;
        this.status = status;
        this.created = created;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Organization
     * @date 2021-12-28 00:51:29
     */
    public Organization() {
        super();
    }

    /**
     * @return oid : 组织机构编号
     */
    public Integer getOid() {
        return oid;
    }

    /**
     * @param oid : 组织机构编号
     */
    public void setOid(Integer oid) {
        this.oid = oid;
    }

    /**
     * @return name : 组织名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name : 组织名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return parentOid : 父级编号
     */
    public Integer getParentOid() {
        return parentOid;
    }

    /**
     * @param parentOid : 父级编号
     */
    public void setParentOid(Integer parentOid) {
        this.parentOid = parentOid;
    }

    /**
     * @return status : 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status : 状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return created : 创建时间
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created : 创建时间
     */
    public void setCreated(Date created) {
        this.created = created;
    }
}