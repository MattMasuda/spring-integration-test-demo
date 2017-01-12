# Spring Integration Test Demo

This is a demonstration of integration testing for Spring Integration using an embedded Apache ActiveMQ broker. This technique lets us test JMS messaging end-to-end in Spring Integration applications without relying on external queue managers.

## Why do this?

Spring Integration makes it easy to work with JMS messaging providers like Websphere MQ. However, it often means we have a lot of application logic in the Spring application context. There isn't a good way to test this logic except to load up the context, let it connect to MQ, and push some messages through to see what happens. Integration testing is good, but using live external dependencies is tricky. If there is a problem with MQ then our tests will fail even though nothing is wrong with the application. With this technique we can use an embedded in-memory ActiveMQ broker for our tests. This eliminates the external dependency on MQ and gives us integration tests that are more reliable. These tests can run on CI build servers like Jenkins giving us continuous coverage. 

## How it works

The project uses Spring profiles to substitute an ActiveMQ in-memory broker for the IBM Websphere MQ broker. During normal operation in a real environment, the "live" profile is active. The live profile contains the beans for the real MQ broker. For the integration test, the "test" profile is activated and loads ActiveMQ. The EmbeddedActiveMQBroker JUnit rule causes the broker to start and stop for each test, and since it is running in non-persistent mode there is no need to purge queues between tests.

### References
* [Using Spring profiles with Java and XML configuration](http://memorynotfound.com/spring-profiles-java-xml-configuration/)
* [How to unit test JMS code with ActiveMQ](http://activemq.apache.org/how-to-unit-test-jms-code.html)
