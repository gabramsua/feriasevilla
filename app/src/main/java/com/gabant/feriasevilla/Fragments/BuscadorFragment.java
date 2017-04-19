package com.gabant.feriasevilla.Fragments;


import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.gabant.feriasevilla.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuscadorFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };
    /*
     * Defines an array that contains resource ids for the layout views
     * that get the Cursor column contents. The id is pre-defined in
     * the Android framework, so it is prefaced with "android.R.id"
     */
    private final static int[] TO_IDS = {
            android.R.id.text1
    };
    // Define global mutable variables
    // Define a ListView object
    ListView mContactsList;
    // Define variables for the contact the user selects
    // The contact's _ID value
    long mContactId;
    // The contact's LOOKUP_KEY
    String mContactKey;
    // A content URI for the selected contact
    Uri mContactUri;
    // An adapter that binds the result Cursor to the ListView
    private SimpleCursorAdapter mCursorAdapter;


    public BuscadorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buscador, container, false);

        String[] projection = new String[]
                {
                        ContactsContract.Contacts.NAME,
                        ContactsContract.Contacts.NUMBER
                };

        Cursor c = ctx.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, projection, null, null,
                People.NAME + " ASC");
        c.moveToFirst();

        int nameCol = c.getColumnIndex(People.NAME);
        int numCol = c.getColumnIndex(People.NUMBER);

        int nContacts = c.getCount();

        do
        {
            // Do something
        } while(c.moveToNext());

        /*mContactsList =
                (ListView) v.findViewById(R.id.contactos);
        // Gets a CursorAdapter
        mCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.agenda_item,
                null,
                FROM_COLUMNS, TO_IDS,
                0);
        // Sets the adapter for the ListView
        mContactsList.setAdapter(mCursorAdapter);
        mContactsList.setOnItemClickListener(this);*/




        return v;
    }

    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION =
            {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.LOOKUP_KEY,
                    Build.VERSION.SDK_INT
                            >= Build.VERSION_CODES.HONEYCOMB ?
                            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                            ContactsContract.Contacts.DISPLAY_NAME

            };

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) { return null; }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {}

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {}
}
