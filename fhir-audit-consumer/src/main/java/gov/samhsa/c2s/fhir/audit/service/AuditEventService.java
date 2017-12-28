package gov.samhsa.c2s.fhir.audit.service;

import org.hl7.fhir.dstu3.model.AuditEvent;

public interface AuditEventService {

    public void publishAuditEvent(AuditEvent auditEvent);
}