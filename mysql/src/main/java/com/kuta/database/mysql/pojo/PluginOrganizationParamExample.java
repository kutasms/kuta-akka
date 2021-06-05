/**
 * copyright (c) 2020 Kuta Service Framework
 * @author: mybatis generator
 */
package com.kuta.database.mysql.pojo;

import java.util.ArrayList;
import java.util.List;

public class PluginOrganizationParamExample {
    /**
     * @date 2021-03-24 18:00:39
     */
    protected String orderByClause;

    /**
     * @date 2021-03-24 18:00:39
     */
    protected boolean distinct;

    /**
     * @date 2021-03-24 18:00:39
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org_Param
     * @date 2021-03-24 18:00:39
     */
    public PluginOrganizationParamExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org_Param
     * @date 2021-03-24 18:00:39
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org_Param
     * @date 2021-03-24 18:00:39
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org_Param
     * @date 2021-03-24 18:00:39
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org_Param
     * @date 2021-03-24 18:00:39
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org_Param
     * @date 2021-03-24 18:00:39
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org_Param
     * @date 2021-03-24 18:00:39
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org_Param
     * @date 2021-03-24 18:00:39
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org_Param
     * @date 2021-03-24 18:00:39
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org_Param
     * @date 2021-03-24 18:00:39
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BS_Plugin_Org_Param
     * @date 2021-03-24 18:00:39
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * PluginOrganizationParam
     * BS_Plugin_Org_Param
     * @date 2021-03-24 18:00:39
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andPoidIsNull() {
            addCriterion("`poid` is null");
            return (Criteria) this;
        }

        public Criteria andPoidIsNotNull() {
            addCriterion("`poid` is not null");
            return (Criteria) this;
        }

        public Criteria andPoidEqualTo(Integer value) {
            addCriterion("`poid` =", value, "poid");
            return (Criteria) this;
        }

        public Criteria andPoidNotEqualTo(Integer value) {
            addCriterion("`poid` <>", value, "poid");
            return (Criteria) this;
        }

        public Criteria andPoidGreaterThan(Integer value) {
            addCriterion("`poid` >", value, "poid");
            return (Criteria) this;
        }

        public Criteria andPoidGreaterThanOrEqualTo(Integer value) {
            addCriterion("`poid` >=", value, "poid");
            return (Criteria) this;
        }

        public Criteria andPoidLessThan(Integer value) {
            addCriterion("`poid` <", value, "poid");
            return (Criteria) this;
        }

        public Criteria andPoidLessThanOrEqualTo(Integer value) {
            addCriterion("`poid` <=", value, "poid");
            return (Criteria) this;
        }

        public Criteria andPoidIn(List<Integer> values) {
            addCriterion("`poid` in", values, "poid");
            return (Criteria) this;
        }

        public Criteria andPoidNotIn(List<Integer> values) {
            addCriterion("`poid` not in", values, "poid");
            return (Criteria) this;
        }

        public Criteria andPoidBetween(Integer value1, Integer value2) {
            addCriterion("`poid` between", value1, value2, "poid");
            return (Criteria) this;
        }

        public Criteria andPoidNotBetween(Integer value1, Integer value2) {
            addCriterion("`poid` not between", value1, value2, "poid");
            return (Criteria) this;
        }

        public Criteria andPptidIsNull() {
            addCriterion("`pptid` is null");
            return (Criteria) this;
        }

        public Criteria andPptidIsNotNull() {
            addCriterion("`pptid` is not null");
            return (Criteria) this;
        }

        public Criteria andPptidEqualTo(Integer value) {
            addCriterion("`pptid` =", value, "pptid");
            return (Criteria) this;
        }

        public Criteria andPptidNotEqualTo(Integer value) {
            addCriterion("`pptid` <>", value, "pptid");
            return (Criteria) this;
        }

        public Criteria andPptidGreaterThan(Integer value) {
            addCriterion("`pptid` >", value, "pptid");
            return (Criteria) this;
        }

        public Criteria andPptidGreaterThanOrEqualTo(Integer value) {
            addCriterion("`pptid` >=", value, "pptid");
            return (Criteria) this;
        }

        public Criteria andPptidLessThan(Integer value) {
            addCriterion("`pptid` <", value, "pptid");
            return (Criteria) this;
        }

        public Criteria andPptidLessThanOrEqualTo(Integer value) {
            addCriterion("`pptid` <=", value, "pptid");
            return (Criteria) this;
        }

        public Criteria andPptidIn(List<Integer> values) {
            addCriterion("`pptid` in", values, "pptid");
            return (Criteria) this;
        }

        public Criteria andPptidNotIn(List<Integer> values) {
            addCriterion("`pptid` not in", values, "pptid");
            return (Criteria) this;
        }

        public Criteria andPptidBetween(Integer value1, Integer value2) {
            addCriterion("`pptid` between", value1, value2, "pptid");
            return (Criteria) this;
        }

        public Criteria andPptidNotBetween(Integer value1, Integer value2) {
            addCriterion("`pptid` not between", value1, value2, "pptid");
            return (Criteria) this;
        }

        public Criteria andValIsNull() {
            addCriterion("`val` is null");
            return (Criteria) this;
        }

        public Criteria andValIsNotNull() {
            addCriterion("`val` is not null");
            return (Criteria) this;
        }

        public Criteria andValEqualTo(String value) {
            addCriterion("`val` =", value, "val");
            return (Criteria) this;
        }

        public Criteria andValNotEqualTo(String value) {
            addCriterion("`val` <>", value, "val");
            return (Criteria) this;
        }

        public Criteria andValGreaterThan(String value) {
            addCriterion("`val` >", value, "val");
            return (Criteria) this;
        }

        public Criteria andValGreaterThanOrEqualTo(String value) {
            addCriterion("`val` >=", value, "val");
            return (Criteria) this;
        }

        public Criteria andValLessThan(String value) {
            addCriterion("`val` <", value, "val");
            return (Criteria) this;
        }

        public Criteria andValLessThanOrEqualTo(String value) {
            addCriterion("`val` <=", value, "val");
            return (Criteria) this;
        }

        public Criteria andValLike(String value) {
            addCriterion("`val` like", value, "val");
            return (Criteria) this;
        }

        public Criteria andValNotLike(String value) {
            addCriterion("`val` not like", value, "val");
            return (Criteria) this;
        }

        public Criteria andValIn(List<String> values) {
            addCriterion("`val` in", values, "val");
            return (Criteria) this;
        }

        public Criteria andValNotIn(List<String> values) {
            addCriterion("`val` not in", values, "val");
            return (Criteria) this;
        }

        public Criteria andValBetween(String value1, String value2) {
            addCriterion("`val` between", value1, value2, "val");
            return (Criteria) this;
        }

        public Criteria andValNotBetween(String value1, String value2) {
            addCriterion("`val` not between", value1, value2, "val");
            return (Criteria) this;
        }

        public Criteria andPoidEqualTo(boolean condition, Integer value) {
            if (condition) {
                addCriterion("`poid` =", value, "poid");
            }
            return (Criteria) this;
        }

        public Criteria andPoidGreaterThanOrEqualTo(boolean condition, Integer value) {
            if (condition) {
                addCriterion("`poid` >=", value, "poid");
            }
            return (Criteria) this;
        }

        public Criteria andPoidIn(boolean condition, List<Integer> value) {
            if (condition) {
                addCriterion("`poid` in", value, "poid");
            }
            return (Criteria) this;
        }

        public Criteria andPptidEqualTo(boolean condition, Integer value) {
            if (condition) {
                addCriterion("`pptid` =", value, "pptid");
            }
            return (Criteria) this;
        }

        public Criteria andPptidGreaterThanOrEqualTo(boolean condition, Integer value) {
            if (condition) {
                addCriterion("`pptid` >=", value, "pptid");
            }
            return (Criteria) this;
        }

        public Criteria andPptidIn(boolean condition, List<Integer> value) {
            if (condition) {
                addCriterion("`pptid` in", value, "pptid");
            }
            return (Criteria) this;
        }

        public Criteria andValEqualTo(boolean condition, String value) {
            if (condition) {
                addCriterion("`val` =", value, "val");
            }
            return (Criteria) this;
        }

        public Criteria andValGreaterThanOrEqualTo(boolean condition, String value) {
            if (condition) {
                addCriterion("`val` >=", value, "val");
            }
            return (Criteria) this;
        }

        public Criteria andValIn(boolean condition, List<String> value) {
            if (condition) {
                addCriterion("`val` in", value, "val");
            }
            return (Criteria) this;
        }
    }

    /**
     * null<p/>
     * BS_Plugin_Org_Param
     * @date 2021-03-24 18:00:39
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * PluginOrganizationParam
     * BS_Plugin_Org_Param
     * @date 2021-03-24 18:00:39
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}