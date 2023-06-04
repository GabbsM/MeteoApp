package com.example.meteoapp;

public class Datos {

    private String ciudad;
    private String countryCode;
    private int temp;
    private int temperaturaMax;
    private int temparaturaMin;
    private int humedad;



    public Datos(String ciudad,String countryCode,int temp, int temperaturaMax, int temparaturaMin, int humedad) {
        this.ciudad = ciudad;
        this.countryCode = countryCode;
        this.temp = temp;
        this.temperaturaMax = temperaturaMax;
        this.temparaturaMin = temparaturaMin;
        this.humedad = humedad;


    }

    public String getCiudad() {
        return ciudad;
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

    public int getTemp() {
        return temp;
    }

    public String getCountryCode() {
        return countryCode;
    }
}

