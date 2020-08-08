package com.app.service;

import com.app.enums.CarBodyType;
import com.app.enums.EngineType;
import com.app.enums.SortType;
import com.app.exceptions.MyException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuService {

    private final String jsonFileName = "W:\\KMPrograms\\02_CARS_COLLECTION_MANAGEMENT\\carsJson";
    private final DataGeneratorService dataGeneratorService = new DataGeneratorService(jsonFileName);
    private final CarService carService = new CarService(jsonFileName);
    private final UserDataService userDataService = new UserDataService();

    public void mainMenuService() {

        do {
            try {

                switch (userDataService.whichOption()) {
                    case 1:
                        option1();
                        break;
                    case 2:
                        option2();
                        break;
                    case 3:
                        option3();
                        break;
                    case 4:
                        option4();
                        break;
                    case 5:
                        option5();
                        break;
                    case 6:
                        option6();
                        break;
                    case 7:
                        option7();
                        break;
                    case 8:
                        option8();
                        break;
                    case 9:
                        option0();
                        /*
                          Czy jest optymalna metoda -> by usunąć wszystkie pliki (po czym wygenerować nowe)?
                         */
                        break;
                    case 0:
                        return;
                }

            } catch (MyException e) {
                System.err.println(e.getLocalDateTime());
                System.err.println(e.getExceptionMessage());
            }
        } while (true);
    }


    /**
     * Option 0
     * 1. You can define how many files you want to generate.
     * 2. One json file equals one car.
     */
    private void option0() {
        dataGeneratorService.generator(userDataService.howManyCars(), jsonFileName);
    }


    /**
     * Option 1
     * 1. Show all cars, which are genrate to json files.
     */

    private void option1() {
        System.out.println(carService.toString());
    }

    /**
     * Option 2
     * 0. Method return sorted car collection
     * 1. Sort by quantity of components
     * 2. Sort by engine power
     * 3. Sort by wheel size
     * 4. Ascending or descending
     */
    private void option2() {
        SortType sortType = userDataService.getSortType();
        boolean ascending = userDataService.ascendingOrDescending();
        carService.sort(sortType, ascending).forEach(System.out::println);
    }

    /**
     * Option 3
     * 0. Method return car list -> price range from A to B and + CarBodyType
     * 1. Price from A
     * 2. Price to B
     * 3. CarBodyType for example SEDAN or HATCHBACK or COMBI
     */

    private void option3() {
        BigDecimal lowerPrice = userDataService.lowerPrice();
        BigDecimal higherPrice = userDataService.higherPrice(lowerPrice);
        CarBodyType carBodyType = userDataService.carBodyType();
        carService.priceRangeAndCarBodyType(lowerPrice, higherPrice, carBodyType).forEach(System.out::println);
    }

    /**
     * Option 4
     * 0. Method return car list sorted aplhabetically && with EngineType
     * 1. EngineType
     */
    private void option4() {
        EngineType engineType = userDataService.engineType();
        carService.sortedAlphabeticallyEngineType(engineType).forEach(System.out::println);
    }

    /**
     * Option 5
     * 0. Method return statistics:
     * a) Price
     * b) Mileage
     * c) Engine Power
     */

    private void option5() {
        int statsOption = userDataService.statisticsOption();
        carService.showStatistics(statsOption);
    }

    /**
     * Option 6
     * Method return Map
     * Key -> Car
     * Value -> Mileage
     */

    private void option6() {
        carService.carsGroupedByMileage().forEach((k, v) -> System.out.println(k + " -> " + v + " miles"));
    }//end option 6

    /**
     * Option 7
     * Method return Map
     * k -> TyreType
     * v -> List
     * Sorted descedning by the number of items in the collection
     */

    private void option7() {
        carService.carsGroupedByTyreType().forEach((k, v) -> System.out.println(k + " -> " + v + " miles"));
    }

    /**
     * Option 8
     * Return cars with input components
     * sorted alphabetically
     */
    private void option8() {
        List<String> components = new ArrayList<>(Arrays.asList("FOG LIGHTS"));
        //int size = userDataService.sizeOfComponents();
        // List<String> components1 = userDataService.components(size);
        carService.carsWithInputComponents(components).forEach(System.out::println);
    }
}

