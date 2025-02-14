package br.dev.arturmiranda.mvpifood.entity.product;

import br.dev.arturmiranda.mvpifood.dto.product.ProductDTO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("DRINK")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Drink extends Product {
    private boolean alcoholic;

    public Drink(ProductDTO dto) {
        super(dto);
        this.alcoholic = dto.isAlcoholic();
    }
}
