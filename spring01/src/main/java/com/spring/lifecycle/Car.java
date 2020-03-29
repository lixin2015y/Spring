package com.spring.lifecycle;

public class Car {

    private String brand;

    private String crop;

    private Double price;

    public Car() {
        System.out.println("调用构造器");
    }

    public Car(String brand, String crop, Double price) {
        this.brand = brand;
        this.crop = crop;
        this.price = price;
    }

    public void init(){
        System.out.println("调用初始化方法");
    }

    public void destroy(){
        System.out.println("调用销毁方法");
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {

        System.out.println("调用set方法为属性赋值");
        this.brand = brand;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", crop='" + crop + '\'' +
                ", price=" + price +
                '}';
    }


}
