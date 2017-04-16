package com.gabant.feriasevilla.Recycler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gabant.feriasevilla.Clases.ListU_usuario;
import com.gabant.feriasevilla.Clases.LoginUsuario;
import com.gabant.feriasevilla.Interfaz.IFeria;
import com.gabant.feriasevilla.Interfaz.IRetrofit;
import com.gabant.feriasevilla.LoginActivity;
import com.gabant.feriasevilla.MainActivity;
import com.gabant.feriasevilla.R;

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
public class UusuarioFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private IFeria mListener;
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UusuarioFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static UusuarioFragment newInstance(int columnCount) {
        UusuarioFragment fragment = new UusuarioFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uusuario_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            getDatos();

        }
        return view;
    }

    private void getDatos() {
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRetrofit service1 = retrofit1.create(IRetrofit.class);

        Call<ListU_usuario> autocompleteList5 = service1.getU_usuario();

        autocompleteList5.enqueue(new Callback<ListU_usuario>() {
            @Override
            public void onResponse(Response<ListU_usuario> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getStatus().equalsIgnoreCase("success")){
                        System.out.println("Recycler");
                        recyclerView.setAdapter(new MyUusuarioRecyclerViewAdapter(response.body().getData(), mListener));
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());
            }
        });

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
}
