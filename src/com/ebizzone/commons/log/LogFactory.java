package com.ebizzone.commons.log;

/**
 * Created by JoeyChong on 10/18/2014.
 */
public final class LogFactory {
    public static Logger getLogger(String context) {

        LogFactoryImpl impl = getFactory();
        Logger logger = impl.getLogger(context);

        return logger;
    }

    public static Logger getLogger(Class<?> clazz) {

        LogFactoryImpl impl = getFactory();
        Logger logger = impl.getLogger(clazz);

        return logger;
    }

    public static LogFactoryImpl getFactory() {
        LogFactoryImpl impl;
        try {
            impl = (LogFactoryImpl) Class.forName("DefaultLogFactoryImpl").newInstance();
        } catch (ClassNotFoundException e) {
            impl = new DummyLogFactoryImpl();
        } catch (InstantiationException e) {
            impl = new DummyLogFactoryImpl();
        } catch (IllegalAccessException e) {
            impl = new DummyLogFactoryImpl();
        }

        return impl;
    }
}
