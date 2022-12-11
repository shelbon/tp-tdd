package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public final class DrivingLicenceTakingPointsService {
    private final InMemoryDatabase database;

    public Optional<DrivingLicence> removePointsFromDrivingLicense(UUID drivingLicenceId, int pointsToTake) {
        Optional<DrivingLicence> retrievedDrivingLicense = database.findById(drivingLicenceId);
        if (retrievedDrivingLicense.isPresent()) {
            DrivingLicence drivingLicense = retrievedDrivingLicense.get();

            int newDrivingLicensePoints = getNewDrivingLicensePoints(pointsToTake, drivingLicense.getAvailablePoints());

            DrivingLicence newDrivingLicense = DrivingLicence.builder()
                    .id(drivingLicenceId)
                    .driverSocialSecurityNumber(drivingLicense.getDriverSocialSecurityNumber())
                    .availablePoints(newDrivingLicensePoints)
                    .build();

            database.save(newDrivingLicense.getId(), newDrivingLicense);
            return Optional.of(newDrivingLicense);


        }
        throw new ResourceNotFoundException("No driving licence found for id" + retrievedDrivingLicense);
    }

    private static int getNewDrivingLicensePoints(int pointsToTake, int currentLicensePoints) {
        return pointsToTake > currentLicensePoints ?
                0 :
                currentLicensePoints - pointsToTake;
    }
}