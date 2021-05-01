package com.leaf.eexamen.dto.common;

import lombok.Data;

@Data
public class DropDownDTO<T> {
	
	private String code;
	private String description;
	private T data;
	
	
	
	public DropDownDTO(String code, String description) {		
		this.code = code;
		this.description = description;
	}

	public DropDownDTO(String code, String description, T data) {
		this.code = code;
		this.description = description;
		this.data = data;
	}
}
