package com.fujitsu.trialtask.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotBlank;

@JacksonXmlRootElement
public class Order {

    private String order = "smth";

    @NotBlank(message = "Invalid city")
    private String city;

    @NotBlank(message = "Invalid vehicle")
    private String vehicle;

    private double fee = 0;

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getOrder() {
        return order;
    }

    public String getCity() {
        return city;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public Order() {}

    public Order(String order, String city, String vehicle) {
        this.order = order;
        this.city = city;
        this.vehicle = vehicle;
    }
}
