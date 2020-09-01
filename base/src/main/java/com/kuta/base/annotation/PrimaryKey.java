package com.kuta.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 主键标识注解
 * <p>在数据实体的数据字段添加本注解将可以使用KutaSQLUtil.isPrimaryKey判断是否为主键</p>
 * */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {

}
