package com.ebizzone.log;

public interface LogBinder {
	public Logger getLogger(Class<?> clazz);
	
	public Logger getLogger(String clazz);
}
