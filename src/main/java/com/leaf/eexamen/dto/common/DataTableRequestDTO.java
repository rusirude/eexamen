package com.leaf.eexamen.dto.common;

public class DataTableRequestDTO {
	private Integer draw;
    private Integer start;
    private Integer length;
    private String sortColumnName;
    private String sortOrder;
    private String search;
    
	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public String getSortColumnName() {
		return sortColumnName;
	}
	public void setSortColumnName(String sortColumnName) {
		this.sortColumnName = sortColumnName;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	
	
    
    
}
