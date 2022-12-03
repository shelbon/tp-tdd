package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.model.DrivingLicence;
import fr.esgi.cleancode.validator.SocialSecurityNumberValidator;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public final class DrivingLicenceSaveService {
    private final InMemoryDatabase database;
    private final DrivingLicenceIdGenerationService drivingLicenceIdGenerationService;
    private final SocialSecurityNumberValidator socialSecurityNumberValidator;

    public DrivingLicence saveDrivingLicence(String driverSocialSecurityNumber) {
        socialSecurityNumberValidator.validate(driverSocialSecurityNumber);
        UUID drivingLicenceId = drivingLicenceIdGenerationService.generateNewDrivingLicenceId();
        DrivingLicence drivingLicence = createDrivingLicence(drivingLicenceId, driverSocialSecurityNumber);
        return database.save(drivingLicenceId, drivingLicence);
    }

    private DrivingLicence createDrivingLicence(UUID drivingLicenceId, String driverSocialSecurityNumber) {
        return DrivingLicence.builder()
                .id(drivingLicenceId)
                .availablePoints(12)
                .driverSocialSecurityNumber(driverSocialSecurityNumber)
                .build();
    }

}