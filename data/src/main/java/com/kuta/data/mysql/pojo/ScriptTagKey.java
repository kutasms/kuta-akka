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
 * This class corresponds to the database table BS_Script_Tag
 * @date 2021-12-28 00:51:29
 */
public class ScriptTagKey extends KutaDBEntity implements Serializable {
    /**
     * @desc 
     * @jdbcType BIGINT
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    @PrimaryKey
    private Long tid;

    /**
     * @desc 
     * @jdbcType BIGINT
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    @PrimaryKey
    private Long oid;

    /**
     * @date 2021-12-28 00:51:29
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script_Tag
     * @date 2021-12-28 00:51:29
     */
    public ScriptTagKey(Long tid, Long oid) {
        this.tid = tid;
        this.oid = oid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Script_Tag
     * @date 2021-12-28 00:51:29
     */
    public ScriptTagKey() {
        super();
    }

    /**
     * @return tid : 
     */
    public Long getTid() {
        return tid;
    }

    /**
     * @param tid : 
     */
    public void setTid(Long tid) {
        this.tid = tid;
    }

    /**
     * @return oid : 
     */
    public Long getOid() {
        return oid;
    }

    /**
     * @param oid : 
     */
    public void setOid(Long oid) {
        this.oid = oid;
    }
}