package com.example.meteoapp;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DatosPrediccionAdapter extends RecyclerView.Adapter<DatosPrediccionAdapter.TaskHolder> {

        private ArrayList<DatosPrediccion> listaPrediccion;
        private LayoutInflater inflater_prediccion;

        public DatosPrediccionAdapter(ArrayList<DatosPrediccion> listaPrediccion, LayoutInflater inflater) {
                this.listaPrediccion = listaPrediccion;
                this.inflater_prediccion = inflater;
        }

        @NonNull
        @Override
        public DatosPrediccionAdapter.TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = inflater_prediccion.inflate(R.layout.datos_prediccion,parent,false);
                return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DatosPrediccionAdapter.TaskHolder holder, int position) {

                DatosPrediccion datosPrediccion = listaPrediccion.get(position);
                holder.bind(datosPrediccion);

        }

        @Override
        public int getItemCount() {
                return listaPrediccion.size();
        }

        public static class TaskHolder extends RecyclerView.ViewHolder{

                private DatosPrediccion datosPrediccion;
                private TextView fecha;
                private TextView tempMax;
                private TextView tempMin;
                private TextView humedad;


                public TaskHolder(View view){
                        super(view);

                        fecha = view.findViewById(R.id.tvFecha);
                        tempMax = view.findViewById(R.id.tvTempMax);
                        tempMin = view.findViewById(R.id.tvTempMin);
                        humedad = view.findViewById(R.id.tvHumedad);

                }

                public void bind(DatosPrediccion datosPrediccion1){
                        datosPrediccion = datosPrediccion1;
                        fecha.setText(datosPrediccion.getFecha());
                        tempMax.setText("Temperatura máx: " + String.valueOf(datosPrediccion.getTemperaturaMax()) + "°");
                        tempMin.setText("Temperatura min: " + String.valueOf(datosPrediccion.getTemparaturaMin()) + "°");
                        humedad.setText("Humedad: " + String.valueOf(datosPrediccion.getHumedad())+ "%");

                }

        }
}
