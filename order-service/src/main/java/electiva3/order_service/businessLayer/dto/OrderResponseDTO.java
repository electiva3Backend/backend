package electiva3.order_service.businessLayer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import electiva3.order_service.persistenceLayer.enums.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@Data
public class OrderResponseDTO {

    @NotNull(message = "El id es obligatorio")
    private Long id;

    @NotEmpty(message = "Los detalles de orden son obligatorios")
    private List<OrderDetailResponseDTO> orderDetails;

    @NotNull(message = "El id del usuario es obligatorio")
    private Integer idUser;

    @NotNull(message = "La fecha de creación no puede estar vacía")
    private Instant createdAt;

    @NotNull(message = "La información no puede estar vacía")
    private String information;

    @NotNull(message = "El estado no puede estar vacío")
    private OrderStatus status;

    @NotNull(message = "El total no puede estar vacío")
    private BigDecimal total;

}
