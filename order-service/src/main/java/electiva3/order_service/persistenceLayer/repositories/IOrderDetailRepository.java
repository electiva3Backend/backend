package electiva3.order_service.persistenceLayer.repositories;

import electiva3.order_service.persistenceLayer.entities.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {

}
