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
 * This class corresponds to the database table BS_Plugin_Org_Param
 * @date 2021-12-28 00:51:29
 */
public class PluginOrganizationParamKey extends KutaDBEntity implements Serializable {
    /**
     * @desc 插件编号
     * @jdbcType INTEGER
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    @PrimaryKey
    private Integer poid;

    /**
     * @desc 插件参数模板编号
     * @jdbcType INTEGER
     * @author mybatis generator
     * @date 2021-12-28 00:51:29
     */
    @PrimaryKey
    private Integer pptid;

    /**
     * @date 2021-12-28 00:51:29
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org_Param
     * @date 2021-12-28 00:51:29
     */
    public PluginOrganizationParamKey(Integer poid, Integer pptid) {
        this.poid = poid;
        this.pptid = pptid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org_Param
     * @date 2021-12-28 00:51:29
     */
    public PluginOrganizationParamKey() {
        super();
    }

    /**
     * @return poid : 插件编号
     */
    public Integer getPoid() {
        return poid;
    }

    /**
     * @param poid : 插件编号
     */
    public void setPoid(Integer poid) {
        this.poid = poid;
    }

    /**
     * @return pptid : 插件参数模板编号
     */
    public Integer getPptid() {
        return pptid;
    }

    /**
     * @param pptid : 插件参数模板编号
     */
    public void setPptid(Integer pptid) {
        this.pptid = pptid;
    }
}