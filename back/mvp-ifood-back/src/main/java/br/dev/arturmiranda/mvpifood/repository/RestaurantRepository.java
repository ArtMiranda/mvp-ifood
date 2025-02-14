package br.dev.arturmiranda.mvpifood.repository;

import br.dev.arturmiranda.mvpifood.dto.restaurant.RestaurantDTO;
import br.dev.arturmiranda.mvpifood.entity.restaurant.Restaurant;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    boolean existsByName(@NotNull String name);

    @Query("SELECT r FROM Restaurant r WHERE " +
            "(:query IS NULL OR :query = '' OR LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<RestaurantDTO> searchByNameOrDescription(String query, Pageable pageable);

    @Query("SELECT r FROM Restaurant r ORDER BY RANDOM()")
    Page<Restaurant> getRecommendations(Pageable pageable);
}
