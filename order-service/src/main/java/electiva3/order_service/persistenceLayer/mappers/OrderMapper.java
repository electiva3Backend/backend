package electiva3.order_service.persistenceLayer.mappers;

import electiva3.order_service.businessLayer.dto.OrderCreateDTO;
import electiva3.order_service.businessLayer.dto.OrderDetailResponseDTO;
import electiva3.order_service.businessLayer.dto.OrderResponseDTO;
import electiva3.order_service.persistenceLayer.entities.OrderEntity;

import java.util.List;

public final class OrderMapper {

    private OrderMapper() {

    }

    public static OrderEntity toEntity(OrderCreateDTO dto) {
        if (dto == null) return null;

        OrderEntity entity = new OrderEntity();

        entity.setIdUser(dto.getIdUser());
        entity.setInformation(dto.getInformation());

        return entity;
    }

    public static OrderResponseDTO toDTO(OrderEntity entity) {
        if (entity == null) return null;

        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setId(entity.getId());
        dto.setInformation(entity.getInformation());
        dto.setStatus(entity.getStatus());
        dto.setIdUser(entity.getIdUser());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setTotal(entity.getTotal());

        if (entity.getOrderDetails() != null) {
            List<OrderDetailResponseDTO> details = entity.getOrderDetails()
                    .stream()
                    .map(OrderDetailMapper::toDTO)
                    .toList();
            dto.setOrderDetails(details);
        }

        return dto;
    }

    public static void updateEntityFromDTO(OrderCreateDTO dto, OrderEntity entity) {
        if (dto == null || entity == null) {
            throw new IllegalArgumentException("Mapper Invalid Data");
        }

        if (dto.getInformation() != null) entity.setInformation(dto.getInformation());
    }

    public static List<OrderResponseDTO> toDTOList(List<OrderEntity> list) {

        if (list == null) return List.of();

        return list
                .stream()
                .map(OrderMapper::toDTO)
                .toList();
    }
}
