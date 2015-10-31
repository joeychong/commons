package com.ebizzone.commons.log;

/**
 * Created by JoeyChong on 10/18/2014.
 */
public interface  LogFactoryImpl {
    public Logger getLogger(String context);
    public Logger getLogger(Class<?> clazz);
}
