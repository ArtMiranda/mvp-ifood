package br.dev.arturmiranda.mvpifood.dto.product;

import br.dev.arturmiranda.mvpifood.entity.product.*;
import br.dev.arturmiranda.mvpifood.entity.product.enums.Doneness;
import br.dev.arturmiranda.mvpifood.entity.product.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private UUID id;
    private UUID restaurantId;
    private String name;
    private String description;
    private BigDecimal price;
    private ProductType type;
    private String image;
    private String allergens;
    private boolean available;
    private boolean alcoholic;
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
    private Doneness doneness;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.restaurantId = product.getRestaurantId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.type = product.getType();
        this.image = product.getImage();
        this.allergens = product.getAllergens();
        this.available = product.isAvailable();
        this.vegetarian = product.isVegetarian();
        this.vegan = product.isVegan();
        this.glutenFree = product.isGlutenFree();
        if (product instanceof Dish) {
            this.doneness = ((Dish) product).getDoneness();
        }
        if (product instanceof Drink) {
            this.alcoholic = ((Drink) product).isAlcoholic();
        }
    }

}
