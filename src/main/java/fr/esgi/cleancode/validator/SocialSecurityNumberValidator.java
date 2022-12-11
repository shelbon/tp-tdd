package fr.esgi.cleancode.validator;

import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;

public final class SocialSecurityNumberValidator {

    private static final String NO_SOCIAL_SECURITY_NUMBERS_PROVIDED = "No social security numbers provided";
    private static final String SHOULD_HAVE_15_CHARACTERS = "Invalid length social security number should have 15 characters";
    public static final String SHOULD_ONLY_CONTAINS_NUMBERS = "Should only contains numbers";

    public void validate(String socialSecurityNumber) {
        if (socialSecurityNumber == null) {
            throw new InvalidDriverSocialSecurityNumberException(NO_SOCIAL_SECURITY_NUMBERS_PROVIDED);
        }
        if (socialSecurityNumber.length() < 15) {
            throw new InvalidDriverSocialSecurityNumberException(SHOULD_HAVE_15_CHARACTERS);
        }
        if (!socialSecurityNumber.matches("^\\d+$")) {
            throw new InvalidDriverSocialSecurityNumberException(SHOULD_ONLY_CONTAINS_NUMBERS);
        }
    }
}