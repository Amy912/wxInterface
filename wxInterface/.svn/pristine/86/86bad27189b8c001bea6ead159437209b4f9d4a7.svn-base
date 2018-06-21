package com.emagsoftware.frame.exception;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

public class ServiceException extends Exception {

	private static Logger log = Logger.getLogger(ServiceException.class);

	private static final long serialVersionUID = 1L;

	private String errorCode;

	private String errorDesc;

	public static final String TOAST_CODE = "88888888";

	public ServiceException(String errorCode, String errorDesc) {
		super(errorDesc);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public ServiceException(String errorDesc, String errorCode, Throwable cause) {
		super(errorDesc, cause);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public ServiceException(String errorDesc, Throwable cause) {
		super(errorDesc, cause);
		this.errorDesc = errorDesc;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return this.errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public ServiceException(Class<?> clazz, String message) {
		super(message);
		log.error(message);
	}

	public ServiceException(Class<?> clazz, Throwable throwable) {
		super(throwable);
		log.error(throwable.getMessage());
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
}