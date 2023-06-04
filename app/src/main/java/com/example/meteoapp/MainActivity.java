package com.example.meteoapp;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "fd1658c0cc521830891301606b421aac";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?";

    private static final String lat = "51.5085300";
    private static final String lon = "-0.1257400";

    private static final String URL_PREDICTION = "https://api.openweathermap.org/data/2.5/forecast?";

    private static final String UNITS = "&units=metric";
    private ArrayList<Datos> listaDatos;
    private ArrayList<DatosPrediccion> listaPrediccion;
    private DatosAdapter datosAdapter;
    private DatosPrediccionAdapter prediccionAdapter;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewPrediccion;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView = findViewById(R.id.rv_datos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewPrediccion = findViewById(R.id.rv_datos_prediction);
        recyclerViewPrediccion.setLayoutManager(new LinearLayoutManager(this));

        listaDatos = new ArrayList<>();
        listaPrediccion = new ArrayList<>();

        datosAdapter = new DatosAdapter(listaDatos, LayoutInflater.from(this));
        recyclerView.setAdapter(datosAdapter);

        prediccionAdapter = new DatosPrediccionAdapter(listaPrediccion, LayoutInflater.from(this));
        recyclerViewPrediccion.setAdapter(prediccionAdapter);



        mostrarDatos();
    }




    public void mostrarDatos() {
        String url = BASE_URL + "lat=" + lat + "&lon=" + lon + UNITS + "&appid=" + API_KEY;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            listaDatos.clear();
                            String ciudad = response.getString("name");
                            JSONObject sys = response.getJSONObject("sys");
                            String countryCode = sys.getString("country");
                            JSONObject main = response.getJSONObject("main");
                            int temperatura = main.getInt("temp");
                            int temperaturaMax = main.getInt("temp_max");
                            int temperaturaMin = main.getInt("temp_min");
                            int humedad = main.getInt("humidity");


                            Datos datos = new Datos(ciudad,countryCode,temperatura, temperaturaMax, temperaturaMin, humedad);
                            listaDatos.add(datos);
                            datosAdapter.notifyDataSetChanged();
                            mostrarDatosPrediccion();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error retrieving API data: " + error.getMessage());
                        Toast.makeText(MainActivity.this, "Error al obtener los datos climatológicos", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


    public void mostrarDatosPrediccion() {
        String url = URL_PREDICTION + "lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            listaPrediccion.clear();
                            JSONArray listArray = response.getJSONArray("list");

                            long timestamp = 0;
                            int tempMax = Integer.MIN_VALUE;
                            int tempMin = Integer.MAX_VALUE;
                            int humedad = 0;
                            int count = 0;

                            for (int i = 0; i < listArray.length(); i++) {
                                JSONObject listObj = listArray.getJSONObject(i);
                                long currentTimestamp = listObj.getLong("dt");



                                // Si es un nuevo día, agregamos el registro
                                if (isNuevoDia(timestamp, currentTimestamp)) {
                                    if (count > 0) {
                                        String fecha = getFechaFromTimestamp(timestamp);
                                        int tempMaxCelsius = (int) (tempMax - 273.15); // Conversión a Celsius
                                        int tempMinCelsius = (int) (tempMin - 273.15); // Conversión a Celsius

                                        DatosPrediccion datosPrediccion = new DatosPrediccion(fecha, tempMaxCelsius, tempMinCelsius, humedad);
                                        listaPrediccion.add(datosPrediccion);

                                        // Reiniciamos los valores para el siguiente día
                                        tempMax = Integer.MIN_VALUE;
                                        tempMin = Integer.MAX_VALUE;
                                        humedad = 0;
                                        count = 0;
                                    }

                                    timestamp = currentTimestamp;
                                }

                                // Actualizamos los valores máximos y mínimos
                                JSONObject main = listObj.getJSONObject("main");
                                double temp = main.getDouble("temp");
                                if (temp > tempMax) {
                                    tempMax = (int) temp;
                                }
                                if (temp < tempMin) {
                                    tempMin = (int) temp;
                                }

                                humedad =  main.getInt("humidity");

                                count++;
                            }

                            // Agregamos el último registro
                            if (count > 0) {
                                String fecha = getFechaFromTimestamp(timestamp);
                                int tempMaxCelsius = (int) (tempMax - 273.15); // Conversión a Celsius
                                int tempMinCelsius = (int) (tempMin - 273.15); // Conversión a Celsius


                                DatosPrediccion datosPrediccion = new DatosPrediccion(fecha, tempMaxCelsius, tempMinCelsius, humedad);
                                listaPrediccion.add(datosPrediccion);
                            }

                            if (!listaPrediccion.isEmpty()){
                                listaPrediccion.remove(0);
                            }

                            prediccionAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error retrieving API data: " + error.getMessage());
                        Toast.makeText(MainActivity.this, "Error al obtener los datos de predicción", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
    private boolean isNuevoDia(long timestamp1, long timestamp2) {
        // Comparamos solo el día, ignorando la hora exacta
        SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.getDefault());
        String day1 = sdf.format(new Date(timestamp1 * 1000));
        String day2 = sdf.format(new Date(timestamp2 * 1000));
        return !day1.equals(day2);
    }
    private String getFechaFromTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date(timestamp * 1000));
    }

}
