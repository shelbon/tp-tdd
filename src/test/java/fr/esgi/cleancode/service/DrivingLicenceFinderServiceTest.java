package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Provider;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DrivingLicenceFinderServiceTest {

    @InjectMocks
    private DrivingLicenceFinderService service;
    @Mock
    private DrivingLicenceIdGenerationService idGenerationService;

    @Mock
    private InMemoryDatabase database;

    @Test
    void should_find() {

        UUID licenceID= UUID.randomUUID();
        DrivingLicence drivingLicence= DrivingLicence.builder().id(licenceID).build();
        when(database.findById(licenceID)).thenReturn(Optional.of(drivingLicence));
        Optional<DrivingLicence> retrievedDrivingLicense =service.findById(licenceID);
        assertThat(retrievedDrivingLicense).containsSame(drivingLicence);
        verify(database).findById(licenceID);
        verifyNoMoreInteractions(database);
}



    @Test
    void should_not_find() {
        UUID licenceID= UUID.randomUUID();
        when(database.findById(licenceID)).thenReturn(Optional.empty());
        Optional<DrivingLicence> notRetreivedDrivingLicense =service.findById(licenceID);
        assertThat(notRetreivedDrivingLicense).isEmpty();
        verify(database).findById(licenceID);
        verifyNoMoreInteractions(database);

    }
}