package electiva3.user_service.businessLayer.services.impl;

import electiva3.user_service.businessLayer.dto.UserRegisterDTO;
import electiva3.user_service.businessLayer.dto.UserResponseDTO;
import electiva3.user_service.businessLayer.dto.UserUpdateDTO;
import electiva3.user_service.businessLayer.services.IUserService;
import electiva3.user_service.persistenceLayer.dao.UserDAO;
import electiva3.user_service.persistenceLayer.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserDAO userDAO;

    @Transactional
    @Override
    public UserResponseDTO create(UserRegisterDTO dto) {

        dto.setEmail(normalizeEmail(dto.getEmail()));

        return userDAO.save(dto);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponseDTO findById(Integer id) {
        if (id == null) throw new RuntimeException("\"El Id es obligatorio\"");

        return userDAO.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuario con id: " + id + " no encontrado")
                );
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDTO> findAll() {
        return userDAO.findAll();
    }

    @Transactional
    @Override
    public UserResponseDTO update(UserUpdateDTO dto, Integer id) {
        if (id == null) throw new RuntimeException("El Id es obligatorio");

        return userDAO.update(dto, id)
                .orElseThrow(() ->
                        new RuntimeException("No se actualizó")
                );
    }

    @Override
    public void delete(Integer id) {
        if (id == null) throw new RuntimeException("El Id es obligatorio");

        boolean deleted = userDAO.delete(id);

        if (!deleted) throw new RuntimeException("No se eliminó el usuario");
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO login(String email, String password) {
        if (email == null || password == null) {
            throw new RuntimeException("Credenciales inválidas");
        }
        String normalizedEmail = email.trim().toLowerCase();
        String normalizedPassword = password.trim();

        UserEntity entity = userDAO.findEntityByEmail(normalizedEmail)
                .orElseThrow(
                        () -> new RuntimeException("Credenciales inválidas.")
                );

        if (!entity.getPassword().equals(normalizedPassword)) {
            throw new RuntimeException("Credenciales inválidas.");
        }

        return new UserResponseDTO(
                entity.getId(),
                entity.getEmail(),
                entity.getName(),
                entity.getLastName(),
                entity.getAge()
        );
    }

}
