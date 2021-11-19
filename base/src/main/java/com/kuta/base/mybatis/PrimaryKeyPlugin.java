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

import com.alibaba.fastjson.annotation.JSONField;
import com.kuta.base.annotation.IncrIgoneColumn;
import com.kuta.base.annotation.PrimaryKey;
import com.kuta.base.util.KutaUtil;

/**
 * 主键注解插件
 * 
 * <pre>
 * 请在generatorConfig.xml配置文件中增加对此插件的配置
 * {@code
 * <context>
 *    <plugin type="com.kuta.base.mybatis.KutaBatchUpdatePlugin"/>
 * </context>
 * }
 * 
 * </pre>
 */
public class PrimaryKeyPlugin extends org.mybatis.generator.api.PluginAdapter {

	private FullyQualifiedJavaType primaryKey;
	private FullyQualifiedJavaType incrIgoneColumn;
	private FullyQualifiedJavaType jsonField;
	
	@Override
	public boolean validate(List<String> warnings) {
		// TODO Auto-generated method stub
		return true;
	}

	public PrimaryKeyPlugin() {
		super();
		primaryKey = new FullyQualifiedJavaType(PrimaryKey.class.getName());
		incrIgoneColumn = new FullyQualifiedJavaType(IncrIgoneColumn.class.getName());
		jsonField = new FullyQualifiedJavaType(JSONField.class.getName());
	}

	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
			IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		// TODO Auto-generated method stub

		if (introspectedTable.getPrimaryKeyColumns().contains(introspectedColumn)) {
			topLevelClass.addImportedType(primaryKey);
			field.addAnnotation("@PrimaryKey");
		}
		this.addIncrIgoneAnn(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
		this.addJSONFieldAnn(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
		return true;
//		return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
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
	
	private void addIncrIgoneAnn(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
			IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		String columns = introspectedTable.getTableConfigurationProperty("incrIgoneColumnList");
		if(columns!=null) {
			Arrays.asList(columns.split(",")).forEach(item->{
				if(introspectedColumn.getActualColumnName().equals(item)) {
					topLevelClass.addImportedType(incrIgoneColumn);
					field.addAnnotation("@IncrIgoneColumn");
				}
			});
		}
		
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
	
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		this.addMaxMethod(interfaze, topLevelClass, introspectedTable);
		return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
	}

	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
		this.addXmlMaxNode(document, introspectedTable);
		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	
	
	private void addXmlMaxNode(Document document, IntrospectedTable introspectedTable) {
		String columnName = introspectedTable.getTableConfigurationProperty("generateMax");
		if(columnName == null) {
			return;
		}
		List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
		String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
		columns.forEach(column -> {
			if (column.getActualColumnName().equals(columnName)) {
				String javaProperty = column.getJavaProperty();
				char c = javaProperty.charAt(0);
				String finalName = Character.toUpperCase(c) + javaProperty.substring(1);
				String name = "max" + finalName;
				XmlElement eleSelect = new XmlElement("select");
				eleSelect.addAttribute(new Attribute("id", name));
				eleSelect.addAttribute(new Attribute("resultType", 
						column.getFullyQualifiedJavaType().getFullyQualifiedName()));
				new  CommentGenerator().addComment(eleSelect);
				String selectContent = "select IFNULL(max(`" + column.getActualColumnName() + "`),0) from "
						+ tableName;
				TextElement mainElem = new TextElement(selectContent);
				eleSelect.addElement(mainElem);

				String generateMaxParams = introspectedTable.getTableConfigurationProperty("generateMaxParams");
				if (!KutaUtil.isEmptyString(generateMaxParams)) {
					List<String> params = new ArrayList<>();
					
					StringBuilder sb = new StringBuilder();
					sb.append("where ");
					if (generateMaxParams.indexOf(",") >= 0) {
						String[] split = generateMaxParams.split(",");
						params.addAll(Arrays.asList(split));
					} else {
						params.add(generateMaxParams);
					}
					boolean isFirst = true;
					for (int i = 0; i < params.size(); i++) {
						String param = params.get(i);
						Optional<IntrospectedColumn> first = columns.stream()
								.filter(elem -> elem.getActualColumnName().equals(param)).findFirst();
						if (first.isPresent()) {
							IntrospectedColumn condColumn = first.get();
							if (isFirst) {
								sb.append(String.format("`%s` = #{%s,jdbcType=%s}", condColumn.getActualColumnName(),
										condColumn.getJavaProperty(), condColumn.getJdbcTypeName()));
								isFirst = false;
							} else {
								sb.append(String.format(" and `%s` = #{%s,jdbcType=%s}", condColumn.getActualColumnName(),
										condColumn.getJavaProperty(), condColumn.getJdbcTypeName()));
								isFirst = false;
							}
						}
					}
					 eleSelect.addElement(new TextElement(sb.toString()));
				}
				document.getRootElement().addElement(eleSelect);
			}
		});
	}

	private void addMaxMethod(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		String columnName = introspectedTable.getTableConfigurationProperty("generateMax");
		List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
		columns.forEach(column -> {
			if (column.getActualColumnName().equals(columnName)) {
				Method method = new Method();
				if (!KutaUtil.isEmptyString(column.getRemarks())) {
					method.addJavaDocLine("/**");
					method.addJavaDocLine(" * 获取" + column.getRemarks() + "最大值");
					method.addJavaDocLine(" */");
				}
				String javaProperty = column.getJavaProperty();
				char c = javaProperty.charAt(0);
				String finalName = Character.toUpperCase(c) + javaProperty.substring(1);
				method.setName("max" + finalName);
				introspectedTable.getPrimaryKeyColumns();
				String generateMaxParams = introspectedTable.getTableConfigurationProperty("generateMaxParams");
				if (!KutaUtil.isEmptyString(generateMaxParams)) {
					List<String> params = new ArrayList<>();

					if (generateMaxParams.indexOf(",") >= 0) {
						String[] split = generateMaxParams.split(",");
						params.addAll(Arrays.asList(split));
					} else {
						params.add(generateMaxParams);
					}
					params.forEach(param -> {
						Optional<IntrospectedColumn> first = columns.stream()
								.filter(elem -> elem.getActualColumnName().equals(param)).findFirst();
						if (first.isPresent()) {
							Parameter parameter = new Parameter(first.get().getFullyQualifiedJavaType(),
									first.get().getJavaProperty());
							parameter.addAnnotation(String.format("@Param(\"%s\")", first.get().getJavaProperty()));
							method.addParameter(parameter);
						}
					});
				}

				method.setReturnType(column.getFullyQualifiedJavaType());
				interfaze.addMethod(method);
			}
		});

	}

}
