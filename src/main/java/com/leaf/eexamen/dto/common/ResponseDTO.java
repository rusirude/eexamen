package com.leaf.eexamen.dto.common;

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


	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
	
}
