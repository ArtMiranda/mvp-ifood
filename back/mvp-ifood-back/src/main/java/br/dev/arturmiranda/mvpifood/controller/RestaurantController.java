package br.dev.arturmiranda.mvpifood.controller;

import br.dev.arturmiranda.mvpifood.dto.restaurant.RestaurantDTO;
import br.dev.arturmiranda.mvpifood.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/restaurant")
@Tag(name = "Restaurant", description = "Restaurant Controller for the application")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/create")
    @Operation(summary = "Create a new restaurant", description = "Create a new restaurant in the application")
    public ResponseEntity<RestaurantDTO> create(@Valid @RequestBody RestaurantDTO restaurant) {
        return ResponseEntity.ok(restaurantService.save(restaurant));
    }

    @GetMapping("/restaurant/{id}")
    @Operation(summary = "Get restaurant by id", description = "Get restaurant by id in the application")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable UUID id) {
        return ResponseEntity.ok(restaurantService.findById(id));
    }

    @GetMapping("/restaurant/all")
    @Operation(summary = "Get all restaurants", description = "Get all restaurants in the application")
    public ResponseEntity<Page<RestaurantDTO>> getAllRestaurants(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(restaurantService.findAll(page, size));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update restaurant by id", description = "Update restaurant by id in the application")
    public ResponseEntity<RestaurantDTO> updateRestaurantById(@PathVariable UUID id, @Valid @RequestBody RestaurantDTO restaurant) {
        return ResponseEntity.ok(restaurantService.update(id, restaurant));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete restaurant by id", description = "Delete restaurant by id in the application")
    public ResponseEntity<HashMap> deleteRestaurantById(@PathVariable UUID id) {
        boolean success = restaurantService.delete(id);
        if(success){
            HashMap<String, String> response = new HashMap<>();
            response.put("success", "Restaurant deleted successfully");
            return ResponseEntity.ok(response);
        }
        HashMap<String, String> response = new HashMap<>();
        response.put("error", "Restaurant not found");
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Search restaurants", description = "Search restaurants by name or description with pagination")
    public ResponseEntity<Page<RestaurantDTO>> searchRestaurants(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(restaurantService.searchRestaurants(query, page, size));
    }

    @PostMapping("/updatePicture/{id}")
    @Operation(summary = "Update restaurant picture", description = "Update restaurant picture by id in the application")
    public ResponseEntity<RestaurantDTO> updateRestaurantPicture(@PathVariable UUID id, @RequestBody String picture) {
        return ResponseEntity.ok(restaurantService.updatePicture(id, picture));
    }

    @PostMapping("/updateBanner/{id}")
    @Operation(summary = "Update restaurant banner", description = "Update restaurant banner by id in the application")
    public ResponseEntity<RestaurantDTO> updateRestaurantBanner(@PathVariable UUID id, @RequestBody String banner) {
        return ResponseEntity.ok(restaurantService.updateBanner(id, banner));
    }

    @GetMapping("/recommendations")
    @Operation(summary = "Get recommendations", description = "Get restaurant recommendations for the user")
    public ResponseEntity<Page<RestaurantDTO>> getRecommendations(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size) {
        return ResponseEntity.ok(restaurantService.getRecommendations(page, size));
    }

}
