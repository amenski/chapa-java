package com.yaphet.chapa;

import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    public void shouldValidatePostData() {
        // given
        PostData formData = PostData.builder()
                .amount(new BigDecimal("100"))
                .currency( "ETB")
                .first_name("Abebe")
                .last_name("Bikila")
                .email("abebe@bikila.com")
                .tx_ref("tx-myecommerce12345")
                .callback_url("https://chapa.co")
                .customization_title("I love e-commerce")
                .customization_description("It is time to pay")
                .customization_logo("My logo")
                .build();

        // then
        assertDoesNotThrow(() -> Util.validatePostData(formData));
    }

    @Test
    public void shouldFailForInvalidPostData(){
        // given
        PostData formData = PostData.builder()
                .currency( "ETB")
                .first_name("Abebe")
                .last_name("Bikila")
                .email("abebe@bikila.com")
                .tx_ref("tx-myecommerce12345")
                .callback_url("https://chapa.co")
                .customization_title("I love e-commerce")
                .customization_description("It is time to pay")
                .customization_logo("My logo")
                .build();

        // then
        assertThrows(ValidationException.class, () -> Util.validatePostData(formData));
    }

    @Test
    void shouldValidatePostDataWithJsonInput() {
        // given
        String formData = " { " +
                "'amount': '100', " +
                "'currency': 'ETB'," +
                "'email': 'abebe@bikila.com'," +
                "'first_name': 'Abebe'," +
                "'last_name': 'Bikila'," +
                "'tx_ref': 'tx-myecommerce12345'," +
                "'callback_url': 'https://chapa.co'," +
                "'customization[title]': 'I love e-commerce'," +
                "'customization[description]': 'It is time to pay'" +
                " }";


        // then
        assertDoesNotThrow(() -> Util.validatePostData(formData));
    }
    @Test
    void shouldFailFoInvalidPostDataWithJsonInput() {
        // given
        String formData = " { " +
                "'amount': '100', " +
                "'email': 'abebe@bikila.com'," +
                "'first_name': 'Abebe'," +
                "'last_name': 'Bikila'," +
                "'tx_ref': 'tx-myecommerce12345'," +
                "'callback_url': 'https://chapa.co'," +
                "'customization[title]': 'I love e-commerce'," +
                "'customization[description]': 'It is time to pay'" +
                " }";

        // then
        assertThrows(ValidationException.class, () -> Util.validatePostData(formData));
    }
}