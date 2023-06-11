package it.aman.chapa.utility;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.aman.chapa.Chapa;
import it.aman.chapa.model.*;

import static it.aman.chapa.utility.StringUtils.isBlank;

/**
 * The <code>Util</code> class serves as a helper class for the main {@link Chapa} class.
 */
public class Util {

    private static final Clock CLOCK = Clock.systemDefaultZone();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy-HH-mm-ss");
    private final static Gson JSON_MAPPER = new Gson();

    /**
     * @param jsonData A json string to be mapped to a {@link PostData} object.
     * @return A {@link PostData} object which contains post fields of the
     * provided JSON data.
     */
    public static PostData jsonToPostData(String jsonData) {
        if (StringUtils.isBlank(jsonData)) {
            throw new IllegalArgumentException("Can't map null or empty json to PostData object");
        }

        Map<String, String> newMap = jsonToMap(jsonData);
        JsonObject jsonObject = JSON_MAPPER.fromJson(jsonData, JsonObject.class);
        Map<String, String> customizations = JSON_MAPPER.fromJson(jsonObject.get("customizations"), new TypeToken<Map<String, String>>() {}.getType());
        Customization customization = new Customization()
                .setTitle(customizations.get("customization[title]"))
                .setTitle(customizations.get("customization[description]"))
                .setTitle(customizations.get("customization[logo]"))
                ;
        return new PostData()
                .setAmount(new BigDecimal(String.valueOf(newMap.get("amount"))))
                .setCurrency(newMap.get("currency"))
                .setEmail(newMap.get("email"))
                .setFirstName(newMap.get("first_name"))
                .setLastName(newMap.get("last_name"))
                .setTxRef(newMap.get("tx_ref"))
                .setCallbackUrl(newMap.get("callback_url"))
                .setCustomization(customization);
    }

    /**
     * @param jsonData A json string to be mapped to a {@link SubAccountDto} object.
     * @return A {@link SubAccountDto} object which contains post fields of the
     * provided JSON data.
     */
    public static SubAccountDto jsonToSubAccount(String jsonData) {
        if (StringUtils.isBlank(jsonData)) {
            throw new IllegalArgumentException("Can't map null or empty json to SubAccountDto object");
        }

        return JSON_MAPPER.fromJson(jsonData, SubAccountDto.class);
    }

    /**
     * @param jsonData a json string to be mapped to a Map object.
     * @return A Map object which contains post fields of the provided
     * JSON data.
     */
    public static Map<String, String> jsonToMap(String jsonData) {
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
    public static String generateToken() {
        final LocalDateTime now = LocalDateTime.now(CLOCK);
        return UUID.randomUUID().toString().substring(0, 8) + "_" + FORMATTER.format(now);
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
