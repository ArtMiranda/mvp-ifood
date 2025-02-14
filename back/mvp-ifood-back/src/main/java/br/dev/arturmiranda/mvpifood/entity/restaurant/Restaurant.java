package br.dev.arturmiranda.mvpifood.entity.restaurant;

import br.dev.arturmiranda.mvpifood.dto.restaurant.RestaurantDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "restaurant_id")
    private UUID restaurantId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "banner", columnDefinition = "TEXT")
    private String banner;

    @Column(name = "picture", columnDefinition = "TEXT")
    private String picture;

    @Column(name = "rating", columnDefinition = "NUMERIC")
    private Double rating;

    @Column(name = "telephone")
    private String telephone;


    public Restaurant(RestaurantDTO restaurantDTO){
        this.name = restaurantDTO.getName();
        this.restaurantId = restaurantDTO.getId();
        this.description = restaurantDTO.getDescription();
        this.address = restaurantDTO.getAddress();
        this.banner = restaurantDTO.getBanner();
        this.picture = restaurantDTO.getPicture();
        this.rating = restaurantDTO.getRating();
        this.telephone = restaurantDTO.getTelephone();
    }
}
