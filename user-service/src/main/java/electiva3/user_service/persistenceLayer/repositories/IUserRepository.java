package electiva3.user_service.persistenceLayer.repositories;

import electiva3.user_service.persistenceLayer.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findEntityByEmail(String email);

}
