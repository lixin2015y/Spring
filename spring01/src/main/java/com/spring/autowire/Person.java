package com.spring.autowire;

public class Person {

    private Car car;

    private Address address;

    private String name;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
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
        return "Person{" +
                "car=" + car +
                ", address=" + address +
                ", name='" + name + '\'' +
                '}';
    }
}
