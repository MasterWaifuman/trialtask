package com.fujitsu.trialtask;


import com.fujitsu.trialtask.model.WeatherData;

import java.util.Hashtable;

public class FeeCalc {

    static final Hashtable<String, Hashtable<String, Float>> rbf = new Hashtable<>() {
        {
            put("Tallinn", new Hashtable<String, Float>(){
                {
                    put("Car", 4f);
                    put("Scooter", 3.5f);
                    put("Bike", 3f);
                }
            });
            put("Tartu", new Hashtable<String, Float>(){
                {
                    put("Car", 3.5f);
                    put("Scooter", 3f);
                    put("Bike", 2.5f);
                }
            });
            put("Pärnu", new Hashtable<String, Float>(){
                {
                    put("Car", 3f);
                    put("Scooter", 2.5f);
                    put("Bike", 2f);
                }
            });
        }
    };

    public static float rbf(WeatherData weatherData, String vehicle) {
        try {
            return rbf.get(weatherData.getName()).get(vehicle);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public static float atef(WeatherData weatherData, String vehicle) {
        if (vehicle.equalsIgnoreCase("scooter") || vehicle.equalsIgnoreCase("bike")) {
            if (weatherData.getAirtemperature() < -10) return 1f;
            else if (weatherData.getAirtemperature() < 0) return 0.5f;
            else return 0;
        }
        else return 0;
    }

    public static float wsef(WeatherData weatherData, String vehicle) {
        if (vehicle.equalsIgnoreCase( "bike")) {
            if (weatherData.getWindspeed() > 20) {
                System.out.println("Usage of selected vehicle type is forbidden");
                return 0;
            }
            else if (weatherData.getWindspeed() > 10) return 0.5f;
            else return 0;
        }
        else return 0;
    }

    public static float wpef(WeatherData weatherData, String vehicle) {
        if (vehicle.equalsIgnoreCase("scooter") || vehicle.equalsIgnoreCase("bike")) {
            if (weatherData.getPhenomenon().toLowerCase().contains("glaze") || weatherData.getPhenomenon().toLowerCase().contains("hail") || weatherData.getPhenomenon().contains("thunder")) return 0;
            else if (weatherData.getPhenomenon().toLowerCase().contains("snow") || weatherData.getPhenomenon().toLowerCase().contains("sleet")) return 1f;
            //shower is rain
            else if (weatherData.getPhenomenon().toLowerCase().contains("rain") || weatherData.getPhenomenon().toLowerCase().contains("shower")) return 0.5f;
            else return 0;
        }
        else return 0;
    }

    public static float calculate(WeatherData weatherData, String vehicle) {
        return rbf(weatherData, vehicle) + atef(weatherData, vehicle) + wsef(weatherData, vehicle) + wpef(weatherData, vehicle);
    }
}
