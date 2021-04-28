package com.kuta.database.mysql.pojo.mongo;

import java.util.Date;

import com.kuta.base.database.mongodb.KutaMongoEntity;

public class BackupScript extends KutaMongoEntity {

	private long sid;

	private String name;

	private String desc;

	private String fileName;

	private String status;

	private Integer uid;

	private Date created;

	private Date modified;

	private Date backupCreated;
	
	private Integer category;
	
	private String categoryNames;
	
	private String categoryArray;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc == null ? null : desc.trim();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName == null ? null : fileName.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Date getBackupCreated() {
		return backupCreated;
	}

	public void setBackupCreated(Date backupCreated) {
		this.backupCreated = backupCreated;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
	}

	public String getCategoryArray() {
		return categoryArray;
	}

	public void setCategoryArray(String categoryArray) {
		this.categoryArray = categoryArray;
	}

	public long getSid() {
		return sid;
	}

	public void setSid(long sid) {
		this.sid = sid;
	}
}
