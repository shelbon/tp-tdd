package fr.esgi.cleancode.validator;

import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
@ExtendWith(MockitoExtension.class)
class SocialSecurityNumberValidatorTest {
    @InjectMocks
    private SocialSecurityNumberValidator validator;

    @Test
    void should_throw_invalid_driver_social_security_number_exception_when_social_security_empty() {
        String socialSecurityNumber = "";
        assertThatExceptionOfType(InvalidDriverSocialSecurityNumberException.class)
                .isThrownBy(() -> validator.validate(socialSecurityNumber));
    }

    @Test
    void should_throw_invalid_driver_social_security_number_exception_when_social_security_less_than_15() {
        String socialSecurityNumber = "12478593123547";
        assertThatExceptionOfType(InvalidDriverSocialSecurityNumberException.class)
                .isThrownBy(() -> validator.validate(socialSecurityNumber));
    }

    @Test
    void should_throw_invalid_driver_social_security_number_exception_when_social_security_contains_not_contains_only_numbers() {
        String socialSecurityNumber = "12478593123547a";
        assertThatExceptionOfType(InvalidDriverSocialSecurityNumberException.class)
                .isThrownBy(() -> validator.validate(socialSecurityNumber));
    }
}