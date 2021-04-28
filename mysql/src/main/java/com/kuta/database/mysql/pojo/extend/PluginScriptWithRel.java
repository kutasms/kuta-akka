package com.kuta.database.mysql.pojo.extend;

import java.util.Date;

import org.slf4j.LoggerFactory;

import com.kuta.database.mysql.pojo.Script;

public class PluginScriptWithRel extends Script{

	private Short relIndex;
	private String relStatus;
	private String relBackupOid;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 98373467839082207L;
	
//	public PluginScriptWithRel(Long sid, String name, String desc, 
//			Integer category, String categoryArray, String categoryNames, String fileName, String resUrl, 
//			String status, Integer uid, Integer oid, Date created, Date modified, String backupOid,
//			Short relIndex, String relStatus, String relBackupOid) {
//		super(sid, name, desc, category, categoryArray, categoryNames, fileName, resUrl, status, uid, oid, created, modified, backupOid);
//		this.relIndex = relIndex;
//		this.relStatus = relStatus;
//		this.relBackupOid = relBackupOid;
//	}
	
	public PluginScriptWithRel(Short relIndex, String relStatus, String relBackupOid) {
		this.relIndex = relIndex;
		this.relStatus = relStatus;
		this.relBackupOid = relBackupOid;
	}

	public Short getRelIndex() {
		return relIndex;
	}

	public void setRelIndex(Short relIndex) {
		this.relIndex = relIndex;
	}

	public String getRelStatus() {
		return relStatus;
	}

	public void setRelStatus(String relStatus) {
		this.relStatus = relStatus;
	}

	public String getRelBackupOid() {
		return relBackupOid;
	}

	public void setRelBackupOid(String relBackupOid) {
		this.relBackupOid = relBackupOid;
	}
}
