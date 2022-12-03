package fr.esgi.cleancode.validator;

import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;

public class SocialSecurityNumberValidator {

    private static final String INVALID_SIZE = "No social security numbers provided";
    private static final String INVALID_LENGTH = "invalid length social security number should have 15 characters";
    public  static final String INVALID_CHARACTERS = "Should only contains numbers";

    public  void validate(String socialSecurityNumber) {
        boolean isNull= socialSecurityNumber == null;
        boolean isLessThan15Characters = (!isNull  && socialSecurityNumber.length() < 15);
        boolean doesNotcontainOnlyNumbers=(!isNull  && !socialSecurityNumber.matches("^[0-9]+$"));
        StringBuilder errorOutput=new StringBuilder();
        if(isNull){
            errorOutput.append(INVALID_SIZE);
        }
        if(isLessThan15Characters){
            errorOutput.append(INVALID_LENGTH);
        }
        if(doesNotcontainOnlyNumbers){
            errorOutput.append(INVALID_CHARACTERS);
        }
        if(errorOutput.length() > 0){

            errorOutput.insert(0,"Errors happened when validating social security nummber:\n");
            throw new InvalidDriverSocialSecurityNumberException( errorOutput.toString());
        }

    }

}