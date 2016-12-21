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

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration("classpath:META-INF/spring/spring-integration-context.xml")
@PropertySource("classpath:application.properties")
public class SpringIntegrationTestDemoApplicationTests {

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
		assertNotNull(received);
	}

	@Test
	public void applicationTransformsMessages() throws JMSException {
		String input = "this is a test";
		jmsTemplate.convertAndSend(inputQueue, input);
		jmsTemplate.setReceiveTimeout(1000);
		TextMessage received = (TextMessage) jmsTemplate.receive(outputQueue);
		assertEquals(input.toUpperCase(), received.getText());
	
	}
	
	
}
