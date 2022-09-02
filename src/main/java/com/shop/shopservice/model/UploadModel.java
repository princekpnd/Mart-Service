package com.shop.shopservice.model;

import org.springframework.web.multipart.MultipartFile;

public class UploadModel {

    private String extraField;

    /**
	 * @return the extraField
	 */
	public String getExtraField() {
		return extraField;
	}

	/**
	 * @param extraField the extraField to set
	 */
	public void setExtraField(String extraField) {
		this.extraField = extraField;
	}

	/**
	 * @return the files
	 */
	public MultipartFile[] getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	private MultipartFile[] files;


}