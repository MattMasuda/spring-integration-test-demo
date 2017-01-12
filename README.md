# Spring Integration Test Demo

This is a demonstration of integration testing for Spring Integration using an embedded Apache ActiveMQ broker. This technique lets you test JMS messaging end-to-end in your Spring Integration application without relying on external queue managers.

## Why do this?

Spring Integration makes it easy to work with JMS messaging providers like Websphere MQ. However, it means you often have a lot of application logic in the Spring application context. There isn't a good way to test this except to load up the context, let it connect to MQ, and push some messages through to see what happens. This is the definition of an integration test, but using live external dependencies can be tricky (what happens if MQ is down, or the queues are unavailable, etc). With this technique we can substitute an embedded in-memory ActiveMQ broker. This eliminates the external dependency on MQ and gives us integration tests that are more reliable. In addition, ActiveMQ is an open-source project so we don't have to worry about paying for a license.

## How it works

The project uses Spring profiles to substitute an ActiveMQ in-memory broker for the IBM Websphere MQ broker. During normal operation in a real environment, the "live" profile is active. The live profile contains the beans for the real MQ broker. For the integration test, the "test" profile is activated and loads ActiveMQ. The EmbeddedActiveMQBroker JUnit rule causes the broker to start and stop for each test, and since it is running in non-persistent mode there is no need to purge queues between tests.

### References
* [Using Spring profiles with Java and XML configuration](http://memorynotfound.com/spring-profiles-java-xml-configuration/)
* [How to unit test JMS code with ActiveMQ](http://activemq.apache.org/how-to-unit-test-jms-code.html)
