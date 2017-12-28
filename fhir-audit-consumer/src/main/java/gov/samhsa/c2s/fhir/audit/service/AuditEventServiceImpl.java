package gov.samhsa.c2s.fhir.audit.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.validation.FhirValidator;
import gov.samhsa.c2s.fhir.audit.config.FhirProperties;
import org.hl7.fhir.dstu3.model.AuditEvent;
import org.hl7.fhir.dstu3.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuditEventServiceImpl implements AuditEventService {

    private final IGenericClient auditFhirClient;
    private final FhirContext fhirContext;
    private final FhirValidator fhirValidator;
    private final Map<Class<? extends Resource>, IGenericClient> fhirClients;
    private final FhirProperties fisProperties;
    private final IParser fhirJsonParser;

    @Autowired
    public AuditEventServiceImpl(FhirContext fhirContext, FhirValidator fhirValidator, Map<Class<? extends Resource>, IGenericClient> fhirClients, FhirProperties fisProperties, IParser fhirJsonParser) {
        this.fhirContext = fhirContext;
        this.fhirValidator = fhirValidator;
        this.fhirClients = fhirClients;
        this.fisProperties = fisProperties;
        this.fhirJsonParser = fhirJsonParser;
        this.auditFhirClient = fhirClients.getOrDefault(AuditEvent.class, fhirClients.get(Resource.class));
    }

    @Override
    public void publishAuditEvent(AuditEvent auditEvent) {
        auditFhirClient.create().resource(auditEvent).execute();
    }
}
