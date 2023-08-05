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

import com.ibm.mq.samples.jms.util.Constant;
import com.ibm.mq.samples.jms.util.Utils;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.io.Console;

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
public class JmsPutGet extends AbstractJms {

	private static final Logger LOGGER = LoggerFactory.getLogger(JmsPutGet.class);
	// System exit status value (assume unset value to be 1)
	private static int status = 1;


	/**
	 * Main method
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		new JmsPutGet().process(args);
	}
	private void process(String[] args) {
		// Sanity check main() arguments and warn user
		if (args.length > 0) {
                	LOGGER.info("\n!!!! WARNING: You have provided arguments to the Java main() function. JVM arguments (such as -Djavax.net.ssl.trustStore) must be passed before the main class or .jar you wish to run.\n\n");
                	Console c = System.console();
					LOGGER.info("Press the Enter key to continue");
                	c.readLine();
                }

		// Variables
		JMSContext context = null;
		Destination destination = null;
		JMSProducer producer = null;
		JMSConsumer consumer = null;



		try {
			// Create a connection factory
			JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
			JmsConnectionFactory cf = Utils.getJmsConnectionFactory(ff);

			// Create JMS objects
			context = cf.createContext();
			destination = context.createQueue("queue:///" + Constant.QUEUE_NAME);

			long uniqueNumber = System.currentTimeMillis() % 1000;
			TextMessage message = context.createTextMessage("Your lucky number today is " + uniqueNumber);

			producer = context.createProducer();
			producer.send(destination, message);
			LOGGER.info("Sent message:\n" + message);

			consumer = context.createConsumer(destination); // autoclosable
			String receivedMessage = consumer.receiveBody(String.class, 15000); // in ms or 15 seconds

			LOGGER.info("\nReceived message:\n" + receivedMessage);

                        context.close();

			recordSuccess();
		} catch (JMSException jmsex) {
			recordFailure(jmsex);
		}

		System.exit(status);

	} // end main()



}
