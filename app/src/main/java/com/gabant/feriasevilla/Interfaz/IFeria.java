package com.gabant.feriasevilla.Interfaz;

import com.gabant.feriasevilla.Clases.Contacto;
import com.gabant.feriasevilla.Clases.U_usuario;

/**
 * Created by macias on 16/04/2017.
 */

public interface IFeria {
    void OnClickU_usuario(U_usuario u);
    void OnClickDetalleLocation(U_usuario u);
    void onClickContactoUsuario(Contacto contacto);
    void onClickInvitar(Contacto contacto);
}
