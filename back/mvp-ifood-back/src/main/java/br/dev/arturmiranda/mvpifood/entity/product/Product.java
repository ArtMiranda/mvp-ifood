package br.dev.arturmiranda.mvpifood.entity.product;

import br.dev.arturmiranda.mvpifood.dto.product.ProductDTO;
import br.dev.arturmiranda.mvpifood.entity.product.enums.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @Column(name = "restaurant_id")
    private UUID restaurantId;

    @Column(name = "type", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "available")
    private boolean available = true;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @Column(name = "vegetarian")
    private boolean vegetarian;

    @Column(name = "vegan")
    private boolean vegan;

    @Column(name = "allergens")
    private String allergens;

    @Column(name = "gluten_free")
    private boolean glutenFree;

    public Product(ProductDTO dto) {
        this.name = dto.getName();
        this.restaurantId = dto.getRestaurantId();
        this.description = dto.getDescription();
        this.price = dto.getPrice();
        this.vegetarian = dto.isVegetarian();
        this.image = dto.getImage();
        this.vegan = dto.isVegan();
        this.allergens = dto.getAllergens();
        this.glutenFree = dto.isGlutenFree();
        this.available = dto.isAvailable();
        this.type = dto.getType();
    }

}
