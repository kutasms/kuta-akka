package com.kuta.base.mybatis;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import com.kuta.base.annotation.PrimaryKey;

/**
 * 主键注解插件
 * <pre>
 * 请在generatorConfig.xml配置文件中增加对此插件的配置
 * {@code
 * <context>
 *    <plugin type="com.kuta.base.mybatis.KutaBatchUpdatePlugin"/>
 * </context>
 * }

 * </pre>
 * */
public class PrimaryKeyPlugin extends org.mybatis.generator.api.PluginAdapter {

	private FullyQualifiedJavaType primaryKey;
	
	@Override
	public boolean validate(List<String> warnings) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public PrimaryKeyPlugin() {
		super();
		primaryKey = new FullyQualifiedJavaType(PrimaryKey.class.getName());
	}
	
	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
			IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		// TODO Auto-generated method stub
//		
		if(introspectedTable.getPrimaryKeyColumns().contains(introspectedColumn)){
			topLevelClass.addImportedType(primaryKey);
			field.addAnnotation("@PrimaryKey");
		}
		return true;
//		return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
	}


}
