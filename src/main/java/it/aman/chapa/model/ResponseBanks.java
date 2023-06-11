package it.aman.chapa.model;

import java.util.List;

public class ResponseBanks extends ResponseData {

    private List<Bank> data;

    public List<Bank> getData() {
        return data;
    }

    public void setData(List<Bank> data) {
        this.data = data;
    }
}
