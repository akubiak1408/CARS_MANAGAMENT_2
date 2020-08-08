package com.app.model;

import com.app.enums.CarBodyColor;
import com.app.enums.CarBodyType;

import java.util.List;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CarBody {
    private CarBodyColor carBodyColor;
    private CarBodyType carBodyType;
    private List<String> components;

    @Override
    public String toString() {
        return "\nCar body color: " + carBodyColor +
                "\nCar body type: " + carBodyType +
                "\nComponents: " + components;
    }
}
