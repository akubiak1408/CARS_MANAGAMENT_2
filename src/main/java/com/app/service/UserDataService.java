package com.app.service;

import com.app.enums.CarBodyType;
import com.app.enums.EngineType;
import com.app.enums.SortType;
import com.app.exceptions.MyException;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class UserDataService {

    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    /**
     * How many cars you want to genrate to jsonFile
     *
     * @return
     */
    int howManyCars() {
        System.out.println("How many cars do you want generate to jsonFile?");
        System.out.print("Enter value <1,20>: ");
        String howMany;
        do {
            howMany = scanner.next();
            wrongNumberTryAgain(isHowManyCarsCorrect(howMany));
        } while (!isHowManyCarsCorrect(howMany));
        return Integer.parseInt(howMany);
    }

    private boolean isHowManyCarsCorrect(String howMany) {
        return howMany.matches("[1-9]|1[0-9]|20");
    }
    //End of methods - HOW MANY CARS

    /**
     * 1. Which option from 0 to 9
     * 2. showOptions() - show avaliable options
     * 3. isWhichOptionCorrect() - validation of input string
     *
     * @return
     */
    int whichOption() {
        showOptions();
        String option;
        do {
            option = scanner.next();
            wrongNumberTryAgain(isWhichOptionCorrect(option));

        } while (!isWhichOptionCorrect(option));
        return Integer.parseInt(option);
    }


    private boolean isWhichOptionCorrect(String option) {
        return option.matches("[0-9]");
    }
    // End methods - WHICH OPTION


    /**
     * Option 2
     * 0. void show proposition
     * 1. getSortType - return sortType
     * 2. ascendingOrDescending - return ascending or descending
     */

    SortType getSortType() {
        showSortTypes();
        String option;

        do {
            option = scanner.next();
            wrongNumberTryAgain(option.matches("[1-3]"));
        } while (!option.matches("[1-3]"));

        return SortType.values()[Integer.parseInt(option) - 1];
    }

    boolean ascendingOrDescending() {
        showSortAscendingOrDescending();
        switch (checkIsAscendingOrDescending()) {
            case 2:
                return false;
            case 1:
            default:
                return true;
        }
    }

    private int checkIsAscendingOrDescending() {

        String option;
        do {
            option = scanner.next();
            wrongNumberTryAgain(option.matches("[1-2]"));
        } while (!option.matches("[1-2]"));

        return Integer.parseInt(option);
    }


    private void showSortAscendingOrDescending() {
        System.out.println();
        System.out.println("1 - Ascending");
        System.out.println("2 - Descending");
        System.out.print("Which option: ");
    }

    private void showSortTypes() {
        System.out.println();
        System.out.println("Sort options");
        System.out.println("1 - COMPONENTS");
        System.out.println("2 - ENGINE POWER");
        System.out.println("3 - WHEEL SIZE");
        System.out.print("Which option?: ");
    }
    //END void option2()

    /**
     * Option 3
     * 1. Return price A (range from lowerPrice to Higher)
     * 2. Return prcie B(range from lowerPrice to Higher)
     * 3. Return CarBodyType (SEDAN / HATCHBACK / COMBI)
     */
    CarBodyType carBodyType() {
        showCarBodyTypes();
        String carBodyOption;
        do {
            carBodyOption = scanner.next();
            wrongNumberTryAgain(isCarBodyTypeCorrect(carBodyOption));
        } while (!isCarBodyTypeCorrect(carBodyOption));

        return CarBodyType.values()[Integer.parseInt(carBodyOption) - 1];
    }

    private boolean isCarBodyTypeCorrect(String carBodyOption) {
        return carBodyOption.matches("[1-3]");
    }

    BigDecimal lowerPrice() {
        System.out.println("Lower price [1000, 100 000]");
        System.out.print("Enter price: ");

        String price;
        do {
            price = scanner.next();
            wrongNumberTryAgain(isPriceCorrect(price));
        } while (!isPriceCorrect(price));

        return new BigDecimal(price);
    }

    BigDecimal higherPrice(BigDecimal lowerPrice) {
        System.out.println("Higher price [" + lowerPrice + ", 100 000]");
        System.out.print("Enter price: ");

        String hPrice;
        BigDecimal higherPrice;
        do {
            hPrice = scanner.next();
            wrongNumberTryAgain(isPriceCorrect(hPrice));
            higherPrice = new BigDecimal(hPrice);
            if (!isHigherPriceGreaterThanLower(lowerPrice, higherPrice)) {
                System.out.println("Your higher Price is lower than LowerPrice, try again");
                System.out.print("Enter correct price: ");
            }
        } while (!isPriceCorrect(hPrice) || !isHigherPriceGreaterThanLower(lowerPrice, higherPrice));

        return higherPrice;
    }

    private boolean isPriceCorrect(String price) {
        return price.matches("[1-9][0-9]{3,4}|100000");
    }

    private boolean isHigherPriceGreaterThanLower(BigDecimal lowerPrice, BigDecimal higherPrice) {
        return higherPrice.compareTo(lowerPrice) > 0;
    }

    //End Option 3

    /**
     * Option 4
     * 0. Method return car list sorted aplhabetically && with EngineType
     * 1. EngineType
     */

    EngineType engineType() {
        showEngineTypes();
        String engineTypeOption;
        do {
            engineTypeOption = scanner.next();
            wrongNumberTryAgain(isEngineTypeCorrect(engineTypeOption));
        } while (!isEngineTypeCorrect(engineTypeOption));
        return EngineType.values()[Integer.parseInt(engineTypeOption) - 1];
    }

    private void showEngineTypes() {
        System.out.println("1 - DIESEL");
        System.out.println("2 - GASOLINE");
        System.out.println("3 - LPG");
        System.out.print("Which option?: ");
    }

    private boolean isEngineTypeCorrect(String eType) {
        return eType.matches("[1-3]");
    }
    //End Option 4

    private void showCarBodyTypes() {
        System.out.println("1 - SEDAN");
        System.out.println("2 - HATCHBACK");
        System.out.println("3 - COMBI");
    }


    /**
     * 1. return int - > which statistic option
     * 2. isStatisticCorrect = is input number is correct
     * 3. showOprionForStatistics -> show all options
     *
     * @return
     */

    int statisticsOption() {
        showOptionForStatistics();
        String stats;
        do {
            stats = scanner.next();
            wrongNumberTryAgain(isStatisticCorrect(stats));
        } while (!isStatisticCorrect(stats));

        return Integer.parseInt(stats);
    }

    private boolean isStatisticCorrect(String statistics) {
        return statistics.matches("[1-4]");
    }

    private void showOptionForStatistics() {
        System.out.println("Show statistics");
        System.out.println("1 - by Price");
        System.out.println("2 - by Mileage");
        System.out.println("3 - by Engine Power");
        System.out.println("4 - for all of them");
    }

    //End Option 5

    /**
     * Option 8
     * List of components
     */
    List<String> components(int size) {
        ArrayList components = new ArrayList();
        System.out.println("Add components " + size + " to list ");
        for (int i = 0; i < size; i++) {
            System.out.println("Add component to list" + " [" + (size - i) + "]");
            System.out.print("-> ");
            components.add(scanner.next());
        }
        return components;
    }

    int sizeOfComponents() {
        System.out.println("List of components size [1,9]");
        System.out.print("Enter size: ");
        String size;
        do {
            size = scanner.next();
            wrongNumberTryAgain(isSizeCorrect(size));
        } while (!isSizeCorrect(size));
        return Integer.parseInt(size);
    }

    private boolean isSizeCorrect(String s) {
        return s.matches("[1-9]");
    }

    /*
    System.out.println(Wrong number, try again)
     */
    private void wrongNumberTryAgain(boolean value) {
        if (!value) {
            throw new MyException("VALID INPUT, TRY AGAIN");
        }
    }

    private void showOptions() {
        System.out.println();
        System.out.println("1 - Show all cars");
        System.out.println("2 - Sort cars collection");
        System.out.println("3 - price range and with carBodyType");
        System.out.println("4 - sort alphabetically and with EngineType");
        System.out.println("5 - show statistics");
        System.out.println("6 - group cars by mileage");
        System.out.println("7 - group cars by TyreType");
        System.out.println("8 - cars with components");
        System.out.println("9 - If you want to generate new Json Files (with cars ofcourse)");
        System.out.println("0 - End program");
        System.out.print("Which option?: ");
    }


}
