package com.ebizzone.log;

import com.ebizzone.log.impl.EmptyLogBinder;

public class LogFactory {
	private static LogBinder mBinder = null;
	
	public static Logger getLogger(Class<?> clazz) {
		return getLogBinder().getLogger(clazz);
	}
	
	public static Logger getLogger(String clazz) {
		return getLogBinder().getLogger(clazz);
	}
	
	protected static LogBinder getLogBinder() {
		if (mBinder == null) {
			init(null);
		}
		
		return mBinder;
	}
	
	public static void init(String logImpl) {
		if (logImpl == null) {
			logImpl = "com.ebizzone.log.impl.DefaultLogBinder";
		}
		
		LogBinder binder = null;
		try {
			binder = (LogBinder) Class.forName(logImpl).newInstance();
		} catch (InstantiationException e) {
			
		} catch (IllegalAccessException e) {
			
		} catch (ClassNotFoundException e) {
			
		}
		
		if (binder == null) {
			mBinder = new EmptyLogBinder();
		} else {
			mBinder = binder;
		}
	}
}
