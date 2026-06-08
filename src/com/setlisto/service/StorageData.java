package com.setlisto.service;

public class StorageData {

	private String id;
	private byte[] data;
	private StorageMetadata metadata;
	
	public StorageData() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public StorageMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(StorageMetadata metadata) {
		this.metadata = metadata;
	}
}
