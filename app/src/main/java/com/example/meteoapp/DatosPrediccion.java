package com.example.meteoapp;

public class DatosPrediccion {


    private String fecha;
    private int temperaturaMax;
    private int temparaturaMin;
    private int humedad;

    public DatosPrediccion(String fecha, int temperaturaMax, int temparaturaMin, int humedad) {
        this.fecha = fecha;
        this.temperaturaMax = temperaturaMax;
        this.temparaturaMin = temparaturaMin;
        this.humedad = humedad;
    }

    public String getFecha() {
        return fecha;
    }
    public int getTemperaturaMax() {
        return temperaturaMax;
    }

    public int getTemparaturaMin() {
        return temparaturaMin;
    }

    public int getHumedad() {
        return humedad;
    }
}