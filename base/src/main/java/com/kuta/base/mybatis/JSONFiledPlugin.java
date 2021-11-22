package com.kuta.base.mybatis;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import com.alibaba.fastjson.annotation.JSONField;

public class JSONFiledPlugin extends org.mybatis.generator.api.PluginAdapter{

	private final FullyQualifiedJavaType jsonField;
	
	@Override
	public boolean validate(List<String> warnings) {
		// TODO Auto-generated method stub
		return true;
	}

	public JSONFiledPlugin() {
		jsonField = new FullyQualifiedJavaType(JSONField.class.getName());
	}
	
	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
			IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		// TODO Auto-generated method stub
		this.addJSONFieldAnn(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
		return true;
	}
	
	private void addJSONFieldAnn(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
			IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		String columns = introspectedTable.getTableConfigurationProperty("nonserializable");
		boolean importAdded = false;
		
		if(columns!=null) {
			String[] list = columns.split(",");
			for(String item : list) {
				if(introspectedColumn.getActualColumnName().equals(item)) {
					if(!importAdded) {
						topLevelClass.addImportedType(jsonField);
						importAdded = true;
					}
					if(introspectedColumn.getJdbcTypeName().equals("TIMESTAMP")) {
						field.addAnnotation("@JSONField(serialize = false, format = \"yyyy-MM-dd HH:mm:ss SSS\")");
					} else {
						field.addAnnotation("@JSONField(serialize = false)");
					}
				} 
			}	
		}
		if(!importAdded && introspectedColumn.getJdbcTypeName().equals("TIMESTAMP")) {
			topLevelClass.addImportedType(jsonField);
			field.addAnnotation("@JSONField(format = \"yyyy-MM-dd HH:mm:ss SSS\")");
		}
	}
}
