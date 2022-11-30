package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class DrivingLicenceTakingPointsService {
    private final InMemoryDatabase database;
    public Optional<DrivingLicence> removePointsFromDrivingLicense(UUID drivingLicenceId, int pointsToTake) {
        return Optional.ofNullable(DrivingLicence.builder().build());
    }
}