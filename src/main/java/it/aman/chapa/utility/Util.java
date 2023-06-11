package it.aman.chapa.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.aman.chapa.Chapa;
import it.aman.chapa.model.Bank;
import it.aman.chapa.model.InitializeResponseData;
import it.aman.chapa.model.SubAccountResponseData;
import it.aman.chapa.model.VerifyResponseData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static it.aman.chapa.utility.StringUtils.isBlank;

/**
 * The <code>Util</code> class serves as a helper class for the main {@link Chapa} class.
 */
public class Util {

    private final static Gson JSON_MAPPER = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
            .create();

    /**
     * @param jsonData a json string to be mapped to a Map object.
     * @return A Map object which contains post fields of the provided
     * JSON data.
     */
    public static Map<String, Object> jsonToMap(String jsonData) {
        return JSON_MAPPER.fromJson(jsonData, Map.class);
    }

    /**
     * @param jsonData a json string to be mapped to an {@link InitializeResponseData} object.
     * @return An {@link InitializeResponseData} object which contains response fields of the provided
     * JSON data.
     */
    public static InitializeResponseData jsonToInitializeResponseData(String jsonData) {
        return JSON_MAPPER.fromJson(jsonData, InitializeResponseData.class);
    }

    /**
     * @param jsonData a json string to be mapped to a {@link VerifyResponseData} object.
     * @return A {@link VerifyResponseData} object which contains response fields of the provided
     * JSON data.
     */
    public static VerifyResponseData jsonToVerifyResponseData(String jsonData) {
        return JSON_MAPPER.fromJson(jsonData, VerifyResponseData.class);
    }

    /**
     * @param jsonData a json string to be mapped to a {@link SubAccountResponseData} object.
     * @return A {@link SubAccountResponseData} object which contains response fields of the provided
     * JSON data.
     */
    public static SubAccountResponseData jsonToSubAccountResponseData(String jsonData) {
        return JSON_MAPPER.fromJson(jsonData, SubAccountResponseData.class);
    }

    /**
     * @param jsonData a json string to be mapped to a list of {@link Bank} objects.
     * @return A list of {@link Bank} objects each containing details of a bank.
     */
    public static List<Bank> extractBanks(String jsonData) {
        JsonObject jsonObject = JSON_MAPPER.fromJson(jsonData, JsonObject.class);
        return JSON_MAPPER.fromJson(jsonObject.get("data"), new TypeToken<List<Bank>>() {}.getType());
    }

    /**
     * @return A random string followed by the current date/time value (dd-MM-yy-HH-mm-ss).
     */
    public static String generateTransactionReferenceToken() {
        final LocalDateTime now = LocalDateTime.now();
        return UUID.randomUUID().toString().substring(0, 8) + "_" + DateTimeFormatter.ofPattern("dd-MM-yy-HH-mm-ss").format(now);
    }

    public static void putIfNotNull(Map<String, Object> fields, String key, String value) {
        if(isBlank(value)) return;
        fields.put(key, value);
    }

    public static boolean isAnyNull(Map<String, Object> fields, String... keys) {
        if (keys == null) return false;
        if (fields == null || fields.isEmpty()) return true;
        for (String key : keys) {
            if (!fields.containsKey(key) || fields.get(key) == null) return true;
        }
        return false;
    }
}
