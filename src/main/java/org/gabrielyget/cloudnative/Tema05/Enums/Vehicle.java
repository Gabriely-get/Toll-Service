package org.gabrielyget.cloudnative.Tema05.Enums;

import org.springframework.lang.NonNull;

public enum Vehicle {
    BUS(1.59), MOTORCYCLE(1.00), BICYCLE(0.49), TRUCK(3.59), BEETLE(2.11);

    Double price;

    Vehicle(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public Double truckPerAxis(@NonNull Integer axle) {
        return TRUCK.price*axle;
    }

}