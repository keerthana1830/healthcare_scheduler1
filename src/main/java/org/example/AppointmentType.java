package org.example;

import java.math.BigDecimal;

public class AppointmentType {
    private int typeId;
    private String name;
    private int duration;
    private BigDecimal cost;

    public AppointmentType(int typeId, String name, int duration, BigDecimal cost) {
        this.typeId = typeId;
        this.name = name;
        this.duration = duration;
        this.cost = cost;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public BigDecimal getCost() {
        return cost;
    }
}
