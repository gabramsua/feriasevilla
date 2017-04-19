package com.gabant.feriasevilla.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.gabant.feriasevilla.Clases.Contacto;
import com.gabant.feriasevilla.MainActivity;
import com.gabant.feriasevilla.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuscadorFragment extends Fragment {

    List<Contacto> contactos;
    ListView mContactsList;


    public BuscadorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buscador, container, false);
        contactos = new ArrayList<>();//MainActivity.getContactos();
        //getAllContacts();

/*
        //ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS},1);
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
                System.out.println(id + " = " + name +  phoneNumber+"\n*");
            }


        }
*/
        System.out.println("CONTACTOS DESDE EL FRAGMENT: "+contactos);



        return v;
    }



}
