package com.example.common;

import java.util.List;

public class Car {
    private List<String> Cars;
    private boolean open;
    private String address;
    private String name;

    public Car() {
    }

    public List<String> getCars() {
        return Cars;
    }

    public void setCars(List<String> cars) {
        this.Cars = cars;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Car{" +
                "Cars=" + Cars +
                ", open=" + open +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
