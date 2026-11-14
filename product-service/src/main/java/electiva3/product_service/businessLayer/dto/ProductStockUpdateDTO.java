package electiva3.product_service.businessLayer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductStockUpdateDTO {

    private Integer id;
    private int quantity;
}
