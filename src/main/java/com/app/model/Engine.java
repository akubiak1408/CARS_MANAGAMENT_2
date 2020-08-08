package com.app.model;

import com.app.enums.EngineType;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Engine {
    private EngineType engineType;
    private int power;

    @Override
    public String toString() {
        return "\nEngine type: " + engineType +
                "\nPower: " + power + " KM";
    }
}
