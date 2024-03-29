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
 * This class corresponds to the database table BS_Department
 * @date 2021-12-28 00:51:29
 */
public class Department extends KutaDBEntity implements Serializable {
    /**
     * @desc 部门编号
     * @jdbcType INTEGER
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    @PrimaryKey
    private Integer did;

    /**
     * @desc 部门名称
     * @jdbcType VARCHAR
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    private String name;

    /**
     * @desc 部门别名
     * @jdbcType VARCHAR
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    private String alias;

    /**
     * @desc 状态
     * @jdbcType VARCHAR
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    private String status;

    /**
     * @desc 父级编号
     * @jdbcType INTEGER
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    private Integer parentDid;

    /**
     * @desc 组织机构编号
     * @jdbcType INTEGER
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    private Integer oid;

    /**
     * @date 2021-12-28 00:51:29
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Department
     * @date 2021-12-28 00:51:29
     */
    public Department(Integer did, String name, String alias, String status, Integer parentDid, Integer oid) {
        this.did = did;
        this.name = name;
        this.alias = alias;
        this.status = status;
        this.parentDid = parentDid;
        this.oid = oid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Department
     * @date 2021-12-28 00:51:29
     */
    public Department() {
        super();
    }

    /**
     * @return did : 部门编号
     */
    public Integer getDid() {
        return did;
    }

    /**
     * @param did : 部门编号
     */
    public void setDid(Integer did) {
        this.did = did;
    }

    /**
     * @return name : 部门名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name : 部门名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return alias : 部门别名
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias : 部门别名
     */
    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
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
     * @return parentDid : 父级编号
     */
    public Integer getParentDid() {
        return parentDid;
    }

    /**
     * @param parentDid : 父级编号
     */
    public void setParentDid(Integer parentDid) {
        this.parentDid = parentDid;
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
}