package gov.samhsa.c2s.fhir.audit;

import gov.samhsa.c2s.fhir.audit.service.AuditEventService;
import org.hl7.fhir.dstu3.model.AuditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableBinding(Sink.class)
public class FhirAuditConsumerApplication {

	@Autowired
	AuditEventService auditEventService;

	public static void main(String[] args) {
		SpringApplication.run(FhirAuditConsumerApplication.class, args);
	}

	@StreamListener(Sink.INPUT)
	public void processVote(AuditEvent auditEvent) {
		System.out.println("Received : " + auditEvent);

		auditEventService.publishAuditEvent(auditEvent);
	}
}
