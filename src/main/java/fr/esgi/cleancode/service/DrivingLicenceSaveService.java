package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.model.DrivingLicence;
import fr.esgi.cleancode.validator.SocialSecurityNumberValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DrivingLicenceSaveService {
    private final InMemoryDatabase database;
    private final DrivingLicenceIdGenerationService drivingLicenceIdGenerationService;
    private final SocialSecurityNumberValidator socialSecurityNumberValidator;
    public DrivingLicence saveDrivingLicence(String driverSocialSecurityNumber) {
        socialSecurityNumberValidator.validate(driverSocialSecurityNumber);

        return null;
    }

    private DrivingLicence createDrivingLicence(String driverSocialSecurityNumber) {
        return null;
    }

}