package com.gabant.feriasevilla.Clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by macias on 12/04/2017.
 */

public class ListUsuario {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Usuario> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Usuario> getData() {
        return data;
    }

    public void setData(List<Usuario> data) {
        this.data = data;
    }
}
