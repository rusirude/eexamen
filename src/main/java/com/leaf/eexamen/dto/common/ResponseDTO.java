package com.leaf.eexamen.dto.common;

import lombok.Data;

@Data
public class ResponseDTO<T> {
	
	private String code;
	private String message;
	private T data;	
	
	public ResponseDTO(String code) {		
		this.code = code;
	}
	
	public ResponseDTO(String code, String message) {		
		this.code = code;
		this.message = message;
	}

	public ResponseDTO(String code, String message, T data) {		
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public ResponseDTO(String code, T data) {
		this.code = code;
		this.data = data;
	}

}
