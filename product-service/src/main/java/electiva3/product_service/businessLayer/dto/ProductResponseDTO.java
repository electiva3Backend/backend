package electiva3.product_service.businessLayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    private Integer id;
    private String name;
    private int stock;
    private String description;
    private BigDecimal price;
    private String category;

}
