package fr.esgi.cleancode.validator;

import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import org.graalvm.collections.Pair;

import java.util.HashMap;
import java.util.Map;

public final class SocialSecurityNumberValidator {

    private static final String INVALID_SIZE = "No social security numbers provided";
    private static final String INVALID_LENGTH = "invalid length social security number should have 15 characters";
    public static final String INVALID_CHARACTERS = "Should only contains numbers";

    public void validate(String socialSecurityNumber) {
        boolean isNull = socialSecurityNumber == null;
        boolean isLessThan15Characters = (!isNull && socialSecurityNumber.length() < 15);
        boolean doesNotContainOnlyNumbers = (!isNull && !socialSecurityNumber.matches("^[0-9]+$"));
        HashMap<String, Boolean> errorsCheck = new HashMap<>();
        errorsCheck.put(INVALID_SIZE, isNull);
        errorsCheck.put(INVALID_LENGTH, isLessThan15Characters);
        errorsCheck.put(INVALID_CHARACTERS, doesNotContainOnlyNumbers);
        String errorOutput = buildErrorOutput(errorsCheck);
        if (errorOutput.length() > 0) {
            throw new InvalidDriverSocialSecurityNumberException(errorOutput);
        }

    }

    private String buildErrorOutput(HashMap<String, Boolean> errorsCheck) {
        StringBuilder errorOutput = new StringBuilder();
        for (var errorCheck : errorsCheck.entrySet()) {
            if (Boolean.TRUE.equals(errorCheck.getValue())) {
                errorOutput.append("- ")
                        .append(errorCheck.getKey())
                        .append("\n");
            }
        }
        if(errorOutput.length() > 0){
            errorOutput.insert(0, "Errors happened when validating social security nummber:\n");
        }
        return errorOutput.toString();
    }


}