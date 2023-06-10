package it.aman.chapa.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class SubAccountDto {

    @SerializedName("business_name")
    private String businessName;
    @SerializedName("bank_code")
    private String bankCode;
    @SerializedName("account_name")
    private String accountName;
    @SerializedName("account_number")
    private String accountNumber;
    @SerializedName("split_type")
    private SplitType splitType;
    @SerializedName("split_value")
    private Double splitValue;

    public String getBusinessName() {
        return businessName;
    }

    public SubAccountDto setBusinessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public String getBankCode() {
        return bankCode;
    }

    public SubAccountDto setBankCode(String bankCode) {
        this.bankCode = bankCode;
        return this;
    }

    public String getAccountName() {
        return accountName;
    }

    public SubAccountDto setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public SubAccountDto setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public SplitType getSplitType() {
        return splitType;
    }

    public SubAccountDto setSplitType(SplitType splitType) {
        this.splitType = splitType;
        return this;
    }

    public Double getSplitValue() {
        return splitValue;
    }

    public SubAccountDto setSplitValue(Double splitValue) {
        this.splitValue = splitValue;
        return this;
    }

    public Map<String, Object> getAsMap() {
        return new HashMap<String, Object>() {
            {
                put("business_name",  getBusinessName());
                put("account_name",   getAccountName());
                put("account_number", getAccountNumber());
                put("bank_code",      getBankCode());
                put("split_type",     getSplitType().name().toLowerCase());
                put("split_value",    getSplitValue());
            }
        };
    }
}
