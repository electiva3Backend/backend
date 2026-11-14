package electiva3.order_service.persistenceLayer.dao;

import electiva3.order_service.persistenceLayer.entities.OrderDetailEntity;
import electiva3.order_service.persistenceLayer.repositories.IOrderDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrderDetailDAO {

    private IOrderDetailRepository orderDetailRepository;

    public OrderDetailEntity save(OrderDetailEntity entity) {
        return orderDetailRepository.save(entity);
    }

    public Optional<OrderDetailEntity> findById(Long id) {
        return orderDetailRepository.findById(id);
    }

    public List<OrderDetailEntity> findAll() {
        return orderDetailRepository.findAll();
    }

    public void delete(Long id) {
        orderDetailRepository.deleteById(id);
    }

}
