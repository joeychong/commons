package com.ebizzone.commons.log;

/**
 * Created by JoeyChong on 10/18/2014.
 */
public class DummyLogFactoryImpl implements LogFactoryImpl {
    @Override
    public Logger getLogger(String context) {
        return new DummyLogger();
    }

    @Override
    public Logger getLogger(Class<?> clazz) {
        return new DummyLogger();
    }
}
