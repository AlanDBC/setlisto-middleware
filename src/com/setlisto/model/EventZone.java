package com.setlisto.model;

import java.math.BigDecimal;

public class EventZone extends AbstractValueObject {

	private Long id;
	private Long musicalEventId;
	private Long seatCategoryId;
	private String seatCategoryName;
	private String sectionName;
	private Integer totalCapacity;
	private Integer availableCapacity;
	private BigDecimal basePrice;
	private Integer posX;
	private Integer posY;
	private Integer width;
	private Integer height;

	public EventZone() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMusicalEventId() {
		return musicalEventId;
	}

	public void setMusicalEventId(Long musicalEventId) {
		this.musicalEventId = musicalEventId;
	}

	public Long getSeatCategoryId() {
		return seatCategoryId;
	}

	public void setSeatCategoryId(Long seatCategoryId) {
		this.seatCategoryId = seatCategoryId;
	}

	public String getSeatCategoryName() {
		return seatCategoryName;
	}

	public void setSeatCategoryName(String seatCategoryName) {
		this.seatCategoryName = seatCategoryName;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public Integer getTotalCapacity() {
		return totalCapacity;
	}

	public void setTotalCapacity(Integer totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public Integer getAvailableCapacity() {
		return availableCapacity;
	}

	public void setAvailableCapacity(Integer availableCapacity) {
		this.availableCapacity = availableCapacity;
	}

	public BigDecimal getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}

	public Integer getPosX() {
		return posX;
	}

	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	public Integer getPosY() {
		return posY;
	}

	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
}
