package com.emagsoftware.frame.log4j;

import org.apache.log4j.Logger;

public class ILogImpl implements ILog {

    private Logger logger;

    /**
     * @param clazz
     */
    public ILogImpl(Class<?> clazz) {
        logger = Logger.getLogger(clazz);
    }

    /* (non-Javadoc)
     * @see com.emagsoftware.frame.log4j.ILog#info(java.lang.Object)
     */
    public void info(Object message) {
        logger.info(message);
    }

    /* (non-Javadoc)
     * @see com.emagsoftware.frame.log4j.ILog#info(long, java.lang.Object)
     */
    public void info(long id, Object message) {
        logger.info(id + "|" + message);
    }

    /* (non-Javadoc)
     * @see com.emagsoftware.frame.log4j.ILog#info(java.lang.Object, java.lang.Throwable)
     */
    public void info(Object message, Throwable t) {
        logger.info(message,
                t);
    }

    /* (non-Javadoc)
     * @see com.emagsoftware.frame.log4j.ILog#info(long, java.lang.Object, java.lang.Throwable)
     */
    public void info(long id, Object message, Throwable t) {
        logger.info(id + "|" + message,
                t);
    }

    /* (non-Javadoc)
     * @see com.emagsoftware.frame.log4j.ILog#error(java.lang.Object)
     */
    public void error(Object message) {
        logger.info(message);
    }

    /* (non-Javadoc)
     * @see com.emagsoftware.frame.log4j.ILog#error(long, java.lang.Object)
     */
    public void error(long id, Object message) {
        logger.info(id + "|" + message);
    }

    /* (non-Javadoc)
     * @see com.emagsoftware.frame.log4j.ILog#error(java.lang.Object, java.lang.Throwable)
     */
    public void error(Object message, Throwable t) {
        logger.error(message,
                t);
    }

    /* (non-Javadoc)
     * @see com.emagsoftware.frame.log4j.ILog#error(long, java.lang.Object, java.lang.Throwable)
     */
    public void error(long id, Object message, Throwable t) {
        logger.info(id + "|" + message,
                t);
    }

    /* (non-Javadoc)
     * @see com.emagsoftware.frame.log4j.ILog#warn(java.lang.Object)
     */
    public void warn(Object message) {
        logger.info(message);
    }

    /* (non-Javadoc)
     * @see com.emagsoftware.frame.log4j.ILog#warn(long, java.lang.Object)
     */
    public void warn(long id, Object message) {
        logger.info(id + "|" + message);
    }

    /* (non-Javadoc)
     * @see com.emagsoftware.frame.log4j.ILog#warn(java.lang.Object, java.lang.Throwable)
     */
    public void warn(Object message, Throwable t) {
        logger.warn(message,
                t);
    }

    /* (non-Javadoc)
     * @see com.emagsoftware.frame.log4j.ILog#warn(long, java.lang.Object, java.lang.Throwable)
     */
    public void warn(long id, Object message, Throwable t) {
        logger.info(id + "|" + message,
                t);
    }

    /* (non-Javadoc)
     * @see com.emagsoftware.frame.log4j.ILog#debug(java.lang.Object)
     */
    public void debug(Object message) {
        logger.info(message);
    }

    /* (non-Javadoc)
     * @see com.emagsoftware.frame.log4j.ILog#debug(long, java.lang.Object)
     */
    public void debug(long id, Object message) {
        logger.info(id + "|" + message);
    }

    /* (non-Javadoc)
     * @see com.emagsoftware.frame.log4j.ILog#debug(java.lang.Object, java.lang.Throwable)
     */
    public void debug(Object message, Throwable t) {
        logger.debug(message,
                t);
    }

    /* (non-Javadoc)
     * @see com.emagsoftware.frame.log4j.ILog#debug(long, java.lang.Object, java.lang.Throwable)
     */
    public void debug(long id, Object message, Throwable t) {
        logger.info(id + "|" + message,
                t);
    }
}