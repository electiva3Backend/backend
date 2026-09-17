package electiva3.user_service.businessLayer.services;

import electiva3.user_service.businessLayer.dto.UserRegisterDTO;
import electiva3.user_service.businessLayer.dto.UserResponseDTO;
import electiva3.user_service.businessLayer.dto.UserUpdateDTO;

import java.util.List;

public interface IUserService {

    UserResponseDTO create(UserRegisterDTO dto);

    UserResponseDTO findById(Integer id);

    List<UserResponseDTO> findAll();

    UserResponseDTO update(UserUpdateDTO dto, Integer id);

    void delete(Integer id);

    UserResponseDTO login(String email, String password);
}
