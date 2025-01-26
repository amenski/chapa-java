package com.yaphet.chapa.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.yaphet.chapa.utility.LocalDateTimeDeserializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VerifyResponse extends Response {

    private Data data;

    public VerifyResponse() {
    }

    public VerifyResponse(String rawJson, String message, String status, int statusCode, Data data) {
        super(rawJson, message, status, statusCode);
        this.data = data;
    }

    @Override
    public VerifyResponse setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public VerifyResponse setStatus(String status) {
        super.setStatus(status);
        return this;
    }

    @Override
    public VerifyResponse setStatusCode(int statusCode) {
        super.setStatusCode(statusCode);
        return this;
    }

    @Override
    public VerifyResponse setRawJson(String rawJson) {
        super.setRawJson(rawJson);
        return this;
    }

    public VerifyResponse setData(Data data) {
        this.data = data;
        return this;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        @SerializedName("first_name")
        private String firstName;
        @SerializedName("last_name")
        private String lastName;
        private String email;
        private String currency;
        private BigDecimal amount;
        private BigDecimal charge;
        private String mode;
        private String method;
        private String type;
        private String status;
        private String reference;
        @SerializedName("tx_ref")
        private String txRef;
        private Customization customization;
        private String meta;
        @SerializedName("created_at")
        @JsonAdapter(LocalDateTimeDeserializer.class)
        private LocalDateTime createdAt;
        @SerializedName("updated_at")
        @JsonAdapter(LocalDateTimeDeserializer.class)
        private LocalDateTime updatedAt;

        @SerializedName("first_name")
        public String getFirstName() {
            return firstName;
        }

        public Data setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public String getLastName() {
            return lastName;
        }

        public Data setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public Data setEmail(String email) {
            this.email = email;
            return this;
        }

        public String getCurrency() {
            return currency;
        }

        public Data setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public Data setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public BigDecimal getCharge() {
            return charge;
        }

        public Data setCharge(BigDecimal charge) {
            this.charge = charge;
            return this;
        }

        public String getMode() {
            return mode;
        }

        public Data setMode(String mode) {
            this.mode = mode;
            return this;
        }

        public String getMethod() {
            return method;
        }

        public Data setMethod(String method) {
            this.method = method;
            return this;
        }

        public String getType() {
            return type;
        }

        public Data setType(String type) {
            this.type = type;
            return this;
        }

        public String getStatus() {
            return status;
        }

        public Data setStatus(String status) {
            this.status = status;
            return this;
        }

        public String getReference() {
            return reference;
        }

        public Data setReference(String reference) {
            this.reference = reference;
            return this;
        }

        public String getTxRef() {
            return txRef;
        }

        public Data setTxRef(String txRef) {
            this.txRef = txRef;
            return this;
        }

        public Customization getCustomization() {
            return customization;
        }

        public Data setCustomization(Customization customization) {
            this.customization = customization;
            return this;
        }

        public String getMeta() {
            return meta;
        }

        public Data setMeta(String meta) {
            this.meta = meta;
            return this;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public Data setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public LocalDateTime getUpdatedAt() {
            return updatedAt;
        }

        public Data setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        @Override
        public String toString() {
            return "Data{" + "firstName='" + firstName + '\'' +
                    ", currency='" + currency + '\'' +
                    ", amount=" + amount +
                    ", charge=" + charge +
                    ", mode='" + mode + '\'' +
                    ", method='" + method + '\'' +
                    ", type='" + type + '\'' +
                    ", status='" + status + '\'' +
                    ", reference='" + reference + '\'' +
                    ", txRef='" + txRef + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "VerifyResponse{" + "status=" + this.getStatus() +
                ", statusCode=" + this.getStatusCode() +
                ", message=" + this.getMessage() +
                ", data=" + data +
                '}';
    }
}
