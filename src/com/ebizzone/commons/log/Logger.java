package com.ebizzone.commons.log;

/**
 * Created by JoeyChong on 10/18/2014.
 */
public interface Logger {
    public void setContext(String context);
    public void trace(String message);
    public void trace(Throwable throwable);
    public void trace(String message, Throwable throwable);
    public void debug(String message);
    public void debug(Throwable throwable);
    public void debug(String message, Throwable throwable);
    public void info(String message);
    public void info(Throwable throwable);
    public void info(String message, Throwable throwable);
    public void warn(String message);
    public void warn(Throwable throwable);
    public void warn(String message, Throwable throwable);
    public void error(String message);
    public void error(Throwable throwable);
    public void error(String message, Throwable throwable);
    public void fatal(String message);
    public void fatal(Throwable throwable);
    public void fatal(String message, Throwable throwable);
}
