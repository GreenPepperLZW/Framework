package org.lzw.bean;

import lombok.ToString;

/**
 * @author : lzw
 * @date : 2022/11/16
 * @since : 1.0
 */
@ToString
public class Pet {

    private String name;

    private Double weight;

    public Pet() {
    }

    public Pet(String name, Double weight) {
        this.name = name;
        this.weight = weight;
    }

    public Pet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
