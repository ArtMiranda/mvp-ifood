package br.dev.arturmiranda.mvpifood.entity.product.enums;

public enum ProductType {
    DISH ("Dish"),
    DRINK ("Drink");

    private final String name;

    ProductType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
