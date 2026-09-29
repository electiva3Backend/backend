package electiva3.order_service.businessLayer.services;

import electiva3.order_service.businessLayer.dto.OrderCreateDTO;
import electiva3.order_service.businessLayer.dto.OrderResponseDTO;
import electiva3.order_service.persistenceLayer.entities.OrderEntity;
import electiva3.order_service.persistenceLayer.enums.OrderStatus;

import java.util.List;

public interface IOrderService {

    OrderResponseDTO create(OrderCreateDTO dto);

    OrderResponseDTO findById(Long id);

    List<OrderResponseDTO> findAll();

    List<OrderResponseDTO> findAllByIdUser(Integer id);

    List<OrderResponseDTO> findAllByStatus(String status);

    List<OrderResponseDTO> findAllByIdUserAndStatus(Integer id, String status);

    OrderResponseDTO cancel(Long id);

}
