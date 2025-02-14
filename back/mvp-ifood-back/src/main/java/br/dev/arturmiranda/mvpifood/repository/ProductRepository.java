package br.dev.arturmiranda.mvpifood.repository;

import aj.org.objectweb.asm.commons.Remapper;
import br.dev.arturmiranda.mvpifood.dto.product.ProductDTO;
import br.dev.arturmiranda.mvpifood.dto.restaurant.RestaurantDTO;
import br.dev.arturmiranda.mvpifood.entity.product.Product;
import br.dev.arturmiranda.mvpifood.entity.product.enums.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')) AND p.available = true")
    Page<Product> searchProducts(String query, Pageable pageable);


    @Query("""
    SELECT p FROM Product p 
    WHERE p.available = true 
    AND p.type = :type 
    AND (:productId IS NULL OR p.id <> :productId)
    ORDER BY RANDOM()
""")
    Page<Product> getRecommendations(Pageable pageable, ProductType type, @Param("productId") UUID productId);

    Page<Product> findByRestaurantIdAndAvailableTrueAndIdNot(UUID restaurantId, UUID productId, Pageable pageable);

    Page<Product> findByRestaurantIdAndAvailableTrue(UUID restaurantId, Pageable pageable);
}
