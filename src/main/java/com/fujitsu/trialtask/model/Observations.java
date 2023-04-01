package com.fujitsu.trialtask.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;
@JacksonXmlRootElement
public class Observations {

    private long timestamp;

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<WeatherData> station = new ArrayList<>();

    public Observations() {}

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<WeatherData> getWeatherData() {
        return station;
    }

    public void setWeatherData(List<WeatherData> weatherData) {
        this.station = weatherData;
    }

    public Observations(List<WeatherData> weatherData) {
        this.station = weatherData;
        //this.timestamp = timestamp;
    }
}
