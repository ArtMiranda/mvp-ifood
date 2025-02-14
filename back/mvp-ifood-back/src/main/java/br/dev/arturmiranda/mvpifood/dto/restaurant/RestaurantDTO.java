package br.dev.arturmiranda.mvpifood.dto.restaurant;

import br.dev.arturmiranda.mvpifood.entity.restaurant.Restaurant;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String address;

    @NotNull
    private String banner;

    @NotNull
    private String picture;

    @NotNull
    @DecimalMax(value = "5.0")
    @DecimalMin(value = "0.1")
    private Double rating;

    @NotNull
    @Size(min = 10, max = 11)
    private String telephone;

    public RestaurantDTO(Restaurant restaurant){
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.description = restaurant.getDescription();
        this.address = restaurant.getAddress();
        this.banner = restaurant.getBanner();
        this.picture = restaurant.getPicture();
        this.rating = restaurant.getRating();
        this.telephone = restaurant.getTelephone();
    }
}
