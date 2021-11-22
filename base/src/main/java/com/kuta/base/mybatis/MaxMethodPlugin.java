package com.kuta.base.mybatis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import com.kuta.base.util.KutaUtil;

public class MaxMethodPlugin extends org.mybatis.generator.api.PluginAdapter{

	@Override
	public boolean validate(List<String> warnings) {
		// TODO Auto-generated method stub
		return true;
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
					method.addJavaDocLine(" * get the max value of " + column.getRemarks());
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
