/**
 * copyright (c) 2020 Kuta Service Framework
 * @author: mybatis generator
 */
package com.kuta.database.mysql.pojo;

import com.kuta.base.annotation.PrimaryKey;
import com.kuta.base.database.KutaDBEntity;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table BS_Plugin
 * @date 2021-03-24 18:00:39
 */
public class Plugin extends KutaDBEntity implements Serializable {
    /**
     * @desc 插件编号
     * @jdbcType INTEGER
     * @author mybatis generator
     * @date 2021-03-24 18:00:39
     */
    @PrimaryKey
    private Integer pid;

    /**
     * @desc 名称
     * @jdbcType VARCHAR
     * @author mybatis generator
     * @date 2021-03-24 18:00:39
     */
    private String name;

    /**
     * @desc 简介
     * @jdbcType VARCHAR
     * @author mybatis generator
     * @date 2021-03-24 18:00:39
     */
    private String desc;

    /**
     * @desc 创建时间
     * @jdbcType TIMESTAMP
     * @author mybatis generator
     * @date 2021-03-24 18:00:39
     */
    private Date created;

    /**
     * @desc 是否可实例化/购买
     * @jdbcType BIT
     * @author mybatis generator
     * @date 2021-03-24 18:00:39
     */
    private Boolean enable;

    /**
     * @desc 标志图
     * @jdbcType VARCHAR
     * @author mybatis generator
     * @date 2021-03-24 18:00:39
     */
    private String avatar;

    /**
     * @desc 状态
     * @jdbcType VARCHAR
     * @author mybatis generator
     * @date 2021-03-24 18:00:39
     */
    private String status;

    /**
     * @desc 最后修改时间
     * @jdbcType TIMESTAMP
     * @author mybatis generator
     * @date 2021-03-24 18:00:39
     */
    private Date modified;

    /**
     * @desc 插件拥有者（OID）
     * @jdbcType INTEGER
     * @author mybatis generator
     * @date 2021-03-24 18:00:39
     */
    private Integer owner;

    /**
     * @desc 最后修改人
     * @jdbcType INTEGER
     * @author mybatis generator
     * @date 2021-03-24 18:00:39
     */
    private Integer modifier;

    /**
     * @desc 插件作者(UID)
     * @jdbcType INTEGER
     * @author mybatis generator
     * @date 2021-03-24 18:00:39
     */
    private Integer author;

    /**
     * @desc 执行此插件的后台工作类
     * @jdbcType VARCHAR
     * @author mybatis generator
     * @date 2021-03-24 18:00:39
     */
    private String workClass;

    /**
     * @desc 数据源模式(HTTP, Memory, etc..)
     * @jdbcType VARCHAR
     * @author mybatis generator
     * @date 2021-03-24 18:00:39
     */
    private String dataSourceMode;

    /**
     * @desc 
     * @jdbcType VARCHAR
     * @author mybatis generator
     * @date 2021-03-24 18:00:39
     */
    private String reportMode;

    /**
     * @date 2021-03-24 18:00:39
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin
     * @date 2021-03-24 18:00:39
     */
    public Plugin(Integer pid, String name, String desc, Date created, Boolean enable, String avatar, String status, Date modified, Integer owner, Integer modifier, Integer author, String workClass, String dataSourceMode, String reportMode) {
        this.pid = pid;
        this.name = name;
        this.desc = desc;
        this.created = created;
        this.enable = enable;
        this.avatar = avatar;
        this.status = status;
        this.modified = modified;
        this.owner = owner;
        this.modifier = modifier;
        this.author = author;
        this.workClass = workClass;
        this.dataSourceMode = dataSourceMode;
        this.reportMode = reportMode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin
     * @date 2021-03-24 18:00:39
     */
    public Plugin() {
        super();
    }

    /**
     * @return pid : 插件编号
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid : 插件编号
     */
    public void setPid(Integer pid) {
        this.pid = pid;
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
     * @return desc : 简介
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc : 简介
     */
    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
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

    /**
     * @return enable : 是否可实例化/购买
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * @param enable : 是否可实例化/购买
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * @return avatar : 标志图
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar : 标志图
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
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
     * @return modified : 最后修改时间
     */
    public Date getModified() {
        return modified;
    }

    /**
     * @param modified : 最后修改时间
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }

    /**
     * @return owner : 插件拥有者（OID）
     */
    public Integer getOwner() {
        return owner;
    }

    /**
     * @param owner : 插件拥有者（OID）
     */
    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    /**
     * @return modifier : 最后修改人
     */
    public Integer getModifier() {
        return modifier;
    }

    /**
     * @param modifier : 最后修改人
     */
    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    /**
     * @return author : 插件作者(UID)
     */
    public Integer getAuthor() {
        return author;
    }

    /**
     * @param author : 插件作者(UID)
     */
    public void setAuthor(Integer author) {
        this.author = author;
    }

    /**
     * @return workClass : 执行此插件的后台工作类
     */
    public String getWorkClass() {
        return workClass;
    }

    /**
     * @param workClass : 执行此插件的后台工作类
     */
    public void setWorkClass(String workClass) {
        this.workClass = workClass == null ? null : workClass.trim();
    }

    /**
     * @return dataSourceMode : 数据源模式(HTTP, Memory, etc..)
     */
    public String getDataSourceMode() {
        return dataSourceMode;
    }

    /**
     * @param dataSourceMode : 数据源模式(HTTP, Memory, etc..)
     */
    public void setDataSourceMode(String dataSourceMode) {
        this.dataSourceMode = dataSourceMode == null ? null : dataSourceMode.trim();
    }

    /**
     * @return reportMode : 
     */
    public String getReportMode() {
        return reportMode;
    }

    /**
     * @param reportMode : 
     */
    public void setReportMode(String reportMode) {
        this.reportMode = reportMode == null ? null : reportMode.trim();
    }
}