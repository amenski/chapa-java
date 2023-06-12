package it.aman.chapa.model;

import java.util.ArrayList;
import java.util.List;

public class ResponseBanks extends ResponseData {

    private List<Bank> data;

    public List<Bank> getData() {
        if(data == null) return new ArrayList<>();
        return data;
    }

    public void setData(List<Bank> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseBanks{");
        sb.append("status=").append(this.getStatus());
        sb.append(", statusCode=").append(this.getStatusCode());
        sb.append(", message=").append(this.getMessage());
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
