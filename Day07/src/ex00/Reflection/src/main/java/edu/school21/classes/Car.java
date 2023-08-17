package edu.school21.classes;

import java.util.StringJoiner;

public class Car {
    private String make;
    private String model;
    private int year;
    private double mileage;

    public Car() {
        this.make = "Default make";
        this.model = "Default model";
        this.year = 0;
        this.mileage = 0.0;
    }

    public Car(String make, String model, int year, double mileage) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
    }

    public double drive(double value) {
        this.mileage += value;
        return mileage;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("make='" + make + "'")
                .add("model='" + model + "'")
                .add("year=" + year)
                .add("mileage=" + mileage)
                .toString();
    }
}
