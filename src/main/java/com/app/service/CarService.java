package com.app.service;



import com.app.converters.CarsJsonConverter;
import com.app.enums.CarBodyType;
import com.app.enums.EngineType;
import com.app.enums.SortType;
import com.app.enums.TyreType;
import com.app.exceptions.MyException;
import com.app.model.Car;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.BigIntegerSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;


public class CarService {

    private Set<Car> cars;

    CarService(String jsonFileName) {
        cars = getCarsFromJson(jsonFileName);
    }

    private Set<Car> getCarsFromJson(String jsonFileName) {
        Set<Car> carSet = new HashSet<>();

        for (int i = 0; i < getFileSize(jsonFileName); i++) {
            carSet.add(
                    new CarsJsonConverter(
                            fileNamesList(jsonFileName).get(i))
                            .fromJson()
                            .orElseThrow(() -> new MyException("CARS SERVICE JSON CONVERSION EXCEPTION")));
        }
        return cars = carSet;
    }

    private List<String> fileNamesList(String jsonFileName) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < getFileSize(jsonFileName); i++) {
            list.add(jsonFileName + "\\car" + i + ".json");
        }

        return list;
    }

    /**
     * 1. How many files are in the carsJson file. -> quantity json files = quantity cars
     *
     * @param filePath
     * @return
     */
    private static int getFileSize(String filePath) {
        File f = new File(filePath);
        File[] files = f.listFiles();
        int count = 0;
        if (files != null)
            for (int i = 0; i < files.length; i++) {
                count++;
                File file = files[i];

                if (file.isDirectory()) {
                    getFileSize(file.getAbsolutePath());
                }
            }
        return count;
    }

    /**
     * Option 1
     * 1. Show all cars which are saved in json files
     *
     * @return
     */
    @Override
    public String toString() {
        return cars
                .stream()
                .map(Car::toString)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Option 2
     * 0. Method return sorted car collection
     * 1. Sort by quantity of components
     * 2. Sort by engine power
     * 3. Sort by wheel size
     * 4. Ascending or descending
     */

    Set<Car> sort(SortType sortType, boolean ascending) {
        if (sortType == null)
            throw new MyException("SORT TYPE IS NOT CORRECT");

        switch (sortType) {
            case COMPONENTS:
                return ascending
                        ? sortByComponentsAscending()
                        : sortByComponentsDescending();
            case ENGINEPOWER:
                return ascending
                        ? sortByEnginePowerAscending()
                        : sortByEnginePowerDescending();
            case WHEELSIZE:
                return ascending
                        ? sortByWheelSizeAscending()
                        : sortByWheelSizeDescending();
        }
        return cars;
    }

    private Set<Car> sortByComponentsAscending() {
        return cars
                .stream()
                .sorted(comparing(c -> c.getCarBody().getComponents().size()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<Car> sortByComponentsDescending() {
        List<Car> carList1 = cars
                .stream()
                .sorted(comparing(c -> c.getCarBody().getComponents().size()))
                .collect(Collectors.toList());
        Collections.reverse(carList1);
        return new LinkedHashSet<>(carList1);

    }

    private Set<Car> sortByEnginePowerAscending() {
        return cars
                .stream()
                .sorted(comparing(car -> car.getEngine().getPower()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<Car> sortByEnginePowerDescending() {
        List<Car> list = cars
                .stream()
                .sorted(comparing(c -> c.getEngine().getPower()))
                .collect(Collectors.toList());
        Collections.reverse(list);
        return new LinkedHashSet<>(list);
    }

    private Set<Car> sortByWheelSizeAscending() {
        return cars
                .stream()
                .sorted(comparing(car -> car.getWheel().getSize()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<Car> sortByWheelSizeDescending() {
        List<Car> list = cars
                .stream()
                .sorted(comparing(c -> c.getWheel().getSize()))
                .collect(Collectors.toList());
        Collections.reverse(list);
        return new LinkedHashSet<>(list);
    }

    // END Option 2

    /**
     * Option 3
     * 0. Method return car list -> price range from A to B and + CarBodyType
     * 1. Price from A
     * 2. Price to B
     * 3. CarBodyType for example SEDAN or HATCHBACK or COMBI
     */

    Set<Car> priceRangeAndCarBodyType(BigDecimal lowerPrice, BigDecimal higherPrice, CarBodyType carBodyType) {

        if (lowerPrice == null)
            throw new MyException("LOWER PRICE IS NULL");

        if (higherPrice == null)
            throw new MyException("HIGHER PRICE IS NULL");

        return cars
                .stream()
                .filter(c -> c.getPrice().compareTo(lowerPrice) >= 0 && c.getPrice().compareTo(higherPrice) <= 0)
                .filter(c -> c.getCarBody().getCarBodyType() == carBodyType)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    //END Option3

    /**
     * Option 4
     * 0. Method return car list sorted aplhabetically && with EngineType
     * 1. EngineType
     */
    Set<Car> sortedAlphabeticallyEngineType(EngineType engineType) {
        return cars
                .stream()
                .filter(c -> c.getEngine().getEngineType() == engineType)
                .sorted(comparing(Car::getModel))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    /**
     * Option 5
     * 0. Method return statistics:
     * a) Price
     * b) Mileage
     * c) Engine Power
     */

    void showStatistics(int statsOption) {
        switch (statsOption) {
            case 1:
                showStatsPrice(priceSummaryStatistics());
                break;
            case 2:
                showStatsMileage(mileageSummaryStatistics());
                break;
            case 3:
                showStatsEnginePower(enginePowerStatistics());
                break;
            case 4:
                showStatsPrice(priceSummaryStatistics());
                showStatsMileage(mileageSummaryStatistics());
                showStatsEnginePower(enginePowerStatistics());
                break;
        }
    }

    private BigDecimalSummaryStatistics priceSummaryStatistics() {
        return cars
                .stream()
                .collect(Collectors2.summarizingBigDecimal(Car::getPrice));
    }

    private DoubleSummaryStatistics mileageSummaryStatistics() {
        return cars
                .stream()
                .collect(Collectors.summarizingDouble(Car::getMileage));
    }

    private BigIntegerSummaryStatistics enginePowerStatistics() {
        return cars
                .stream()
                .collect(Collectors2.summarizingBigInteger(
                        c -> BigInteger.valueOf(
                                c.getEngine().getPower())));
    }

    private void showStatsPrice(BigDecimalSummaryStatistics price) {
        System.out.println("Mileage: \nAVERAGE: " + price.getAverage());
        System.out.println("MAX: " + price.getMax());
        System.out.println("MIN: " + price.getMin());
        System.out.println();
    }

    private void showStatsMileage(DoubleSummaryStatistics mileage) {
        System.out.println("Mileage: \nAVERAGE: " + mileage.getAverage());
        System.out.println("MAX: " + mileage.getMax());
        System.out.println("MIN: " + mileage.getMin());
        System.out.println();
    }

    private void showStatsEnginePower(BigIntegerSummaryStatistics enginePower) {
        System.out.println("Mileage: \nAVERAGE: " + enginePower.getAverage());
        System.out.println("MAX: " + enginePower.getMax());
        System.out.println("MIN: " + enginePower.getMin());
        System.out.println();
    }

    //END Option 5

    /**
     * Option 6
     * Method return Map
     * Key -> Car
     * Value -> Mileage
     */

    Map<Car, Double> carsGroupedByMileage() {

        return cars.
                stream()
                .collect(Collectors.toMap(
                        k -> k,
                        Car::getMileage
                ))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));

    }
    //End Option 6

    /**
     * Option 7
     * Method return Map
     * k -> TyreType
     * v -> List<Car>
     * Sorted descedning by the number of items in the collection
     */

    Map<TyreType, List<Car>> carsGroupedByTyreType() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(c -> c.getWheel().getTyreType()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                        )
                );
    }
//END Option 7


    /**
     * Option 8
     * Return cars with input components
     * sorted alphabetically
     */

    Set<Car> carsWithInputComponents(List<String> componentsList) {
        return cars
                .stream()
                .sorted(comparing(Car::getModel))
                .filter(c -> c.getCarBody().getComponents().equals(componentsList))
                .collect(Collectors.toCollection(LinkedHashSet::new));

    }

}
