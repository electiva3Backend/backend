package electiva3.user_service.businessLayer.services.impl;

import electiva3.user_service.businessLayer.dto.AuthResponseDTO;
import electiva3.user_service.businessLayer.dto.UserRegisterDTO;
import electiva3.user_service.businessLayer.dto.UserResponseDTO;
import electiva3.user_service.businessLayer.dto.UserUpdateDTO;
import electiva3.user_service.businessLayer.services.IUserService;
import electiva3.user_service.persistenceLayer.dao.UserDAO;
import electiva3.user_service.persistenceLayer.entities.UserEntity;
import electiva3.user_service.persistenceLayer.enums.Role;
import electiva3.user_service.persistenceLayer.mappers.UserMapper;
import electiva3.user_service.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    @Override
    public UserResponseDTO create(UserRegisterDTO dto) {

        dto.setEmail(normalizeEmail(dto.getEmail()));

        Role role = Role.USER;

        return userDAO.save(dto, role, passwordEncoder.encode(dto.getPassword()));
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

    @Transactional()
    public AuthResponseDTO login(String email, String password) {
        if (email == null || password == null) {
            throw new RuntimeException("Credenciales inválidas");
        }
        String normalizedEmail = email.trim().toLowerCase();

        UserEntity entity = userDAO.findEntityByEmail(normalizedEmail)
                .orElseThrow(
                        () -> new RuntimeException("Credenciales inválidas.")
                );

        String storedPassword = entity.getPassword();

        boolean validPassword;
        if (isBcryptHash(storedPassword)) {
            validPassword = passwordEncoder.matches(password, storedPassword);
        } else {
            validPassword = storedPassword.equals(password);

            if (validPassword) {
                entity.setPassword(passwordEncoder.encode(password));
                userDAO.saveEntity(entity);
            }
        }

        if (!validPassword) {
            throw new RuntimeException("Credenciales inválidas");
        }

        UserResponseDTO user = UserMapper.toDTO(entity);
        String token = jwtService.createToken(user.getEmail(), user.getRol());

        return new AuthResponseDTO(
                token,
                "Bearer",
                jwtService.getExpirationSeconds(),
                user
        );
    }

    private boolean isBcryptHash(String value) {
        return value != null
                && (value.startsWith("$2a$")
                || value.startsWith("$2b$")
                || value.startsWith("$2y$"));
    }

}
