package com.example.fitmvp.bean;

public class Recipebean {
    private String food_name;
    private double food_weight;
    private double cal;
    private double pro;
    private double fat;
    private double ch2o;

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public double getFood_weight() {
        return food_weight;
    }

    public void setFood_weight(double food_weight) {
        this.food_weight = food_weight;
    }

    public double getCal() {
        return cal;
    }

    public void setCal(double cal) {
        this.cal = cal;
    }

    public double getPro() {
        return pro;
    }

    public void setPro(double pro) {
        this.pro = pro;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCh2o() {
        return ch2o;
    }

    public void setCh2o(double ch2o) {
        this.ch2o = ch2o;
    }
}
