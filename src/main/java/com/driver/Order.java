package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(){

    }

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
        String hr = deliveryTime.substring(0,2);
        String min = deliveryTime.substring(3,5);
        int hour = Integer.parseInt(hr);
        int minute = Integer.parseInt(min);
        this.deliveryTime = hour*60 + minute;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
