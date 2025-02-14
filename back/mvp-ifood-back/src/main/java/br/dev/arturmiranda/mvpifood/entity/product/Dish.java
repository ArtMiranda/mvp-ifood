package br.dev.arturmiranda.mvpifood.entity.product;


import br.dev.arturmiranda.mvpifood.dto.product.ProductDTO;
import br.dev.arturmiranda.mvpifood.entity.product.enums.Doneness;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@DiscriminatorValue("DISH")
@NoArgsConstructor
@AllArgsConstructor
public class Dish extends Product{

    @Column(name = "doneness")
    @Enumerated(EnumType.STRING)
    private Doneness doneness;

    public Dish(ProductDTO dto) {
        super(dto);
        this.doneness = dto.getDoneness();
    }
}
