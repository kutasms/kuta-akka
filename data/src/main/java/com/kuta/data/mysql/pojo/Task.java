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
 * This class corresponds to the database table BS_Task
 * @date 2021-12-28 00:51:29
 */
public class Task extends KutaDBEntity implements Serializable {
    /**
     * @desc 任务编号
     * @jdbcType BIGINT
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    @PrimaryKey
    private Long tid;

    /**
     * @desc 时间类型（0：即时任务 1: 定时任务）
     * @jdbcType TINYINT
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    private Byte timeType;

    /**
     * @desc 创建时间
     * @jdbcType TIMESTAMP
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss SSS")
    private Date created;

    /**
     * @desc 是否周期任务
     * @jdbcType BIT
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    private Boolean isCycle;

    /**
     * @desc 下次运行时间
     * @jdbcType TIMESTAMP
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss SSS")
    private Date nextRunTime;

    /**
     * @desc cron表达式
     * @jdbcType VARCHAR
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    private String cronExp;

    /**
     * @desc 状态
     * @jdbcType VARCHAR
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    private String status;

    /**
     * @date 2021-12-28 00:51:29
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Task
     * @date 2021-12-28 00:51:29
     */
    public Task(Long tid, Byte timeType, Date created, Boolean isCycle, Date nextRunTime, String cronExp, String status) {
        this.tid = tid;
        this.timeType = timeType;
        this.created = created;
        this.isCycle = isCycle;
        this.nextRunTime = nextRunTime;
        this.cronExp = cronExp;
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Task
     * @date 2021-12-28 00:51:29
     */
    public Task() {
        super();
    }

    /**
     * @return tid : 任务编号
     */
    public Long getTid() {
        return tid;
    }

    /**
     * @param tid : 任务编号
     */
    public void setTid(Long tid) {
        this.tid = tid;
    }

    /**
     * @return timeType : 时间类型（0：即时任务 1: 定时任务）
     */
    public Byte getTimeType() {
        return timeType;
    }

    /**
     * @param timeType : 时间类型（0：即时任务 1: 定时任务）
     */
    public void setTimeType(Byte timeType) {
        this.timeType = timeType;
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
     * @return isCycle : 是否周期任务
     */
    public Boolean getIsCycle() {
        return isCycle;
    }

    /**
     * @param isCycle : 是否周期任务
     */
    public void setIsCycle(Boolean isCycle) {
        this.isCycle = isCycle;
    }

    /**
     * @return nextRunTime : 下次运行时间
     */
    public Date getNextRunTime() {
        return nextRunTime;
    }

    /**
     * @param nextRunTime : 下次运行时间
     */
    public void setNextRunTime(Date nextRunTime) {
        this.nextRunTime = nextRunTime;
    }

    /**
     * @return cronExp : cron表达式
     */
    public String getCronExp() {
        return cronExp;
    }

    /**
     * @param cronExp : cron表达式
     */
    public void setCronExp(String cronExp) {
        this.cronExp = cronExp == null ? null : cronExp.trim();
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
}