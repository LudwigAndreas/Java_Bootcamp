package edu.school21.application.model;

import edu.school21.orm.annotation.OrmColumn;
import edu.school21.orm.annotation.OrmColumnId;
import edu.school21.orm.annotation.OrmEntity;

import java.util.Objects;
import java.util.StringJoiner;

@OrmEntity
public class Car {
    @OrmColumnId
    private Long id;

    @OrmColumn(name = "make", length = 10)
    private String make;

    @OrmColumn(name = "model", length = 10)
    private String model;

    @OrmColumn(name = "year")
    private int year;

    @OrmColumn(name = "millage")
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

    public Car(long id, String make, String model, int year, double mileage) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return getYear() == car.getYear() && Double.compare(car.getMileage(), getMileage()) == 0 && id.equals(car.id) && Objects.equals(getMake(), car.getMake()) && Objects.equals(getModel(), car.getModel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getMake(), getModel(), getYear(), getMileage());
    }
}
