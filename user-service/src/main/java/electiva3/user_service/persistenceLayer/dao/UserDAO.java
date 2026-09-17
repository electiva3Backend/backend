package electiva3.user_service.persistenceLayer.dao;

import electiva3.user_service.businessLayer.dto.UserRegisterDTO;
import electiva3.user_service.businessLayer.dto.UserResponseDTO;
import electiva3.user_service.businessLayer.dto.UserUpdateDTO;
import electiva3.user_service.persistenceLayer.entities.UserEntity;
import electiva3.user_service.persistenceLayer.enums.Role;
import electiva3.user_service.persistenceLayer.mappers.UserMapper;
import electiva3.user_service.persistenceLayer.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDAO {

    private final IUserRepository userRepository;

    public UserResponseDTO save(UserRegisterDTO dto) {
        Role role = Role.USER;

        UserEntity entity = UserMapper.toEntity(dto, role);
        UserEntity savedEntity = userRepository.save(entity);

        return UserMapper.toDTO(savedEntity);
    }

    public Optional<UserResponseDTO> findById(Integer id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO);
    }

    public List<UserResponseDTO> findAll() {
        List<UserEntity> entities = userRepository.findAll();
        return UserMapper.toDTOList(entities);
    }

    public Optional<UserResponseDTO> update(UserUpdateDTO dto, Integer id) {
        return userRepository.findById(id)
                .map(existingEntity -> {
                    UserMapper.updateEntityFromDTO(dto, existingEntity);

                    userRepository.save(existingEntity);
                    return UserMapper.toDTO(existingEntity);
                });
    }

    public boolean delete(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<UserEntity> findEntityByEmail(String email) {
        return userRepository.findEntityByEmail(email);
    }

}
