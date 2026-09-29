package electiva3.order_service.businessLayer.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailCreateDTO {

    @NotNull(message = "Id de producto obligatorio.")
    private Integer idProduct;

    @NotNull(message = "Cantidad de producto obligatoria.")
    private Integer quantity;

}
