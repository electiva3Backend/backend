package electiva3.product_service.persistenceLayer.mappers;

import electiva3.product_service.businessLayer.dto.ProductCreateDTO;
import electiva3.product_service.businessLayer.dto.ProductResponseDTO;
import electiva3.product_service.persistenceLayer.entities.ProductEntity;

import java.util.List;

public final class ProductMapper {

    private ProductMapper() {

    }

    public static ProductEntity toEntity(ProductCreateDTO dto) {

        if (dto == null) return null;

        ProductEntity prod = new ProductEntity();
        prod.setName(dto.getName());
        prod.setCategory(dto.getCategory());
        prod.setPrice(dto.getPrice());
        prod.setStock(dto.getStock());
        prod.setDescription(dto.getDescription());

        return prod;
    }

    public static ProductResponseDTO toResponseDTO(ProductEntity entity) {

        if (entity == null) return null;

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCategory(entity.getCategory());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        dto.setDescription(entity.getDescription());

        return dto;
    }

    public static List<ProductResponseDTO> toDTOList(List<ProductEntity> list) {
        if (list == null) return List.of();
        return list.stream()
                .map(productEntity -> toResponseDTO(productEntity))
                //.map(this::toResponseDto) //Hace lo mismo y es más limpio
                .toList();
    }

    public static void updateEntityFromDTO(ProductEntity entity, ProductCreateDTO dto) {

        if (entity == null || dto == null) {
            throw new RuntimeException("Invalid data");
        }

        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getCategory() != null) entity.setCategory(dto.getCategory());
        if (dto.getStock() != entity.getStock()) entity.setStock(dto.getStock());
        if (dto.getPrice() != entity.getPrice()) entity.setPrice(dto.getPrice());

    }

}
