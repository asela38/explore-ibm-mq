package com.ibm.mq.samples.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;

public class AbstractJms {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJms.class);
    // System exit status value (assume unset value to be 1)
    protected int status = 1;

    /**
     * Record this run as successful.
     */
    protected void recordSuccess() {
        LOGGER.info("SUCCESS");
        status = 0;
        return;
    }

    /**
     * Record this run as failure.
     *
     * @param ex
     */
    protected void recordFailure(Exception ex) {
        if (ex != null) {
            if (ex instanceof JMSException) {
                processJMSException((JMSException) ex);
            } else {
                LOGGER.error("Error: ", ex);
            }
        }
        LOGGER.error("FAILURE");
        status = -1;
        return;
    }

    /**
     * Process a JMSException and any associated inner exceptions.
     *
     * @param jmsex
     */
    protected void processJMSException(JMSException jmsex) {
        LOGGER.info(jmsex.toString());
        Throwable innerException = jmsex.getLinkedException();
        if (innerException != null) {
            LOGGER.error("Inner exception(s):");
        }
        while (innerException != null) {
            LOGGER.error("ERROR: ",innerException);
            innerException = innerException.getCause();
        }
        return;
    }
}
