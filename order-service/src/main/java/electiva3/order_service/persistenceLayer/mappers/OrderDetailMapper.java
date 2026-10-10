package electiva3.order_service.persistenceLayer.mappers;

import electiva3.order_service.businessLayer.dto.OrderDetailResponseDTO;
import electiva3.order_service.persistenceLayer.entities.OrderDetailEntity;

public final class OrderDetailMapper {

    private OrderDetailMapper() {
    }

    public static OrderDetailResponseDTO toDTO(OrderDetailEntity entity) {
        if (entity == null) return null;

        OrderDetailResponseDTO dto = new OrderDetailResponseDTO();
        dto.setId(entity.getId());
        dto.setIdOrder(entity.getOrder() == null ? null : entity.getOrder().getId());
        dto.setIdProduct(entity.getIdProduct());
        dto.setUnitaryCurrentPrice(entity.getUnitaryCurrentPrice());
        dto.setQuantity(entity.getQuantity());
        dto.setSubtotal(entity.getSubtotal());
        return dto;
    }
}
