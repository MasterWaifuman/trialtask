package com.fujitsu.trialtask.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "Weather")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "wmocode")
    private int wmocode;

    @Column(name = "airtemperature")
    private float airtemperature;

    @Column(name = "windspeed")
    private float windspeed;

    @Column(name = "phenomenon")
    private String phenomenon;

    @Column(name = "timestamp")
    private long timestamp;

    public WeatherData() {}

    public WeatherData(String name, int wmocode, float airtemperature, float windspeed, String phenomenon, long timestamp) {
        this.name = name;
        this.wmocode = wmocode;
        this.airtemperature = airtemperature;
        this.windspeed = windspeed;
        this.phenomenon = phenomenon;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWmocode() {
        return wmocode;
    }

    public float getAirtemperature() {
        return airtemperature;
    }

    public float getWindspeed() {
        return windspeed;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPhenomenon() {
        return phenomenon;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "WeatherData [ id = " + id + ", name = " + name + ", wmocode = " + wmocode + ", airtemperature = " + airtemperature + ", windspeed = " + windspeed + ", phenomenon = " + phenomenon + ", timestamp = " + timestamp + "]";
    }
}
