package com.setlisto.model;

import java.util.List;

public class Results<T> {

	private List<T> page = null;
	private int total = 0;
	
	public Results() {		
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public List<T> getPage() {
		return page;
	}

	public void setPage(List<T> page) {
		this.page = page;
	}
}
