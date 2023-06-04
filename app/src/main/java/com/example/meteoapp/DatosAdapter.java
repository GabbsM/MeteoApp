package com.example.meteoapp;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DatosAdapter extends RecyclerView.Adapter<DatosAdapter.TaskHolder> {

        private ArrayList<Datos> listaDatos;
        private LayoutInflater inflater;

        public DatosAdapter(ArrayList<Datos> listaDatos, LayoutInflater inflater) {
                this.listaDatos = listaDatos;
                this.inflater = inflater;
        }

        @NonNull
        @Override
        public DatosAdapter.TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = inflater.inflate(R.layout.datos, parent, false);
                return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DatosAdapter.TaskHolder holder, int position) {

                Datos datos = listaDatos.get(position);
                holder.bind(datos);

        }

        @Override
        public int getItemCount() {
                return listaDatos.size();
        }

        public static class TaskHolder extends RecyclerView.ViewHolder {


                private Datos datos;
                private TextView ciudad;
                private TextView codigoPais;

                private TextView temp;
                private TextView tempMax;
                private TextView tempMin;
                private TextView humedad;



                public TaskHolder(View view) {
                        super(view);

                        ciudad = view.findViewById(R.id.tvCiudad);
                        temp = view.findViewById(R.id.tvTemp);
                        tempMax = view.findViewById(R.id.tvTempMax);
                        tempMin = view.findViewById(R.id.tvTempMin);
                        humedad = view.findViewById(R.id.tvHumedad);
                        codigoPais =view.findViewById(R.id.tvCountryCode);


                }

                public void bind(Datos datos) {
                        this.datos = datos;
                        ciudad.setText(datos.getCiudad ());
                        codigoPais.setText(datos.getCountryCode());
                        temp.setText(String.valueOf((datos.getTemp()))+ "°");
                        tempMax.setText(Html.fromHtml("<b>Temp. máx:</b> " + String.valueOf(datos.getTemperaturaMax()) + "°"));
                        tempMin.setText(Html.fromHtml("<b>Temp. min:</b> " + String.valueOf(datos.getTemparaturaMin()) + "°"));
                        humedad.setText(Html.fromHtml("<b>Humedad:</b> " + String.valueOf(datos.getHumedad()) + "%"));

                }

        }
}
