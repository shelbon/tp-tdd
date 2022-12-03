package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import fr.esgi.cleancode.validator.SocialSecurityNumberValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DrivingLicenceSaveServiceTest {
    @InjectMocks
    private DrivingLicenceSaveService service;
    @Mock
    private DrivingLicenceIdGenerationService drivingLicenceIdGenerationService;
    @Mock
    private InMemoryDatabase database;

    @Mock
    private SocialSecurityNumberValidator socialSecurityNumberValidator;

    @Captor
    private ArgumentCaptor<UUID> drivingLicenceIdCaptor;

    @Captor
    private ArgumentCaptor<DrivingLicence> drivingLicenceCaptor;

    @Test
    void should_save_driving_licence() {
        String socialSecurityNumber = "124785931235474";
        UUID licenceId = UUID.randomUUID();
        DrivingLicence expectedCreatedDrivingLicence = DrivingLicence.builder()
                .id(licenceId)
                .driverSocialSecurityNumber(socialSecurityNumber)
                .build();

        when(drivingLicenceIdGenerationService.generateNewDrivingLicenceId()).thenReturn(licenceId);
        when(database.save(any(UUID.class), any(DrivingLicence.class)))
                .thenReturn(expectedCreatedDrivingLicence);

        DrivingLicence drivingLicence = service.saveDrivingLicence(socialSecurityNumber);

        verify(database).save(drivingLicenceIdCaptor.capture(), drivingLicenceCaptor.capture());
        verifyNoMoreInteractions(database);

        assertThat(drivingLicenceIdCaptor.getValue())
                .isEqualTo(licenceId);
        assertThat(drivingLicenceCaptor.getValue())
                .isNotNull()
                .isEqualTo(expectedCreatedDrivingLicence);
        assertThat(drivingLicence)
                .isEqualTo(expectedCreatedDrivingLicence);

    }

    @Test
    void should_not_save_driving_licence() {
        String socialSecurityNumber = "12478593123547a";
        when(socialSecurityNumberValidator.validate(socialSecurityNumber))
                .thenThrow(InvalidDriverSocialSecurityNumberException.class);
        assertThatExceptionOfType(InvalidDriverSocialSecurityNumberException.class)
                .isThrownBy(() -> service.saveDrivingLicence(socialSecurityNumber));

    }


}