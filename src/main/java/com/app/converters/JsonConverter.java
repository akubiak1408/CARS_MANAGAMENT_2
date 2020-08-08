package com.app.converters;

import com.app.exceptions.MyException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public abstract class JsonConverter<T> {
    private final String jsonFilename;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    /**
     * Przechowuje informacje o tym jaki aktualnie typ został wstawiony za T
     * Przechwytuje informacje o typie w Runtime
     * Mechanizm programowania uogólnionego
     */
    private final Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    protected JsonConverter(String jsonFilename) {
        this.jsonFilename = jsonFilename;
    }


    public void toJson(final T element) {
        try (FileWriter fileWriter = new FileWriter(jsonFilename)) {
            if (element == null) {
                throw new NullPointerException("Element is NULL");
            }
            gson.toJson(element, fileWriter);
        } catch (Exception e) {
            throw new MyException("TO JSON CONVERSION EXCEPTION");
        }
    }

    public Optional<T> fromJson() {
        try (FileReader fileReader = new FileReader(jsonFilename)) {
            return Optional.of(gson.fromJson(fileReader, type));
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("FROM JSON CONVERSION EXCEPTION");
        }
    }
}
