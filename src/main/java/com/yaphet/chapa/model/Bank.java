package com.yaphet.chapa.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.yaphet.chapa.utility.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

public class Bank {

    private String id;

    private String name;

    @SerializedName("country_id")
    private int countryId;

    private Integer active;

    @SerializedName("acct_length")
    private Integer accountLength;

    @SerializedName("is_mobilemoney")
    private Integer isMobileMoney;

    @SerializedName("is_rtgs")
    private Integer isRtgs;

    private String swift;

    private String currency;

    @SerializedName("created_at")
    @JsonAdapter(LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @SerializedName("updated_at")
    @JsonAdapter(LocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt;

    public String getId() {
        return id;
    }

    public Bank setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Bank setName(String name) {
        this.name = name;
        return this;
    }

    public int getCountryId() {
        return countryId;
    }

    public Bank setCountryId(int countryId) {
        this.countryId = countryId;
        return this;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getAccountLength() {
        return accountLength;
    }

    public void setAccountLength(Integer accountLength) {
        this.accountLength = accountLength;
    }

    public Integer getIsMobileMoney() {
        return isMobileMoney;
    }

    public void setIsMobileMoney(Integer isMobileMoney) {
        this.isMobileMoney = isMobileMoney;
    }

    public Integer getIsRtgs() {
        return isRtgs;
    }

    public void setIsRtgs(Integer isRtgs) {
        this.isRtgs = isRtgs;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bank{");
        sb.append("name='").append(name).append('\'');
        sb.append(", countryId=").append(countryId);
        sb.append(", active=").append(active);
        sb.append(", accountLength=").append(accountLength);
        sb.append(", swift='").append(swift).append('\'');
        sb.append(", currency='").append(currency).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
