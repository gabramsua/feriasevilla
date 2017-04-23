package com.gabant.feriasevilla;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.gabant.feriasevilla.Clases.Contacto;
import com.gabant.feriasevilla.Clases.ListU_usuario;
import com.gabant.feriasevilla.Clases.ListUsuario;
import com.gabant.feriasevilla.Clases.Registro;
import com.gabant.feriasevilla.Clases.U_usuario;
import com.gabant.feriasevilla.Clases.Usuario;
import com.gabant.feriasevilla.Fragments.AgendaFragment;
import com.gabant.feriasevilla.Fragments.MapFragment;
import com.gabant.feriasevilla.Interfaz.IFeria;
import com.gabant.feriasevilla.Interfaz.IRetrofit;
import com.gabant.feriasevilla.Recycler.ContactoFragmentList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements IFeria{

    Fragment f;
    static List<Contacto> contactos;
    List<Usuario> all_usuarios;
    Usuario user_clicked;

    Retrofit retrofit1;
    IRetrofit service1;

    public static List<Contacto> getContactos() {return contactos;}

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
        = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.buscador:
                    f = new ContactoFragmentList();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, f)
                            .commit();
                    return true;
                case R.id.agenda:
                    f = new AgendaFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, f)
                            .commit();
                    return true;
                case R.id.peticiones:
                    /*f = new PeticionesFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, f)
                            .commit();*/
                    return true;
                case R.id.map:
                    f = new MapFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, f)
                            .commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        contactos = new ArrayList<>();
        all_usuarios = new ArrayList<>();
        //getAllContacts();

        retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service1 = retrofit1.create(IRetrofit.class);
    }

    @Override
    public void OnClickU_usuario(U_usuario u) {
        Toast.makeText(this, u.getEstado()+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnClickDetalleLocation(U_usuario u) {

        getLatLon(u.getIdAmigo().getCaseta());

    }

    @Override
    public void onClickContactoUsuario(final Contacto contacto) {
        final boolean[] friend = {false};
        Long my_id = null;

        // Mapeo el contacto a Usuario de la app
        getAllUsers(contacto);

        // Tengo que ver si el contacto clickeado es amigo mío => Me traigo todos mis amigos
        SharedPreferences settings = getSharedPreferences("PREFS_USER", 0);
        my_id = Long.parseLong(settings.getString("ID_USER", "N"));
                // System.out.println("ID DEL LOGUEADO: "+settings.getString("ID_USER", "N"));
        if(!settings.getString("ID_USER", "N").equalsIgnoreCase("N")){
            Call<ListU_usuario> autocompleteList5 = service1.getAmigos(settings.getString("ID_USER", "N").toString());

            final Long finalMy_id = my_id;
            autocompleteList5.enqueue(new Callback<ListU_usuario>() {
                @Override
                public void onResponse(final Response<ListU_usuario> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        if (response.body().getStatus().equalsIgnoreCase("success")){
                            List<U_usuario>amigos = new LinkedList<U_usuario>(response.body().getData());
                            System.out.println("USUARIOS AMIGOS: "+amigos);
                            // Si es amigo mío => Ver detalle
                            for(U_usuario amigo : amigos) {
                                if (amigo.getIdUsuario().getNumTelefono().equals(contacto.getTelefono()) ||
                                        amigo.getIdAmigo().getNumTelefono().equals(contacto.getTelefono())) {
                                    Toast.makeText(MainActivity.this, "ES AMIGO MÍO. VER DETALLE", Toast.LENGTH_SHORT).show();
                                    // OnClickU_usuario(new U_usuario());
                                    friend[0] = true;

                                }
                            }
                            // Si NO es amigo => Enviar petición de amistad
                            if(!friend[0]) {
                                Toast.makeText(MainActivity.this, "No es AMIGO", Toast.LENGTH_SHORT).show();
                                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setCustomImage(R.drawable.noria)
                                        .setTitleText(contacto.getNombre()+" aún no es tu amigo")
                                        .setContentText("¿Quieres agregarlo?")
                                        .setCancelText("Ahora no")
                                        .setConfirmText("Sí, mandar petición")
                                        .showCancelButton(true)
                                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                                //redirect(findViewById(R.id.activity_detalle));
                                            }
                                        }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                                Toast.makeText(MainActivity.this, "Enviar petición", Toast.LENGTH_SHORT).show();
                                                requestFriendship(finalMy_id, Long.valueOf(user_clicked.getId()), "fecha");
                                            }
                                        })
                                        .show();
                            }


                        }else System.out.println("No hay éxito");
                    }else System.out.println("La petición no se ha realizado bien");
                }

                @Override
                public void onFailure(Throwable t) {
                    System.out.println("ERROR en el Servidor => "+t.getMessage());
                }
            });
        }
    }

    @Override
    public void onClickInvitar(Contacto contacto) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        String shareBody = "NOMBRE APP FeriaSevilla";
        String shareSub = "Descarga la app en www.url.es";
        intent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
        intent.putExtra(Intent.EXTRA_TEXT, shareSub);

        startActivity(Intent.createChooser(intent, "Compartir mediante"));
    }

    private void getLatLon(String caseta) {
        Geocoder geocoder;
        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocationName(caseta, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        Double lat = addresses.get(0).getLatitude();
        Double lon = addresses.get(0).getLongitude();

        f = new MapFragment();
        Bundle args = new Bundle();
        args.putDouble("lat", lat);
        args.putDouble("lon", lon);
        f.setArguments(args);

        System.out.println("Caseta: "+caseta);
        System.out.println(lat +" "+lon);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, f)
                .commit();
    }

    private void getAllUsers(final Contacto contacto) {
        Call<ListUsuario> autocompleteList5 = service1.getUsuarios();

        autocompleteList5.enqueue(new Callback<ListUsuario>() {
            @Override
            public void onResponse(Response<ListUsuario> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getStatus().equalsIgnoreCase("success")){
                        all_usuarios = new LinkedList<Usuario>(response.body().getData());
                        System.out.println("USUARIOS: "+all_usuarios);

                        for(Usuario u : all_usuarios){
                            if(u.getNumTelefono().equals(contacto.getTelefono())){
                                // user_clicked = new Usuario();
                                user_clicked = u;
                            }
                        }


                    }else System.out.println("No hay éxito");
                }else System.out.println("La petición no se ha realizado bien");
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("ERROR en el Servidor => "+t.getMessage());
            }
        });
    }

    public void requestFriendship(Long finalMy_id, Long i, String fecha){
        System.out.println("ENVIANDO AMISTAD ENTRE "+finalMy_id+" y "+i);

        Call<Registro> autocompleteList5 = service1.requestFriendship(finalMy_id, i, fecha);

        autocompleteList5.enqueue(new Callback<Registro>() {
            @Override
            public void onResponse(Response<Registro> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    /*System.out.println("REGISTRO: "+response.body().toString());
                    if (response.body().getStatus().equalsIgnoreCase("success")){*/
                        Toast.makeText(MainActivity.this, "Has enviado la petición de amistad.", Toast.LENGTH_SHORT).show();

                    //}else System.out.println("Ha habido una petición enviando la solicitud de amistad");
                }else System.out.println("La petición no se ha realizado bien");
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("ERROR en el Servidor => "+t.getMessage());
            }
        });
    }

}
