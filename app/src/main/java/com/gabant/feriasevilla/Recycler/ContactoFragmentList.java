package com.gabant.feriasevilla.Recycler;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabant.feriasevilla.Clases.Contacto;
import com.gabant.feriasevilla.Clases.ListU_usuario;
import com.gabant.feriasevilla.Clases.ListUsuario;
import com.gabant.feriasevilla.Clases.Usuario;
import com.gabant.feriasevilla.Interfaz.IFeria;
import com.gabant.feriasevilla.Interfaz.IRetrofit;
import com.gabant.feriasevilla.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link IFeria}
 * interface.
 */
public class ContactoFragmentList extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    List<Contacto> contactos;
    RecyclerView recyclerView;

    private IFeria mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactoFragmentList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacto_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            contactos = new ArrayList<>();//MainActivity.getContactos();
            getAllContacts();
            getAllUsers();
            

        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFeria) {
            mListener = (IFeria) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IFeria");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    private void getAllUsers() {
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRetrofit service1 = retrofit1.create(IRetrofit.class);

        Call<ListUsuario> autocompleteList5 = service1.getUsuarios();

        autocompleteList5.enqueue(new Callback<ListUsuario>() {
            @Override
            public void onResponse(Response<ListUsuario> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getStatus().equalsIgnoreCase("success")){
                        List<Usuario>usuarios = new LinkedList<Usuario>(response.body().getData());
                        System.out.println("USUARIOS: "+usuarios);
                        recyclerView.setAdapter(new MyContactoRecyclerViewAdapter(usuarios, contactos, mListener));
                    }else System.out.println("No hay éxito");
                }else System.out.println("La petición no se ha realizado bien");
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("ERROR en el Servidor => "+t.getMessage());
            }
        });
    }

    public void getAllContacts(){
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS},1);
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_CONTACTS},1);
        ContentResolver resolver = getActivity().getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while(cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            System.out.println("\n*CONTACT LIST: ");

            Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);

            while(phoneCursor.moveToNext()){
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //System.out.println(id + " = " + name +  phoneNumber+"\n*");
                contactos.add(new Contacto(name, phoneNumber));
            }

            System.out.println(contactos);

        }
    }
}
