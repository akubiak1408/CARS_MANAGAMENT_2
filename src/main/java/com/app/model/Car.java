package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data


public class Car {

    private String model;
    private BigDecimal price;
    private double mileage;
    private Engine engine;
    private CarBody carBody;
    private Wheel wheel;


    public void setModel(String model) {
        this.model = model.matches("[A-Z ]+") ? model.replace("[ ]+", " ") : "AUDI";
    }

    public void setPrice(BigDecimal price) {
        this.price = price.compareTo(new BigDecimal(0)) > 0 ? price : new BigDecimal(10000);
    }

    public void setMileage(double mileage) {
        this.mileage = mileage > 0 ? mileage : 10000;
    }

    @Override
    public String toString() {
        return "Car: " +
                "\nModel: " + model +
                "\nPrice: " + price + " $" +
                "\nMileage: " + mileage + " miles" +
                "\n\nEngine: " + engine +
                "\n\nCarBody: " + carBody +
                "\n\nWheel: " + wheel;
    }
}
