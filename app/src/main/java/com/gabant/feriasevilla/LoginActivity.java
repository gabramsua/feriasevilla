package com.gabant.feriasevilla;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gabant.feriasevilla.Clases.LoginUsuario;
import com.gabant.feriasevilla.Interfaz.IRetrofit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    EditText user, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences settings = getSharedPreferences("PREFS_USER", 0);
        //idUser = settings.getString("ID_USER", "N");

        //Eliminar SharedPrefs
        /*SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();*/


        //Si el usuario ya se ha registrado, entramos.
        if(!settings.getString("ID_USER", "N").equalsIgnoreCase("N")){

            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }

        user = (EditText) findViewById(R.id.editUser);
        password = (EditText) findViewById(R.id.editPass);
    }

    //Guardamos el ID del usuario que se ha logeado en las preferencias
    public void logear(View view) {
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRetrofit service1 = retrofit1.create(IRetrofit.class);

        Call<LoginUsuario> autocompleteList5 = service1.getLogin(user.getText().toString(), password.getText().toString());

        autocompleteList5.enqueue(new Callback<LoginUsuario>() {
            @Override
            public void onResponse(Response<LoginUsuario> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getStatus().equalsIgnoreCase("success")){

                        //String idUser = "";
                        SharedPreferences settings = getSharedPreferences("PREFS_USER", 0);
                        //idUser = settings.getString("ID_USER", "N");

                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("ID_USER", String.valueOf(response.body().getData().getId()));
                        editor.commit();

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(LoginActivity.this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
