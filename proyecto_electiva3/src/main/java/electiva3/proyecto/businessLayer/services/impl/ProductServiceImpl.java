package electiva3.proyecto.businessLayer.services.impl;

import electiva3.proyecto.businessLayer.dto.ProductCreateDTO;
import electiva3.proyecto.businessLayer.dto.ProductResponseDTO;
import electiva3.proyecto.businessLayer.services.IProductService;

import electiva3.proyecto.persistenceLayer.entities.ProductEntity;
import electiva3.proyecto.persistenceLayer.repositories.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepo;

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductCreateDTO createDto) {
        normalizeDto(createDto);

        ProductEntity prod = toEntity(createDto);
        productRepo.save(prod);

        ProductResponseDTO responseDto = toResponseDto(prod);
        return responseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Integer id) {
        if (id == null) throw new RuntimeException("El id es obligatorio");

        ProductEntity prod = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el producto con id: " + id));

        ProductResponseDTO responseDTO = toResponseDto(prod);
        return responseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductByName(String name) {
        if (name == null) throw new RuntimeException("El nombre es obligatorio");

        ProductEntity prod = productRepo.findByName(name)
                .orElseThrow(() -> new RuntimeException("No se encontró el producto con nombre: " + name));

        ProductResponseDTO responseDTO = toResponseDto(prod);
        return responseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        List<ProductEntity> entities = productRepo.findAll();
        List<ProductResponseDTO> dtos = toDtoList(entities);
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProductsByCategory(String category) {
        if (category == null) throw new RuntimeException("La categoria es obligatoria");

        List<ProductEntity> entities = productRepo.findAllByCategory(category);
        List<ProductResponseDTO> dtos = toDtoList(entities);
        return dtos;
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Integer id, ProductCreateDTO updateDto) {
        if (id == null) throw new RuntimeException("El id es obligatorio");

        normalizeDto(updateDto);

        return productRepo.findById(id)
                .map(existingEntity -> {
                    updateEntityFromDTO(existingEntity, updateDto);

                    ProductEntity updatedEntity = productRepo.save(existingEntity);
                    return toResponseDto(updatedEntity);
                })
                .orElseThrow(() -> new RuntimeException("No se pudo actualizar"));
    }

    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        if (id == null) throw new RuntimeException("El id es obligatorio");

        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);

        } else {
            throw new RuntimeException("No se pudo eliminar producto: " + id);
        }
    }

    private void normalizeDto(ProductCreateDTO dto) {
        dto.setName(dto.getName().trim().toLowerCase());
        dto.setCategory(dto.getCategory().trim().toLowerCase());
    }

    private ProductEntity toEntity(ProductCreateDTO dto) {
        ProductEntity prod = new ProductEntity();
        prod.setName(dto.getName());
        prod.setCategory(dto.getCategory());
        prod.setPrice(dto.getPrice());
        prod.setStock(dto.getStock());
        prod.setDescription(dto.getDescription());

        return prod;
    }

    private ProductResponseDTO toResponseDto(ProductEntity entity) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCategory(entity.getCategory());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        dto.setDescription(entity.getDescription());

        return dto;
    }

    private List<ProductResponseDTO> toDtoList(List<ProductEntity> list) {
        if (list == null) return List.of();
        return list.stream()
                .map(productEntity -> toResponseDto(productEntity))
                //.map(this::toResponseDto) //Hace lo mismo y es más limpio
                .toList();
    }

    private void updateEntityFromDTO(ProductEntity entity, ProductCreateDTO dto) {

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
