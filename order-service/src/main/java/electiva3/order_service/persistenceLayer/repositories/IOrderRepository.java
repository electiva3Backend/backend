package electiva3.order_service.persistenceLayer.repositories;

import electiva3.order_service.persistenceLayer.entities.OrderEntity;
import electiva3.order_service.persistenceLayer.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByStatus(OrderStatus status);

    List<OrderEntity> findAllByIdUser(Integer id);

    List<OrderEntity> findAllByIdUserAndStatus(Integer idUser, OrderStatus status);

}
