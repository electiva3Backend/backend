package electiva3.order_service.businessLayer.dto;

import electiva3.order_service.persistenceLayer.entities.OrderEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailResponseDTO {

    @NotNull(message = "Id de detalle de orden obligatorio.")
    private Long id;

    @NotNull(message = "Id de orden obligatorio.")
    private Long idOrder;

    @NotNull(message = "Id de producto obligatorio.")
    private Integer idProduct;

    @NotNull(message = "Precio actual del producto obligatorio.")
    private Double unitaryCurrentPrice;

    @NotNull(message = "Cantidad de producto obligatorio.")
    private Integer quantity;

    @NotNull(message = "Subtotal obligatorio.")
    private Double subtotal;

}
