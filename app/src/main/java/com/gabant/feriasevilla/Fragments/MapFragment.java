package com.gabant.feriasevilla.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabant.feriasevilla.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    MapView mMapView;;
    private GoogleMap mMap;
    Marker perth;
    Double lat,lon;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        Bundle args = getArguments();
        /*
        Double lat = args.getDouble("lat", 37.370085);
        Double lon = args.getDouble("lon", -5.999582);
        */
        lat = args.getDouble("lat", 0);
        lon = args.getDouble("lon", 0);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mMapView.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Sitio especifico
        LatLng sitioActual = new LatLng(lat, lon);

        //Añado un marcador y lo definimos
        perth = mMap.addMarker(new MarkerOptions()
                .position(sitioActual)
                .title("En la feria")
                .snippet("Illo que pasa")
                .draggable(false));


        //Movemos la cámara del mapa para centrar la ubicacion
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sitioActual));
        mMap.setMapType(mMap.MAP_TYPE_SATELLITE);


    }

}


