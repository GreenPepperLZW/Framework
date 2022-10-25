package com.lzw.bean;

/**
 * @author : lzw
 * @date : 2022/5/11
 * @since : 1.0
 */
public class Color {

    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Color{" +
                "car=" + car +
                '}';
    }
}
