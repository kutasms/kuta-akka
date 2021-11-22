package com.kuta.base.mybatis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import com.kuta.base.annotation.PrimaryKey;
import com.kuta.base.util.KutaUtil;

/**
 * 主键注解插件
 * 
 * <pre>
 * Please add this plugin into generatorConfig.xml file.
 * {@code
 * <context>
 *    <plugin type="com.kuta.base.mybatis.PrimaryKeyPlugin"/>
 * </context>
 * }
 * 
 * </pre>
 */
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

		if (introspectedTable.getPrimaryKeyColumns().contains(introspectedColumn)) {
			topLevelClass.addImportedType(primaryKey);
			field.addAnnotation("@PrimaryKey");
		}
		return true;
//		return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
	}
}
