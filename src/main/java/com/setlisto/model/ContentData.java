package com.setlisto.model;

public class ContentData {
	
	private byte[] raw = null;
	private ContentMetadata metadata = null;
	
	
	public ContentData() {		
	}

	public byte[] getRaw() {
		return raw;
	}

	public void setRaw(byte[] raw) {
		this.raw = raw;
	}

	public ContentMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(ContentMetadata metadata) {
		this.metadata = metadata;
	}
}
