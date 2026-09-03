package electiva3.proyecto.businessLayer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "Debe tener máximo 100 caracteres")
    private String name;

    private int stock;

    @Size(max = 250, message = "Debe tener máximo 250 caracteres")
    private String description;

    private double price;

    @Size(max = 100, message = "Debe tener máximo 100 caracteres")
    private String category;

}
