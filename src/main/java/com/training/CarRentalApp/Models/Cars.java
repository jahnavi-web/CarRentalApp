package com.training.CarRentalApp.Models;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cars
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long car_id;

    @JsonProperty("car_name")
    private String car_name;

    @JsonProperty("fuel_type")
    private String fuel_type;

    @JsonProperty("num_seats")
    private int num_seats;

    @JsonProperty("price_per_day")
    private double price_per_day;

    @JsonProperty("car_image")
    private String car_image;

    @JsonProperty("availability")
    private boolean availability;

    public Long getCar_id() {
        return car_id;
    }

    public void setCar_id(Long car_id) {
        this.car_id = car_id;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public int getNum_seats() {
        return num_seats;
    }

    public void setNum_seats(int num_seats) {
        this.num_seats = num_seats;
    }

    public double getPrice_per_day() {
        return price_per_day;
    }

    public void setPrice_per_day(double price_per_day) {
        this.price_per_day = price_per_day;
    }

    public String getCar_image() {
        return car_image;
    }

    public void setCar_image(String car_image) {
        this.car_image = car_image;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public Cars(Long car_id, String car_name, String fuel_type, int num_seats, double price_per_day, String car_image,
                boolean availability) {
        super();
        this.car_id = car_id;
        this.car_name = car_name;
        this.fuel_type = fuel_type;
        this.num_seats = num_seats;
        this.price_per_day = price_per_day;
        this.car_image = car_image;
        this.availability = availability;
    }

    public Cars() {
        super();
        // TODO Auto-generated constructor stub
    }


}

