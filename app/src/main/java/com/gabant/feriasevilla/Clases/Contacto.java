package com.gabant.feriasevilla.Clases;

/**
 * Created by gabri_neno on 19/04/2017.
 */

public class Contacto {
    String nombre, telefono;

    public Contacto() {
    }

    public Contacto(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return nombre +", " + telefono+"\n";
    }
}
