package com.gabant.feriasevilla.Interfaz;

import com.gabant.feriasevilla.Clases.ListU_usuario;
import com.gabant.feriasevilla.Clases.ListUsuario;
import com.gabant.feriasevilla.Clases.LoginUsuario;
import com.gabant.feriasevilla.Clases.Registro;
import com.gabant.feriasevilla.Clases.U_usuario;
import com.gabant.feriasevilla.Clases.Usuario;

import java.util.List;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by macias on 12/04/2017.
 */

public interface IRetrofit {
    String ENDPOINT = "http://gabantdev.esy.es/api.php/";

    @FormUrlEncoded
    @POST("log")
    Call<LoginUsuario> getLogin(@Field("user") String user, @Field("password") String password);

    @GET("usuarios")
    Call<ListUsuario> getUsuarios();

    @GET("u_usuarios")
    Call<ListU_usuario> getU_usuario();

    // Amigos del usuario logueado
    @GET("amigosDe/{id}")
    Call<ListU_usuario> getAmigos(@Path("id") String id);

    @FormUrlEncoded
    @POST("peticionAmistad")
    Call<Registro> requestFriendship(@Field("idUsuario") Long idUsuario,
                                     @Field("idAmigo") Long idAmigo, @Field("fecha") String fecha);
}
