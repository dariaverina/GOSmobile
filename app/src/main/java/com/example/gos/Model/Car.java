package com.example.gos.Model;
public class Car {
    private int id;
    private String brand;
    private String model;
    private int year;
    private String number;

    private String state; // состояние машины (в салоне, на руках, списана)
    private String personName; // ФИО человека, кому выдана машина (если есть)

    public Car(int id, String brand, String model, int year, String number, String state, String personName) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.number = number;
        this.state = state;
        this.personName = personName;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getNumber() {
        return number;
    }

    public String getStatus() {
        return state;
    }

}
