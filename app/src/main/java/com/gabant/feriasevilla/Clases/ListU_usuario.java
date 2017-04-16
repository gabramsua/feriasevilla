package com.gabant.feriasevilla.Clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by macias on 16/04/2017.
 */

public class ListU_usuario {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<U_usuario> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<U_usuario> getData() {
        return data;
    }

    public void setData(List<U_usuario> data) {
        this.data = data;
    }
}
