package com.gabant.feriasevilla.Recycler;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gabant.feriasevilla.Clases.Contacto;
import com.gabant.feriasevilla.Clases.Usuario;
import com.gabant.feriasevilla.Interfaz.IFeria;
import com.gabant.feriasevilla.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Contacto} and makes a call to the
 * specified {@link IFeria}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyContactoRecyclerViewAdapter extends RecyclerView.Adapter<MyContactoRecyclerViewAdapter.ViewHolder> {

    private final List<Contacto> mValues;
    List<Usuario>usuarios;
    private final IFeria mListener;

    public MyContactoRecyclerViewAdapter(List<Usuario>usuarios, List<Contacto> items, IFeria listener) {
        this.usuarios = usuarios;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_contacto_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        for(Usuario u : usuarios){
            System.out.println("Comparando "+u.getNumTelefono()+" con "+holder.mItem.getTelefono());
            if(u.getNumTelefono().equals(holder.mItem.getTelefono())){ // Tiene la app
                System.out.println("HAY UNO IGUAL!!"+ holder.mItem);
                holder.mIdView.setText(mValues.get(position).getNombre());
                holder.mContentView.setText(mValues.get(position).getTelefono());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mListener) {
                            // Notify the active callbacks interface (the activity, if the
                            // fragment is attached to one) that an item has been selected.
                            mListener.onClickContactoUsuario(holder.mItem);
                        }
                    }
                });
            } else{ // NO tiene la app
                holder.mIdView.setText(mValues.get(position).getNombre());
                holder.mContentView.setText(mValues.get(position).getTelefono());

                /*holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mListener) {
                            // Notify the active callbacks interface (the activity, if the
                            // fragment is attached to one) that an item has been selected.
                            mListener.onClickInvitar(holder.mItem);
                        }
                    }
                });
                */
            }
        }
        System.out.println("fin del for");


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Contacto mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
