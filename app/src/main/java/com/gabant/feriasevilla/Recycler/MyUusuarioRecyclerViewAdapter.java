package com.gabant.feriasevilla.Recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gabant.feriasevilla.Clases.U_usuario;
import com.gabant.feriasevilla.Interfaz.IFeria;
import com.gabant.feriasevilla.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.gabant.feriasevilla.Clases.U_usuario} and makes a call to the
 * specified {@link IFeria}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyUusuarioRecyclerViewAdapter extends RecyclerView.Adapter<MyUusuarioRecyclerViewAdapter.ViewHolder> {

    private final List<U_usuario> mValues;
    private final IFeria mListener;

    public MyUusuarioRecyclerViewAdapter(List<U_usuario> items, IFeria listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_uusuario_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        System.out.println(mValues.get(position).getIdAmigo().getNombre());
        holder.nombre.setText(mValues.get(position).getIdAmigo().getNombre());
        holder.caseta.setText(mValues.get(position).getIdAmigo().getCaseta());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnClickU_usuario(holder.mItem);
                }
            }
        });

        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mListener) {
                    mListener.OnClickDetalleLocation(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nombre;
        public final TextView caseta;
        public U_usuario mItem;
        public final ImageView location;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nombre = (TextView) view.findViewById(R.id.nombreAmigo);
            caseta = (TextView) view.findViewById(R.id.caseta);
            location = (ImageView) view.findViewById(R.id.location);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
