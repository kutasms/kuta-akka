package com.kuta.data.mysql.biz;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;

import com.kuta.base.database.KutaSQLUtil;
import com.kuta.base.exception.KutaIllegalArgumentException;
import com.kuta.base.util.KutaUtil;
import com.kuta.data.mysql.dao.CommonMapper;

public class SqlInitizer {
	/**
	 * 执行sql脚本文件 使用ScriptRunner
     * @param sqlPath SQL文件的路径
     */
    public static void runSqlByScriptRunner(SqlSession session,String sqlPath) throws Exception {
        try {
        	ScriptRunner runner = new ScriptRunner(session.getConnection());
   		 	//runner.setEscapeProcessing(false);
            runner.setSendFullScript(false);
            runner.setAutoCommit(true);
            runner.setThrowWarning(true);
            runner.setStopOnError(true);
            runner.setEscapeProcessing(false);
            runner.setDelimiter(";");
			runner.setFullLineDelimiter(false);
			PrintWriter errorWriter = new PrintWriter(System.out);
			runner.setErrorLogWriter(errorWriter);
			runner.setLogWriter(errorWriter);
            runner.runScript(new InputStreamReader(new FileInputStream(sqlPath), "UTF-8"));
            
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public static void initize(boolean force) throws Exception {
    	String sqlPath = SqlInitizer.class.getClassLoader().getResource("ksf-mysql.sql").getPath();
    	KutaSQLUtil.exec(session->{
    		if(force) {
    			runSqlByScriptRunner(session, sqlPath);
    			return;
    		}
    		CommonMapper mapper = session.getMapper(CommonMapper.class);
    		Map<String, String> map = mapper.checkTableExistsWithShow("BS_Organization");
    		if(KutaUtil.isEmptyMap(map)) {
    			runSqlByScriptRunner(session, sqlPath);
    		} else {
    			throw new KutaIllegalArgumentException("数据表已经初始化,跳过此步骤");
    		}
    	});
    }
}
