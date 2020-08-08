package com.app.converters;

import com.app.model.Car;

public class CarsJsonConverter extends JsonConverter<Car> {

    public CarsJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
