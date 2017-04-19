package com.gabant.feriasevilla;

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
import com.gabant.feriasevilla.Clases.U_usuario;
import com.gabant.feriasevilla.Fragments.AgendaFragment;
import com.gabant.feriasevilla.Fragments.MapFragment;
import com.gabant.feriasevilla.Interfaz.IFeria;
import com.gabant.feriasevilla.Recycler.ContactoFragmentList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements IFeria{

    Fragment f;
    static List<Contacto> contactos;

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
        //getAllContacts();
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
    public void onClickContactoUsuario(Contacto contacto) {

        Toast.makeText(this, "Ver detalle de "+contacto.getNombre(), Toast.LENGTH_SHORT).show();

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


}
