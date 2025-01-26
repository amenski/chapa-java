package com.yaphet.chapa;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.yaphet.chapa.utility.LocalDateTimeDeserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class DateTimeDeserializerTest {

    @Test
    void deserialize() {
        //given
        String json = "{\"created_at\":\"2023-02-02T07:05:23.000000Z\"}";

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new com.yaphet.chapa.utility.LocalDateTimeDeserializer())
                .create();
        PrimitiveLocalDateTime target = gson.fromJson(json, PrimitiveLocalDateTime.class);

        // verify
        Assertions.assertNotNull(target);
        Assertions.assertEquals(target.getCreatedAt(), LocalDateTime.parse("2023-02-02T07:05:23"));
    }

    private static class PrimitiveLocalDateTime {
        @SerializedName("created_at")
        @JsonAdapter(LocalDateTimeDeserializer.class)
        private LocalDateTime createdAt;

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }
    }
}
