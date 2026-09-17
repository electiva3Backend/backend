package electiva3.user_service.persistenceLayer.mappers;

import electiva3.user_service.businessLayer.dto.UserRegisterDTO;
import electiva3.user_service.businessLayer.dto.UserResponseDTO;
import electiva3.user_service.businessLayer.dto.UserUpdateDTO;
import electiva3.user_service.persistenceLayer.entities.UserEntity;
import electiva3.user_service.persistenceLayer.enums.Role;

import java.util.List;

public final class UserMapper {

    private UserMapper() {

    }

    public static UserEntity toEntity(UserRegisterDTO dto, Role role) {
        if (dto == null || role == null) return null;

        UserEntity entity = new UserEntity();

        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setName(dto.getName());
        entity.setLastName(dto.getLastName());
        entity.setAge(dto.getAge());

        entity.setRole(role);

        return entity;
    }

    public static UserResponseDTO toDTO(UserEntity entity) {
        if (entity == null) return null;

        UserResponseDTO responseDTO = new UserResponseDTO();

        responseDTO.setId(entity.getId());
        responseDTO.setEmail(entity.getEmail());
        responseDTO.setName(entity.getName());
        responseDTO.setLastName(entity.getLastName());
        responseDTO.setAge(entity.getAge());

        return responseDTO;
    }

    public static void updateEntityFromDTO(UserUpdateDTO dto, UserEntity entity) {

        if (dto == null || entity == null) {
            throw new RuntimeException("Invalid data");
        }

        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getPassword() != null) entity.setPassword(dto.getPassword());
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getLastName() != null) entity.setLastName(dto.getLastName());
        if (dto.getAge() != null) entity.setAge(dto.getAge());
    }

    public static List<UserResponseDTO> toDTOList(List<UserEntity> list) {

        if (list == null) return List.of();

        return list
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }

}
