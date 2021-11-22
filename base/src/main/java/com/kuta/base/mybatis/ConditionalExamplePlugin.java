package com.kuta.base.mybatis;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class ConditionalExamplePlugin extends org.mybatis.generator.api.PluginAdapter{

	@Override
	public boolean validate(List<String> warnings) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

		List<IntrospectedColumn> columns = introspectedTable.getAllColumns();

		List<InnerClass> innerClasses = topLevelClass.getInnerClasses();
		InnerClass generatedCriteriaClass = null;
		for (int i = 0; i < innerClasses.size(); i++) {

			if (innerClasses.get(i).getType().getFullyQualifiedName().equals("GeneratedCriteria")) {
				generatedCriteriaClass = innerClasses.get(i);
			}
		}
		for (int i = 0; i < columns.size(); i++) {
			IntrospectedColumn column = columns.get(i);
			generateEqualToMethod(generatedCriteriaClass, column);
			generateGreaterThanOrEqualToMethod(generatedCriteriaClass, column);
			generateInMethod(generatedCriteriaClass, column);
		}

		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}
	
	private void generateGreaterThanOrEqualToMethod(InnerClass generatedCriteriaClass, IntrospectedColumn column) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		String javaProperty = column.getJavaProperty();
		char c = javaProperty.charAt(0);
		String finalName = Character.toUpperCase(c) + javaProperty.substring(1);
		method.setName(String.format("and%sGreaterThanOrEqualTo", finalName));
		method.addParameter(new Parameter(FullyQualifiedJavaType.getBooleanPrimitiveInstance(), "condition"));
		method.addParameter(new Parameter(column.getFullyQualifiedJavaType(), "value"));
		method.addBodyLine("if (condition) {");
		method.addBodyLine(String.format("addCriterion(\"`%s` >=\", value, \"%s\");", column.getActualColumnName(),
				column.getJavaProperty()));
		method.addBodyLine("}");
		method.addBodyLine("return (Criteria) this;");
		method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
		generatedCriteriaClass.addMethod(method);
	}

	private void generateEqualToMethod(InnerClass generatedCriteriaClass, IntrospectedColumn column) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		String javaProperty = column.getJavaProperty();
		char c = javaProperty.charAt(0);
		String finalName = Character.toUpperCase(c) + javaProperty.substring(1);
		method.setName(String.format("and%sEqualTo", finalName));
		method.addParameter(new Parameter(FullyQualifiedJavaType.getBooleanPrimitiveInstance(), "condition"));
		method.addParameter(new Parameter(column.getFullyQualifiedJavaType(), "value"));
		method.addBodyLine("if (condition) {");
		method.addBodyLine(String.format("addCriterion(\"`%s` =\", value, \"%s\");", column.getActualColumnName(),
				column.getJavaProperty()));
		method.addBodyLine("}");
		method.addBodyLine("return (Criteria) this;");
		method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
		generatedCriteriaClass.addMethod(method);
	}

	
	
	private void generateInMethod(InnerClass generatedCriteriaClass, IntrospectedColumn column) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		String javaProperty = column.getJavaProperty();
		char c = javaProperty.charAt(0);
		String finalName = Character.toUpperCase(c) + javaProperty.substring(1);
		method.setName(String.format("and%sIn", finalName));
		method.addParameter(new Parameter(FullyQualifiedJavaType.getBooleanPrimitiveInstance(), "condition"));
		FullyQualifiedJavaType listType = FullyQualifiedJavaType.getNewListInstance();
		listType.addTypeArgument(column.getFullyQualifiedJavaType());
		method.addParameter(new Parameter(listType, "value"));
		method.addBodyLine("if (condition) {");
		method.addBodyLine(String.format("addCriterion(\"`%s` in\", value, \"%s\");", column.getActualColumnName(),
				column.getJavaProperty()));
		method.addBodyLine("}");
		method.addBodyLine("return (Criteria) this;");
		method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
		generatedCriteriaClass.addMethod(method);
	}

}
