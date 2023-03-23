package com.myCompany.delivery.model;

// Main class: demonstrates the use of the pizza cafe
public class Main {
    public static void main(String[] args) {
        PizzaCafe pizzaCafe = PizzaCafe.getInstance();
        pizzaCafe.orderPizza("Margherita");
        pizzaCafe.orderPizza("Pepperoni");
        pizzaCafe.orderPizza("Hawaiian");
    }
}