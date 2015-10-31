package com.ebizzone.log;

public interface Logger {
	public void trace(Object message);
	public void trace(String message, Object...params);
	public void trace(Object message, Throwable throwable);
	//public void trace(String message, Throwable throwable, Object...params);
	
	public void debug(Object message);
	public void debug(String message, Object...params);
	public void debug(Object message, Throwable throwable);
	//public void debug(String message, Throwable throwable, Object...params);
	
	public void info(Object message);
	public void info(String message, Object...params);
	public void info(Object message, Throwable throwable);
	//public void info(String message, Throwable throwable, Object...params);
	
	public void warn(Object message);
	public void warn(String message, Object...params);
	public void warn(Object message, Throwable throwable);
	//public void warn(String message, Throwable throwable, Object...params);
	
	public void error(Object message);
	public void error(String message, Object...params);
	public void error(Object message, Throwable throwable);
	//public void error(String message, Throwable throwable, Object...params);
	
	public void fatal(Object message);
	public void fatal(String message, Object...params);
	public void fatal(Object message, Throwable throwable);
	//public void fatal(String message, Throwable throwable, Object...params);
}
