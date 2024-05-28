package com.example.gos.Model;
import org.json.JSONException;
import org.json.JSONObject;
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

    public String getState() { return state; }

    public String getPersonName() { return personName; }
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", brand);
            json.put("model", model);
            json.put("year", year);
            json.put("number", number);
            json.put("state", state);
            json.put("personName", personName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

}
