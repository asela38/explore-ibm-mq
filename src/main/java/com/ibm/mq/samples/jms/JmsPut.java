/*
* (c) Copyright IBM Corporation 2018
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.ibm.mq.samples.jms;


import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;

import com.ibm.mq.samples.jms.util.Utils;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ibm.mq.samples.jms.util.Constant.*;

/**
 * A minimal and simple application for Point-to-point messaging.
 *
 * Application makes use of fixed literals, any customisations will require
 * re-compilation of this source file. Application assumes that the named queue
 * is empty prior to a run.
 *
 * Notes:
 *
 * API type: JMS API (v2.0, simplified domain)
 *
 * Messaging domain: Point-to-point
 *
 * Provider type: IBM MQ
 *
 * Connection mode: Client connection
 *
 * JNDI in use: No
 *
 */
public class JmsPut extends AbstractJms{

	private static final Logger LOGGER = LoggerFactory.getLogger(JmsPut.class);

	/**
	 * Main method
	 *
	 * @param args
	 */

	public static void main(String[] args) {
		new JmsPut().process(args);
	}
	private void process(String[] args) {

		// Variables
		JMSProducer JMSContext = null;

		try {
			// Create a connection factory
			JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
			JmsConnectionFactory cf = Utils.getJmsConnectionFactory(ff);

			// Create JMS objects
			JMSContext context = cf.createContext();
			Destination destination = context.createQueue("queue:///" + QUEUE_NAME);

			long uniqueNumber = System.currentTimeMillis() % 1000;
			TextMessage message = context.createTextMessage("Your lucky number today is " + uniqueNumber);

			JMSProducer producer = context.createProducer();
			producer.send(destination, message);
			LOGGER.info("Sent message: {}", message.toString());

                        context.close();

			recordSuccess();
		} catch (JMSException jmsex) {
			recordFailure(jmsex);
		}

		System.exit(status);

	} // end main()
}
