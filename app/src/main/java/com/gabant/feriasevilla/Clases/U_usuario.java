package com.gabant.feriasevilla.Clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by macias on 16/04/2017.
 */

public class U_usuario {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("idUsuario")
    @Expose
    private Usuario idUsuario;
    @SerializedName("idAmigo")
    @Expose
    private Usuario idAmigo;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("estado")
    @Expose
    private String estado;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario getIdAmigo() {
        return idAmigo;
    }

    public void setIdAmigo(Usuario idAmigo) {
        this.idAmigo = idAmigo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "U_usuario{" +
                "id='" + id + '\'' +
                ", idUsuario=" + idUsuario +
                ", idAmigo=" + idAmigo +
                ", fecha='" + fecha + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
