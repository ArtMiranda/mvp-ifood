package br.dev.arturmiranda.mvpifood.controller;

import br.dev.arturmiranda.mvpifood.dto.product.ProductDTO;
import br.dev.arturmiranda.mvpifood.entity.product.enums.ProductType;
import br.dev.arturmiranda.mvpifood.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@Tag(name = "Product", description = "Product Controller for the application")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/save")
    @Operation(summary = "Create a new product", description = "Create a new product in the application")
    public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO productDTO) throws BadRequestException {
        return ResponseEntity.ok(productService.create(productDTO));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a product", description = "Delete a product in the application")
    public ResponseEntity<HashMap> delete(@PathVariable UUID id) {
        boolean success = productService.delete(id);
        HashMap<String, String> response = new HashMap<>();
        if(success){
            response.put("success", "Product deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Product not found");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a product", description = "Update a product in the application")
    public ResponseEntity<ProductDTO> update(@PathVariable UUID id, @RequestBody ProductDTO productDTO) throws BadRequestException {
        return ResponseEntity.ok(productService.update(id, productDTO));
    }

    @GetMapping("/product/{id}")
    @Operation(summary = "Get product by id", description = "Get product by id in the application")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Search products", description = "Search products by name or description with pagination")
    public ResponseEntity<Page<ProductDTO>> searchProducts(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.searchProducts(query, page, size));
    }

    @GetMapping("/restaurant/{id}")
    @Operation(summary = "Get products by restaurant id", description = "Get products by restaurant, provide a productId to filter out a specific product")
    public ResponseEntity<Page<ProductDTO>> getProductsByRestaurantId(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) UUID productId) {
        return ResponseEntity.ok(productService.findByRestaurantId(id, page, size, productId));
    }

    @GetMapping("/recommendations")
    @Operation(summary = "Get recommendations", description = "Get recommendations for products, provide a productId to filter out a specific product")
    public ResponseEntity<Page<ProductDTO>> getRecommendations(
            @RequestParam(defaultValue = "DISH")ProductType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(required = false) UUID productId) {
        return ResponseEntity.ok(productService.getRecommendations(page, size, type, productId));
    }


}
