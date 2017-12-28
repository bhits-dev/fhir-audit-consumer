package gove.samhsa.c2s.fhir.audit.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.ICreate;
import ca.uhn.fhir.rest.gclient.ICreateTyped;
import ca.uhn.fhir.validation.FhirValidator;
import gov.samhsa.c2s.fhir.audit.config.FhirProperties;
import gov.samhsa.c2s.fhir.audit.service.AuditEventService;
import gov.samhsa.c2s.fhir.audit.service.AuditEventServiceImpl;
import org.hl7.fhir.dstu3.model.AuditEvent;
import org.hl7.fhir.dstu3.model.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ca.uhn.fhir.parser.IParser;

import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class AuditEventServiceImplTest {

    @Mock
    FhirContext fhirContext;

    @Mock
    FhirValidator fhirValidator;

    @Mock
    Map<Class<? extends Resource>, IGenericClient> fhirClients;

    @Mock
    FhirProperties fisProperties;

    @Mock
    IParser fhirJsonParser;

    @Mock
    AuditEventService auditEventService;

    @Captor
    ArgumentCaptor<AuditEvent> captor;

    @Test
    public void testpublishAuditEvent() {
        //Arrange
        AuditEvent auditEvent = new AuditEvent();
        IGenericClient auditFhirClient = mock(IGenericClient.class);
        MethodOutcome outcomeMocked = mock(MethodOutcome.class);
        ICreateTyped createTyped = mock(ICreateTyped.class);
        ICreate create = mock(ICreate.class);
        when(fhirClients.getOrDefault(AuditEvent.class, fhirClients.get(Resource.class))).thenReturn(auditFhirClient);
        when(auditFhirClient.create()).thenReturn(create);
        when(auditFhirClient.create().resource(auditEvent)).thenReturn(createTyped);
        when(auditFhirClient.create().resource(auditEvent).execute()).thenReturn(outcomeMocked);
        /**
         * Instruct mockito to do nothing when auditEventServiceImpl.publishAuditEvent is called
         */
        doNothing().when(auditEventService).publishAuditEvent(any(AuditEvent.class));

        //Act
        auditEventService.publishAuditEvent(auditEvent);


        //Assert
        /**
         * Verify that publishAuditEvent was called once
         */
        verify(auditEventService, times(1)).publishAuditEvent(captor.capture());

    }
}
