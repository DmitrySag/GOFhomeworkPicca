package com.myCompany.delivery.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Component interface: defines the common interface for all pizza types
interface Pizza {
    String getDescription();
    double getPrice();
}

// Concrete component class: defines a specific pizza type
class MargheritaPizza implements Pizza {
    @Override
    public String getDescription() {
        return "Margherita Pizza";
    }

    @Override
    public double getPrice() {
        return 10.0;
    }
}

// Decorator abstract class: maintains a reference to a Pizza object and defines the common interface for all pizza decorators
abstract class PizzaDecorator implements Pizza {
    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public String getDescription() {
        return pizza.getDescription();
    }

    @Override
    public double getPrice() {
        return pizza.getPrice();
    }
}

// Concrete decorator class: adds extra cheese to the pizza
class ExtraCheeseDecorator extends PizzaDecorator {
    public ExtraCheeseDecorator(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + " (Extra Cheese)";
    }

    @Override
    public double getPrice() {
        return pizza.getPrice() + 1.5;
    }
}

// Factory interface: defines the common interface for all pizza factories
interface PizzaFactory {
    Pizza createPizza(String type);
}

// Concrete factory class: creates pizza objects
class ConcretePizzaFactory implements PizzaFactory {
    @Override
    public Pizza createPizza(String type) {
        if (type.equalsIgnoreCase("Margherita")) {
            return new MargheritaPizza();
        }
        return null;
    }
}

// Observer interface: defines the common interface for all pizza observers
interface PizzaObserver {
    void update(Pizza pizza);
}

// Subject interface: defines the common interface for all pizza subjects
interface PizzaSubject {
    void registerObserver(PizzaObserver observer);
    void removeObserver(PizzaObserver observer);
    void notifyObservers();
    void addOrder(Pizza pizza);
}

// Concrete subject class: maintains a list of observers and notifies them when a new pizza order is added
class ConcretePizzaSubject implements PizzaSubject {
    private List<PizzaObserver> observers = new ArrayList<>();
    private List<Pizza> orders = new ArrayList<>();

    @Override
    public void registerObserver(PizzaObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(PizzaObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (PizzaObserver observer : observers) {
            observer.update(orders.get(orders.size()-1));
        }
    }

    @Override
    public void addOrder(Pizza pizza) {
        orders.add(pizza);
        notifyObservers();
    }
}

// Concrete observer class: prints the new pizza order to the console
class ConcretePizzaObserver implements PizzaObserver {
    @Override
    public void update(Pizza pizza) {
        System.out.println("New order received: " + pizza.getDescription() + " for $" + pizza.getPrice());
    }
}

// Client class: orders a pizza from the pizza cafe
class PizzaCafe {
    private static PizzaCafe instance = null;
    private final PizzaFactory pizzaFactory;
    private final PizzaSubject pizzaSubject;

    private PizzaCafe() {
        pizzaFactory = new ConcretePizzaFactory();
        pizzaSubject = new ConcretePizzaSubject();
        pizzaSubject.registerObserver(new ConcretePizzaObserver());
    }

    public static PizzaCafe getInstance() {
        if (instance == null) {
            instance = new PizzaCafe();
        }
        return instance;
    }

    public void orderPizza(String type) {
        Pizza pizza = pizzaFactory.createPizza(type);
        if (pizza != null) {
            Random rand = new Random();
            if (rand.nextBoolean()) {
                pizza = new ExtraCheeseDecorator(pizza);
            }
            pizzaSubject.addOrder(pizza);
        } else {
            System.out.println("Invalid pizza type: " + type);
        }
    }
}
