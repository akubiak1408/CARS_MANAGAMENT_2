package com.app.service;

import com.app.converters.CarsJsonConverter;
import com.app.enums.CarBodyColor;
import com.app.enums.CarBodyType;
import com.app.enums.EngineType;
import com.app.enums.TyreType;
import com.app.model.Car;
import com.app.model.CarBody;
import com.app.model.Engine;
import com.app.model.Wheel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DataGeneratorService {

    private final String filename;
    private final String[] carMarks = {"AUDI", "BMW", "JEEP", "HYUNDAI", "MERCEDES", "FIAT", "CITROEN", "RENAULT"};
    private final String[] carComponentsList = {"AUDIO", "CAMERAS", "SENSORS",
            "ABS", "WIRING HARNESSES", "AIR CONDITIONING", "FOG LIGHTS", "ASP"};
    private final CarBodyColor[] carBodyColors = {CarBodyColor.BLACK, CarBodyColor.BLUE,
            CarBodyColor.GREEN, CarBodyColor.RED, CarBodyColor.SILVER, CarBodyColor.WHITE};
    private final CarBodyType[] carBodyTypes = {CarBodyType.SEDAN, CarBodyType.HATCHBACK, CarBodyType.COMBI};
    private final EngineType[] engineTypes = {EngineType.DIESEL, EngineType.GASOLINE, EngineType.LPG};
    private final TyreType[] tyreTypes = {TyreType.SUMMER, TyreType.WINTER};
    private final String[] wheelModels = {"PIRELLI", "DBICA", "MICHELIN", "CONTINENTAL", "GOODYEAR"};


    public DataGeneratorService(String filename) {
        this.filename = filename;
    }


    public Car genrateCar() {
        Random r = new Random();
        return Car.builder()
                .model(carMarks[r.nextInt(carMarks.length)])
                .price(BigDecimal.valueOf(r.nextDouble() * 100000).setScale(2,RoundingMode.HALF_UP))
                .mileage(BigDecimal.valueOf(r.nextDouble() * 100000).setScale(2, RoundingMode.HALF_UP).doubleValue())
                .engine(
                        new Engine(
                                engineTypes[r.nextInt(engineTypes.length)],
                                r.nextInt(150) + 50
                        )
                )
                .carBody(
                        new CarBody(
                                carBodyColors[r.nextInt(carBodyColors.length)],
                                carBodyTypes[r.nextInt(carBodyTypes.length)],
                                components(r.nextInt(8))

                        )
                )
                .wheel(
                        new Wheel(
                                wheelModels[r.nextInt(wheelModels.length)],
                                r.nextInt(8) + 15,
                                tyreTypes[r.nextInt(tyreTypes.length)]
                        )
                ).build();
    }

    private List<String> components(int size) {
        Random r = new Random();
        List<String> list = new ArrayList<>();

        if (size == 0) {
            list.add("No components");
        }

        for (int i = 0; i < size; i++) {
            list.add(carComponentsList[r.nextInt(carComponentsList.length)]);
        }
        return list
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    void generator(int size, String jsonFilePath) {
        for (int i = 0; i < size; i++) {
            new CarsJsonConverter(jsonFilePath + "\\car" + i + ".json").toJson(genrateCar());
        }
    }
}

