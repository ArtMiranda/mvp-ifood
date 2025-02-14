package br.dev.arturmiranda.mvpifood.service;

import br.dev.arturmiranda.mvpifood.dto.product.ProductDTO;
import br.dev.arturmiranda.mvpifood.entity.product.Dish;
import br.dev.arturmiranda.mvpifood.entity.product.Drink;
import br.dev.arturmiranda.mvpifood.entity.product.Product;
import br.dev.arturmiranda.mvpifood.entity.product.enums.ProductType;
import br.dev.arturmiranda.mvpifood.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RestaurantService restaurantService;

    public ProductDTO create(ProductDTO productDTO) throws BadRequestException {
        if(!restaurantService.existsById(productDTO.getRestaurantId())){
            throw new EntityNotFoundException("Restaurant not found");
        }
        if(productDTO.getType() == ProductType.DISH){
            Dish dish = new Dish(productDTO);
            return new ProductDTO(productRepository.save(dish));
        } else if (productDTO.getType() == ProductType.DRINK){
            Drink drink = new Drink(productDTO);
            return new ProductDTO(productRepository.save(drink));
        }
        else {
            throw new BadRequestException("Invalid product type");
        }
    }

    public Page<ProductDTO> searchProducts(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Product> products = productRepository.searchProducts(query, pageable);
        return products.map(ProductDTO::new);
    }

    public boolean delete(UUID id) {
        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ProductDTO update(UUID id, ProductDTO productDTO) throws BadRequestException {
        if(productRepository.existsById(id)) {
            Product updatedProduct;
            if(productDTO.getType() == ProductType.DISH){
                updatedProduct = new Dish(productDTO);
            } else if(productDTO.getType() == ProductType.DRINK){
                updatedProduct = new Drink(productDTO);
            }
            else{
                throw new BadRequestException("Invalid product type");
            }
            updatedProduct.setId(id);
            return new ProductDTO(productRepository.save(updatedProduct));
        }
        throw new EntityNotFoundException("Product not found");
    }

    public ProductDTO findById(UUID id) {
        return new ProductDTO(productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found")));
    }

    public Page<ProductDTO> getRecommendations(int page, int size, ProductType type, UUID productId) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.getRecommendations(pageable, type, productId).map(ProductDTO::new);
    }

    public Page<ProductDTO> findByRestaurantId(UUID restaurantId, int page, int size, UUID productId) {
        Pageable pageable = PageRequest.of(page, size);

        if (productId == null) {
            return productRepository.findByRestaurantIdAndAvailableTrue(restaurantId, pageable)
                    .map(ProductDTO::new);
        }
        return productRepository.findByRestaurantIdAndAvailableTrueAndIdNot(restaurantId, productId, pageable)
                .map(ProductDTO::new);
    }

}
