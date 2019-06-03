package com.learnyeai.batch.videoffmpeg.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "res_file")
public class ResFile {

	@Id
	@Column(name = "RES_FIEL_ID", length = 64)
	private String resFileId;

	@Column(name = "FILE_NAME", length = 128)
	private String fileName;

	@Column(name = "RES_ID", length = 64)
	private String resId;

	@Column(name = "FILE_ID", length = 200)
	private String fileId;

	@Column(name = "FILE_Type", length = 10)
	private String fileType;

	@Column(name = "FILE_SIZE", length = 10)
	private String fileSize;

	@Column(name = "FILE_SUFFIX", length = 10)
	private String fileSuffix;

	@Column(name = "FILE_TIME_LEN", length = 10)
	private String fileTimeLen;

	public String getResFileId() {
		return resFileId;
	}

	public void setResFileId(String resFileId) {
		this.resFileId = resFileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public String getFileTimeLen() {
		return fileTimeLen;
	}

	public void setFileTimeLen(String fileTimeLen) {
		this.fileTimeLen = fileTimeLen;
	}

}
