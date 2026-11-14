package electiva3.order_service.integrationLayer.product.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Integer id,
        String name,
        int stock,
        String description,
        BigDecimal price,
        String category
) {
}
