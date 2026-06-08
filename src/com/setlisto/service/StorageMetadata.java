package com.setlisto.service;

public class StorageMetadata {
	// No useis enum
	
	public static final String ENTITY_MUSICAL_EVENT = "musical-event";
	public static final String ENTITY_USER = "user";
	
	public static final String RESOURCE_TYPE_MAIN_IMAGE = "main-image";
	public static final String RESOURCE_TYPE_SMALL_IMAGE = "small-image";
	
	public static final String FORMAT_JPEG = "jpg";
	public static final String FORMAT_PNG = "png";
	public static final String FORMAT_MP4 = "mp4";
	
	
	private String entityType;
	private String resourceType; 
	private String format; // mime type, ej: image/jpeg, image/png, etc.
	
	public StorageMetadata() {
		
	}
	
	public String getEntityType() {
		return entityType;
	}
	
	public String getResourceType() {
		return resourceType;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
}