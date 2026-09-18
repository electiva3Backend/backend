package electiva3.user_service.businessLayer.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateDTO {

    //Permite null, en caso de que no se envíe, el mapper válida que sea diferente de null para actualizar
    @Email(message = "Formato inválido")
    private String email;

    //Permite null, en caso de que no se envíe, el mapper válida que sea diferente de null para actualizar
    //Lookahead (condición negativa) No permite campo vacío ""
    @Pattern(regexp = "^(?!\\s*$).{2,}$", message = "La contraseña debe tener al menos 2 caracteres y no estar vacía")
    private String password;

    @Pattern(regexp = "^(?!\\s*$).{2,}$", message = "El nombre debe tener al menos 2 caracteres y no estar vacío")
    private String name;

    @Pattern(regexp = "^(?!\\s*$).{2,}$", message = "El apellido debe tener al menos 2 caracteres y no estar vacío")
    private String lastName;

    // Permite que sea null (no se envía), pero si se envía, debe ser mayor o igual a 1
    @Min(value = 1, message = "La edad debe tener al menos 1 dígito y ser mayor a 0")
    @Max(value = 120, message = "La edad ingresada no es válida")
    private Integer age;

}
