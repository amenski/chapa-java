package com.yaphet.chapa.client;

/**
 * <p>A default implementation of {@link IChapaClient}.</p><br>
 *
 * <p>Uses Unirest to make HTTP requests.</p>
 *
 * @see <a href="http://kong.github.io/unirest-java/">Unirest</a>
 */
public class ChapaClientImpl /* implements IChapaClient */{

//    private static final String authorizationHeader = "Authorization";
//    private static final String acceptEncodingHeader = "Accept-Encoding";
//
//    private int statusCode;
//
//    @Override
//    public String post(String url, Map<String, Object> fields, String secreteKey) throws Throwable {
//        MultipartBody request = Unirest.post(url)
//                .header(acceptEncodingHeader, "application/json")
//                .header(authorizationHeader, "Bearer " + secreteKey)
//                .fields(fields);
//
//        HttpResponse<JsonNode> jsonNodeHttpResponse = request.asJson();
//
//        statusCode = jsonNodeHttpResponse.getStatus();
//        return jsonNodeHttpResponse.getBody().toString();
//    }
//
//    @Override
//    public String post(String url, String body, String secreteKey) throws Throwable {
//        RequestBodyEntity request = Unirest.post(url)
//                .header(acceptEncodingHeader, "application/json")
//                .header(authorizationHeader, "Bearer " + secreteKey)
//                .body(body);
//
//        HttpResponse<JsonNode> jsonNodeHttpResponse = request.asJson();
//
//        statusCode = jsonNodeHttpResponse.getStatus();
//        return jsonNodeHttpResponse.getBody().toString();
//    }
//
//    @Override
//    public String get(String url, String secreteKey) throws Throwable {
//        HttpResponse<JsonNode> httpResponse = Unirest.get(url)
//                .header(acceptEncodingHeader, "application/json")
//                .header(authorizationHeader, "Bearer " + secreteKey)
//                .asJson();
//
//        statusCode = httpResponse.getStatus();
//        return httpResponse.getBody().toString();
//    }
//
//    @Override
//    public int getStatusCode() {
//        return statusCode;
//    }
}
