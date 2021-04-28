package com.kuta.base.mybatis;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.JDBCConnectionFactory;
import org.mybatis.generator.internal.util.StringUtility;

public class EntityCommentPlugin extends PluginAdapter{
	 @Override
	    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
	        addModelClassComment(topLevelClass, introspectedTable);
	        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
	    }

	    @Override
	    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass,
	            IntrospectedTable introspectedTable) {

	        addModelClassComment(topLevelClass, introspectedTable);
	        return super.modelRecordWithBLOBsClassGenerated(topLevelClass, introspectedTable);
	    }

	    protected void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

	        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
	        String tableComment = getTableComment(table);

	        topLevelClass.addJavaDocLine("/**");
	        if(StringUtility.stringHasValue(tableComment)) {
	        	topLevelClass.addJavaDocLine(" *	" + tableComment);
	        }
	        topLevelClass.addJavaDocLine(" *	" + table.toString());
	        topLevelClass.addJavaDocLine(" *	@date " + new Date().toString());
	        topLevelClass.addJavaDocLine(" *");
	        topLevelClass.addJavaDocLine(" */");
	    }

	    /**
	     * @author Marvis
	     * @date Jul 13, 2017 4:39:52 PM
	     * @param table
	     */
	    private String getTableComment(FullyQualifiedTable table) {
	        String tableComment = "";
	        Connection connection = null;
	        Statement statement = null;
	        ResultSet rs = null;
	        try {
	            JDBCConnectionFactory jdbc = new JDBCConnectionFactory(context.getJdbcConnectionConfiguration());
	            connection = jdbc.getConnection();
	            statement = connection.createStatement();
	            String tableName =  table.getIntrospectedTableName();
	            rs = statement.executeQuery("SHOW CREATE TABLE " + tableName);

	            if (rs != null && rs.next()) {
	                String createDDL;
					try {
						
						createDDL = new String(rs.getBytes(2), "gbk");
						System.out.println("[备注插件]检测表名:" + tableName);
		                if(tableName.equals("BS_User")) {
			            	System.out.println(createDDL);
			            }
		                int index = createDDL.indexOf("COMMENT='");
		                if (index < 0) {
		                    tableComment = "";
		                } else {
		                    tableComment = createDDL.substring(index + 9);
		                    tableComment = tableComment.substring(0, tableComment.length() - 1);
		                    
		                }
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                
	            }

	        } catch (SQLException e) {

	        } finally {
	            closeConnection(connection, statement, rs);
	        }
	        return tableComment;
	    }
	    /**
	     * 
	     * @author Marvis
	     * @date Jul 13, 2017 4:45:26 PM
	     * @param connection
	     * @param statement
	     * @param rs
	     */
	    private void closeConnection(Connection connection, Statement statement, ResultSet rs) {
	        try {
	            if (null != rs)
	                rs.close();
	        } catch (SQLException e) {

	            e.printStackTrace();
	        } finally {
	            try {
	                if (statement != null)
	                    statement.close();
	            } catch (Exception e) {
	                e.printStackTrace();

	            } finally {

	                try {
	                    if (connection != null)
	                        connection.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
	    /**
	     * This plugin is always valid - no properties are required
	     */
	    @Override
	    public boolean validate(List<String> warnings) {
	        return true;
	    }
}
