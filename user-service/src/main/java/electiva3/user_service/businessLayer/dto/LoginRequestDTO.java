package electiva3.user_service.businessLayer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "El email no puede estar vacío")
    private  String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private  String password;

}
