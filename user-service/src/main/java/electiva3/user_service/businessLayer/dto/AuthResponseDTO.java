package electiva3.user_service.businessLayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private UserResponseDTO user;
}