package com.example.qlks;

import java.io.Serializable;

public class MinhAnh_0909 implements Serializable {
    private  int Id;
    private  String name;
    private int roomId;
    private int day;
    private int price;

    public MinhAnh_0909(){

    }
    public MinhAnh_0909( String name, int roomId, int price, int day){
        this.name = name;
        this.roomId = roomId;
        this.price = price;
        this.day = day;
    }
    public MinhAnh_0909(int Id, String name, int roomId, int price, int day){
        this.Id = Id;
        this.name = name;
        this.roomId = roomId;
        this.price = price;
        this.day = day;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int tongTien(){
        return this.price * this.day;
    }

    @Override
    public String toString(){
        return "Id=" +Id+
                ", Name=" + name +
                ",Room Id=" + roomId +
                ", Price=" + price +
                ", Day=" + day;
    }
}
