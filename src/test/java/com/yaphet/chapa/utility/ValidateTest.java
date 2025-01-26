package com.yaphet.chapa.utility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidateTest {

    @Test
    void test_valid_email() {
        // given
        String email = "chapa@test.com";

        // then
        boolean result = Validate.isValidEmail(email);

        // expect
        assertTrue(result);
    }

    @Test
    void test_invalid_email() {
        // given
        String email = "chapa@test.invalid";

        // then
        boolean result = Validate.isValidEmail(email);

        // expect
        assertFalse(result);
    }

    @Test
    void isInValidPhoneNumber() {
        // given
        String phoneNumber = "0910121212111";

        // then
        boolean result = Validate.isValidPhoneNumber(phoneNumber);

        // expect
        assertFalse(result);
    }

    @Test
    void isValidPhoneNumber() {
        String phoneNumber = "0910121212";

        boolean isValidPhone = Validate.isValidPhoneNumber(phoneNumber);

        assertTrue(isValidPhone);
    }
}