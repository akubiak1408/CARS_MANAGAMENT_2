package com.app.model;

import com.app.enums.TyreType;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Wheel {
    private String model;
    private int size;
    private TyreType tyreType;


    public void setModel(String model) {
        this.model = model.matches("[A-Z ]+") ? model : "PIRELLI";
    }

    public void setSize(int size) {
        this.size = size > 0 ? size : 15;
    }

    @Override
    public String toString() {
        return "\nModel: " + model +
                "\nSize: " + size + " \u0278" +
                "\nTyre type: " + tyreType + "\n";
    }
}
