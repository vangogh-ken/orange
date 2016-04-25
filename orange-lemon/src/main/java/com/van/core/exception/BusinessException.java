package com.van.core.exception;

public class BusinessException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int status;
	private int errorCode;
	
	public BusinessException(){
		
	}
	
	public BusinessException(int status, int errorCode){
		this.status = status;
		this.errorCode = errorCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
