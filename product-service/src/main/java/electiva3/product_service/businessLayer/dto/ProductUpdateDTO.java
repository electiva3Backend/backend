package electiva3.product_service.businessLayer.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductUpdateDTO {

    @Pattern(regexp = "^(?!\\s*$).{2,}$", message = "El nombre debe tener al menos 2 caracteres y no estar vacío")
    @Size(max = 100, message = "Debe tener máximo 100 caracteres")
    private String name;

    @Min(value = 0, message = "El stock no puede ser menor a 0")
    private int stock;

    @Pattern(regexp = "^(?!\\s*$).{2,}$", message = "La descripción debe tener al menos 2 caracteres y no estar vacía")
    @Size(max = 250, message = "Debe tener máximo 250 caracteres")
    private String description;

    @NotNull
    @Min(value = 0, message = "El precio no puede ser menor a 0")
    private BigDecimal price;

    @Pattern(regexp = "^(?!\\s*$).{2,}$", message = "La categoría debe tener al menos 2 caracteres y no estar vacía")
    @Size(max = 100, message = "Debe tener máximo 100 caracteres")
    private String category;

}
