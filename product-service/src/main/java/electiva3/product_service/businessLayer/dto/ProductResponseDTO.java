package electiva3.product_service.businessLayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    private Integer id;
    private String name;
    private int stock;
    private String description;
    private double price;
    private String category;

}
