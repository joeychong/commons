package com.ebizzone.log.impl;

import com.ebizzone.log.LogBinder;
import com.ebizzone.log.Logger;

public class EmptyLogBinder implements LogBinder {

	@Override
	public Logger getLogger(Class<?> clazz) {
		return new EmptyLogger();
	}

	@Override
	public Logger getLogger(String clazz) {
		return new EmptyLogger();
	}

}
