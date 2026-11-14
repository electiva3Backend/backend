package electiva3.product_service.businessLayer.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "Debe tener máximo 100 caracteres")
    private String name;

    @Min(value = 0, message = "El stock no puede ser menor a 0")
    private int stock;

    @Size(max = 250, message = "Debe tener máximo 250 caracteres")
    private String description;

    @NotNull(message = "El precio no puede ser nulo")
    @Min(value = 0, message = "El precio no puede ser menor a 0")
    private BigDecimal price;

    @Size(max = 100, message = "Debe tener máximo 100 caracteres")
    private String category;

}
