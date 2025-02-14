package br.dev.arturmiranda.mvpifood.service;

import br.dev.arturmiranda.mvpifood.dto.restaurant.RestaurantDTO;
import br.dev.arturmiranda.mvpifood.entity.restaurant.Restaurant;
import br.dev.arturmiranda.mvpifood.repository.RestaurantRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantDTO save(RestaurantDTO restaurant) {
        if(restaurantRepository.existsByName(restaurant.getName())) {
            throw new EntityExistsException("Restaurant already exists");
        }
        return new RestaurantDTO(restaurantRepository.save(new Restaurant(restaurant)));
    }

    public RestaurantDTO findById(UUID id) {
        return new RestaurantDTO(restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found")));
    }

    public boolean existsById(UUID id) {
        return restaurantRepository.existsById(id);
    }

    public boolean delete(UUID id) {
        if(restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
            return true;
        }
        return false;
    }

   public RestaurantDTO update(UUID id, RestaurantDTO restaurant) {
        if(restaurantRepository.existsById(id)) {
            Restaurant updatedRestaurant = new Restaurant(restaurant);
            updatedRestaurant.setId(id);
            return new RestaurantDTO(restaurantRepository.save(updatedRestaurant));
        }
        throw new EntityNotFoundException("Restaurant not found");
    }

    public Page<RestaurantDTO> findAll(int page, int size) {
        return restaurantRepository.findAll(PageRequest.of(page, size)).map(RestaurantDTO::new);
    }

    public Page<RestaurantDTO> searchRestaurants(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return restaurantRepository.searchByNameOrDescription(query, pageable);
    }


    public Page<RestaurantDTO> getRecommendations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return restaurantRepository.getRecommendations(pageable).map(RestaurantDTO::new);
    }
}
