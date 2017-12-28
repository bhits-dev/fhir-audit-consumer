## fhir-audit-consumer

This project consumes AuditEvents from a messaging service. After the logs are consumed, they are persisted in the Fhir server. 

## Implementation

Also add a dependency to `spring-cloud-starter-stream-rabbit`:
```yml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
</dependency>
```

In the class where you want to consume the messages, decorate the class with @EnableBinding and use the built-in Sink interface. Next create a method to process any mesages found in the broker. To achieve that, decorate the method with @StreamListener and all the content type handling is done for us. In this case, the received messaged is persisted in Fhir server.
```yml
@StreamListener(Sink.INPUT)
public void processVote(AuditEvent auditEvent) {
  auditEventService.publishAuditEvent(auditEvent);
}
```

The properties for RabbitMQ are specified in `application.yml`. It tells Spring Cloud Stream how to connect to the broker. We do not need to tell Spring Cloud explicitly to use RabbitMQ; it happens automatically by having that dependency in the classpath. A consumer group `auditProcessingGroup` has been added. This is used to scale up the subscribers. Also the consumer group subscriptions are durable. It means if the consumer goes offline and messages continue to go that target queue. When a consumer comes back online, it can read from the queue.

## Prerequisites

+ Oracle Java JDK 8
+ Apache Maven
+ RabbitMQ. This service can be run in docker using `docker run -d -p 15672:15672 -p 5672:5672 rabbitmq:latest`.

## Build

This project requires [Apache Maven](https://maven.apache.org) to build it. To build the project, navigate to the folder that contains `pom.xml` file using the terminal/command line.

+ To build a JAR:
    + Run `mvn clean package`

