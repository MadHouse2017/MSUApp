package com.madhouse.msu.v10.exceptions;

public class ApplicationException extends Exception{
	String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
}
