package electiva3.user_service.businessLayer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseDTO {

    @NotNull(message = "El id no puede ser nulo")
    private Integer id;

    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastName;

    @NotNull(message = "La edad no puede estar vacía")
    private Integer age;

}
