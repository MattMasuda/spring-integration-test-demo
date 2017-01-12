package matt;

import static org.junit.Assert.*;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) // SpringRunner.class is shorthand for SpringJUnit4ClassRunner.class
@SpringBootTest
@ActiveProfiles("test") // Activate the "test" profile to pull in ActiveMQ instead of IBM MQ
@ContextConfiguration({"classpath:META-INF/spring/spring-integration-context.xml", "classpath:META-INF/spring/test-mq-context.xml"})
@PropertySource("classpath:application.properties")
public class SpringIntegrationTestDemoApplicationTests {

	// This rule instantiates a new ActiveMQ broker for each test ensuring test independence
	@Rule
	public EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private Destination inputQueue;
	
	@Autowired
	private Destination outputQueue;
	
	@Test
	public void contextLoads() {
		assertNotNull(broker);
	}
	
	@Test
	public void applicationMovesMessagesFromInputToOutput() {
		jmsTemplate.convertAndSend(inputQueue, "test123");
		jmsTemplate.setReceiveTimeout(1000);
		Message received = jmsTemplate.receive(outputQueue);
		// We are just asserting that we were able to get a message on the output queue
		assertNotNull(received);
		
	}

	@Test
	public void applicationTransformsMessages() throws JMSException {
		String input = "this is a test";
		jmsTemplate.convertAndSend(inputQueue, input);
		jmsTemplate.setReceiveTimeout(1000);
		TextMessage received = (TextMessage) jmsTemplate.receive(outputQueue);
		// Assert that the message was transformed the way we expect
		assertEquals(input.toUpperCase(), received.getText());
	}
	
	
}
