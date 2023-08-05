package com.ibm.mq.samples.jms.util;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.JMSException;

import static com.ibm.mq.samples.jms.util.Constant.*;

public class Utils {
    public static JmsConnectionFactory getJmsConnectionFactory(JmsFactoryFactory ff) throws JMSException {
        JmsConnectionFactory cf = ff.createConnectionFactory();

        // Set the properties
        cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, HOST);
        cf.setIntProperty(WMQConstants.WMQ_PORT, PORT);
        cf.setStringProperty(WMQConstants.WMQ_CHANNEL, CHANNEL);
        cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
        cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, QMGR);
        cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "JmsPutGet (JMS)");
        cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
        cf.setStringProperty(WMQConstants.USERID, APP_USER);
        cf.setStringProperty(WMQConstants.PASSWORD, APP_PASSWORD);
        //cf.setStringProperty(WMQConstants.WMQ_SSL_CIPHER_SUITE, "*TLS12ORHIGHER");
        return cf;
    }
}
