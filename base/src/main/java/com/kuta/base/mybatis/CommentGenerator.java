package com.kuta.base.mybatis;

import java.util.Date;
import java.util.Properties;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import com.kuta.base.util.KutaTimeUtil;

public class CommentGenerator extends DefaultCommentGenerator {
	private Properties properties;
    private boolean suppressDate;
    private boolean suppressAllComments;

    public CommentGenerator() {
        this.properties = new Properties();
        this.suppressDate = false;
        this.suppressAllComments = false;
    }

    public void addJavaFileComment(CompilationUnit compilationUnit) {
        compilationUnit.addFileCommentLine("/**");
        compilationUnit.addFileCommentLine(" * copyright (c) 2020 Kuta Service Framework");
        compilationUnit.addFileCommentLine(" * @author: mybatis generator");
        compilationUnit.addFileCommentLine(" */");
    }
    /**
     * XML file Comment
     */
    public void addComment(XmlElement xmlElement) {
        if (this.suppressAllComments) {
            return;
        }
        super.addComment(xmlElement);
    }

    
    
    public void addRootComment(XmlElement rootElement) {
    	super.addRootComment(rootElement);
    }

    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        this.suppressDate = StringUtility.isTrue(properties.getProperty("suppressDate"));
        this.suppressAllComments = StringUtility.isTrue(properties.getProperty("suppressAllComments"));
        super.addConfigurationProperties(properties);
    }

    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append("@date");
        String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    protected String getDateString() {
        if (this.suppressDate) {
            return null;
        }
        return KutaTimeUtil.format(new Date());
    }
    /** 
     *  Comment of Example inner class(GeneratedCriteria ,Criterion)
     */
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (this.suppressAllComments) {
            return;
        }

        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        innerClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable().toString());
        addJavadocTag(innerClass, false);
        innerClass.addJavaDocLine(" */");
    }

    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (this.suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerEnum.addJavaDocLine("/**");
        innerEnum.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable().getAlias()+ "<p/>");
        innerEnum.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString());

        addJavadocTag(innerEnum, false);

        innerEnum.addJavaDocLine(" */");
    }
    /**
     * entity filed Comment
     */
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        if (this.suppressAllComments) {
            return;
        }

//      if(introspectedColumn.getRemarks() != null && !introspectedColumn.getRemarks().trim().equals(""))
        
        
        
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * @desc " + introspectedColumn.getRemarks());
        field.addJavaDocLine(" * @jdbcType " + introspectedColumn.getJdbcTypeName());
        field.addJavaDocLine(" * @author mybatis generator" );
        
        field.addJavaDocLine(" * @date " + getDateString() );
        field.addJavaDocLine(" */");
    }
    /**
     *  Comment of EXample filed 
     */
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (this.suppressAllComments) {
            return;
        }
        field.addJavaDocLine("/**");
        addJavadocTag(field, false);
        field.addJavaDocLine(" */");
    }
    /**
     * Comment of Example method
     */
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (this.suppressAllComments) {
            return;
        }
        super.addGeneralMethodComment(method, introspectedTable);
    }
    /**
     * 
     * entity Getter Comment
     */
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        if (this.suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * @return " + introspectedColumn.getJavaProperty() + " : " + introspectedColumn.getRemarks());
        method.addJavaDocLine(" */");
    }

    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        if (this.suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        method.addJavaDocLine("/**");

        Parameter parm = (Parameter) method.getParameters().get(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        sb.append(" : ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */");
    }
    
    /**
     * Comment of Example inner class(Criteria)
     */
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (this.suppressAllComments) {
            return;
        }

        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable().getAlias()+ "<p/>");
        innerClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable().toString());
        addJavadocTag(innerClass, markAsDoNotDelete);

        innerClass.addJavaDocLine(" */");
    }
}
