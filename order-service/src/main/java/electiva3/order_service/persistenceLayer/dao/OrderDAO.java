package electiva3.order_service.persistenceLayer.dao;

import electiva3.order_service.persistenceLayer.entities.OrderEntity;
import electiva3.order_service.persistenceLayer.enums.OrderStatus;
import electiva3.order_service.persistenceLayer.repositories.IOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrderDAO {

    private final IOrderRepository orderRepository;

    public OrderEntity save(OrderEntity entity) {
        return orderRepository.save(entity);
    }

    public OrderEntity saveAndFlush(OrderEntity entity) {
        return orderRepository.saveAndFlush(entity);
    }

    public Optional<OrderEntity> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<OrderEntity> findAll() {
        return orderRepository.findAll();
    }

    public List<OrderEntity> findAllByIdUser(Integer id) {
        return orderRepository.findAllByIdUser(id);
    }

    public List<OrderEntity> findAllByStatus(OrderStatus status) {
        return orderRepository.findAllByStatus(status);
    }

    public List<OrderEntity> findAllByIdUserAndStatus(Integer idUser, OrderStatus status) {
        return orderRepository.findAllByIdUserAndStatus(idUser, status);
    }

    public void cancel(Long id) {
        orderRepository.deleteById(id);
    }

}
