package com.emagsoftware.frame.log4j;

public interface ILog {

    void info(Object message);

    void info(long id, Object message);

    void info(Object message, Throwable t);

    void info(long id, Object message, Throwable t);

    void error(Object message);

    void error(long id, Object message);

    void error(Object message, Throwable t);

    void error(long id, Object message, Throwable t);

    void warn(Object message);

    void warn(long id, Object message);

    void warn(Object message, Throwable t);

    void warn(long id, Object message, Throwable t);

    void debug(Object message);

    void debug(long id, Object message);

    void debug(Object message, Throwable t);

    void debug(long id, Object message, Throwable t);
}