package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DrivingLicenceTakingPointsServiceTest {
    @InjectMocks
    private DrivingLicenceTakingPointsService testedService;

    @Mock
    private InMemoryDatabase database;


    @Test
    void should_throw_exception_when_permis_not_found() {
        UUID licenseID = UUID.randomUUID();
        int pointsToTake = 5;
        when(database.findById(licenseID)).thenReturn(Optional.empty());
        Assertions.assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> testedService.removePointsFromDrivingLicense(licenseID, pointsToTake));
        verify(database).findById(licenseID);
        verifyNoMoreInteractions(database);
    }

    @Test
    void should_take_3_points_and_return_driving_license_with_9_points() {
        UUID licenseID = UUID.randomUUID();
        int pointsToTake = 3;
        int expectedPoints = 9;
        DrivingLicence drivingLicence = DrivingLicence.builder().id(licenseID).build();
        DrivingLicence expectedDrivingLicence = DrivingLicence.builder().id(licenseID)
                .availablePoints(expectedPoints)
                .build();
        when(database.findById(licenseID)).thenReturn(Optional.of(drivingLicence));
        when(database.save(licenseID, expectedDrivingLicence)).thenReturn(expectedDrivingLicence);
        Optional<DrivingLicence> returnedLicense = testedService.removePointsFromDrivingLicense(licenseID, pointsToTake);
        assertThat(returnedLicense)
                .isPresent()
                .isNotSameAs(drivingLicence);
        assertThat(returnedLicense.get()
                .getAvailablePoints())
                .isEqualTo(expectedPoints);
        verify(database).findById(licenseID);
        verify(database).save(licenseID, expectedDrivingLicence);
        verifyNoMoreInteractions(database);

    }

    @Test
    void should_not_make_points_to_fall_below_0() {
        UUID licenseID = UUID.randomUUID();
        int pointsToTake = 13;
        DrivingLicence expectedDrivingLicence = DrivingLicence.builder()
                .id(licenseID)
                .availablePoints(0)
                .build();
        when(database.findById(licenseID)).thenReturn(Optional.of(expectedDrivingLicence));
        when(database.save(licenseID, expectedDrivingLicence)).thenReturn(expectedDrivingLicence);
        Optional<DrivingLicence> returnedLicense = testedService.removePointsFromDrivingLicense(licenseID, pointsToTake);
        assertThat(returnedLicense.get().getAvailablePoints()).isEqualTo(expectedDrivingLicence.getAvailablePoints());
        verify(database).findById(licenseID);
        verify(database).save(licenseID, expectedDrivingLicence);
        verifyNoMoreInteractions(database);
    }
}