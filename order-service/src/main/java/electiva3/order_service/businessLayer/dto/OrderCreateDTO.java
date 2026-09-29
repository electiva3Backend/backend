package electiva3.order_service.businessLayer.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@Data
public class OrderCreateDTO {

    @NotEmpty(message = "Los detalles de la compra son obligatorios")
    @Valid // Crucial para que valide también las anotaciones dentro de OrderDetailCreateDTO
    private List<OrderDetailCreateDTO> orderDetails;

    @NotNull(message = "El id del usuario es obligatorio")
    private Integer idUser;

    private String information;


}
